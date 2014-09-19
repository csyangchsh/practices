package com.csyangchsh.hibernate.one2many.unidirectional.one;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author csyangchsh
 *         Date: 9/16/14
 */
public class UnidirectionalDemo {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UnidirectionalDemo.class);
    protected final SessionFactory sessionFactory;

    public UnidirectionalDemo() {
        final Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Club.class);
        configuration.addAnnotatedClass(Member.class);

        sessionFactory = configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder().build());
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    public void create() {
        Session session = openSession();
        session.beginTransaction();
        Club javaClub = new Club("Java Club");
        Member member1 = new Member("Alex");
        Member member2 = new Member("Tom");
        // relation is uni-directional, only set the relation on one side
        javaClub.getMembers().add(member1);
        javaClub.getMembers().add(member2);
        session.save(javaClub);
        session.getTransaction().commit();
    }

    public static void main(String[] args) {
        UnidirectionalDemo demo = new UnidirectionalDemo();
        //create method leads to 5 queries, 3 inserts and 2 updates
        demo.create();
    }

}
