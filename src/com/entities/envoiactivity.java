package com.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.primefaces.model.DefaultStreamedContent;

import com.dao.UtilisateurDaoImpl;
import com.util.HibernateUtil;

@ManagedBean
@RequestScoped
@Entity
@Table

public class envoiactivity implements Serializable {

	private static final long serialVersionUID = 545455441L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "nom", unique = false, nullable = true)
	private String nom;
	@Column(name = "prenom", unique = false, nullable = true)
	private String prenom;
	@Column(name = "date", unique = false, nullable = true)
	private Date date;
	@Column(name = "emplacement", unique = false, nullable = true, columnDefinition = "LONGBLOB")
	private UploadedFile emplacement;
	@Column(name = "chemin", unique = false, nullable = true)
	private String chemin;

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	@Type(type = "text")
	@Column(name = "description", unique = false, nullable = true, columnDefinition = "text")
	private String description;
	@ManyToOne
	@JoinColumn(name = "id_activity")
	private Activity activity;

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UploadedFile getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(UploadedFile emplacement) {
		this.emplacement = emplacement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public envoiactivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public envoiactivity(String nom, Date date, String prenom, UploadedFile emplacement, String description) {
		super();
		this.nom = nom;
		this.date = date;
		this.prenom = prenom;
		this.emplacement = emplacement;
		this.description = description;
		this.chemin = emplacement.getName();
	}

	public String lancerenvoi(String x, String y, long z) {

		date = new Date();
		this.setNom(x);
		this.setPrenom(y);

		FacesMessage message = new FacesMessage("Ajout effectué avec succés  !");
		FacesContext.getCurrentInstance().addMessage(null, message);

		envoiactivity eactivity = new envoiactivity(nom, date, prenom, emplacement, description);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Activity owner = (Activity) session.get(Activity.class, z);
			eactivity.setActivity(owner);

			session.save(eactivity);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

		return "activities?faces-redirect=true";
	}

	public List<envoiactivity> recuactivity(long x)

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.recuact(x);

	}

	private DefaultStreamedContent download;

	public void setDownload(DefaultStreamedContent download) {
		this.download = download;
	}

	public DefaultStreamedContent getDownload() throws Exception {
		System.out.println("GET = " + download.getName());
		return download;
	}

	public void prepDownload(String path) throws Exception {
		File file = new File(path);
		InputStream input = new FileInputStream(file);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
		System.out.println("PREP = " + download.getName());

	}

}
