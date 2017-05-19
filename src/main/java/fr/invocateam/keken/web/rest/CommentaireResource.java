package fr.invocateam.keken.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.invocateam.keken.domain.Commentaire;

import fr.invocateam.keken.repository.CommentaireRepository;
import fr.invocateam.keken.repository.search.CommentaireSearchRepository;
import fr.invocateam.keken.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Commentaire.
 */
@RestController
@RequestMapping("/api")
public class CommentaireResource {

    private final Logger log = LoggerFactory.getLogger(CommentaireResource.class);

    private static final String ENTITY_NAME = "commentaire";
        
    private final CommentaireRepository commentaireRepository;

    private final CommentaireSearchRepository commentaireSearchRepository;

    public CommentaireResource(CommentaireRepository commentaireRepository, CommentaireSearchRepository commentaireSearchRepository) {
        this.commentaireRepository = commentaireRepository;
        this.commentaireSearchRepository = commentaireSearchRepository;
    }

    /**
     * POST  /commentaires : Create a new commentaire.
     *
     * @param commentaire the commentaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentaire, or with status 400 (Bad Request) if the commentaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commentaires")
    @Timed
    public ResponseEntity<Commentaire> createCommentaire(@RequestBody Commentaire commentaire) throws URISyntaxException {
        log.debug("REST request to save Commentaire : {}", commentaire);
        if (commentaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commentaire cannot already have an ID")).body(null);
        }
        Commentaire result = commentaireRepository.save(commentaire);
        commentaireSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/commentaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commentaires : Updates an existing commentaire.
     *
     * @param commentaire the commentaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentaire,
     * or with status 400 (Bad Request) if the commentaire is not valid,
     * or with status 500 (Internal Server Error) if the commentaire couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commentaires")
    @Timed
    public ResponseEntity<Commentaire> updateCommentaire(@RequestBody Commentaire commentaire) throws URISyntaxException {
        log.debug("REST request to update Commentaire : {}", commentaire);
        if (commentaire.getId() == null) {
            return createCommentaire(commentaire);
        }
        Commentaire result = commentaireRepository.save(commentaire);
        commentaireSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commentaires : get all the commentaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commentaires in body
     */
    @GetMapping("/commentaires")
    @Timed
    public List<Commentaire> getAllCommentaires() {
        log.debug("REST request to get all Commentaires");
        List<Commentaire> commentaires = commentaireRepository.findAll();
        return commentaires;
    }

    /**
     * GET  /commentaires/:id : get the "id" commentaire.
     *
     * @param id the id of the commentaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentaire, or with status 404 (Not Found)
     */
    @GetMapping("/commentaires/{id}")
    @Timed
    public ResponseEntity<Commentaire> getCommentaire(@PathVariable Long id) {
        log.debug("REST request to get Commentaire : {}", id);
        Commentaire commentaire = commentaireRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentaire));
    }

    /**
     * DELETE  /commentaires/:id : delete the "id" commentaire.
     *
     * @param id the id of the commentaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commentaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Long id) {
        log.debug("REST request to delete Commentaire : {}", id);
        commentaireRepository.delete(id);
        commentaireSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commentaires?query=:query : search for the commentaire corresponding
     * to the query.
     *
     * @param query the query of the commentaire search 
     * @return the result of the search
     */
    @GetMapping("/_search/commentaires")
    @Timed
    public List<Commentaire> searchCommentaires(@RequestParam String query) {
        log.debug("REST request to search Commentaires for query {}", query);
        return StreamSupport
            .stream(commentaireSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
