package fr.invocateam.keken.repository.search;

import fr.invocateam.keken.domain.Biere;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Biere entity.
 */
public interface BiereSearchRepository extends ElasticsearchRepository<Biere, Long> {
}
