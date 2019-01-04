package app.competitions.service;

import javax.transaction.Transactional;

import app.competitions.dao.RoleDao;
import app.competitions.model.Role;

public class RoleServiceImpl implements RoleService{
	private RoleDao roleDao;
	public void setRoleDao(RoleDao expertDao) {
		this.roleDao=expertDao;
	}
	
	@Transactional
	@Override
	public Role findRoleByRoleName(String roleName) {
		return roleDao.findRoleByRoleName(roleName);
	}

}
