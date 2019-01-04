package app.competitions.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "evaluates")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.expert",
        joinColumns = @JoinColumn(name = "exp_id")),
    @AssociationOverride(name = "primaryKey.project",
        joinColumns = @JoinColumn(name = "proj_id")),
        @AssociationOverride(name = "evaluation",
        joinColumns = @JoinColumn(name = "eval_id")) })
public class EmployerEvaluation {

	@EmbeddedId
	private EmployerEvaluationKey primaryKey = new EmployerEvaluationKey();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Evaluation evaluation;

	public EmployerEvaluationKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(EmployerEvaluationKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
}
