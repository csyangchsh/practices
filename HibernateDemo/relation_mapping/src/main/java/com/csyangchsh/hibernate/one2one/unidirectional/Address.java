package com.csyangchsh.hibernate.one2one.unidirectional;

import javax.persistence.*;


/**
 * @author csyangchsh
 *         Date: 9/15/14
 */

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long id;

    @Column(name = "street")
    protected String street;

    @Column(name = "zip_code")
    protected String zipcode;

    @Column(name = "city")
    protected String city;

    public Address() {
    }

    public Address(String street, String zipcode, String city) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
