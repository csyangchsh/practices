package com.csyangchsh.hibernate.demo.eager.entity;

import javax.persistence.*;

/**
 * @Author csyangchsh
 * Date: 14/8/5
 */

@Entity
@Table(name = "Track")
public class Track {

    @Id
    @GeneratedValue
    @Column(name = "TrackId")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Composer")
    private String composer;

    @Column(name = "UnitPrice")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "AlbumId", nullable = true)
    private Album album;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
