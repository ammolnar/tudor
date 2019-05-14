package hu.elte.szgy.tudor.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import hu.elte.szgy.tudor.model.Kerdes;
import hu.elte.szgy.tudor.model.Szolgaltatas;

public interface SzolgaltatasRepository extends JpaRepository<Szolgaltatas, Integer> {
	List<Szolgaltatas> findByKerdes(Kerdes kerdes);

}
