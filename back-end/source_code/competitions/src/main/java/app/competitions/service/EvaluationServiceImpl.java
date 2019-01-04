package app.competitions.service;

import java.util.List;

import javax.transaction.Transactional;

import app.competitions.dao.EvaluationDao;
import app.competitions.model.EmployerEvaluation;
import app.competitions.model.Evaluation;
import app.competitions.model.ExpertEvaluation;

public class EvaluationServiceImpl implements EvaluationService{
	private EvaluationDao evaluationDao;
	public void setEvaluationDao(EvaluationDao evaluationDao) {
		this.evaluationDao=evaluationDao;
	}
	
	@Transactional
	@Override
	public void save(Evaluation evaluation) {
		evaluationDao.save(evaluation);
		
	}
	@Transactional
	@Override
	public List<ExpertEvaluation> findExpertEvaluationsByProject(long projId) {
		return evaluationDao.findExpertEvaluationsByProject(projId);
	}
	@Transactional
	@Override
	public int deleteExpertEvaluation(long userId, long projId) {
		return evaluationDao.deleteExpertEvaluation(userId, projId);
	}
	@Transactional
	@Override
	public void saveExpertEvaluation(ExpertEvaluation evaluation) {
		evaluationDao.saveExpertEvaluation(evaluation);
	}
	@Transactional
	@Override
	public List<ExpertEvaluation> findExpertEvaluationsByExpert(long expId) {
		return evaluationDao.findExpertEvaluationsByExpert(expId);
	}
	@Transactional
	@Override
	public void saveEmployerEvaluation(EmployerEvaluation evaluation) {
		 evaluationDao.saveEmployerEvaluation(evaluation);
		
	}
	@Transactional
	@Override
	public List<EmployerEvaluation> findEmployerEvaluationsByProject(long projId) {
		return evaluationDao.findEmployerEvaluationsByProject(projId);
	}
	@Transactional
	@Override
	public List<ExpertEvaluation> findEmployerEvaluationsByExpert(long expId) {
		return evaluationDao.findExpertEvaluationsByExpert(expId);
	}
	@Transactional
	@Override
	public int deleteEmployerEvaluation(long userId, long projId) {
		return evaluationDao.deleteEmployerEvaluation(userId, projId);
	}
	@Transactional
	@Override
	public ExpertEvaluation findExpertEvaluationByExpertAndProject(long projId,
			long expId) {
		return evaluationDao.findExpertEvaluationByExpertAndProject(projId, expId);
	}

}
