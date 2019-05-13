package hu.elte.szgy.tudor.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tudor")
//@PrimaryKeyJoinColumn(name="temaid") //ez a sor lehet nem szükséges
public class Tudor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	@JsonIgnore
	private Tema tema;
	
	@Column(name = "tema_nev", insertable = false, updatable = false)
	private String temaId;
	
	public Tema getTema() { return tema; }
	public void setTema(Tema tema) { this.tema = tema; }
	
	public String getTemaId() { return temaId; }
	public void setTemaId(String temaId) { this.temaId = temaId; }
	
}
