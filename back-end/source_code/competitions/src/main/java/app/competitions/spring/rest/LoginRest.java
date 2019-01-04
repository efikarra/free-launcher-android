package app.competitions.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.competitions.spring.security.MyToken;
import app.competitions.spring.security.MyUserDetails;
import app.competitions.spring.security.MyUserDetailsService;
import app.competitions.spring.security.TokenProvider;
import app.competitions.spring.security.UserTransfer;


@RestController
public class LoginRest {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	@Qualifier("authenticationManager")	
	private AuthenticationManager authManager;
	
	@RequestMapping(value="/authenticate",method = RequestMethod.POST, produces = "application/json")
	 public MyToken login(@RequestBody UserTransfer user){
		
		String username=user.getUsername();
		String password=user.getPassword();
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		MyUserDetails userDetails = (MyUserDetails) myUserDetailsService.loadUserByUsername(username);

		return new MyToken(TokenProvider.createToken(userDetails));
	}
	
}
