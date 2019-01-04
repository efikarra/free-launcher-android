package app.competitions.dao;

import app.competitions.model.Role;

public interface RoleDao {
	public Role findRoleByRoleName(String roleName);
}
