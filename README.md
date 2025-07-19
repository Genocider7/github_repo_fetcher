# GitHub Repository Fetcher API

A simple Spring Boot application that uses the GitHub REST API v3 to fetch all **non-fork repositories** of a given GitHub user.  
It also lists each repository’s branches with their **latest commit SHA**.

---

## Features

- Connects to GitHub's public REST API v3
- Returns all **non-fork** repositories for a given user
- Includes **branch names and last commit SHAs**
- Handles **404 errors** gracefully when the user does not exist
- Supports **authenticated requests** with a GitHub token (optional, but recommended)

---

### Requirements

- Java 21
- Maven
- Internet connection (for GitHub API access)

---

### Run the App

1. Clone the repository:
   ```bash
   git clone https://github.com/Genocider7/github_repo_fetcher.git
   ```
2. Enter main project's directory:
   ```bash
   cd github_repo_fetcher/github_repo_fetcher
   ```
3. Build and run:
   ```bash
   mvn spring-boot:run
   ```

---

### Tests

To run the tests:
   ```bash
   mvn test
   ```
Tests are in /src/test/java/ directory and currently there's one integral test on usernames:
- **"octocat"** - verified github user with lots of repositories, including forks
- **"not-existing-user-abduiabdganie"** - which should return 404 error since there's no user with such username

---

### Github Authentication

Currently, Github only allows for 60 uses per hour for a single IP address without authentication. To authenticate, you need to generate an access token that needs to be entered in /src/main/resources/application.properties as github.token.</br>
(Please not that for tests you need to put the token in /src/test/resources/application-test.properties with the same name as above)</br>
Leaving the field empty defaults to unauthenticated use.

---

### Example Output
```json
[
    {
        "RepositoryName": "hello-world",
        "OwnerLogin": "Steve",
        "Branches": [
            {
                "BranchName": "main",
                "LastCommitSha": "868a95311f9404728064f92597653db32fee102fdf684602a086832a0e40143f"
            }
        ]
    },
    {
        "RepositoryName": "tortilla-chip",
        "OwnerLogin": "Steve",
        "Branches": [
            {
                "BranchName": "main",
                "LastCommitSha": "26f9f9109cc98cc120ec39a952b4774ae727ae177818dc75965b0b3438a1248d"
            },
            {
                "BranchName": "development",
                "LastCommitSha": "81965ee14cc67bb1f6a23c722b91da1df7f626b81a9e8fa2e1cdd8d643c19b75"
            }
        ]
    }
]
```