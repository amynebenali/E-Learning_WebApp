package com.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dao.UtilisateurDaoImpl;



@ManagedBean
@RequestScoped
@Entity
@Table
public class matiere   implements Serializable {

	private static final long serialVersionUID = 188021L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "nom", unique = false, nullable = true)
	private String nom;
	@Column(name = "groupe", unique = false, nullable = true)
	private String groupe;
	
	
	 @ManyToOne
	 @JoinColumn(name = "id_section")
	 private section section;
	
	 
	 @ManyToOne
	 @JoinColumn(name = "id_enseignant")
	 private Enseignant enseignant;

	

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

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}



	public section getSection() {
		return section;
	}

	public void setSection(section section) {
		this.section = section;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}
	
	
	public matiere() {
	super();	// TODO Auto-generated constructor stub
		
	}
	public matiere(String groupe, String nom) {
	super();	
	this.groupe=groupe;
	this.nom=nom;
	
	}
	
public String	 lancermatiere( long x)
{
	
	

	UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
	return utilisateur.lancermat(groupe,nom,x);
	


}
   public String   delet(long x)
   {
		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
	utilisateur.effacermatiere(x);	   
	   
	   return"matiere.xhtml?faces-redirect=true";
   }

 public List<matiere> mesmatiere(long x)
 {
	 
	 UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.mesmat(x);	 
	 
	 
 }
	
 
 
}