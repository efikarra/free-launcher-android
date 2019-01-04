package app.competitions.dto;


public class QualificationDTO {
	private long qualId;
	private String title;
	private String description;

	public long getQualId() {
		return qualId;
	}

	public void setQualId(long qualId) {
		this.qualId = qualId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
