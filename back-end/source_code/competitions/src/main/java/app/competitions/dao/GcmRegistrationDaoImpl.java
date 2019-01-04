package app.competitions.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import app.competitions.model.GcmRegistration;

public class GcmRegistrationDaoImpl implements GcmRegistrationDao{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	    @Override
	    public void saveGcmRegistration(GcmRegistration gcmRegistration) {
	    	sessionFactory.getCurrentSession().saveOrUpdate(gcmRegistration);
	    }

		@SuppressWarnings("unchecked")
		@Override
		public List<GcmRegistration> findGcmRegistrationByUser(long userId) {
			return (List<GcmRegistration>) sessionFactory.getCurrentSession().
					createCriteria(GcmRegistration.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("user.userId", userId)).list();
		}
}
