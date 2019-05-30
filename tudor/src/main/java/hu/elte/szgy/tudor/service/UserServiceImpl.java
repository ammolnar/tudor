package hu.elte.szgy.tudor.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;
import org.apache.tomcat.util.buf.C2BConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hu.elte.szgy.tudor.dao.UserRepository;
import hu.elte.szgy.tudor.model.User;
import hu.elte.szgy.tudor.model.User.UserType;


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
	
	//public ResponseEntity<Object> getUsers() {
	public List<User> getUsers() {
		//return new ResponseEntity<>(userDao.findAll(), HttpStatus.OK);
		return userDao.findAll();
	}
	
	
	//public ResponseEntity<Void> createUser(User u, Authentication a) {
	public ResponseEntity<Void> createUser(User u) {
		/*
		boolean admin = a.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		log.info("CREATING NEW USER BY ADMIN");
		if (u.getType() != UserType.UGYFEL && !admin) {
			throw new AccessDeniedException("Only authorized to create UGYFEL users");
		}
		if (u.getType() == UserType.UGYFEL && admin || u.getType() == UserType.ADMIN) {
		    throw new AccessDeniedException("Only authorized to create TUDOR users");
		}
		*/
		System.out.println("creating:"+u.getUsername()+","+u.getPassword()+","+u.getType());
		/*if (!u.getPassword().startsWith("{"))
		    u.setPassword("{noop}" + u.getPassword());*/
		//if (userDao.existsById(u.getUsername())) {
		if (userDao.findByUsername(u.getUsername()).isPresent() ) {
		    throw new EntityExistsException("Name already used");
		}
		userDao.save(u);
		//log.info("Creating user: " + u.getUserid());
		log.info("Creating user: " + u.getUsername());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	//public ResponseEntity<Void> deleteUser(String userid) {
	public ResponseEntity<Void> deleteUser(String username) {
		//Object u = userDao.findById(userid);
		//Object u = userDao.findByUsername(username);
		User u = userDao.getOne(username);
		/*if (!a.getAuthorities().contains("ROLE_ADMIN")
			&& !(a.getAuthorities().contains("ROLE_RECEPCIO") && u.getType() == UserType.UGYFEL)) {
		    throw new AccessDeniedException("Not authorized to delete");
		}*/
		//System.out.println("to be deleted:"+userid);
		System.out.println("to be deleted:"+username);
		userDao.delete(u);
		
		//userDao.deleteById(Integer.parseInt(userid));
		//userDao.deleteById(userid);
		//userDao.delete((User)u);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	
	public ResponseEntity<Void> updateUser(User u1) {
		//userDao.update(u);
		System.out.println("updating:"+u1.getUsername()+","+u1.getPassword()+","+u1.getType());
		
		User u2 = userDao.getOne(u1.getUsername());
		if (!u1.getPassword().isEmpty() ) {
			u2.setPassword(u1.getPassword());
		}
		/*if (!u1.getType().equals(null)) {
			u2.setType(u1.getType());
		}*/
		//userDao.save(u2);
		
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	
	}
	
	public User self() {
		SecurityContext cc = SecurityContextHolder.getContext();
		HttpHeaders headers = new HttpHeaders();
		if (cc.getAuthentication() != null) {
			Authentication a = cc.getAuthentication();
			System.out.println("Name: "+a.getName());
			User u = userDao.getOne(a.getName());
			return u;
		}
		return null;
	}
	
	public User otherUser(String username) {
		User u = userDao.getOne(username);
		/*if (u.getType() != UserType.UGYFEL) {
			throw new AccessDeniedException("No access to User: " + username);
		}*/
		return u;
	}
	

	public RedirectView dispatchUser() {
		SecurityContext cc = SecurityContextHolder.getContext();
		HttpHeaders headers = new HttpHeaders();
		if (cc.getAuthentication() != null) {
			Authentication a = cc.getAuthentication();
			
			System.out.println("Name: "+a.getName());
			Optional<User> u = userDao.findByUsername(a.getName());
			System.out.println(", type: "+u.get().getType());
			//System.out.println(", type: "+userDao.getOne(a.getName()).getType());
			
			//headers.add("Location", "/" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html");
			/*try {
				headers.setLocation(
						new URI("/" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html")
						//new URI("redirect:" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html")
				);
				 headers.add("Location", "/" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html");
			} catch (URISyntaxException e) {
				log.warn("Dispatcher cannot redirect");
			}*/
			//String url = "/" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html";
			//System.out.println("redirecting to:"+url);
			//return new RedirectView(url, true);
			
			//String role = userDao.getOne(a.getName()).getType().toString().toLowerCase();
			String role = u.get().getType().toString().toLowerCase();
			//System.out.println("role:"+role);
			//if(role == "ugyfel") {
			if(role.equals("ugyfel")) {
				return new RedirectView("/ugyfel_home", true);
			} else if (role.equals("admin")) {
				return new RedirectView("/admin_home", true);
			} else if (role.equals("tudor")) {
				return new RedirectView("/tudor_home", true);
			}
		}
		//return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
		//return new ResponseEntity<Void>(headers, HttpStatus.TEMPORARY_REDIRECT);
		//return new RedirectView("/ugyfel_home", true);
		return null; // TODO
		
	}
	
}
