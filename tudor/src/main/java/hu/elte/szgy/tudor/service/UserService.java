package hu.elte.szgy.tudor.service;

import java.util.Collection;

import hu.elte.szgy.tudor.dao.UserRepository;
import hu.elte.szgy.tudor.model.User;
import hu.elte.szgy.tudor.model.User.UserType;
import hu.elte.szgy.tudor.rest.PassDTO;

public interface UserService {
	public abstract Collection<User> getUsers();
}
