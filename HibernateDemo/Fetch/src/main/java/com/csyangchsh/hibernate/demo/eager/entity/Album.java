package com.csyangchsh.hibernate.demo.eager.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "album", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    //@BatchSize(size = 5)
    private List<Track> tracks = new ArrayList<Track>();

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

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
