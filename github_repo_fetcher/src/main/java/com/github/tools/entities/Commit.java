package com.github.tools.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commit {
	private String sha;

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}
}
