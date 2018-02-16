package com.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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

import com.dao.UtilisateurDaoImpl;

@ManagedBean
@RequestScoped
@Entity
@Table
public class Enseignant implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_enseignant", unique = true, nullable = false)
	private Long id_enseignant;

	@Column(name = "nom", unique = false, nullable = true)
	private String nom;
	@Column(name = "prenom", unique = false, nullable = true)
	private String prenom;
	@Column(name = "password", unique = false, nullable = true)
	private String password;
	@Column(name = "email", unique = false, nullable = true)
	private String email;
	
	private Date date;
	@Column(name = "date", unique = false, nullable = true)

	@OneToMany(mappedBy = "enseignant")
	private Set<partager> partager;

	@OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Activity> activities;

	
	@OneToMany(mappedBy = "enseignant")
	private Set<matiere>  matiere   ;
	
	
	
	public Set<matiere> getMatiere() {
		return matiere;
	}

	public void setMatiere(Set<matiere> matiere) {
		this.matiere = matiere;
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public Set<partager> getPartager() {
		return partager;
	}

	public void setPartager(Set<partager> partager) {
		this.partager = partager;
	}

	public String getEmail() {
		return email;
	}

	

	public Long getId_enseignant() {
		return id_enseignant;
	}

	public void setId_enseignant(Long id_enseignant) {
		this.id_enseignant = id_enseignant;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Enseignant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Enseignant(String nom, String prenom, String password, String email, Date date) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.email = email;
		this.date = date;
		
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

	public String inscription() {

		FacesMessage message = new FacesMessage("Succes de l'inscription !");
		FacesContext.getCurrentInstance().addMessage(null, message);
		Enseignant enseignant = new Enseignant(nom, prenom, password, email, date);
		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		utilisateur.addEnseignant(enseignant);
		return "index";

	}
	public List<Enseignant> liste()

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.getAllEnseignant();

	}

public String delet(long x)
{
	
	UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
	return utilisateur.deletenseignant(x);


}



}

