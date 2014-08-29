package com.csyangchsh.hibernate.demo.lazy.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    //@Fetch(FetchMode.SELECT)
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
