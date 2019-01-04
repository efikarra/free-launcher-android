package app.competitions.spring.security;

public class MyToken {

	private final String token;

	public MyToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}
