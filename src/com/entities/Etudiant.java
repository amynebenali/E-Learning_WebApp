package com.entities;

import java.io.Serializable;
import java.util.Date;
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

import org.hibernate.Query;
import org.hibernate.Session;

import com.dao.UtilisateurDaoImpl;
import com.util.HibernateUtil;

@ManagedBean
@RequestScoped
@Entity
@Table
public class Etudiant implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "nom", unique = false, nullable = true)
	private String nom;
	@Column(name = "prenom", unique = false, nullable = true)
	private String prenom;
	@Column(name = "groupe", unique = false, nullable = true)
	private String groupe;
	@Column(name = "password", unique = false, nullable = true)
	private String password;
	@Column(name = "email", unique = false, nullable = true)
	private String email;
	@Column(name = "branche", unique = false, nullable = true)
	private String branche;
	public String getBranche() {
		return branche;
	}

	public void setBranche(String branche) {
		this.branche = branche;
	}

	@Column(name = "date", unique = false, nullable = true)
	private Date date;
     	@OneToMany(mappedBy = "etudiant")
	    private Set<Publication> publications;
	
     	
	 @ManyToOne
	 @JoinColumn(name = "id_section")
	 private section section;
	
	

	public section getSection() {
		return section;
	}

	public void setSection(section section) {
		this.section = section;
	}

	public Set<Publication> getPublications() {
		return publications;
	}

	public void setPublications(Set<Publication> publications) {
		this.publications = publications;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public Etudiant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Etudiant(String nom, String prenom, String groupe, String password, String email, Date date, String branche) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.email = email;
		this.date = date;
		this.groupe = groupe;
		this.branche=branche;
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
		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();

		FacesMessage message = new FacesMessage("Succes de l'inscription !");
		FacesContext.getCurrentInstance().addMessage(null, message);
		Etudiant etudiant = new Etudiant(nom, prenom, groupe, password, email, date,branche);

		utilisateur.addEtudiant(etudiant);

		return "index";

	}

	public List<Etudiant> liste()

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.getAllEtudiant();

	}

	public String delet(long x)

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.deletetudiant(x);

	}





@SuppressWarnings("unchecked")
public	List<Etudiant>	mes( long x, String g)	
	
	{
		List<Etudiant> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Query query = session.createQuery("from Etudiant u   where u.groupe=:g1 and u.section.id=:id1");
			query.setString("g1",g);
			query.setParameter("id1",x);
			liste = (List<Etudiant>) query.list();
			return liste;
		} 
		catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {
			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}

	}

		
		
	

}
