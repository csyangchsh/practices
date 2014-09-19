package com.csyangchsh.hibernate.one2one.bidirectional;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * @author csyangchsh
 *         Date: 9/15/14
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long id;

    @Column(name = "user_name")
    protected String username;

    @OneToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "shipping_address_id")
    protected Address shippingAddress;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}

