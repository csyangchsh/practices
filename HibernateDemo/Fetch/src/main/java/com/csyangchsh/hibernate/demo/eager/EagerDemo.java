package com.csyangchsh.hibernate.demo.eager;

import com.csyangchsh.hibernate.demo.eager.entity.Album;
import com.csyangchsh.hibernate.demo.eager.entity.Artist;
import com.csyangchsh.hibernate.demo.eager.entity.Track;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @Author csyangchsh
 * Date: 14/8/5
 */
public class EagerDemo {

    private final SessionFactory sessionFactory;


    public EagerDemo() {
        final Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Artist.class);
        configuration.addAnnotatedClass(Album.class);
        configuration.addAnnotatedClass(Track.class);
        sessionFactory = configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder().build() );;
    }

    public void test() {
        Session session = openSession();
        session.getTransaction().begin();

        Artist artist = (Artist)session.get(Artist.class, 22L);

        session.getTransaction().commit();
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    public static void main(String[] args) {
        EagerDemo demo = new EagerDemo();
        demo.test();
    }
}
