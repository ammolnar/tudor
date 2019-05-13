package hu.elte.szgy.tudor.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tema")
public class Tema implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String nev;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	@JsonIgnore
	private Tudor vezeto;
	
	@Column(name = "vezeto_elid", insertable = false, updatable = false)
	private Integer vezetoId;
	
	public Integer getVezetoId() { return vezetoId; }
	public void setVezetoId(Integer vezetoId) { this.vezetoId = vezetoId; }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "osztaly")
    @JsonIgnore
    private Set<Tudor> tudorok = new HashSet<Tudor>(0);
    
	public String getNev() { return nev; }
	public void setNev( String nev ) { this.nev = nev; }

	public Tudor getVezeto() { return vezeto; }
	public void setVezeto( Tudor vezeto ) { this.vezeto = vezeto; }

	public Set<Tudor> getTudorok() { return tudorok; }
	public void setTudorok( Set<Tudor> tudorok ) { this.tudorok = tudorok; }

	public Tema() { }
	public Tema(String n) { nev = n; }

}
