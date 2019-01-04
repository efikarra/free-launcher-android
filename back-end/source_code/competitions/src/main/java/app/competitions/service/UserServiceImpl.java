package app.competitions.service;

import javax.transaction.Transactional;

import app.competitions.dao.UserDao;

public class UserServiceImpl implements UserService{
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao=userDao;
	}
	
	@Transactional
	@Override
	public int updateUserPassword(String password, long userId) {
		return userDao.updateUserPassword(password, userId);
	}

}
