package com.entities;

import java.io.Serializable;
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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Type;

import com.util.HibernateUtil;


@ManagedBean
@RequestScoped
@Entity
@Table
public class section implements Serializable {

	private static final long serialVersionUID = 10021L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "nom", unique = false, nullable = true)
	private String nom;
	@Column(name = "groupe", unique = false, nullable = true)
	private String groupe;
	
	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	@OneToMany(mappedBy = "section")
	private Set<Etudiant> etudiant;
	
	@OneToMany(mappedBy = "section")
	private Set<matiere> matiere;

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

	public Set<Etudiant> getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Set<Etudiant> etudiant) {
		this.etudiant = etudiant;
	}

	public Set<matiere> getMatiere() {
		return matiere;
	}

	public void setMatiere(Set<matiere> matiere) {
		this.matiere = matiere;
	}
	
	public section() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String a() {
		
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        
        //Class
        section c = new section() ;
      
        session.save(c);
        session.getTransaction().commit();
        
        session.close();
		
		return "test";
	}
	
}