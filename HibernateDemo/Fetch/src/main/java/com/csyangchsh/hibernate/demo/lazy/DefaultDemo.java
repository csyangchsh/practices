package com.csyangchsh.hibernate.demo.lazy;

import com.csyangchsh.hibernate.demo.lazy.entity.Album;
import com.csyangchsh.hibernate.demo.lazy.entity.Artist;
import com.csyangchsh.hibernate.demo.lazy.entity.Track;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author csyangchsh
 * Date: 14/8/5
 */
public class DefaultDemo {

    private final SessionFactory sessionFactory;


    public DefaultDemo() {
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
        List<Album> albums = (List<Album>) artist.getAlbumList();
        //albums.size();

        session.getTransaction().commit();
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    public static void main(String[] args) {
        DefaultDemo demo = new DefaultDemo();
        demo.test();
    }
}
