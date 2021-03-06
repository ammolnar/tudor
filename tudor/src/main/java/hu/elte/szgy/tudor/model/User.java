package hu.elte.szgy.tudor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="userid")
	private int userid;
	*/
	
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	public enum UserType {
		UGYFEL, TUDOR, ADMIN
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private UserType type;
	
	/*
	public int getUserid() {return this.userid;}
	public void setUserid(int userid) {this.userid = userid;}
	*/
	
	public String getUsername() {return this.username;}
	public void setUsername(String username) {this.username = username;}
	
	public String getPassword() {return this.password;}
	public void setPassword(String password) {this.password = password;}
	
	public UserType getType() {return this.type;}
	public void setType(UserType type) {this.type = type;}
	
}

