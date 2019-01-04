package app.competitions.dto;

public class SimpleEvaluationDTO {
	private long evalId;
	private String comment;
	private double rating;
	private SimpleUserDTO user;
	private SimpleProjectDTO project;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public SimpleUserDTO getUser() {
		return user;
	}
	public void setUser(SimpleUserDTO user) {
		this.user = user;
	}
	public SimpleProjectDTO getProject() {
		return project;
	}
	public void setProject(SimpleProjectDTO project) {
		this.project = project;
	}
	public long getEvalId() {
		return evalId;
	}
	public void setEvalId(long evalId) {
		this.evalId = evalId;
	}
}
