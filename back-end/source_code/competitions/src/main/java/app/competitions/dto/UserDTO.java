package app.competitions.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDTO {
	@NotNull
	private int userId;
	@NotEmpty
	@NotNull
	@Size(max = 50, message = "Your name should be at most 50 characters.")
	private String username;

	@NotEmpty
	@NotNull
	@Size(max = 10, message = "Your name should be at most 100 characters.")
	private String firstname;

	@NotEmpty
	@NotNull
	@Size(max = 100, message = "Your lastname should be at most 100 characters.")
	private String lastname;

	@NotEmpty
	@NotNull
	@Email
	@Size(max = 80, message = "Your email should be at most 80 characters.")
	private String email;

	@NotEmpty
	@NotNull
	private String role;

	private Double rating = null;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
}
