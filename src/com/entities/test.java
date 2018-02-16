package com.entities;

import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dao.UtilisateurDaoImpl;

import com.util.HibernateUtil;

public class test {
	public static void main(String[] args) {
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        
        //Class
        section c = new section() ;
      
        session.save(c);
        session.getTransaction().commit();
        
        session.close();
        
     
        
	}


}
