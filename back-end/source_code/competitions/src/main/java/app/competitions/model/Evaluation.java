package app.competitions.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "evaluation")
public class Evaluation {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="eval_id")
	private long evalId;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="rating",nullable=false)
	private Double rating;

	@OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ExpertEvaluation> expertEvaluations;
	
	@OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<EmployerEvaluation> expertEvaluated;
	
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

	public long getEvalId() {
		return evalId;
	}

	public void setEvalId(long evalId) {
		this.evalId = evalId;
	}

	public Set<ExpertEvaluation> getExpertEvaluations() {
		return expertEvaluations;
	}

	public void setExpertEvaluations(Set<ExpertEvaluation> expertEvaluations) {
		this.expertEvaluations = expertEvaluations;
	}

	public Set<EmployerEvaluation> getExpertEvaluated() {
		return expertEvaluated;
	}

	public void setExpertEvaluated(Set<EmployerEvaluation> expertEvaluated) {
		this.expertEvaluated = expertEvaluated;
	}
}
