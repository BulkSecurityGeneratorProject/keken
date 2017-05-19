package fr.invocateam.keken.repository.search;

import fr.invocateam.keken.domain.Commentaire;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Commentaire entity.
 */
public interface CommentaireSearchRepository extends ElasticsearchRepository<Commentaire, Long> {
}
