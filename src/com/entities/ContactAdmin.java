package com.entities;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.dao.UtilisateurDaoImpl;

@ManagedBean
@RequestScoped
@Entity
@Table
public class ContactAdmin implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "nom", unique = false, nullable = true)
	private String nom;
	@Column(name = "prenom", unique = false, nullable = true)
	private String prenom;
	@Column(name = "email", unique = false, nullable = true)
	private String email;
	@Type(type = "text")
	@Column(name = "message", unique = false, nullable = true, columnDefinition = "text")
	private String message;

	@Column(name = "type", unique = false, nullable = true)
	private String type;
	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ContactAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContactAdmin(String nom, String prenom, String email, String message,String type) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.message = message;
		this.type=type;
	}

	public String envoyer(String nom1, String prenom1, String email1,String type) {
		this.nom = nom1;
		this.prenom = prenom1;
		this.email = email1;
		this.type=type;
		FacesMessage tmessage = new FacesMessage("Message envoyer avec succes !");
		FacesContext.getCurrentInstance().addMessage(null, tmessage);
		ContactAdmin contactAdmin = new ContactAdmin(nom, prenom, email, message,type);
		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		utilisateur.addMessage(contactAdmin);
		return "Home?faces-redirect=true";
	}
	
	public List<ContactAdmin> liste()

	{
		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.getAllMessages();

	}

	public String delet(long x)

	{
		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.deletMessages(x);

	}


}
