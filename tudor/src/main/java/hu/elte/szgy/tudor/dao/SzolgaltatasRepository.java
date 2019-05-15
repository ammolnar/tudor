package hu.elte.szgy.tudor.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.szgy.tudor.model.Kerdes;
import hu.elte.szgy.tudor.model.Szolgaltatas;

@Transactional
@Repository
public interface SzolgaltatasRepository extends JpaRepository<Szolgaltatas, Integer> {
	List<Szolgaltatas> findByKerdes(Kerdes kerdes);

}
