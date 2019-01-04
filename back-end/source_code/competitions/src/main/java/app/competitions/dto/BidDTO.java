package app.competitions.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

public class BidDTO {
	private long bidId;
	@NotNull
	private Timestamp estTime;
	@NotNull
	private double price;
	@NotNull
	private ExpertDTO expert;
	@NotNull
	private ProjectDTO project;

	public Timestamp getEstTime() {
		return estTime;
	}

	public void setEstTime(Timestamp estTime) {
		this.estTime = estTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ExpertDTO getExpert() {
		return expert;
	}

	public void setExpert(ExpertDTO expert) {
		this.expert = expert;
	}

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public long getBidId() {
		return bidId;
	}

	public void setBidId(long bidId) {
		this.bidId = bidId;
	}
}
