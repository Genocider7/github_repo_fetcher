package com.github.tools.entities;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
	private String name;
	private Commit commit;
	// Getters and Setters generated automatically
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Commit getCommit() {
		return commit;
	}
	public void setCommit(Commit commit) {
		this.commit = commit;
	}
	
	public Map<String, Object> toMap() {
		return Map.of(
				"BranchName", this.name,
				"LastCommitSha", this.commit.getSha()
			);
	}
}
