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

public class reponse implements Serializable {

	private static final long serialVersionUID = 6547851L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_reponse", unique = true, nullable = false)
	private Long id_reponse;

	public Long getId_reponse() {
		return id_reponse;
	}

	public void setId_reponse(Long id_reponse) {
		this.id_reponse = id_reponse;
	}

	public String getPrenonetudiant() {
		return prenonetudiant;
	}

	public void setPrenonetudiant(String prenonetudiant) {
		this.prenonetudiant = prenonetudiant;
	}

	@Column(name = "date", unique = false, nullable = true)
	private Date date;

	@Type(type = "text")
	@Column(name = "description", unique = false, nullable = true, columnDefinition = "text")
	private String description;
	@Column(name = "nometudiant", unique = false, nullable = true)
	private String nometudiant;
	@Column(name = "prenonetudiant", unique = false, nullable = true)
	private String prenonetudiant;
	@ManyToOne
	@JoinColumn(name = "id")
	private Publication publication;

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

	public String getNometudiant() {
		return nometudiant;
	}

	public void setNometudiant(String nometudiant) {
		this.nometudiant = nometudiant;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public reponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public reponse(String nometudiant, Date date, String prenonetudiant, String description) {
		super();
		this.nometudiant = nometudiant;
		this.date = date;
		this.prenonetudiant = prenonetudiant;
		this.description = description;
	}

	public String envoireponse(String x, String y, long z) {

		date = new Date();
		this.setNometudiant(x);
		this.setPrenonetudiant(y);

		reponse rep = new reponse(nometudiant, date, prenonetudiant, description);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		if (!session.getTransaction().isActive())
			session.getTransaction().begin();

		try {

			Publication owner = (Publication) session.get(Publication.class, z);
			rep.setPublication(owner);
			session.save(rep);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		} finally {
			if (session.isOpen()) {
				session.close();

				return "reponse?faces-redirect=true";
			}
		}

		return "reponse?faces-redirect=true";
	}

}
