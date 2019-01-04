package app.competitions.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import app.competitions.model.Evaluation;
import app.competitions.model.Expert;
import app.competitions.model.Project;

public class ExpertDaoImpl implements ExpertDao {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Expert expert) {
		sessionFactory.getCurrentSession().saveOrUpdate(expert);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Expert> listExperts() {
		return sessionFactory.getCurrentSession().createCriteria(Expert.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Expert> searchExperts(String keyword) {
		return sessionFactory
				.getCurrentSession()
				.createCriteria(Expert.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.add(Restrictions.or(
						Restrictions.like("firstname", "%" + keyword + "%"),
						Restrictions.or(
								Restrictions.like("lastname", "%" + keyword
										+ "%"),
								Restrictions.like("qualfications.title", "%"
										+ keyword + "%")))).list();
	}

	@Override
	public Expert findExpertByUsername(String username) {
		return (Expert) sessionFactory.getCurrentSession()
				.createCriteria(Expert.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> findExpertProjects(long expId) {
		Expert expert = (Expert) sessionFactory.getCurrentSession()
				.createCriteria(Expert.class).add(Restrictions.idEq(expId))
				.uniqueResult();
		return new ArrayList<Project>(expert.getProjects());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evaluation> findExpertProjectsEvaluations(long expId,
			long projId) {
		return (List<Evaluation>) sessionFactory
				.getCurrentSession()
				.createQuery(
						"select eval from ExpertEvaluation "
								+ "as expEval left join expEval.evaluation as eval left join expEval.primaryKey.expert as exp"
								+ " left join expEval.primaryKey.project as proj"
								+ " where exp.userId= :expId and proj.projId= :projId")
				.setParameter("expId", expId).setParameter("projId", projId)
				.list();
	}

	@Override
	public Expert findExpertById(long expId) {
		return (Expert) sessionFactory.getCurrentSession()
				.createCriteria(Expert.class).add(Restrictions.idEq(expId))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Expert> search(String keyword) {
		return sessionFactory
				.getCurrentSession()
				.createCriteria(Expert.class)
				.createAlias("qualifications", "qs")
				.add(Restrictions.or(Restrictions.ilike("username", keyword,
						MatchMode.ANYWHERE), Restrictions.or(Restrictions
						.ilike("firstname", keyword, MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("lastname", keyword,
								MatchMode.ANYWHERE), Restrictions.or(
								Restrictions.ilike("qs.title", keyword,
										MatchMode.ANYWHERE), Restrictions
										.ilike("qs.description", keyword,
												MatchMode.ANYWHERE)))))).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Expert> findExpertsByProject(long projId) {
		return sessionFactory.getCurrentSession().createCriteria(Expert.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.createAlias("projects", "p")
				.add(Restrictions.eq("p.projId", projId)).list();
	}

}
