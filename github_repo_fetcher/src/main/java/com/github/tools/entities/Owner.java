package com.github.tools.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner {
	private String Login;

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}
}
