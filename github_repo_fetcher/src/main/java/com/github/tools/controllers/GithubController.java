package com.github.tools.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.tools.GithubRepoFetcherApplication;
import com.github.tools.entities.GithubRepository;
import com.github.tools.exceptions.UserNotFoundException;
import com.github.tools.services.GithubService;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubRepoFetcherApplication githubRepoFetcherApplication;
	private final GithubService githubService;
	
	public GithubController(GithubService githubService, GithubRepoFetcherApplication githubRepoFetcherApplication) {
		this.githubService = githubService;
		this.githubRepoFetcherApplication = githubRepoFetcherApplication;
	}
	
	@GetMapping("/repos/{username}")
	public ResponseEntity<?> getNonForksRepos(@PathVariable String username) {
		try {
			List<GithubRepository> repos = githubService.fetchNonForkRepositories(username);
			return ResponseEntity.ok(repos.stream().map(GithubRepository::toMap).collect(Collectors.toList()));
		} catch (UserNotFoundException e) {
			Map<String, Object> errorBody = Map.of(
					"status", 404,
					"message", e.getMessage()
				);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
		} catch (Exception e) {
			Map<String, Object> errorBody = Map.of(
					"status", 500,
					"message", "Unexpected error occured"
				);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
		}
	}
}
