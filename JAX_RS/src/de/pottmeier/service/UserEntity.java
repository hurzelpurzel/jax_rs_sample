package de.pottmeier.service;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class UserEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3715365066410034423L;
	private  int id;
	private String name;
	private String firstname;
	private String email;
	
	

	public UserEntity() {
		
		this.id = -1;
	}

	public UserEntity( int id, String name, String firstname, String email) {
		this(name, firstname,email);
		this.id = id;
		
	}

	public UserEntity( String name, String firstname, String email) {
		this();
		this.name = name;
		this.firstname = firstname;
		this.email = email;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}



	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	@XmlElement
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

}
