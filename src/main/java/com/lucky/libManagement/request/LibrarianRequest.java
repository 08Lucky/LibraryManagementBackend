package com.lucky.libManagement.request;

public class LibrarianRequest {

	private String username;
    private String password;
    private boolean librarian;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLibrarian() {
		return librarian;
	}

	public void setLibrarian(boolean librarian) {
		this.librarian = librarian;
	}

}
