package hu.elte.szgy.tudor.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SzolgaltatasRepository extends JpaRepository<Szolgaltatas, Integer> {
	List<Szolgaltatas> findByEset(Eset eset);

}
