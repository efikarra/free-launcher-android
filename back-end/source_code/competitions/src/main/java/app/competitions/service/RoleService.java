package app.competitions.service;

import app.competitions.model.Role;

public interface RoleService {
	public Role findRoleByRoleName(String roleName);
}
