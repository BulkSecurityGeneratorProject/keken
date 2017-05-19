package fr.invocateam.keken.repository.search;

import fr.invocateam.keken.domain.UserExtra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserExtra entity.
 */
public interface UserExtraSearchRepository extends ElasticsearchRepository<UserExtra, Long> {
}
