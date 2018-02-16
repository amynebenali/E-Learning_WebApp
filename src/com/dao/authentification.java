package com.dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.entities.admin;

@ManagedBean
@SessionScoped

public class authentification implements Serializable {

	private static final long serialVersionUID = 10948018252286363L;
	private String type;
	private String prenom;
	private String nom;
	private String email;
	private String password;
	private Date date;
	private String groupe;
	private long id;
	private String matiere;
	private long z;
	private long c;
	private String g;
	private long i;
	private long en;
	public long getEn() {
		return en;
	}

	public void setEn(long en) {
		this.en = en;
	}

	public long getVar() {
		return var;
	}

	public void setVar(long var) {
		this.var = var;
	}

	private long var ;
private String mat;
private String section;
public String getSection() {
	return section;
}

public void setSection(String section) {
	this.section = section;
}

public String getMat() {
	return mat;
}

public void setMat(String mat) {
	this.mat = mat;
}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public long getI() {
		return i;
	}

	public void setI(long i) {
		this.i = i;
	}

	public long getC() {
		return c;
	}

	public void setC(long c) {
		this.c = c;
	}

	public long getZ() {
		return z;
	}

	public void setZ(long z) {
		this.z = z;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMatiere() {
		return matiere;
	}

	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}

	private long x;
	private long y;

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	
	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date2) {
		this.date = date2;
	}

	public authentification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public authentification(String password, String email, String type) {
		super();
		this.password = password;
		this.type = type;
		this.email = email;

	}

	public String connection() {

		String x;
		String y;
		y = type;

		FacesMessage message = new FacesMessage(y);
		FacesContext.getCurrentInstance().addMessage(null, message);
		compt connect = new compt(password, email, type);
		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();

		x = utilisateur.authentificationEtud(connect) + "?faces-redirect=true";

		if (type.equals("Etudiant"))
			{this.setGroupe(connect.getGroupe());
			this.section=connect.getSection();
			
			}
		else
			this.setMatiere(connect.getMatiere());

		this.setNom(connect.getNom());
		this.setPrenom(connect.getPrenom());
		this.setId(connect.getId());
		this.setDate(connect.getDate());

		return x;

	}

	public String deconnection() {

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";

	}

	public void checkAlreadyLoggedin() throws IOException {

		if (this.email == null)

		{
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
		}
	}

	public String test(long id)

	{
		this.x = id;

		return "envoi?faces-redirect=true";
	}

	public String test1(long id)

	{
		this.y = id;

		return "activitiesrecu?faces-redirect=true";
	}

	public String updatet(Long id, String nom, String prenom, String password, String email, Date date, String groupe) {
		compt comt = new compt();

		comt.charger(nom, prenom, password, email, groupe, type, id, date);

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();

		utilisateur.updatet(comt);

		this.setNom(comt.getNom());
		this.setPrenom(comt.getPrenom());
		this.setPassword(comt.getPassword());
		this.email = comt.getEmail();
		this.date = comt.getDate();
		this.groupe = comt.getGroupe();

		return "Home?faces-redirect=true";
	}

	public String updaten(Long id, String nom, String prenom, String password, String email, Date date) {
		compt comt = new compt();

		comt.charger(nom, prenom, password, email, groupe, type, id, date);

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();

		utilisateur.updaten(comt);

		this.setNom(comt.getNom());
		this.setPrenom(comt.getPrenom());
		this.setPassword(comt.getPassword());
		this.email = comt.getEmail();
		this.date = comt.getDate();
	

		return "Home?faces-redirect=true";
	}

	public String deletact(long x) {

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.effacer(x);

	}

	public String reponse(long y) {
		this.z = y;
		return "reponse?faces-redirect=true";

	}

	public String reponsepar(long y) {
		this.c = y;
		return "reponsepar?faces-redirect=true";

	}
	
	public String matiereenseignant(long y) {
		this.var = y;
		return "matiere?faces-redirect=true";

	}
	
	
	public String adminconnection() {
		String x;
					
				admin connect = new admin(password, email);
				UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
				this.setType(null);
				x = utilisateur.authentificationadmin(connect) + "?faces-redirect=true";

		if ( !x.equals("admin.xhtml?faces-redirect=true"))		
		{
				this.setNom(connect.getNom());
				this.setPrenom(connect.getPrenom());
				this.setId(connect.getId_admin());
				
		}
				return x;

			}


	public String deletact1(long x) {

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.admineffacer(x);

	}


	public String deletpub1(long x) {

		UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
		return utilisateur.effacerpub2(x);

	}

public String deletrep(long x)
{
	UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
	return utilisateur.effacerrep(x);


}

public String deletreppar(long x)
{
	UtilisateurDaoImpl utilisateur = new UtilisateurDaoImpl();
	return utilisateur.effacerreppar(x);


}

public String deconnection1() {

	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	return "/admin.xhtml?faces-redirect=true";

}

public void checkAlreadyLoggedin1() throws IOException {

	if (  this.type!=null)

	{ 
	    
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/admin.xhtml");
		
	
	}

	else if (  this.email==null )
	{            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	             ec.redirect(ec.getRequestContextPath() + "/admin.xhtml");
	}
	
	
	
		
}




 public String  meseleves(long id , String groupe)
 {
	 this.i=id;
	 this.g=groupe;
	 return "mystudent?faces-redirect=true";
 }

 public String  envoiact(String matiere, String groupe, String section)
 {
	 this.mat=matiere;
	 this.g=groupe;
	 this.section=section;
	 return "envoiactivity?faces-redirect=true";
 }
 
 public String matiereenseigner(long x)
 {
	 this.en=x;
	 return"matiereenseignant?faces-redirect=true";
	 
 }
 
}
