package com.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dao.UtilisateurDaoImpl;
import com.util.HibernateUtil;

@ManagedBean
@RequestScoped
@Entity
@Table
public class admin implements Serializable {

	private static final long serialVersionUID = 885564851L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_admin", unique = true, nullable = false)
	private Long id_admin;

	@Column(name = "nom", unique = false, nullable = true)
	private String nom;
	@Column(name = "prenom", unique = false, nullable = true)
	private String prenom;
	@Column(name = "password", unique = false, nullable = true)
	private String password;
	@Column(name = "email", unique = false, nullable = true)
	private String email;
	@OneToMany(mappedBy = "admin")
	private Set<Actualite> actualites;

	public Set<Actualite> getActualites() {
		return actualites;
	}

	public void setActualites(Set<Actualite> actualites) {
		this.actualites = actualites;
	}

	public Long getId_admin() {
		return id_admin;
	}

	public void setId_admin(Long id_admin) {
		this.id_admin = id_admin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public admin() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public admin(String email, String pass) {
		super();
		this.email = email;
		this.password = pass;

	}

	public admin(String email, String pass, String nom, String prenom) {
		super();
		this.email = email;
		this.password = pass;
		this.nom = nom;

		this.prenom = prenom;

	}
	public List<admin> liste()

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.getAllAdmin();

	}
}
