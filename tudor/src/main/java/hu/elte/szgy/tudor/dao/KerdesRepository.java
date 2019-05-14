package hu.elte.szgy.tudor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.elte.szgy.tudor.model.Kerdes;
import hu.elte.szgy.tudor.model.Ugyfel;

public interface KerdesRepository  extends JpaRepository<Kerdes, Integer> {
	List<Kerdes> findKerdesekByUgyfel(Ugyfel ugyfel);

}
