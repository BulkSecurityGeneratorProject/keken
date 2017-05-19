package fr.invocateam.keken.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
@Document(indexName = "userextra")
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "image")
    private String image;

    @OneToOne
    @JoinColumn(unique = true)
    private Biere biereFavorite;

    @OneToMany(mappedBy = "usersLikes")
    @JsonIgnore
    private Set<Biere> bieresLikes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_extra_amis",
               joinColumns = @JoinColumn(name="user_extras_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="amis_id", referencedColumnName="id"))
    private Set<UserExtra> amis = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Commentaire> commentaires = new HashSet<>();

    @ManyToMany(mappedBy = "amis")
    @JsonIgnore
    private Set<UserExtra> users = new HashSet<>();

    @OneToOne
    @MapsId
    private User user;

    public

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public UserExtra image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Biere getBiereFavorite() {
        return biereFavorite;
    }

    public UserExtra biereFavorite(Biere biere) {
        this.biereFavorite = biere;
        return this;
    }

    public void setBiereFavorite(Biere biere) {
        this.biereFavorite = biere;
    }

    public Set<Biere> getBieresLikes() {
        return bieresLikes;
    }

    public UserExtra bieresLikes(Set<Biere> bieres) {
        this.bieresLikes = bieres;
        return this;
    }

    public UserExtra addBieresLikes(Biere biere) {
        this.bieresLikes.add(biere);
        biere.setUsersLikes(this);
        return this;
    }

    public UserExtra removeBieresLikes(Biere biere) {
        this.bieresLikes.remove(biere);
        biere.setUsersLikes(null);
        return this;
    }

    public void setBieresLikes(Set<Biere> bieres) {
        this.bieresLikes = bieres;
    }

    public Set<UserExtra> getAmis() {
        return amis;
    }

    public UserExtra amis(Set<UserExtra> userExtras) {
        this.amis = userExtras;
        return this;
    }

    public UserExtra addAmis(UserExtra userExtra) {
        this.amis.add(userExtra);
        userExtra.getUsers().add(this);
        return this;
    }

    public UserExtra removeAmis(UserExtra userExtra) {
        this.amis.remove(userExtra);
        userExtra.getUsers().remove(this);
        return this;
    }

    public void setAmis(Set<UserExtra> userExtras) {
        this.amis = userExtras;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public UserExtra commentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public UserExtra addCommentaires(Commentaire commentaire) {
        this.commentaires.add(commentaire);
        commentaire.setUser(this);
        return this;
    }

    public UserExtra removeCommentaires(Commentaire commentaire) {
        this.commentaires.remove(commentaire);
        commentaire.setUser(null);
        return this;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Set<UserExtra> getUsers() {
        return users;
    }

    public UserExtra users(Set<UserExtra> userExtras) {
        this.users = userExtras;
        return this;
    }

    public UserExtra addUser(UserExtra userExtra) {
        this.users.add(userExtra);
        userExtra.getAmis().add(this);
        return this;
    }

    public UserExtra removeUser(UserExtra userExtra) {
        this.users.remove(userExtra);
        userExtra.getAmis().remove(this);
        return this;
    }

    public void setUsers(Set<UserExtra> userExtras) {
        this.users = userExtras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExtra userExtra = (UserExtra) o;
        if (userExtra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExtra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            "}";
    }
}
