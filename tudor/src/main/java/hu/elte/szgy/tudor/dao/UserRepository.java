package hu.elte.szgy.tudor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.szgy.tudor.model.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	//not using a DAO interface here
}
