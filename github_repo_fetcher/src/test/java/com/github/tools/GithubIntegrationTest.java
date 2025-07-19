package com.github.tools;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GithubIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@Order(1)
	void ShouldReturnNonForkRepositoriesWithBranches() throws Exception {
		String username = "octocat"; // known GitHub user with a lot of repos, including forks
		
		mockMvc.perform(get("/api/github/repos/" + username))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].OwnerLogin").value(username))
			.andExpect(jsonPath("$[0].RepositoryName").exists())
			.andExpect(jsonPath("$[0].Branches").isArray())
			.andExpect(jsonPath("$[0].Branches[0].BranchName").exists())
			.andExpect(jsonPath("$[0].Branches[0].LastCommitSha").exists());
	}
	
	@Test
	@Order(2)
	void ShouldReturn404WhenUserDoesNotExist() throws Exception {
		String username = "not-existing-user-abduiabdganie";
		
		mockMvc.perform(get("/api/github/repos/" + username))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.status").value(404))
			.andExpect(jsonPath("$.message").exists());
	}
}
