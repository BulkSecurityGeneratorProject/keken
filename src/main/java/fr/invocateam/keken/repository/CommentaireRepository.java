package fr.invocateam.keken.repository;

import fr.invocateam.keken.domain.Commentaire;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Commentaire entity.
 */
@SuppressWarnings("unused")
public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {

}
