package hu.elte.szgy.tudor.rest;

import hu.elte.szgy.tudor.data.Tudor;
import hu.elte.szgy.tudor.data.TudorRepository;
import hu.elte.szgy.tudor.data.Tema;
import hu.elte.szgy.tudor.data.TemaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tema")
@Transactional
public class TemaManager {

    private static Logger log = LoggerFactory.getLogger(UgyfelManager.class);
    @Autowired
    private TemaRepository temaDao;

    @Autowired
    private TudorRepository tudorDao;

    @GetMapping("/all")
    public ResponseEntity<List<Tema>> getAllTema(@RequestParam(name="type",required=false) String type) {
    	List<Tema> temalist = temaDao.findAll();
    	if(type != null) {
    		List<Tema> fulllist = temalist;
    		temalist = new ArrayList<Tema>();
    		//for(Tema e : fulllist) if(e.getType().equals(type)) temalist.add(e); // ???
    		for(Tema e : fulllist) temalist.add(e);
	   
    		/* ListIterator<Tema> lii = temalist.listIterator();
	    		while(lii.hasNext()) if(!lii.next().getType().equals(type)) lii.remove();*/
    	}
    	return new ResponseEntity<List<Tema>>(temalist, HttpStatus.OK);
    }

    @GetMapping("/{temaid}")
    public ResponseEntity<Tema> getEllato(@PathVariable("temaid") Integer temaid) {
    	Tema tema = temaDao.findById(temaid.toString()).get();
		/*log.debug("TEMA identified: " + tema.getClass().getName() + "Equals Tudor: "
			+ (tema.getClass() == Tudor.class));*/
		return new ResponseEntity<Tema>(tema, HttpStatus.OK);
    }

    /*@GetMapping("/byname/{name}")
    public ResponseEntity<Tema> getNamedTema(@PathVariable("name") String nev) {
    	return getTema(temaDao.findByNev(nev).getTemaid());
    }*/

    @GetMapping("/tema/{temaid}")
    public ResponseEntity<Tema> getTemaByName(@PathVariable("temaid") String tema_nev) {
    	Tema o = temaDao.findById(tema_nev).get();
		// Tema o = temaDao.getOne(tema_nev);  THIS DOES NOT WORK. RETURNS OBJECT REFERENCE  
		return new ResponseEntity<Tema>(o, HttpStatus.OK);
    }

    @GetMapping("/tema/all")
    public ResponseEntity<List<Tema>> getAllTema() {
    	return new ResponseEntity<List<Tema>>(temaDao.findAll(), HttpStatus.OK);
    }

    @GetMapping("/tema/{temaid}/tudorok")
    public ResponseEntity<List<Tudor>> getTUdorokByTema(@PathVariable("temaid") String tema_nev) {
    	Tema oszi = temaDao.getOne(tema_nev);
		return new ResponseEntity<List<Tudor>>(tudorDao.findTudorByTema(oszi), HttpStatus.OK);
    }

    @PostMapping("/tudor/new")
    public ResponseEntity<Tudor> createTudor(@RequestBody Tudor b) {
    	if(b.getTemaId() != null) b.setTema(temaDao.findById(b.getTemaId()).get());
    	tudorDao.save(b);
    	return new ResponseEntity<Tudor>(b, HttpStatus.CREATED);
    }

    @PostMapping("/tudor/{tudorid}")
    public ResponseEntity<Tudor> updateTudor(@PathVariable("tudorid") String tudorid, @RequestBody Tudor b) {
    	if(Integer.parseInt(b.getTemaId()) != Integer.parseInt(tudorid)) {
			throw new AccessDeniedException("Cannot save on different object");
		}
		Tudor tudor = tudorDao.findById(Integer.parseInt(tudorid)).get();
		if(b.getTemaId() != null) {
	    	if(b.getTemaId().equals("none")) tudor.setTema(null); 
	    	else tudor.setTema(temaDao.findById(b.getTemaId()).get());
		}
		/*if(b.getNev() != null) {
			tudor.setNev(b.getNev());
		}*/
		tudorDao.save(tudor);
		return new ResponseEntity<Tudor>(b, HttpStatus.ACCEPTED);
    }

    
  //itt valami nem okés
    void setTemaRelations(Tema o, Integer vezid) {
		/*Tema tema;
		if (vezid != null) {
		    tema = temaDao.findById(vezid.toString()).get(); 
		    if (!tema.getType().equals("TUDOR"))
			throw new IllegalArgumentException("Tema specified is not a Tudor");
		    o.setVezeto((Tudor) tudor);
		}*/
    }

    @PostMapping("/tema/new")
    public ResponseEntity<Tema> createTema(@RequestBody Tema tema) {
    	setTemaRelations(tema, tema.getVezetoId());
    	temaDao.save(tema);
    	return new ResponseEntity<Tema>(tema, HttpStatus.CREATED);
    }

    @PostMapping("/tema/{temaid}")
    public ResponseEntity<Void> saveTema(@PathVariable("temaid") String temaid, @RequestBody Tema temanew) {
    	if (!temanew.getNev().equals(temaid))
    		throw new AccessDeniedException("Cannot save on different object");
    	Tema tema = temaDao.findById(temaid).get();
    	setTemaRelations(tema, temanew.getVezetoId());
    	temaDao.save(tema);
    	return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }


}
