package hu.elte.szgy.tudor.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.http.HttpHeaders;

import hu.elte.szgy.tudor.dao.UserRepository;
import hu.elte.szgy.tudor.model.User;
import hu.elte.szgy.tudor.model.User.UserType;
import hu.elte.szgy.tudor.rest.PassDTO;

public interface UserService {
	public abstract Collection<User> getUsers();
	public abstract HttpHeaders dispatchUser();
	public abstract Optional<User> findUserByUsername(String username);
}
