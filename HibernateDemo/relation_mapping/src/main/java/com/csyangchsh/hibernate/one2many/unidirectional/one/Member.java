package com.csyangchsh.hibernate.one2many.unidirectional.one;

import javax.persistence.*;

/**
 * @author csyangchsh
 *         Date: 9/16/14
 */
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long id;

    @Column(name = "name")
    protected String name;

    public Member(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Member " + getId() + " Name: " + getName();
    }
}
