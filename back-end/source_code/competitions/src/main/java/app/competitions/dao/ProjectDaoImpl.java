package app.competitions.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import app.competitions.model.Bid;
import app.competitions.model.Project;

public class ProjectDaoImpl implements ProjectDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> searchProjects(String keyword, boolean isClosed) {
		return sessionFactory
				.getCurrentSession()
				.createCriteria(Project.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.createAlias("employer", "emp")
				.add(Restrictions.eq("isClosed", isClosed))
				.add(Restrictions.or(Restrictions.ilike("title", keyword,
						MatchMode.ANYWHERE), Restrictions.or(Restrictions
						.ilike("shortDescr", keyword, MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("emp.username",
								keyword, MatchMode.ANYWHERE),
								Restrictions.ilike("longDescr", keyword,
										MatchMode.ANYWHERE))))).list();

	}

	@Override
	public Project findProjectById(long projId) {
		return (Project) sessionFactory.getCurrentSession()
				.createCriteria(Project.class).add(Restrictions.idEq(projId))
				.uniqueResult();
	}

	@Override
	public List<Bid> findProjectBids(long projId) {
		Project project = (Project) sessionFactory.getCurrentSession()
				.createCriteria(Project.class)
				.add(Restrictions.eq("projId", projId)).uniqueResult();
		return new ArrayList<Bid>(project.getBids());
	}

	@Override
	public void save(Project project) {
		sessionFactory.getCurrentSession().saveOrUpdate(project);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> findProjectsByEmployer(long empId, boolean isClosed) {
		return sessionFactory
				.getCurrentSession()
				.createCriteria(Project.class)
				.add(Restrictions.and(Restrictions.eq("isClosed", isClosed),
						Restrictions.eq("employer.userId", empId))).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> findAllProjectsByEmployer(long empId) {
		return sessionFactory.getCurrentSession().createCriteria(Project.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setFetchMode("employer", FetchMode.SELECT)
				.add(Restrictions.eq("employer.userId", empId)).list();
	}

	@Override
	public int delete(long projId) {
		String hql = "delete from Project where projId= :projId";
		int c = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("projId", projId).executeUpdate();
		return c;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> findProjects(boolean isClosed) {
		return sessionFactory.getCurrentSession().createCriteria(Project.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.add(Restrictions.eq("isClosed", isClosed)).list();
	}

}
