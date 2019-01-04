package app.competitions.service;

import java.util.List;

import app.competitions.model.EmployerEvaluation;
import app.competitions.model.Evaluation;
import app.competitions.model.ExpertEvaluation;

public interface EvaluationService {
	public void save(Evaluation evaluation);
	public int deleteExpertEvaluation(long userId,long projId);
	public void saveExpertEvaluation(ExpertEvaluation evaluation);
	List<ExpertEvaluation> findExpertEvaluationsByProject(long projId);
	List<ExpertEvaluation> findExpertEvaluationsByExpert(long expId);
	ExpertEvaluation findExpertEvaluationByExpertAndProject(long projId,long expId);
	public void saveEmployerEvaluation(EmployerEvaluation evaluation);
	List<EmployerEvaluation> findEmployerEvaluationsByProject(long projId);
	List<ExpertEvaluation> findEmployerEvaluationsByExpert(long expId);
	public int deleteEmployerEvaluation(long userId,long projId);
}
