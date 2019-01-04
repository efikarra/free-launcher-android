package app.competitions.dto;

import java.sql.Timestamp;

public class SimpleBidDTO {
	private long bidId;
	private Timestamp estTime;
	private double price;
	private long expId;
	private long projId;
	private String expUsername;
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
	public long getExpId() {
		return expId;
	}
	public void setExpId(long expId) {
		this.expId = expId;
	}
	public long getProjId() {
		return projId;
	}
	public void setProjId(long projId) {
		this.projId = projId;
	}
	public String getExpUsername() {
		return expUsername;
	}
	public void setExpUsername(String expUsername) {
		this.expUsername = expUsername;
	}
	public long getBidId() {
		return bidId;
	}
	public void setBidId(long bidId) {
		this.bidId = bidId;
	}

}
