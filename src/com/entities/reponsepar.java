package com.entities;

import java.io.Serializable;
import java.util.Date;

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

import org.hibernate.Session;
import org.hibernate.annotations.Type;

import com.util.HibernateUtil;

@ManagedBean
@RequestScoped
@Entity
@Table

public class reponsepar implements Serializable {

	private static final long serialVersionUID = 65854151L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_reponsepar", unique = true, nullable = false)
	private Long id_reponsepar;

	@Column(name = "date", unique = false, nullable = true)
	private Date date;

	@Type(type = "text")
	@Column(name = "description", unique = false, nullable = true, columnDefinition = "text")
	private String description;
	@Column(name = "nomprof", unique = false, nullable = true)
	private String nomprof;
	@Column(name = "prenomprof", unique = false, nullable = true)
	private String prenomprof;
	@ManyToOne
	@JoinColumn(name = "id")
	private partager partager;

	public Long getId_reponsepar() {
		return id_reponsepar;
	}

	public void setId_reponsepar(Long id_reponsepar) {
		this.id_reponsepar = id_reponsepar;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNomprof() {
		return nomprof;
	}

	public void setNomprof(String nomprof) {
		this.nomprof = nomprof;
	}

	public String getPrenomprof() {
		return prenomprof;
	}

	public void setPrenomprof(String prenomprof) {
		this.prenomprof = prenomprof;
	}

	public partager getPartager() {
		return partager;
	}

	public void setPartager(partager partager) {
		this.partager = partager;
	}

	public reponsepar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public reponsepar(String nomprof, Date date, String prenomprof, String description) {
		super();
		this.nomprof = nomprof;
		this.date = date;
		this.prenomprof = prenomprof;
		this.description = description;
	}

	public String envoireponsepar(String x, String y, long z) {

		date = new Date();
		this.setNomprof(x);
		this.setPrenomprof(y);

		reponsepar rep = new reponsepar(nomprof, date, prenomprof, description);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			partager owner = (partager) session.get(partager.class, z);
			rep.setPartager(owner);
			session.save(rep);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();

				return "reponsepar?faces-redirect=true";
			}
		}

		return "reponsepar?faces-redirect=true";
	}

}
