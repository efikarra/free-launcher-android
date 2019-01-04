package app.competitions.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "expert")
@PrimaryKeyJoinColumn(name = "exp_id")
public class Expert extends User {

	@OneToMany(mappedBy = "expert", cascade = CascadeType.ALL)
	private List<Bid> bids;

	@ManyToMany(cascade=CascadeType.ALL, mappedBy="experts",fetch=FetchType.LAZY)  
	private Set<Project> projects;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "expert_qualification", joinColumns = { @JoinColumn(name = "exp_id") }, inverseJoinColumns = { @JoinColumn(name = "qual_id") })
	private Set<Qualification> qualifications;
	
	@OneToMany(mappedBy = "primaryKey.expert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ExpertEvaluation> expertEvaluations;

	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(Set<Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public Set<ExpertEvaluation> getExpertEvaluations() {
		return expertEvaluations;
	}

	public void setExpertEvaluations(Set<ExpertEvaluation> expertEvaluations) {
		this.expertEvaluations = expertEvaluations;
	}

}
