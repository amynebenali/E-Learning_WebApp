package com.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.hibernate.annotations.Type;
import org.primefaces.model.DefaultStreamedContent;

import com.dao.UtilisateurDaoImpl;

@ManagedBean
@RequestScoped
@Entity
@Table
public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_activity", unique = true, nullable = false)
	private Long id_activity;
	@Column(name = "titre", unique = false, nullable = true)
	private String titre;
	@Column(name = "date", unique = false, nullable = true)
	private Date date;
	@Column(name = "categorie", unique = false, nullable = true)
	private String categorie;
	@Column(name = "groupe", unique = false, nullable = true)
	private String groupe;
	@Column(name = "emplacement", unique = false, nullable = true, columnDefinition = "LONGBLOB")
	private UploadedFile emplacement;
	@Type(type = "text")
	@Column(name = "description", unique = false, nullable = true, columnDefinition = "text")
	private String description;
	@Column(name = "chemin", unique = false, nullable = true)
	private String chemin;

	@Column(name = "matiere", unique = false, nullable = true)
	private String matiere;
	@Column(name = "section", unique = false, nullable = true)
	private String section;
	
	
	public String getMatiere() {
		return matiere;
	}

	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@OneToMany(mappedBy = "activity")
	private Set<envoiactivity> envois;
	@ManyToOne
	@JoinColumn(name = "id_enseignant")
	private Enseignant enseignant;

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public Activity(String titre, Date date, String categorie, String groupe, UploadedFile emplacement,
			String description,String matiere,String section ) {
		super();
		this.titre = titre;
		this.date = date;
		this.categorie = categorie;
		this.groupe = groupe;
		this.emplacement = emplacement;
		this.description = description;
		this.matiere=matiere;
		this.section=section;
		this.chemin = emplacement.getName();
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public Long getId_activity() {
		return id_activity;
	}

	public void setId_activity(Long id_activity) {
		this.id_activity = id_activity;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
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

	public Set<envoiactivity> getEnvois() {
		return envois;
	}

	public void setEnvois(Set<envoiactivity> envois) {
		this.envois = envois;
	}

	public String lancerActivity(long x,String groupe,String matiere,String  section ){
		FacesMessage message = new FacesMessage("L'activit� est ajout�e avec succ�s  !");
		FacesContext.getCurrentInstance().addMessage(null, message);
		;

		Activity activity = new Activity(titre, date, categorie, groupe, emplacement, description,matiere,section);
		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		utilisateur.addActivity(activity, x);
		return "activities?faces-redirect=true";

	}

	//

	public List<Activity> rechercheactivity(String p,String x)

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.rechercheact(p,x);

	}

	public List<Activity> mesactivity(long x)

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.mesact(x);

	}

	public Activity() {
		super();
		// TODO Auto-generated constructor stub
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







	public List<Activity> adminactivity()

	{

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.getAllactivity();

	}












































}
