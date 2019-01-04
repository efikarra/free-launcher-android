package app.competitions.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bid")
public class Bid {

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bid_id")
	private long bidId;
	
	@Column(name="est_time",nullable=false)
	private Timestamp estTime;
	
	@Column(name="price",nullable=false)
	private double price;

	@ManyToOne  
    @JoinColumn(name = "exp_id",nullable=false) 
	private Expert expert;
	
	@ManyToOne 
    @JoinColumn(name = "proj_id",nullable=false) 
	private Project project;
	
	public long getBidId() {
		return bidId;
	}

	public void setBidId(long bidId) {
		this.bidId = bidId;
	}

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

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
