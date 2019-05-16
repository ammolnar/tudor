package hu.elte.szgy.tudor.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.szgy.tudor.dao.UserRepository;
import hu.elte.szgy.tudor.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository userDao;
	
	@Override
	//@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Authenticating " + username);
		//User user = userDao.findById(username).get();
		Optional<User> optionalUser = userDao.findByUsername(username);
		//logger.info("User data:" + optionalUser.getPassword());

		//return new UserDetailsImpl(user);
		return Optional.ofNullable(optionalUser).orElseThrow(()->new UsernameNotFoundException("Username Not Found")).map(UserDetailsImpl::new).get();
	}
}
