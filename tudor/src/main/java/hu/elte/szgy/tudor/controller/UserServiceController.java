package hu.elte.szgy.tudor.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import hu.elte.szgy.tudor.dao.UserRepository;
import hu.elte.szgy.tudor.dto.PassDTO;
import hu.elte.szgy.tudor.model.User;
import hu.elte.szgy.tudor.model.User.UserType;
import hu.elte.szgy.tudor.service.UserServiceImpl;

@RestController
@RequestMapping("user")
@Transactional
public class UserServiceController {
	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = "/users")
	//public ResponseEntity<Object> getUsers() {
	//public String getUsers() {
	public List<User> getUsers() {
		//return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
		return userService.getUsers();
	}
	
	//@PostMapping("/dispatch")
	@RequestMapping(value = "/dispatch", method = RequestMethod.POST)
	//public ResponseEntity<Void> dispatchUser() {
	public RedirectView dispatchUser() {
		//return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
		//return new ResponseEntity<Void>(userService.dispatchUser(), HttpStatus.FOUND);
		return userService.dispatchUser();
		//return new RedirectView("/hello", true);
	}
	
	//@PostMapping("/new")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	//public ResponseEntity<Object> createUser(@RequestBody(required = false) User u, Authentication a) {
	public ResponseEntity<Void> createUser(@RequestBody(required = false) User u) {
		//userService.createUser(u, a);
		//userService.createUser(u);
		return userService.createUser(u);
		//return new ResponseEntity<>("User is created successfully", HttpStatus.CREATED);
	}
	
	//@PostMapping("/delete/{userid}")
	@RequestMapping(value = "/delete/{userid}", method = RequestMethod.GET)
	//public ResponseEntity<Void> deleteUser(@PathVariable("userid") String username, Authentication a) {
	public ResponseEntity<Void> deleteUser(@PathVariable("userid") String userid) {
		//return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		return userService.deleteUser(userid);
	}
	
	/*
	//@RequestMapping("/add_user", method = RequestMethod.GET)
	@RequestMapping("/add_user")
	//public ResponseEntity<Void> addUser() {
	public String addUser() {
		return "add_user";
	}
	*/
	
	/*
	private static Logger log = LoggerFactory.getLogger(UserServiceController.class);
	
	@Autowired
	private UserRepository userDao;
	
	private String printUser(User u) {
		return "{ \"username\":\"" + u.getUsername() + "\", " + "\type\":\"" + u.getType().name() + "\", "
					+ "\"id\":\"" + u.getUserid() + "\"}";
	}
	
	@GetMapping("/self")
	public String selfUser(Authentication a) {
		User u = userDao.getOne(a.getName());
		return printUser(u);
	}
	
	@GetMapping("/{userid}")
	public String otherUser(@PathVariable("userid") String username, Authentication a) {
		User u = userDao.getOne(username);
		if (u.getType() != UserType.UGYFEL) {
			throw new AccessDeniedException("No access to User: " + username);
		}
		return printUser(u);
	}
	
	@PostMapping("/password")
	public ResponseEntity<Void> setSelfpassword(@RequestBody PassDTO pass, Authentication a) {
		return setPassword(a.getName(), pass, a);
	}
	
	@PostMapping("/password/{userId}")
	public ResponseEntity<Void> setPassword(@PathVariable("userid") String username, @RequestBody PassDTO pass, Authentication a) {
		User u = userDao.getOne(username);
		if(!username.contentEquals(a.getName()) && !a.getAuthorities().contains("ROLE_ADMIN")
				&& u.getType() == UserType.UGYFEL) {
			throw new AccessDeniedException("Invalid access to password");
		}
		u.setPassword("{noop}" + pass.getNew_pass());
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/new")
	public ResponseEntity<Void> createUser(@RequestBody(required = false) User u, Authentication a) {
		boolean admin = a.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		log.info("CREATING NEW USER BY", admin ? "ADMIN" : "RECEPCIO");
		if (u.getType() != UserType.UGYFEL && !admin) {
			throw new AccessDeniedException("Only authorized to create UGYFEL users");
		}
		if (u.getType() == UserType.UGYFEL && admin || u.getType() == UserType.ADMIN) {
		    throw new AccessDeniedException("Only authorized to create REC & TUDOR users");
		}
		if (!u.getPassword().startsWith("{"))
		    u.setPassword("{noop}" + u.getPassword());
		if (userDao.existsById(u.getUsername())) {
		    throw new EntityExistsException("Name already used");
		}
		userDao.save(u);
		log.info("Creating user: " + u.getUserid());
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PostMapping("/delete/{userid}")
	public ResponseEntity<Void> deleteUser(@PathVariable("userid") String username, Authentication a) {
		User u = userDao.getOne(username);
		if (!a.getAuthorities().contains("ROLE_ADMIN")
			&& !(a.getAuthorities().contains("ROLE_RECEPCIO") && u.getType() == UserType.UGYFEL)) {
		    throw new AccessDeniedException("Not authorized to delete");
		}
		userDao.delete(u);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/dispatch")
	public ResponseEntity<Void> dispatchUser() {
		// log.debug("Into URI: " + rr.getURI().toString() );
		SecurityContext cc = SecurityContextHolder.getContext();
		HttpHeaders headers = new HttpHeaders();
		if (cc.getAuthentication() != null) {
			Authentication a = cc.getAuthentication();
			try {
				headers.setLocation(
						new URI("/" + userDao.getOne(a.getName()).getType().toString().toLowerCase() + "_home.html"));
			} catch (URISyntaxException e) {
				log.warn("Dispatcher cannot redirect");
			}
		}
		return new ResponseEntity<Void>(headers, HttpStatus.FOUND);
	}
	*/
}
