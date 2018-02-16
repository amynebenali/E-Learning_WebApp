package com.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

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
import com.entities.section;
import com.util.HibernateUtil;

public class UtilisateurDaoImpl implements UtilisateurDao {

	public UtilisateurDaoImpl() {

	}

	public String authentificationEtud(compt p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		if (p.getType().equals("Etudiant")) {
			Query query = session.createQuery("from Etudiant u   where u.email=:email1 and u.password=:password1");
			query.setString("email1", p.getEmail());
			query.setString("password1", p.getPassword());
			if (query.list().size() > 0) {

				//
				Iterator etudiant1 = query.iterate();

				while (etudiant1.hasNext()) {
					Etudiant personne = (Etudiant) etudiant1.next();

					p.setNom(personne.getNom());
					p.setPrenom(personne.getPrenom());
					p.setGroupe(personne.getGroupe());
					p.setDate(personne.getDate());
					p.setId(personne.getId());
					p.setSection(personne.getBranche());
					
				}

				return "./vue_etudiant/Home.xhtml?faces-redirect=true";
			}

			return "index";

		}

		{

			Query query = session.createQuery("from Enseignant u   where u.email=:email1 and u.password=:password1");

			query.setString("email1", p.getEmail());
			query.setString("password1", p.getPassword());
			if (query.list().size() > 0) {

				Iterator etudiant1 = query.iterate();

				while (etudiant1.hasNext()) {
					Enseignant personne = (Enseignant) etudiant1.next();

					p.setNom(personne.getNom());
					p.setPrenom(personne.getPrenom());
					
					p.setDate(personne.getDate());
					p.setId(personne.getId_enseignant());

				}

				// p.setConnecte(true);
				return "./vue_enseignant/Home.xhtml?faces-redirect=true";
			}

			return "index";

		}

	}
	//
	
	public long recherchesec(String groupe , String nom) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		
			Query query = session.createQuery("from section u   where u.groupe=:groupe1 and u.nom=:nom1");
			query.setString("groupe1",groupe);
			query.setString("nom1",nom);
			if (query.list().size() > 0) {

				//
				Iterator section1 = query.iterate();

				while (section1.hasNext()) {
					section sec = (section) section1.next();

					   return sec.getId() ;
					
				}

				return 0;
			}

		return 0;

		}
	
	
	
	//

	//

	@Override
	public void addEtudiant(Etudiant etudiant) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			section owner = (section) session.get(section.class,recherchesec(etudiant.getGroupe(),etudiant.getBranche()));
			
			
			etudiant.setSection(owner);
			
			session.save(etudiant);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	@Override
	public void addEnseignant(Enseignant enseignant) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();
		try {

			session.save(enseignant);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	@Override
	public void addActualite(Actualite actualite,long x) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			admin owner = (admin) session.get(admin.class, x);
			actualite.setAdmin(owner);
			
			session.save(actualite);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	@Override
	public void addActivity(Activity activity, long x) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();
		try {
			Enseignant owner = (Enseignant) session.get(Enseignant.class, x);

			activity.setEnseignant(owner);
			session.save(activity);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	public List<Activity> rechercheact(String x,String y)

	{
		List<Activity> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();
		try {
			Query query = session.createQuery("from Activity u   where u.groupe=:groupe1 and u.section=:section");
			query.setString("groupe1", x);
			query.setString("section", y);
			liste = (List<Activity>) query.list();
			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {
			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}

	}

	public List<Activity> mesact(long x)

	{
		List<Activity> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Query query = session.createQuery("from Activity u   where u.enseignant.id_enseignant=:Id");
			query.setParameter("Id", x);
			liste = (List<Activity>) query.list();

			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {
			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}

	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<envoiactivity> recuact(long x)

	{
		List<envoiactivity> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Query query = session.createQuery("from envoiactivity u where u.activity.id_activity=:Id");
			query.setParameter("Id", x);
			liste = (List<envoiactivity>) query.list();

		} catch (RuntimeException e) {
			session.getTransaction().rollback();

		} finally {
			if (session.isOpen()) {
				session.close();

			}

			return liste;
		}

	}

	public void updatet(compt p) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();
		try {

			Etudiant c = new Etudiant();
			c = (Etudiant) session.get(Etudiant.class, p.getId());
			c.setNom(p.getNom());
			c.setPrenom(p.getPrenom());
			c.setPassword(p.getPassword());
			c.setEmail(p.getEmail());
			c.setDate(p.getDate());
			c.setGroupe(p.getGroupe());
			session.update(c);
			session.getTransaction().commit();

		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	public void updaten(compt p) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();
		try {

			Enseignant c = new Enseignant();
			c = (Enseignant) session.get(Enseignant.class, p.getId());
			c.setNom(p.getNom());
			c.setPrenom(p.getPrenom());
			c.setPassword(p.getPassword());
			c.setEmail(p.getEmail());
			c.setDate(p.getDate());
			session.update(c);
			session.getTransaction().commit();

		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	public boolean effacer1(long x)

	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Activity p = (Activity) session.load(Activity.class, x);

			if (p.getEnvois().iterator().hasNext()) {
				envoiactivity c = (envoiactivity) p.getEnvois().iterator().next();

				session.delete(c);
				session.saveOrUpdate(p);
				session.getTransaction().commit();
				return false;
			} else
				return true;
		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	public String effacer(long x) {

		while (!effacer1(x)) {

		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Activity p = (Activity) session.load(Activity.class, x);

			session.delete(p);
			session.getTransaction().commit();
			return "activities?faces-redirect=true";

		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	@Override
	public List<Etudiant> getAllEtudiant() {
		List<Etudiant> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from Etudiant ");

			liste = (List<Etudiant>) query.list();

			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {
			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}
	}

	public void addMessage(ContactAdmin contactAdmin) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			session.save(contactAdmin);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	@Override
	public List<Publication> getAllPublication() {
		List<Publication> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from Publication order by id desc");

			liste = (List<Publication>) query.list();

			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {

			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}
	}

	@Override
	public List<Actualite> getAllActualites() {
		List<Actualite> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from Actualite order by id desc");

			liste = (List<Actualite>) query.list();

			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {

			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<reponse> recurep(long x)

	{
		List<reponse> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Query query = session.createQuery("from reponse u where u.publication.id=:Id");
			query.setParameter("Id", x);
			liste = (List<reponse>) query.list();

		} catch (RuntimeException e) {
			session.getTransaction().rollback();

		} finally {
			if (session.isOpen()) {
				session.close();

			}
			return liste;
		}

	}

	public List<Publication> mespub(long x)

	{
		List<Publication> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from Publication u where u.etudiant.id=:Id order by id desc ");
			query.setParameter("Id", x);
			liste = (List<Publication>) query.list();
			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {
			if (session.isOpen()) {
				session.close();
				return liste;
			}

		}

	}

	public boolean effacerpub1(long x)

	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Publication p = (Publication) session.load(Publication.class, x);

			if (p.getReponse().iterator().hasNext()) {
				reponse c = (reponse) p.getReponse().iterator().next();

				session.delete(c);
				session.saveOrUpdate(p);
				session.getTransaction().commit();
				return false;
			} else
				return true;
		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	public String effacerpub(long x) {

		while (!effacerpub1(x)) {

		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Publication p = (Publication) session.load(Publication.class, x);

			session.delete(p);
			session.getTransaction().commit();
			return "mespub?faces-redirect=true";

		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	public List<partager> getAllpartager() {
		List<partager> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from partager order by id desc");

			liste = (List<partager>) query.list();

			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {

			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<reponsepar> recureppar(long x)

	{
		List<reponsepar> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Query query = session.createQuery("from reponsepar u where u.partager.id=:Id");
			query.setParameter("Id", x);
			liste = (List<reponsepar>) query.list();

		} catch (RuntimeException e) {
			session.getTransaction().rollback();

		} finally {
			if (session.isOpen()) {
				session.close();

			}
			return liste;
		}

	}

	public List<partager> mespartage(long x)

	{
		List<partager> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from partager u where u.enseignant.id=:Id order by id desc ");
			query.setParameter("Id", x);
			liste = (List<partager>) query.list();
			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {
			if (session.isOpen()) {
				session.close();
				return liste;
			}

		}

	}

	public boolean effacerpar1(long x)

	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			partager p = (partager) session.load(partager.class, x);

			if (p.getReponsepar().iterator().hasNext()) {
				reponsepar c = (reponsepar) p.getReponsepar().iterator().next();

				session.delete(c);
				session.saveOrUpdate(p);
				session.getTransaction().commit();
				return false;
			} else
				return true;
		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	public String effacerpar(long x) {

		while (!effacerpar1(x)) {

		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			partager p = (partager) session.load(partager.class, x);

			session.delete(p);
			session.getTransaction().commit();
			return "mespar?faces-redirect=true";

		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	public List<Enseignant> getAllEnseignant() {
		List<Enseignant> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from Enseignant ");

			liste = (List<Enseignant>) query.list();

			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {
			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}
	}

	public String authentificationadmin(admin p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		Query query = session.createQuery("from admin u   where u.email=:email1 and u.password=:password1");
		query.setString("email1", p.getEmail());
		query.setString("password1", p.getPassword());
		if (query.list().size() > 0) {

			//
			Iterator admin1 = query.iterate();

			while (admin1.hasNext()) {
				admin personne = (admin) admin1.next();

				p.setNom(personne.getNom());
				p.setPrenom(personne.getPrenom());

				p.setId_admin(personne.getId_admin());
			}

			return "./vue_admin/Utilisateurs.xhtml?faces-redirect=true";
		}

		return "admin.xhtml";

	}

	public List<Activity> getAllactivity() {
		List<Activity> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from Activity order by id desc");

			liste = (List<Activity>) query.list();

			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {

			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}
	}

	@SuppressWarnings("finally")
	public String admineffacer(long x) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			envoiactivity p = (envoiactivity) session.load(envoiactivity.class, x);

			session.delete(p);
			session.getTransaction().commit();

		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
			return "activitiesrecu?faces-redirect=true";
		}

	}

	@SuppressWarnings("finally")
	public String effacerpub2(long x) {

		while (!effacerpub1(x)) {

		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Publication p = (Publication) session.load(Publication.class, x);

			session.delete(p);
			session.getTransaction().commit();

		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
			return "activities?faces-redirect=true";

		}

	}

	@SuppressWarnings("finally")
	public String effacerrep(long x) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			reponse p = (reponse) session.load(reponse.class, x);

			session.delete(p);
			session.getTransaction().commit();

		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
			return "reponse?faces-redirect=true";
		}

	}

	@SuppressWarnings("finally")
	public String effacerparad(long x) {

		while (!effacerpar1(x)) {

		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			partager p = (partager) session.load(partager.class, x);

			session.delete(p);
			session.getTransaction().commit();

		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}

			return "activities?faces-redirect=true";
		}

	}

	@SuppressWarnings("finally")
	public String effacerreppar(long x) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			reponsepar p = (reponsepar) session.load(reponsepar.class, x);

			session.delete(p);
			session.getTransaction().commit();

		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
			return "reponsepar?faces-redirect=true";
		}

	}

	public List<ContactAdmin> getAllMessages() {
		List<ContactAdmin> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from ContactAdmin ");

			liste = (List<ContactAdmin>) query.list();

			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {
			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}
	}

	
	
	public boolean deletenseignant1(long x)

	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Enseignant p = (Enseignant) session.load(Enseignant.class, x);

			if (p.getActivities().iterator().hasNext()) {
				Activity c = (Activity) p.getActivities().iterator().next();

				effacer(c.getId_activity());
				return false;
			} else
				return true;
		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

	public boolean deletenseignant2(long x)

	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Enseignant p = (Enseignant) session.load(Enseignant.class, x);

			if (p.getPartager().iterator().hasNext()) {
				partager c = (partager) p.getPartager().iterator().next();
				effacerpar(c.getId());
				
				return false;
			} else
				return true;
		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

//
	public boolean deletenseignant4(long x)

	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Enseignant p = (Enseignant) session.load(Enseignant.class, x);

			if (p.getMatiere().iterator().hasNext()) {
				matiere c = (matiere) p.getMatiere().iterator().next();
				effacermatiere(c.getId());
				
				return false;
			} else
				return true;
		}

		catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}

	}

//
public void effacermatiere(long x){
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	if (!session.getTransaction().isActive())
		session.getTransaction().begin();
	try {

		matiere c = new matiere();
		c = (matiere) session.get(matiere.class,x);
		c.setEnseignant(null);
		session.update(c);
		session.getTransaction().commit();

	} catch (RuntimeException e) {
		session.getTransaction().rollback();
		throw e;
	} finally {
		if (session.isOpen()) {
			session.close();
		}
	}
	
}
	
@SuppressWarnings("finally")
public String	deletenseignant (long x)	
{
 	
	while (!deletenseignant1(x))
			{}
	
	while (!deletenseignant2(x))
	{}
	while (!deletenseignant4(x))
	{}

	
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	if (!session.getTransaction().isActive())
		session.getTransaction().begin();

	try {

	Enseignant p = (Enseignant) session.load(Enseignant.class, x);

		session.delete(p);
		session.getTransaction().commit();

	}

	catch (RuntimeException e) {
		session.getTransaction().rollback();
		throw e;
	} finally {
		if (session.isOpen()) {
			session.close();
		}

		return "Utilisateurs.xhtml?faces-redirect=true";
	}
	



}
	
	
public boolean deletetudiant1(long x)

{
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	if (!session.getTransaction().isActive())
		session.getTransaction().begin();

	try {

		Etudiant p = (Etudiant) session.load(Etudiant.class, x);

		if (p.getPublications().iterator().hasNext()) {
			Publication c = (Publication) p.getPublications().iterator().next();
			effacerpub(c.getId());
			
			return false;
		} else
			return true;
	}

	catch (RuntimeException e) {
		session.getTransaction().rollback();
		throw e;
	} finally {
		if (session.isOpen()) {
			session.close();
		}
	}

}


@SuppressWarnings("finally")
public String	deletetudiant (long x)	
{
 	
	while (!deletetudiant1(x))
			{}
	
	

	
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	if (!session.getTransaction().isActive())
		session.getTransaction().begin();

	try {

	Etudiant p = (Etudiant) session.load(Etudiant.class, x);

		session.delete(p);
		session.getTransaction().commit();

	}

	catch (RuntimeException e) {
		session.getTransaction().rollback();
		throw e;
	} finally {
		if (session.isOpen()) {
			session.close();
		}

		return "Utilisateurs.xhtml?faces-redirect=true";
	}
	



}

	public List<admin> getAllAdmin() {
		List<admin> liste = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {
			Query query = session.createQuery("from admin ");

			liste = (List<admin>) query.list();

			return liste;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			return liste;
		} finally {
			if (session.isOpen()) {
				session.close();
				return liste;
			}
		}
	}
	

@SuppressWarnings("finally")
public String	deletActualites( long x)	
{
	
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	if (!session.getTransaction().isActive())
		session.getTransaction().begin();

	try {

	Actualite p = (Actualite) session.load(Actualite.class, x);

		session.delete(p);
		session.getTransaction().commit();

	}

	catch (RuntimeException e) {
		session.getTransaction().rollback();
		throw e;
	} finally {
		if (session.isOpen()) {
			session.close();
		}

		return "publication.xhtml?faces-redirect=true";
	}




}
 	
@SuppressWarnings("finally")
public String deletMessages(long x)
{
	
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	if (!session.getTransaction().isActive())
		session.getTransaction().begin();

	try {

	ContactAdmin p = (ContactAdmin) session.load(ContactAdmin.class, x);

		session.delete(p);
		session.getTransaction().commit();

	}

	catch (RuntimeException e) {
		session.getTransaction().rollback();
		throw e;
	} finally {
		if (session.isOpen()) {
			session.close();
		}

		return "messages.xhtml?faces-redirect=true";
	}




}

public long recherchemat(String groupe , String nom) {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	if (!session.getTransaction().isActive())
		session.getTransaction().begin();

	
		Query query = session.createQuery("from matiere u   where u.groupe=:groupe1 and u.nom=:nom1");
		query.setString("groupe1",groupe);
		query.setString("nom1",nom);
		if (query.list().size() > 0) {

			//
			Iterator section1 = query.iterate();

			while (section1.hasNext()) {
				matiere sec = (matiere) section1.next();

				   return sec.getId() ;
				
			}

			return 0;
		}

	return 0;

	}


@SuppressWarnings("finally")
public String  lancermat(String groupe,String nom,long x)

{
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	if (!session.getTransaction().isActive())
		session.getTransaction().begin();

	try {

		
		
		
		Enseignant owner = (Enseignant) session.get(Enseignant.class,x);
		matiere ma = (matiere) session.get(matiere.class,recherchemat(groupe,nom));
		
		
		ma.setEnseignant(owner);
		
		
		session.update(ma);
		session.getTransaction().commit();
	} catch (RuntimeException e) {
		session.getTransaction().rollback();
		throw e;
	} finally {
		if (session.isOpen()) {
			session.close();
		}

		return "matiere.xhtml?faces-redirect=true";
	}


}

@SuppressWarnings("unchecked")
public List<matiere> mesmat(long x)

{
	List<matiere> liste = null;
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	if (!session.getTransaction().isActive())
		session.getTransaction().begin();

	try {

		Query query = session.createQuery("from matiere u   where u.enseignant.id_enseignant=:Id");
		query.setParameter("Id", x);
		liste = (List<matiere>) query.list();

		return liste;
	} catch (RuntimeException e) {
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
