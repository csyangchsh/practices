package com.csyangchsh.hibernate.demo.eager.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Table(name = "Artist")
//@Cacheable
//@Cache(region = "ArtistCache", usage = CacheConcurrencyStrategy.READ_ONLY)
public class Artist {

    @Id
    @GeneratedValue
    @Column(name = "ArtistId")
    private Long id;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
    private List<Album> albumList = new ArrayList<Album>();

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

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }
}
