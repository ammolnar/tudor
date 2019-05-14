package hu.elte.szgy.tudor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.szgy.tudor.model.Tema;
import hu.elte.szgy.tudor.model.Tudor;

@Transactional
@Repository
public interface TudorRepository extends JpaRepository<Tudor, Integer>{
	public Tudor findTudorByNev(String name);
	List<Tudor> findTudorByTema(Tema tema);

}
