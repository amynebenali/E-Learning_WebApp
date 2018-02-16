package com.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.dao.UtilisateurDaoImpl;

@ManagedBean
@RequestScoped
@Entity
@Table
public class Actualite implements Serializable {

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
	@Column(name = "article", unique = false, nullable = true)
	private String article;
	@Column(name = "date", unique = false, nullable = true)
	private String date;
	@ManyToOne
	@JoinColumn(name = "id_admin")
	private admin admin ;
	

	public admin getAdmin() {
		return admin;
	}

	public void setAdmin(admin admin) {
		this.admin = admin;
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

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Actualite() {
		super();
	}

	public Actualite(String titre, String categorie, String article, String date) {
		super();
		this.titre = titre;
		this.categorie = categorie;
		this.article = article;
		this.date = gettime();

	}

	public String gettime() {

		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		return (df.format(calobj.getTime()));
	}

	public String pub_actualite(long x) {

		Actualite actualite = new Actualite(titre, categorie, article, date);
		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		utilisateur.addActualite(actualite,x);
		return "publication?faces-redirect=true";

	}

	public List<Actualite> liste()

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.getAllActualites();
	}
	
	public String delet(long x)

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.deletActualites(x);
	}

}
