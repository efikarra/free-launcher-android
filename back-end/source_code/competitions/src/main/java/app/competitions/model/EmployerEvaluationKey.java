package app.competitions.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class EmployerEvaluationKey implements Serializable {
	@ManyToOne(fetch = FetchType.LAZY)
	private Expert expert;
	@ManyToOne(fetch = FetchType.LAZY)
	private Project project;

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
