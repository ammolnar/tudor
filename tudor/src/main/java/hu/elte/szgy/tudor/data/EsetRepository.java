package hu.elte.szgy.tudor.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EsetRepository  extends JpaRepository<Eset, Integer> {
	List<Eset> findEsetekByUgyfel(Ugyfel ugyfel);

}
