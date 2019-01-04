package app.competitions.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import app.competitions.model.EmployerEvaluation;
import app.competitions.model.Evaluation;
import app.competitions.model.ExpertEvaluation;

public class EvaluationDaoImpl implements EvaluationDao {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Evaluation evaluation) {
		sessionFactory.getCurrentSession().saveOrUpdate(evaluation);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertEvaluation> findExpertEvaluationsByProject(long projId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select expEval from ExpertEvaluation "
								+ "as expEval left join fetch expEval.evaluation as eval left join fetch expEval.primaryKey.expert as exp"
								+ " left join fetch expEval.primaryKey.project as proj"
								+ " where proj.projId= :projId")
				.setParameter("projId", projId).list();
	}

	@Override
	public int deleteExpertEvaluation(long userId, long projId) {
		String hql = "delete from ExpertEvaluation where primaryKey.expert.userId= :userId and primaryKey.project.projId= :projId";
		int c = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("userId", userId).setParameter("projId", projId)
				.executeUpdate();
		return c;
	}

	@Override
	public void saveExpertEvaluation(ExpertEvaluation evaluation) {
		sessionFactory.getCurrentSession().saveOrUpdate(evaluation);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertEvaluation> findExpertEvaluationsByExpert(long expId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select expEval from ExpertEvaluation "
								+ "as expEval left join fetch expEval.evaluation as eval left join fetch expEval.primaryKey.expert as exp"
								+ " left join fetch expEval.primaryKey.project as proj"
								+ " where exp.userId= :userId")
				.setParameter("userId", expId).list();
	}

	@Override
	public void saveEmployerEvaluation(EmployerEvaluation evaluation) {
		sessionFactory.getCurrentSession().saveOrUpdate(evaluation);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployerEvaluation> findEmployerEvaluationsByProject(long projId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select empEval from EmployerEvaluation "
								+ "as empEval left join fetch empEval.evaluation as eval left join fetch empEval.primaryKey.expert as exp"
								+ " left join fetch empEval.primaryKey.project as proj"
								+ " where proj.projId= :projId")
				.setParameter("projId", projId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertEvaluation> findEmployerEvaluationsByExpert(long expId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select empEval from EmployerEvaluation "
								+ "as empEval left join fetch expEval.evaluation as eval left join fetch empEval.primaryKey.expert as exp"
								+ " left join fetch empEval.primaryKey.project as proj"
								+ " where exp.userId= :userId")
				.setParameter("userId", expId).list();
	}

	@Override
	public int deleteEmployerEvaluation(long userId, long projId) {
		String hql = "delete from EmployerEvaluation where primaryKey.expert.userId= :userId and primaryKey.project.projId= :projId";
		int c = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("userId", userId).setParameter("projId", projId)
				.executeUpdate();
		return c;
	}

	@Override
	public ExpertEvaluation findExpertEvaluationByExpertAndProject(long projId,
			long expId) {
		return (ExpertEvaluation) sessionFactory
				.getCurrentSession()
				.createQuery(
						"select expEval from ExpertEvaluation "
								+ "as expEval left join fetch expEval.evaluation as eval left join fetch expEval.primaryKey.expert as exp"
								+ " left join fetch expEval.primaryKey.project as proj"
								+ " where proj.projId= :projId and exp.userId= :expId")
				.setParameter("projId", projId).setParameter("expId", expId)
				.uniqueResult();
	}

}
