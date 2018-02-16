package com.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
import org.hibernate.annotations.Type;

import com.dao.UtilisateurDaoImpl;
import com.util.HibernateUtil;

@ManagedBean
@RequestScoped
@Entity
@Table
public class Publication implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "titre", unique = false, nullable = true)
	private String titre;
	@Column(name = "categorie", unique = false, nullable = true)
	private String categorie;
	@Type(type = "text")
	@Column(name = "question", unique = false, nullable = true)
	private String question;
	@Column(name = "date", unique = false, nullable = true)
	private String date;
	@ManyToOne
	@JoinColumn(name = "id_etudiant")
	private Etudiant etudiant;
	@OneToMany(mappedBy = "publication")
	private Set<reponse> reponse;

	public Set<reponse> getReponse() {
		return reponse;
	}

	public void setReponse(Set<reponse> reponse) {
		this.reponse = reponse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Publication() {
		super();
	}

	public Publication(String titre, String categorie, String question, String date) {
		super();
		this.titre = titre;
		this.categorie = categorie;
		this.question = question;
		this.date = date;

	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public String gettime() {

		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		return (df.format(calobj.getTime()));
	}

	public String publier(long x) {

		FacesMessage tmessage = new FacesMessage("publication ajout�� avec succ�s !");
		FacesContext.getCurrentInstance().addMessage(null, tmessage);
		Publication publication = new Publication(titre, categorie, question, gettime());
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();
		try {

			Etudiant owner = (Etudiant) session.get(Etudiant.class, x);
			publication.setEtudiant(owner);
			session.save(publication);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
				return "forum?faces-redirect=true";
			}
		}

		return "forum?faces-redirect=true";

	}

	public List<Publication> liste()

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.getAllPublication();

	}

	public List<reponse> recureponse(long x)

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.recurep(x);

	}

	public List<Publication> mespub(long x)

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.mespub(x);

	}

	public String deletpub(long x) {

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.effacerpub(x);

	}
	
	
	
}
