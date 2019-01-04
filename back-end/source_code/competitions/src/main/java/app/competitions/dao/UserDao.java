package app.competitions.dao;

import app.competitions.model.User;

public interface UserDao {

	public User findUserByUsername(String username);
	public int updateUserPassword(String password, long userId);
}
