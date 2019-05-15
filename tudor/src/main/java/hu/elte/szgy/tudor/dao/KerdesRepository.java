package hu.elte.szgy.tudor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.szgy.tudor.model.Kerdes;
import hu.elte.szgy.tudor.model.Ugyfel;

@Transactional
@Repository
public interface KerdesRepository  extends JpaRepository<Kerdes, Integer> {
	List<Kerdes> findKerdesekByUgyfel(Ugyfel ugyfel);

}
