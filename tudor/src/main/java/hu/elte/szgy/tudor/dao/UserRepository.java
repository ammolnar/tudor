package hu.elte.szgy.tudor.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.szgy.tudor.model.User;

/*
 * Data layer (JPA Repositories)
 * The repositories allow us to access the information stored in the data base.
 * */

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	//not using a DAO interface here
	
	Optional<User> findByUsername(String username);

}
