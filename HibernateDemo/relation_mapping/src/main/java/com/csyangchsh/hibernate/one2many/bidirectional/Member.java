package com.csyangchsh.hibernate.one2many.bidirectional;

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

    @ManyToOne()
    @JoinColumn(name="club_id")
    protected Club club;

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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String toString() {
        return "Member " + getId() + " Name: " + getName();
    }
}
