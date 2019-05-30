package hu.elte.szgy.tudor.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hu.elte.szgy.tudor.dao.KerdesRepository;
import hu.elte.szgy.tudor.model.Kerdes;

@Service
public class UgyfelServiceImpl {
	private KerdesRepository kerdesDao;
	
	private static final Logger log = LoggerFactory.getLogger(UgyfelServiceImpl.class);
	
	@Autowired
	public UgyfelServiceImpl(KerdesRepository kerdesDao) {
		this.kerdesDao = kerdesDao;
	}
	
	public List<Kerdes> getKerdesek() {
		return kerdesDao.findAll();
	}
	
	public ResponseEntity<Void> createKerdes(Kerdes k) {
		kerdesDao.save(k);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	public void deleteKerdes(String kerdes) {
		//TODO
	}
	
}
