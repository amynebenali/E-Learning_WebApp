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
public class partager implements Serializable {

	private static final long serialVersionUID = 5454995461L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "titre", unique = false, nullable = true)
	private String titre;
	@Column(name = "categorie", unique = false, nullable = true)
	private String categorie;
	@Type(type = "text")
	@Column(name = "statut", unique = false, nullable = true)
	private String statut;
	@Column(name = "date", unique = false, nullable = true)
	private String date;
	@ManyToOne
	@JoinColumn(name = "id_enseignant")
	private Enseignant enseignant;
	@OneToMany(mappedBy = "partager")
	private Set<reponsepar> reponsepar;

	public Set<reponsepar> getReponsepar() {
		return reponsepar;
	}

	public void setReponsepar(Set<reponsepar> reponsepar) {
		this.reponsepar = reponsepar;
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

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public partager() {
		super();
	}

	public partager(String titre, String categorie, String statut, String date) {
		super();
		this.titre = titre;
		this.categorie = categorie;
		this.statut = statut;
		this.date = date;

	}

	public String gettime() {

		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		return (df.format(calobj.getTime()));
	}

	public String partage(long x) {

		FacesMessage tmessage = new FacesMessage("partage ajout�� avec succ�s !");
		FacesContext.getCurrentInstance().addMessage(null, tmessage);
		partager partage = new partager(titre, categorie, statut, gettime());
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();
		try {

			Enseignant owner = (Enseignant) session.get(Enseignant.class, x);
			partage.setEnseignant(owner);

			session.save(partage);
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

	public List<partager> liste()

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.getAllpartager();

	}

	public List<reponsepar> recureponsepar(long x)

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.recureppar(x);

	}

	public List<partager> mespartage(long x)

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.mespartage(x);

	}

	public String deletpar(long x) {

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.effacerpar(x);

	}

	public String deletpar1(long x) {

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.effacerparad(x);

	}
	
	
	
	
	
}
