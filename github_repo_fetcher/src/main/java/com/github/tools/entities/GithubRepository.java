package com.github.tools.entities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubRepository {
	private String name;
	private boolean fork;
	private Owner owner;
	private List<Branch> branches;
	// Getters and Setters generated automatically
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isFork() {
		return fork;
	}
	public void setFork(boolean fork) {
		this.fork = fork;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public List<Branch> getBranches() {
		return branches;
	}
	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}
	
	public Map<String, Object> toMap() {
		return Map.of(
				"RepositoryName", this.name,
				"OwnerLogin", this.owner.getLogin(),
				"Branches", this.branches.stream().map(Branch::toMap).collect(Collectors.toList())
			);
	}
	
}
