package app.competitions.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "proj_id")
	private long projId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "time_limit", nullable = false)
	private Timestamp timeLimit;

	@Column(name = "price_limit")
	private double priceLimit;

	@Column(name = "country")
	private String country;

	@Column(name = "location")
	private String location;

	@Column(name = "short_descr", nullable = false)
	private String shortDescr;

	@Column(name = "long_descr")
	private String longDescr;

	@ManyToOne
	@JoinColumn(name = "emp_id", nullable = false)
	private Employer employer;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "project_qualification", joinColumns = { @JoinColumn(name = "proj_id") }, inverseJoinColumns = { @JoinColumn(name = "qual_id") })
	private Set<Qualification> qualifications;

	@Column(name = "is_closed", nullable = false, columnDefinition = "TINYINT", length = 1)
	private boolean isClosed;
	
	@Column(name = "end_date", nullable = false)
	private Timestamp endDate;

	@OneToMany(mappedBy = "project",cascade = CascadeType.ALL,fetch=FetchType.LAZY) 
	private Set<Bid> bids;
	
	@OneToMany(mappedBy = "primaryKey.project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ExpertEvaluation> expertEvaluations;
	
	
	@ManyToMany(fetch = FetchType.LAZY,cascade=CascadeType.MERGE)
	@JoinTable(name = "works_on", joinColumns = { @JoinColumn(name = "proj_id") }, inverseJoinColumns = { @JoinColumn(name = "exp_id") })
	private Set<Expert> experts;
	
	public long getProjId() {
		return projId;
	}

	public void setProjId(long projId) {
		this.projId = projId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Timestamp timeLimit) {
		this.timeLimit = timeLimit;
	}

	public double getPriceLimit() {
		return priceLimit;
	}

	public void setPriceLimit(double priceLimit) {
		this.priceLimit = priceLimit;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getShortDescr() {
		return shortDescr;
	}

	public void setShortDescr(String shortDescr) {
		this.shortDescr = shortDescr;
	}

	public String getLongDescr() {
		return longDescr;
	}

	public void setLongDescr(String longDescr) {
		this.longDescr = longDescr;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public Set<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(Set<Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Set<Bid> getBids() {
		return bids;
	}

	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}

	public Set<ExpertEvaluation> getExpertEvaluations() {
		return expertEvaluations;
	}

	public void setExpertEvaluations(Set<ExpertEvaluation> expertEvaluations) {
		this.expertEvaluations = expertEvaluations;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public Set<Expert> getExperts() {
		return experts;
	}

	public void setExperts(Set<Expert> experts) {
		this.experts = experts;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
