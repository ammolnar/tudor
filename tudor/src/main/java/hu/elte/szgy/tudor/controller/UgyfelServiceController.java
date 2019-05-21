package hu.elte.szgy.tudor.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hu.elte.szgy.tudor.dao.KerdesRepository;
import hu.elte.szgy.tudor.dao.SzolgaltatasRepository;
import hu.elte.szgy.tudor.dao.TemaRepository;
import hu.elte.szgy.tudor.dao.UgyfelRepository;
import hu.elte.szgy.tudor.model.Kerdes;
import hu.elte.szgy.tudor.model.Szolgaltatas;
import hu.elte.szgy.tudor.model.Tema;
import hu.elte.szgy.tudor.model.Ugyfel;
import hu.elte.szgy.tudor.service.UserDetailsImpl;

@RestController
@RequestMapping("ugyfel")
@Transactional
public class UgyfelServiceController {
	private static Logger log = LoggerFactory.getLogger(UgyfelServiceController.class);

	@Autowired
	private UgyfelRepository ugyfelRepo;
	@Autowired
	private KerdesRepository kerdesRepo;
	@Autowired
	private SzolgaltatasRepository szolgaltatasRepo;
	@Autowired
	private TemaRepository temaRepo;

	/*
	int userTudorID(Authentication auth) {
		return ((UserDetailsImpl)auth.getPrincipal()).getTudorId();
	}
	*/
	
	/*
	@GetMapping("/self")
	public ResponseEntity<Ugyfel> selfBeteg(Authentication auth) {
		 return new ResponseEntity<Ugyfel>(ugyfelRepo.findById(userTudorID(auth)).get(), HttpStatus.OK);
	}
	*/
	
	@GetMapping("/{azon}")
    public ResponseEntity<Ugyfel> findUgyfel(@PathVariable("azon") Integer azon, Principal principal, Authentication auth) {
		Ugyfel b = ugyfelRepo.findById(azon).get();
		log.info("Ugyfel type: {} {}", b.getClass().getName(), 11);
		//if(b == null) throw new ResourceNotFoundException( "Ugyfel "+azon+" not found"  );
		if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEMA"))) {
			//if(ugyfelRepo.hasPendingSzolgaltatasBy(azon,userTudorID(auth))==0) throw new AccessDeniedException("No access to (ugyfel)azon: "+azon);
			//if(ugyfelRepo.hasPendingSzolgaltatasBy(azon)==0) throw new AccessDeniedException("No access to (ugyfel)azon: "+azon);
			log.debug( "Checked uses for tudor" );
		}
		return new ResponseEntity<Ugyfel>(b, HttpStatus.OK);
    }

	@GetMapping("/{azon}/kerdesek")
    public ResponseEntity<List<Kerdes>> listKerdesek(@PathVariable("azon") Integer azon, Authentication auth) { 
		return new ResponseEntity<List<Kerdes>>(kerdesRepo.findKerdesekByUgyfel(ugyfelRepo.getOne(azon)),HttpStatus.OK);
	} 

	@GetMapping("/{azon}/{kerdesid}/szolgaltatasok")
    public ResponseEntity<List<Szolgaltatas>> listSzolgaltatasok(@PathVariable("azon") Integer azon, @PathVariable("kerdesid") Integer kerdesid, Authentication auth) { 
		return new ResponseEntity<List<Szolgaltatas>>(szolgaltatasRepo.findByKerdes(kerdesRepo.getOne(kerdesid)),HttpStatus.OK);
	} 
    		
	@GetMapping("/szolgaltatas/{kid}")
    public ResponseEntity<Szolgaltatas> getSzolgaltatas(@PathVariable("kid") Integer kid, @PathVariable("kerdesid") Integer kerdesid, Authentication auth) { return null; } 

	@GetMapping("/szolgaltatas/{kid}/alt_dates")
    public ResponseEntity<Date> getSzolgaltatasAltDate(@PathVariable("kid") Integer kid, @PathVariable("kerdesid") Integer kerdesid, Authentication auth) { return null; } 

	@PostMapping("/new")
	public ResponseEntity<Void> createUgyfel(@RequestBody Ugyfel b, UriComponentsBuilder builder) {
                boolean flag = true; 
				ugyfelRepo.save(b);
                if (flag == false) {
        	       return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/{azon}").buildAndExpand(b.getAzon()).toUri());
                return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@PostMapping("/save}")
	public ResponseEntity<Void> saveBeteg(@RequestBody Ugyfel b) { return null; }

	@PostMapping("/kerdes/new")
	public ResponseEntity<Kerdes> newKerdes(@RequestBody Kerdes e) {
		log.info("Saving kerdes...");
		e.setUgyfel( ugyfelRepo.getOne( e.getUgyfelAzon() ) );
		e.setNyitdate( new Date() );
		e.setStatusz( Kerdes.Statusz.NYITOTT );
		e = kerdesRepo.save(e); 
		log.info("Saved kerdes..." + e.getKerdesid() + e.getUgyfel().getNev());
		return new ResponseEntity<Kerdes>(e, HttpStatus.CREATED);
	}
	
	@PostMapping("/kerdes/save")
	public ResponseEntity<Void> saveKerdes(@RequestBody Kerdes e) { return null; }
	
	@PostMapping("/szolgaltatas/new")
	public ResponseEntity<Szolgaltatas> saveKerdes(@RequestBody Szolgaltatas k) {
		k.setKerdes( kerdesRepo.getOne( k.getKerdesId() ) );
		//k.setTema( temaRepo.getOne( k.getTemaId() ) ); //?? itt valami nem okés

		//k.setStatusz( Szolgaltatas.Statusz.ELOJEGYZETT );
		k.setNyitdate( new Date() );
		k=szolgaltatasRepo.save(k);
		return new ResponseEntity<Szolgaltatas>(k, HttpStatus.CREATED);
	}
	
	@PostMapping("/szolgaltatas/{kid}/nyit")
	public ResponseEntity<Szolgaltatas> nyitSzolgaltatas(@PathVariable("kid") Integer kid, @RequestBody Szolgaltatas knew ) { 
		if (knew.getSzolgid() != kid) 
		    throw new AccessDeniedException("Cannot save on different object");
		Szolgaltatas k = szolgaltatasRepo.findById(kid).get();
		k.setNyitdate(new Date());
		k.setNyitallapot(knew.getNyitallapot());
		k.setSpecifikacio(knew.getSpecifikacio());
		return new ResponseEntity<Szolgaltatas>(k, HttpStatus.CREATED);
	}

	@PostMapping("/szolgaltatas/{kid}/zar")
	public ResponseEntity<Szolgaltatas> zarSzolgaltatas(@PathVariable("kid") Integer kid, @RequestBody Szolgaltatas knew ) { 
		if (knew.getSzolgid() != kid) 
		    throw new AccessDeniedException("Cannot save on different object");
		Szolgaltatas k = szolgaltatasRepo.findById(kid).get();
		k.setVegdate(new Date());
		k.setVegallapot(knew.getVegallapot());
		return new ResponseEntity<Szolgaltatas>(k, HttpStatus.CREATED);
	}


	@PostMapping("/szolgaltatas/{kid}")
	public ResponseEntity<Szolgaltatas> updateSzolgaltatas(@PathVariable("kid") Integer kid, @RequestBody Szolgaltatas knew ) {
		if (knew.getSzolgid() != kid) 
		    throw new AccessDeniedException("Cannot save on different object");
		Szolgaltatas k = szolgaltatasRepo.findById(kid).get();
		//if(knew.getTemaId() != null) k.setTema(temaRepo.getOne(knew.getTemaId())); // ??? itt valami nem okés
		//if(knew.getKerdesId() != null) k.setKerdes(temaRepo.getOne(knew.getKerdesId())); // ??? itt valami nem okés
		if(knew.getNyitdate() != null) k.setNyitdate(knew.getNyitdate());
		if(knew.getVegdate() != null) k.setVegdate(knew.getVegdate());
		if(knew.getStatusz() != null) k.setStatusz(knew.getStatusz());
		if(knew.getTajpontok() >= 0) k.setTajpontok(knew.getTajpontok());
		if(knew.getSpecifikacio() != null) k.setSpecifikacio(knew.getSpecifikacio());
		// TODO: egyéb fieldek
	    
		return new ResponseEntity<Szolgaltatas>(k, HttpStatus.ACCEPTED);
	    
	}

	@PostMapping("/szolgaltatas/{kid}/change_date")
	public ResponseEntity<Date> setSzolgaltatasDate(@PathVariable("kid") Integer kid, @RequestBody Date d ) { 
	    Szolgaltatas k = szolgaltatasRepo.getOne(kid);
	    /*if(k.getStatusz() != Szolgaltatas.Statusz.ELOJEGYZETT) {
	    	throw new AccessDeniedException("Kezeles is already in progress");
	    }*/
	    if(d.before(new Date())) {
	    	throw new AccessDeniedException("Date is in the past");
	    }
	    k.setNyitdate(d);
	    szolgaltatasRepo.save(k);
	    return new ResponseEntity<Date>(k.getNyitdate(), HttpStatus.ACCEPTED);
	}
}
