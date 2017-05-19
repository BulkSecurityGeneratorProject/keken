package fr.invocateam.keken.repository;

import fr.invocateam.keken.domain.UserExtra;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserExtra entity.
 */
@SuppressWarnings("unused")
public interface UserExtraRepository extends JpaRepository<UserExtra,Long> {

    @Query("select distinct userExtra from UserExtra userExtra left join fetch userExtra.amis")
    List<UserExtra> findAllWithEagerRelationships();

    @Query("select userExtra from UserExtra userExtra left join fetch userExtra.amis where userExtra.id =:id")
    UserExtra findOneWithEagerRelationships(@Param("id") Long id);

}
