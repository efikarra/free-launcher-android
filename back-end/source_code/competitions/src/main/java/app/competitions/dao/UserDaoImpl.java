package app.competitions.dao;


import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import app.competitions.model.User;

public class UserDaoImpl implements UserDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	@Override
	@Transactional
	public User findUserByUsername(String username) {
		return (User) sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
	}
	@Override
	public int updateUserPassword(String password, long userId) {
		String hql = "update User set password= :password where userId= :userId";  
		int c=sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId",userId).setParameter("password",password).executeUpdate(); 
		return c;
		
	}

}
