package com.csyangchsh.hibernate.demo;

import com.csyangchsh.hibernate.demo.entity.Album;
import com.csyangchsh.hibernate.demo.entity.Artist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Get() vs Load()
 *
 * @Author csyangchsh
 * Date: 14/8/4
 */
public class GetLoadDemo {

    protected final SessionFactory sessionFactory;

    public GetLoadDemo() {
        final Configuration configuration = new Configuration();
        configuration.addAnnotatedClass( Artist.class );
        configuration.addAnnotatedClass( Album.class );

        sessionFactory = configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder().build());
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    public void test1() {
        System.out.println("-------Test 1 start----------");

        Session session = openSession();
        session.getTransaction().begin();

        Artist artist = (Artist)session.get(Artist.class, 1L);
        System.out.println("Test 1 get() #1");
        Artist artist2 = (Artist)session.get(Artist.class, 1L);
        System.out.println("Test 1 get() #2 artist2.getName " + artist2.getName());
        System.out.println("artist == artist2 " + (artist == artist2));
        System.out.println(artist2.getClass());
        session.getTransaction().commit();
    }

    public void test2() {
        System.out.println("-------Test 2 start----------");

        Session session = openSession();
        session.getTransaction().begin();

        Artist artist = (Artist)session.load(Artist.class, 1L);
        System.out.println("Test 2 load() #1");
        Artist artist2 = (Artist)session.load(Artist.class, 1L);
        System.out.println("Test 2 load() #2 artist2.getName " + artist2.getName());
        System.out.println("artist == artist2 " + (artist == artist2));
        System.out.println(artist2.getClass());
        session.getTransaction().commit();
    }

    public void test3() {
        System.out.println("-------Test 3 start----------");

        Session session = openSession();
        session.getTransaction().begin();

        Artist artist = (Artist)session.get(Artist.class, 1L);
        System.out.println("Test 3 get() ");
        Artist artist2 = (Artist)session.load(Artist.class, 1L);
        System.out.println("Test 3 load() ");
        System.out.println("artist == artist2 " + (artist == artist2));
        System.out.println(artist2.getClass());
        session.getTransaction().commit();
    }

    public void test4() {
        System.out.println("-------Test 4 start----------");

        Session session = openSession();
        session.getTransaction().begin();

        Artist artist = (Artist)session.load(Artist.class, 1L);
        System.out.println("Test 4 load() ");
        Artist artist2 = (Artist)session.get(Artist.class, 1L);
        System.out.println("Test 4 get() " + artist2.getName());
        System.out.println("artist == artist2 " + (artist == artist2));
        System.out.println(artist2.getClass());
        session.getTransaction().commit();
    }

    public void test5() {
        System.out.println("-------Test 5 start----------");

        Session session = openSession();
        session.getTransaction().begin();

        Artist artist = (Artist)session.load(Artist.class, 1000L);
        try {
            artist.getName();
        } catch (Exception e) {
            System.out.println(e.getClass());
        }

        Artist artist2 = (Artist)session.get(Artist.class, 1000L);
        try {
            artist2.getName();
        } catch (Exception e) {
            System.out.println(e.getClass());
        }

        session.getTransaction().commit();
    }

    public static void main(String[] args) {
        GetLoadDemo demo = new GetLoadDemo();
        demo.test1();
        demo.test2();
        demo.test3();
        demo.test4();
        demo.test5();
    }
}
