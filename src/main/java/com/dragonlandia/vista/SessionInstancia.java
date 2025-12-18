package com.dragonlandia.vista;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionInstancia {

    private static SessionFactory factory;

    private SessionInstancia() {

    }

    public static SessionFactory getInstance() {
        if (factory == null) {
            synchronized (SessionInstancia.class) {
                if (factory == null) {
                    factory = (new Configuration()).configure().buildSessionFactory();
                }
            }
        }
        return factory;
    }
}