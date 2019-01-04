package app.competitions.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.competitions.dao.GcmRegistrationDao;
import app.competitions.dto.GcmRegistrationDTO;
import app.competitions.dto.utils.HibernateMapperFactory;
import app.competitions.model.GcmRegistration;
import app.competitions.service.UserService;

@RestController
@RequestMapping("/users")
public class UserRest {
	@Autowired
	GcmRegistrationDao gcmRegistrationDao;
	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	HibernateMapperFactory mapperFactory;

	
	@RequestMapping(value = "/{userId}/updatePassword", method = RequestMethod.PUT, headers = "Accept=application/json")
	public void updatePassword(@PathVariable long userId,@RequestBody String password) {
		userService.updateUserPassword(passwordEncoder.encode(password), userId);
	}


}
