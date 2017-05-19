package fr.invocateam.keken.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.invocateam.keken.domain.Biere;

import fr.invocateam.keken.repository.BiereRepository;
import fr.invocateam.keken.repository.search.BiereSearchRepository;
import fr.invocateam.keken.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Biere.
 */
@RestController
@RequestMapping("/api")
public class BiereResource {

    private final Logger log = LoggerFactory.getLogger(BiereResource.class);

    private static final String ENTITY_NAME = "biere";
        
    private final BiereRepository biereRepository;

    private final BiereSearchRepository biereSearchRepository;

    public BiereResource(BiereRepository biereRepository, BiereSearchRepository biereSearchRepository) {
        this.biereRepository = biereRepository;
        this.biereSearchRepository = biereSearchRepository;
    }

    /**
     * POST  /bieres : Create a new biere.
     *
     * @param biere the biere to create
     * @return the ResponseEntity with status 201 (Created) and with body the new biere, or with status 400 (Bad Request) if the biere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bieres")
    @Timed
    public ResponseEntity<Biere> createBiere(@Valid @RequestBody Biere biere) throws URISyntaxException {
        log.debug("REST request to save Biere : {}", biere);
        if (biere.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new biere cannot already have an ID")).body(null);
        }
        Biere result = biereRepository.save(biere);
        biereSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bieres : Updates an existing biere.
     *
     * @param biere the biere to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated biere,
     * or with status 400 (Bad Request) if the biere is not valid,
     * or with status 500 (Internal Server Error) if the biere couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bieres")
    @Timed
    public ResponseEntity<Biere> updateBiere(@Valid @RequestBody Biere biere) throws URISyntaxException {
        log.debug("REST request to update Biere : {}", biere);
        if (biere.getId() == null) {
            return createBiere(biere);
        }
        Biere result = biereRepository.save(biere);
        biereSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, biere.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bieres : get all the bieres.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of bieres in body
     */
    @GetMapping("/bieres")
    @Timed
    public List<Biere> getAllBieres(@RequestParam(required = false) String filter) {
        if ("usersfavs-is-null".equals(filter)) {
            log.debug("REST request to get all Bieres where usersFavs is null");
            return StreamSupport
                .stream(biereRepository.findAll().spliterator(), false)
                .filter(biere -> biere.getUsersFavs() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Bieres");
        List<Biere> bieres = biereRepository.findAll();
        return bieres;
    }

    /**
     * GET  /bieres/:id : get the "id" biere.
     *
     * @param id the id of the biere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the biere, or with status 404 (Not Found)
     */
    @GetMapping("/bieres/{id}")
    @Timed
    public ResponseEntity<Biere> getBiere(@PathVariable Long id) {
        log.debug("REST request to get Biere : {}", id);
        Biere biere = biereRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(biere));
    }

    /**
     * DELETE  /bieres/:id : delete the "id" biere.
     *
     * @param id the id of the biere to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bieres/{id}")
    @Timed
    public ResponseEntity<Void> deleteBiere(@PathVariable Long id) {
        log.debug("REST request to delete Biere : {}", id);
        biereRepository.delete(id);
        biereSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bieres?query=:query : search for the biere corresponding
     * to the query.
     *
     * @param query the query of the biere search 
     * @return the result of the search
     */
    @GetMapping("/_search/bieres")
    @Timed
    public List<Biere> searchBieres(@RequestParam String query) {
        log.debug("REST request to search Bieres for query {}", query);
        return StreamSupport
            .stream(biereSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
