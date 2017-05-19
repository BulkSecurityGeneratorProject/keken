package fr.invocateam.keken.repository;

import fr.invocateam.keken.domain.Biere;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Biere entity.
 */
@SuppressWarnings("unused")
public interface BiereRepository extends JpaRepository<Biere,Long> {

}
