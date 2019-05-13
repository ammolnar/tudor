package hu.elte.szgy.tudor.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KerdesRepository  extends JpaRepository<Kerdes, Integer> {
	List<Kerdes> findKerdesekByUgyfel(Ugyfel ugyfel);

}
