package com.csyangchsh.hibernate.one2one.unidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * @author csyangchsh
 *         Date: 9/15/14
 */
public class UnidirectionalDemo {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UnidirectionalDemo.class);
    protected final SessionFactory sessionFactory;

    public UnidirectionalDemo() {
        final Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Address.class);

        sessionFactory = configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder().build());
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    public User createCascade() {
        Session session = openSession();
        session.beginTransaction();
        Address address = new Address("Suhui Road", "215021", "Suzhou");
        User user = new User("Alex Chen");
        user.setShippingAddress(address);
        session.save(user); //cascade will save address as well
        session.getTransaction().commit();
        return user;
    }

    public void deleteAddress(User user) {
        Session session = openSession();
        session.beginTransaction();
        //order is detached, need to re-attach it to new session
        session.update(user); //cascade also updates the address
        session.delete(user.getShippingAddress());
        user.setShippingAddress(null);
        session.save(user);
        session.getTransaction().commit();
    }

    public void query() {
        log.debug("Begin query--");
        Session session = openSession();
        List<User> list = session.createQuery(
                "from User u join u.shippingAddress").list();
    }

    public static void main(String[] args) {

        UnidirectionalDemo demo = new UnidirectionalDemo();
        User user = demo.createCascade();
        demo.query();
    }

}
