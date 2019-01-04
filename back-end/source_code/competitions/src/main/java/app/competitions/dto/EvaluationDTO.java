package app.competitions.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class EvaluationDTO {
	private long evalId;
	@NotEmpty
	@NotNull
	private String comment;
	@NotNull
	private Double rating;
	private UserDTO user;
	private ProjectDTO project;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public ProjectDTO getProject() {
		return project;
	}
	public void setProject(ProjectDTO project) {
		this.project = project;
	}
	public long getEvalId() {
		return evalId;
	}
	public void setEvalId(long evalId) {
		this.evalId = evalId;
	}
}
