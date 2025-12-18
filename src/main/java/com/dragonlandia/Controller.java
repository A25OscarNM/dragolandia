package com.dragonlandia;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.dragonlandia.modelo.Bosque;
import com.dragonlandia.modelo.Conjuro;
import com.dragonlandia.modelo.Dragon;
import com.dragonlandia.modelo.Hechizo;
import com.dragonlandia.modelo.Mago;
import com.dragonlandia.modelo.Monstruo;
import com.dragonlandia.modelo.TipoMonstruo;
import com.dragonlandia.vista.DragoVista;
import com.dragonlandia.vista.SessionInstancia;

public class Controller {

    // IMPORTANTE
    // HACER SINGLETON CON EL SESSION FACTORY
    private DragoVista vista;
    private SessionFactory factory;

    public Controller() {
        this.vista = new DragoVista(this);
        this.vista.setVisible(true);
        factory = SessionInstancia.getInstance();
    }

    public Mago crearMago(String nombre, int vida, int nivelMagia) {
        Session session = null;

        Mago mago = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            mago = new Mago(0, nombre, vida, nivelMagia);
            session.persist(mago);
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return mago;
    }

    public Monstruo crearMonstruo(String nombre, int vida, int tipo) {
        Session session = null;

        Monstruo monstruo = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            monstruo = new Monstruo(0, nombre, vida, TipoMonstruo.values()[tipo]);
            session.persist(monstruo);
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return monstruo;
    }

    public Bosque crearBosque(String nombre, int nivelPeligro, Monstruo monstruoJefe, List<Monstruo> listaMonstruos) {
        Session session = null;
        Bosque bosque = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            bosque = new Bosque(0, nombre, nivelPeligro, monstruoJefe, listaMonstruos);
            session.persist(bosque);
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return bosque;
    }

    public void crearDragon(String nombre, int intensidadFuego, int resistencia, Bosque bosque) {
        Session session = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            Dragon dragon = new Dragon(0, nombre, intensidadFuego, resistencia, bosque);
            session.persist(dragon);
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    public void crearConjuro(Mago mago, Hechizo hechizo) {
        Session session = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            Conjuro conjuro = new Conjuro(0, mago, hechizo);
            session.persist(conjuro);
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    public Hechizo crearHechizo(String nombre) {
        Session session = null;

        Hechizo hechizo = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            hechizo = new Hechizo(0, nombre);
            session.persist(hechizo);
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return hechizo;
    }

    public static ArrayList<Monstruo> getMonstruos() {
        Session session = null;
        ArrayList<Monstruo> listaMonstruos = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            listaMonstruos = (ArrayList<Monstruo>) session.createQuery("from Monstruo", Monstruo.class).list();
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return listaMonstruos;
    }

    public static ArrayList<Bosque> getBosques() {
        Session session = null;
        ArrayList<Bosque> listaBosques = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            listaBosques = (ArrayList<Bosque>) session.createQuery("from Bosque", Bosque.class).list();
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return listaBosques;
    }

    public static ArrayList<Hechizo> getHechizos() {
        Session session = null;
        ArrayList<Hechizo> listaHechizos = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            listaHechizos = (ArrayList<Hechizo>) session.createQuery("from Hechizo", Hechizo.class).list();
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return listaHechizos;
    }

    public static ArrayList<Monstruo> getMonstruosPosibles() {
        Session session = null;
        ArrayList<Monstruo> listaMonstruos = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            listaMonstruos = (ArrayList<Monstruo>) session.createQuery("SELECT * from Monstruo ", Monstruo.class)
                    .list();
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return listaMonstruos;
    }

    public void showAllMonstruos() {
        ArrayList<Monstruo> monstruos = getMonstruos();
        for (Monstruo monstruo : monstruos) {
            vista.getPanelLateral().addMonstruo(monstruo.getId(), monstruo.getNombre(), monstruo.getVida(),
                    monstruo.getTipo().ordinal(),
                    monstruo.getFuerza());
        }
    }

    public Monstruo buscarMonstruo(int id) {
        Session session = null;
        Monstruo monstruo = null;

        try (SessionFactory factory = (new Configuration()).configure().buildSessionFactory();) {

            session = factory.getCurrentSession();
            Transaction tx = session.beginTransaction();
            monstruo = session
                    .createQuery("SELECT monstruo from Monstruo monstruo WHERE monstruo.id = :id", Monstruo.class)
                    .setParameter("id", id)
                    .getSingleResult();
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return monstruo;
    }

    // Obtener los monstruos que no sean jefes ya, obtener todos los monstruos que
    // no sean parte de un bosque
}
