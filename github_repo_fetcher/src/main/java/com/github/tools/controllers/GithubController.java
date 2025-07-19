package com.github.tools.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	public Map<String, Object> getNonForkRepos(@PathVariable String username) {
		try {
			List<GithubRepository> repos = githubService.fetchNonForkRepositories(username);
			
			return Map.of(
					"status", 200,
					"data", repos.stream().map(GithubRepository::toMap).collect(Collectors.toList()) 
				);
		} catch(UserNotFoundException e) {
			return Map.of(
					"status", 404,
					"message", e.getMessage()
				);
		}
	}
}
