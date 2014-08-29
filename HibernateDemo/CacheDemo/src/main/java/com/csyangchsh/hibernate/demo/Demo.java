package com.csyangchsh.hibernate.demo;

import com.csyangchsh.hibernate.demo.entity.Album;
import com.csyangchsh.hibernate.demo.entity.Artist;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Query cache and second level cache demo
 *
 * Enable query cache and second level cache
 * timeToLiveSeconds="5" in ehcache.xml
 *
 * Query cache hitting but second level cache missing, hibernate will generate db query for each entity.
 *
 * @Author csyangchsh
 * Date: 14/8/1
 */
public class Demo {

    protected final SessionFactory sessionFactory;

    public Demo() {
        final Configuration configuration = new Configuration();
        configuration.addAnnotatedClass( Artist.class );
        configuration.addAnnotatedClass( Album.class );

        sessionFactory = configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder().build());
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    public void printStats() {

        System.out.println("query cache put count: " + sessionFactory.getStatistics().getQueryCachePutCount());
        System.out.println("query cache hit count: " + sessionFactory.getStatistics().getQueryCacheHitCount());
        System.out.println("query cache miss count: " + sessionFactory.getStatistics().getQueryCacheMissCount());
        System.out.println("second level cache put count: " + sessionFactory.getStatistics().getSecondLevelCachePutCount());
        System.out.println("second level cache hit count: " + sessionFactory.getStatistics().getSecondLevelCacheHitCount());
        System.out.println("second level cache miss count: " + sessionFactory.getStatistics().getSecondLevelCacheMissCount());
    }

    public List<Artist> getArtists() {
        final Session s = openSession();
        s.getTransaction().begin();
        final Query q = s.createQuery("FROM Artist");
        q.setCacheable( true );
        final List<Artist> artists = q.list();
        s.getTransaction().commit();
        return artists;
    }

    public static void main(String[] args) throws InterruptedException {
        final Demo demo = new Demo();

        System.out.println("GET Artists, ATTEMPT #1");
        // DB hit
        List<Artist> artists = demo.getArtists();
        demo.printStats();

        System.out.println("GET Artists, ATTEMPT #2");
        // query cache hit
        artists = demo.getArtists();
        demo.printStats();

        // timeToLiveSeconds="5" in ehcache.xml
        Thread.sleep(5000);
        System.out.println("GET Artists, ATTEMPT #3");
        // query cache hit, second level cache missing
        artists = demo.getArtists();
        demo.printStats();
    }
}
