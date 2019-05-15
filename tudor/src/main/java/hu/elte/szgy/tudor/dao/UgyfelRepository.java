package hu.elte.szgy.tudor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.szgy.tudor.model.Ugyfel;

@Transactional
@Repository
public interface UgyfelRepository extends JpaRepository<Ugyfel, Integer> {
	//int hasPendingSzolgaltatasBy(int azon);
}

