package app.competitions.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import app.competitions.model.Role;

public class RoleDaoImpl implements RoleDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public Role findRoleByRoleName(String roleName) {
		return (Role) sessionFactory.getCurrentSession()
				.createCriteria(Role.class)
				.add(Restrictions.eq("role", roleName)).uniqueResult();
	}

}
