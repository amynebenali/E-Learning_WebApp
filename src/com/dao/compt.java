package com.dao;
import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped


public class compt implements Serializable {

	private static final long serialVersionUID = 10942445463L;
	private String type ;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String prenom;
	private String nom;
	private String email;
	private String password;
	private String groupe ;
	private String matiere ;
	private Date date ;
	private long id;
	private String section;
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMatiere() {
		return matiere;
	}
	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getGroupe() {
		return groupe;
	}
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}
	public compt () {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public compt(String password, String email, String type) {
		super();
		this.password = password;
		this.type=type;
		this.email = email;

	}	
	

	
	
	public void charger (String a,String b ,String c ,String d,String e,String y,long g,Date h)
	{
	
	this.setNom(a); 
	this.setPrenom(b);
	this.setPassword(c); 
	this.email=d;
	this.id=g;
	this.date=h;
	if (y.equals("Etudiant"))
	this.groupe=e;
	
		
		
	}
	

	
	
	
}
