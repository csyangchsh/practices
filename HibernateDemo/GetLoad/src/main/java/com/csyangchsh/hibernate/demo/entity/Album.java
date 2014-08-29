package com.csyangchsh.hibernate.demo.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @Author csyangchsh
 * Date: 14/8/1
 */
@Entity
@Table(name = "Album")
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Album {

    @Id
    @GeneratedValue
    @Column(name = "AlbumId")
    private Long id;

    @Column(name = "Title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "ArtistId", nullable = true)
    private Artist artist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
