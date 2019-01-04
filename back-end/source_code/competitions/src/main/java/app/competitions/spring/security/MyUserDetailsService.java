package app.competitions.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import app.competitions.dao.UserDao;
import app.competitions.model.User;

public class MyUserDetailsService implements UserDetailsService {

	private final UserDao userDao;

	@Autowired
	public MyUserDetailsService(UserDao userDao) {
		this.userDao = userDao;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findUserByUsername(username);
		if (user == null) {
			
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		return new MyUserDetails(user);
	}
}
