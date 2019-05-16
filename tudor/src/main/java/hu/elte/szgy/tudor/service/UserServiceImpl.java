package hu.elte.szgy.tudor.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hu.elte.szgy.tudor.dao.UserRepository;
import hu.elte.szgy.tudor.model.User;


@Service
public class UserServiceImpl {
	private UserRepository userDao;
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	
	@Autowired
	public UserServiceImpl(UserRepository userDao) {
		this.userDao = userDao;
	}
	
	public Optional<User> findUserByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public ResponseEntity<Object> getUsers() {
		return new ResponseEntity<>(userDao.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<Void> dispatchUser() {
		SecurityContext cc = SecurityContextHolder.getContext();
		HttpHeaders headers = new HttpHeaders();
		if (cc.getAuthentication() != null) {
			Authentication a = cc.getAuthentication();
			
			System.out.println("Name: "+a.getName());
			System.out.println(", type: "+userDao.getOne(a.getName()).getType());
			
			//headers.add("Location", "/" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html");
			try {
				headers.setLocation(
						new URI("/" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html")
						//new URI("redirect:" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html")
				);
				 headers.add("Location", "/" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html");
			} catch (URISyntaxException e) {
				log.warn("Dispatcher cannot redirect");
			}
			
		}
		return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
		//return new ResponseEntity<Void>(headers, HttpStatus.TEMPORARY_REDIRECT);
	}
	
}
