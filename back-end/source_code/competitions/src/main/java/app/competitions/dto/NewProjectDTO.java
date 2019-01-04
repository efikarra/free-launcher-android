package app.competitions.dto;

import java.sql.Timestamp;
import java.util.Set;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class NewProjectDTO {
	@NotEmpty
	@NotNull
	private String title;
	@NotNull
	private Timestamp timeLimit;

	@NotNull
	private double priceLimit;

	private String country;
	private String location;

	@NotEmpty
	@NotNull
	private String shortDescr;
	private String longDescr;

	@NotNull
	private EmployerDTO employer;
	private Set<QualificationDTO> qualifications;

	@NotNull
	private Timestamp endDate;
	private boolean isClosed;

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

	public EmployerDTO getEmployer() {
		return employer;
	}

	public void setEmployer(EmployerDTO employer) {
		this.employer = employer;
	}

	public Set<QualificationDTO> getQualifications() {
		return qualifications;
	}

	public void setQualifications(Set<QualificationDTO> qualifications) {
		this.qualifications = qualifications;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
