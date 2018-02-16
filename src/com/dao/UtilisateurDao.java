package com.dao;

import java.util.List;

import com.entities.Activity;
import com.entities.Actualite;
import com.entities.ContactAdmin;
import com.entities.Enseignant;
import com.entities.Etudiant;
import com.entities.Publication;
import com.entities.admin;
import com.entities.envoiactivity;
import com.entities.matiere;
import com.entities.partager;
import com.entities.reponse;
import com.entities.reponsepar;

public interface UtilisateurDao {

	public void addEtudiant(Etudiant etudiant);

	public void addEnseignant(Enseignant enseignant);

	public String authentificationEtud(compt p);

	public void addActivity(Activity activity, long x);

	public void addMessage(ContactAdmin contactAdmin);

	public List<Etudiant> getAllEtudiant();

	public List<Publication> getAllPublication();

	public List<Activity> rechercheact(String x,String y);

	public List<Activity> mesact(long x);

	public List<envoiactivity> recuact(long x);

	public void updatet(compt p);

	public void updaten(compt p);

	public String effacer(long x);

	public boolean effacer1(long x);

	public List<reponse> recurep(long x);

	public List<Publication> mespub(long x);

	public String effacerpar(long x);

	public boolean effacerpar1(long x);

	public List<partager> getAllpartager();

	public List<reponsepar> recureppar(long x);

	public List<partager> mespartage(long x);

	public void addActualite(Actualite actualite, long x);

	public List<Actualite> getAllActualites();

	public String authentificationadmin(admin p);

	public List<ContactAdmin> getAllMessages();

	public List<Activity> getAllactivity();

	public String admineffacer(long x);

	public String effacerpub2(long x);

	public String effacerparad(long x);

	public String effacerreppar(long x);

	public boolean deletenseignant1(long x);

	public String deletenseignant(long x);

	public String deletetudiant(long x);

	public boolean deletetudiant1(long x);

	public List<admin> getAllAdmin();
	public String	deletActualites( long x);
	public String deletMessages(long x);
	public long recherchesec(String groupe , String nom);
	public String  lancermat(String groupe,String nom,long x);
	public long recherchemat(String groupe , String nom);
	public List<matiere> mesmat(long x);
	public boolean deletenseignant4(long x);
	public void effacermatiere(long x);
	
}
