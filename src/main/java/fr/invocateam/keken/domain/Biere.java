package fr.invocateam.keken.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fr.invocateam.keken.domain.enumeration.TypeBiere;

/**
 * A Biere.
 */
@Entity
@Table(name = "biere")
@Document(indexName = "biere")
public class Biere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeBiere type;

    @Column(name = "degree_alc")
    private Float degreeAlc;

    @Column(name = "brasseur")
    private String brasseur;

    @Column(name = "pays")
    private String pays;

    @Column(name = "ville")
    private String ville;

    @Column(name = "image")
    private String image;

    @OneToOne(mappedBy = "biereFavorite")
    @JsonIgnore
    private UserExtra usersFavs;

    @ManyToOne
    private UserExtra usersLikes;

    @OneToMany(mappedBy = "biere")
    @JsonIgnore
    private Set<Commentaire> commentaires = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Biere nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeBiere getType() {
        return type;
    }

    public Biere type(TypeBiere type) {
        this.type = type;
        return this;
    }

    public void setType(TypeBiere type) {
        this.type = type;
    }

    public Float getDegreeAlc() {
        return degreeAlc;
    }

    public Biere degreeAlc(Float degreeAlc) {
        this.degreeAlc = degreeAlc;
        return this;
    }

    public void setDegreeAlc(Float degreeAlc) {
        this.degreeAlc = degreeAlc;
    }

    public String getBrasseur() {
        return brasseur;
    }

    public Biere brasseur(String brasseur) {
        this.brasseur = brasseur;
        return this;
    }

    public void setBrasseur(String brasseur) {
        this.brasseur = brasseur;
    }

    public String getPays() {
        return pays;
    }

    public Biere pays(String pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public Biere ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getImage() {
        return image;
    }

    public Biere image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserExtra getUsersFavs() {
        return usersFavs;
    }

    public Biere usersFavs(UserExtra userExtra) {
        this.usersFavs = userExtra;
        return this;
    }

    public void setUsersFavs(UserExtra userExtra) {
        this.usersFavs = userExtra;
    }

    public UserExtra getUsersLikes() {
        return usersLikes;
    }

    public Biere usersLikes(UserExtra userExtra) {
        this.usersLikes = userExtra;
        return this;
    }

    public void setUsersLikes(UserExtra userExtra) {
        this.usersLikes = userExtra;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public Biere commentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public Biere addCommentaires(Commentaire commentaire) {
        this.commentaires.add(commentaire);
        commentaire.setBiere(this);
        return this;
    }

    public Biere removeCommentaires(Commentaire commentaire) {
        this.commentaires.remove(commentaire);
        commentaire.setBiere(null);
        return this;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Biere biere = (Biere) o;
        if (biere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), biere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Biere{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", type='" + getType() + "'" +
            ", degreeAlc='" + getDegreeAlc() + "'" +
            ", brasseur='" + getBrasseur() + "'" +
            ", pays='" + getPays() + "'" +
            ", ville='" + getVille() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
