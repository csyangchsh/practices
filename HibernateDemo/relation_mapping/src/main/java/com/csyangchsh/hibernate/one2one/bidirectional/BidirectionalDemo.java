package com.csyangchsh.hibernate.one2one.bidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * @author csyangchsh
 *         Date: 9/15/14
 */
public class BidirectionalDemo {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BidirectionalDemo.class);
    protected final SessionFactory sessionFactory;

    public BidirectionalDemo() {
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
        //set on both sides
        address.setUser(user);
        session.save(user); //cascade will save address as well
        session.getTransaction().commit();
        return user;
    }

    public void query() {
        log.debug("--Begin query--");
        Session session = openSession();
        List<User> list = session.createQuery(
                "from User u join u.shippingAddress").list();

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

    public static void main(String[] args) {

        BidirectionalDemo demo = new BidirectionalDemo();
        User user = demo.createCascade();
        demo.query();
        //demo.deleteAddress(user);
    }

}
