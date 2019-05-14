package hu.elte.szgy.tudor.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.elte.szgy.tudor.model.Ugyfel;

public interface UgyfelRepository extends JpaRepository<Ugyfel, Integer> {
	int hasPendingSzolgaltatasBy(int azon);
}

