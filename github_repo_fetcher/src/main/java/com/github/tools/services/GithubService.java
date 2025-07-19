package com.github.tools.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.github.tools.entities.Branch;
import com.github.tools.entities.GithubRepository;
import com.github.tools.exceptions.UserNotFoundException;
import com.github.tools.utils.Constants;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GithubService {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${github.token}")
	private String githubToken;
	
	private HttpEntity<Void> createAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		if (githubToken != null && !githubToken.isBlank()) {
			headers.setBearerAuth(githubToken);
		}
		return new HttpEntity<>(headers);
	}
	
	@PostConstruct
	public void warnIfUnauthenticated() {
		if (githubToken == null || githubToken.isBlank()) {
			System.out.println("Warning: No GitHub token provided. Using unauthenticated connection with rate of 60/h.");
		}
	}
	
	public List<GithubRepository> fetchNonForkRepositories(String username) throws HttpClientErrorException {
		try {
			ResponseEntity<GithubRepository[]> response = restTemplate.exchange(
					Constants.GITHUB_REPO_BASE,
					HttpMethod.GET,
					createAuthEntity(),
					GithubRepository[].class,
					username
				);
			
			GithubRepository[] repos = response.getBody();
			
			if (repos == null) return List.of();
			
			return Arrays.stream(repos)
					.filter(repo -> !repo.isFork())
					.map(repo -> {
						List<Branch> branches = fetchBranches(repo.getOwner().getLogin(), repo.getName());
						repo.setBranches(branches);
						return repo;
					})
					.collect(Collectors.toList());
			
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new UserNotFoundException(String.format("User \"%s\" not found", username));
			}
			//throw all other errors
			throw e;
		}
	}
	
	public List<Branch> fetchBranches(String owner, String repoName) {
		ResponseEntity<Branch[]> response = restTemplate.exchange(
				Constants.GITHUB_BRANCH_BASE,
				HttpMethod.GET,
				createAuthEntity(),
				Branch[].class,
				owner,
				repoName
			);
		
		if (!response.getStatusCode().is2xxSuccessful()) {
			return List.of();
		}
		
		Branch[] branches = response.getBody();
		
		if (branches == null) return List.of();
		
		return List.of(branches);
	}
}
