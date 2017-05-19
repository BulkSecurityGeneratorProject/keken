package fr.invocateam.keken.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Commentaire.
 */
@Entity
@Table(name = "commentaire")
@Document(indexName = "commentaire")
public class Commentaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note")
    private Float note;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    private Biere biere;

    @ManyToOne
    private UserExtra user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNote() {
        return note;
    }

    public Commentaire note(Float note) {
        this.note = note;
        return this;
    }

    public void setNote(Float note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Commentaire commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Biere getBiere() {
        return biere;
    }

    public Commentaire biere(Biere biere) {
        this.biere = biere;
        return this;
    }

    public void setBiere(Biere biere) {
        this.biere = biere;
    }

    public UserExtra getUser() {
        return user;
    }

    public Commentaire user(UserExtra userExtra) {
        this.user = userExtra;
        return this;
    }

    public void setUser(UserExtra userExtra) {
        this.user = userExtra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commentaire commentaire = (Commentaire) o;
        if (commentaire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentaire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commentaire{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
