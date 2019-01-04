package app.competitions.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;

import app.competitions.model.Expert;
import app.competitions.model.Qualification;

public class QualificationDaoImpl implements QualificationDao{

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void save(Expert expert) {
		sessionFactory.getCurrentSession().saveOrUpdate(expert);
		
	}
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<Qualification> listQualifications() {
		return sessionFactory.getCurrentSession()
                .createCriteria(Qualification.class).list();
	}

}
