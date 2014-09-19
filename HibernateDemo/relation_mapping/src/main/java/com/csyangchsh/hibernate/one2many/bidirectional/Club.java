package com.csyangchsh.hibernate.one2many.bidirectional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author csyangchsh
 *         Date: 9/16/14
 */
@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long id;

    @Column(name = "name")
    protected String name;

    @OneToMany(mappedBy="club")
    private Set<Member> members = new HashSet<Member>();

    public Club(String name) {
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

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public String toString() {
        return "Club " + getId() + " Name: " + getName();
    }
}
