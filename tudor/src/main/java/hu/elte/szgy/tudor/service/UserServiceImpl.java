package hu.elte.szgy.tudor.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import hu.elte.szgy.tudor.dao.UserRepository;
import hu.elte.szgy.tudor.model.User;

@Service
public class UserServiceImpl implements UserService {
	private static UserRepository userDao;

	@Override
	public Collection<User> getUsers() {
		return userDao.findAll();
	}
	
	
}
