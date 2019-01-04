package app.competitions.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import app.competitions.model.Employer;

public class EmployerDaoImpl implements EmployerDao {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Employer employer) {
		sessionFactory.getCurrentSession().saveOrUpdate(employer);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employer> listEmployers() {
		return sessionFactory.getCurrentSession()
				.createCriteria(Employer.class).list();
	}

	@Override
	public Employer findEmployerById(long empId) {
		return (Employer) sessionFactory.getCurrentSession()
				.createCriteria(Employer.class).add(Restrictions.idEq(empId))
				.uniqueResult();
	}

	@Override
	public Employer findEmployerByProject(long projId) {
		return (Employer) sessionFactory.getCurrentSession()
				.createCriteria(Employer.class)
				.createAlias("projects", "projects")
				.add(Restrictions.eq("projects.projId", projId)).uniqueResult();
	}

}
