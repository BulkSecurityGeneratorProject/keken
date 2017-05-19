package fr.invocateam.keken.web.rest;

import fr.invocateam.keken.KekenApp;

import fr.invocateam.keken.domain.Biere;
import fr.invocateam.keken.repository.BiereRepository;
import fr.invocateam.keken.repository.search.BiereSearchRepository;
import fr.invocateam.keken.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.invocateam.keken.domain.enumeration.TypeBiere;
/**
 * Test class for the BiereResource REST controller.
 *
 * @see BiereResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KekenApp.class)
public class BiereResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final TypeBiere DEFAULT_TYPE = TypeBiere.BLONDE;
    private static final TypeBiere UPDATED_TYPE = TypeBiere.BRUNE;

    private static final Float DEFAULT_DEGREE_ALC = 1F;
    private static final Float UPDATED_DEGREE_ALC = 2F;

    private static final String DEFAULT_BRASSEUR = "AAAAAAAAAA";
    private static final String UPDATED_BRASSEUR = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    @Autowired
    private BiereRepository biereRepository;

    @Autowired
    private BiereSearchRepository biereSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBiereMockMvc;

    private Biere biere;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BiereResource biereResource = new BiereResource(biereRepository, biereSearchRepository);
        this.restBiereMockMvc = MockMvcBuilders.standaloneSetup(biereResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Biere createEntity(EntityManager em) {
        Biere biere = new Biere()
            .nom(DEFAULT_NOM)
            .type(DEFAULT_TYPE)
            .degreeAlc(DEFAULT_DEGREE_ALC)
            .brasseur(DEFAULT_BRASSEUR)
            .pays(DEFAULT_PAYS)
            .ville(DEFAULT_VILLE)
            .image(DEFAULT_IMAGE);
        return biere;
    }

    @Before
    public void initTest() {
        biereSearchRepository.deleteAll();
        biere = createEntity(em);
    }

    @Test
    @Transactional
    public void createBiere() throws Exception {
        int databaseSizeBeforeCreate = biereRepository.findAll().size();

        // Create the Biere
        restBiereMockMvc.perform(post("/api/bieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biere)))
            .andExpect(status().isCreated());

        // Validate the Biere in the database
        List<Biere> biereList = biereRepository.findAll();
        assertThat(biereList).hasSize(databaseSizeBeforeCreate + 1);
        Biere testBiere = biereList.get(biereList.size() - 1);
        assertThat(testBiere.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testBiere.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBiere.getDegreeAlc()).isEqualTo(DEFAULT_DEGREE_ALC);
        assertThat(testBiere.getBrasseur()).isEqualTo(DEFAULT_BRASSEUR);
        assertThat(testBiere.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testBiere.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testBiere.getImage()).isEqualTo(DEFAULT_IMAGE);

        // Validate the Biere in Elasticsearch
        Biere biereEs = biereSearchRepository.findOne(testBiere.getId());
        assertThat(biereEs).isEqualToComparingFieldByField(testBiere);
    }

    @Test
    @Transactional
    public void createBiereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biereRepository.findAll().size();

        // Create the Biere with an existing ID
        biere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiereMockMvc.perform(post("/api/bieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biere)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Biere> biereList = biereRepository.findAll();
        assertThat(biereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = biereRepository.findAll().size();
        // set the field null
        biere.setNom(null);

        // Create the Biere, which fails.

        restBiereMockMvc.perform(post("/api/bieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biere)))
            .andExpect(status().isBadRequest());

        List<Biere> biereList = biereRepository.findAll();
        assertThat(biereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBieres() throws Exception {
        // Initialize the database
        biereRepository.saveAndFlush(biere);

        // Get all the biereList
        restBiereMockMvc.perform(get("/api/bieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biere.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].degreeAlc").value(hasItem(DEFAULT_DEGREE_ALC.doubleValue())))
            .andExpect(jsonPath("$.[*].brasseur").value(hasItem(DEFAULT_BRASSEUR.toString())))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getBiere() throws Exception {
        // Initialize the database
        biereRepository.saveAndFlush(biere);

        // Get the biere
        restBiereMockMvc.perform(get("/api/bieres/{id}", biere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(biere.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.degreeAlc").value(DEFAULT_DEGREE_ALC.doubleValue()))
            .andExpect(jsonPath("$.brasseur").value(DEFAULT_BRASSEUR.toString()))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBiere() throws Exception {
        // Get the biere
        restBiereMockMvc.perform(get("/api/bieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBiere() throws Exception {
        // Initialize the database
        biereRepository.saveAndFlush(biere);
        biereSearchRepository.save(biere);
        int databaseSizeBeforeUpdate = biereRepository.findAll().size();

        // Update the biere
        Biere updatedBiere = biereRepository.findOne(biere.getId());
        updatedBiere
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE)
            .degreeAlc(UPDATED_DEGREE_ALC)
            .brasseur(UPDATED_BRASSEUR)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .image(UPDATED_IMAGE);

        restBiereMockMvc.perform(put("/api/bieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBiere)))
            .andExpect(status().isOk());

        // Validate the Biere in the database
        List<Biere> biereList = biereRepository.findAll();
        assertThat(biereList).hasSize(databaseSizeBeforeUpdate);
        Biere testBiere = biereList.get(biereList.size() - 1);
        assertThat(testBiere.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testBiere.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBiere.getDegreeAlc()).isEqualTo(UPDATED_DEGREE_ALC);
        assertThat(testBiere.getBrasseur()).isEqualTo(UPDATED_BRASSEUR);
        assertThat(testBiere.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testBiere.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testBiere.getImage()).isEqualTo(UPDATED_IMAGE);

        // Validate the Biere in Elasticsearch
        Biere biereEs = biereSearchRepository.findOne(testBiere.getId());
        assertThat(biereEs).isEqualToComparingFieldByField(testBiere);
    }

    @Test
    @Transactional
    public void updateNonExistingBiere() throws Exception {
        int databaseSizeBeforeUpdate = biereRepository.findAll().size();

        // Create the Biere

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBiereMockMvc.perform(put("/api/bieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(biere)))
            .andExpect(status().isCreated());

        // Validate the Biere in the database
        List<Biere> biereList = biereRepository.findAll();
        assertThat(biereList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBiere() throws Exception {
        // Initialize the database
        biereRepository.saveAndFlush(biere);
        biereSearchRepository.save(biere);
        int databaseSizeBeforeDelete = biereRepository.findAll().size();

        // Get the biere
        restBiereMockMvc.perform(delete("/api/bieres/{id}", biere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean biereExistsInEs = biereSearchRepository.exists(biere.getId());
        assertThat(biereExistsInEs).isFalse();

        // Validate the database is empty
        List<Biere> biereList = biereRepository.findAll();
        assertThat(biereList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBiere() throws Exception {
        // Initialize the database
        biereRepository.saveAndFlush(biere);
        biereSearchRepository.save(biere);

        // Search the biere
        restBiereMockMvc.perform(get("/api/_search/bieres?query=id:" + biere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(biere.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].degreeAlc").value(hasItem(DEFAULT_DEGREE_ALC.doubleValue())))
            .andExpect(jsonPath("$.[*].brasseur").value(hasItem(DEFAULT_BRASSEUR.toString())))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Biere.class);
        Biere biere1 = new Biere();
        biere1.setId(1L);
        Biere biere2 = new Biere();
        biere2.setId(biere1.getId());
        assertThat(biere1).isEqualTo(biere2);
        biere2.setId(2L);
        assertThat(biere1).isNotEqualTo(biere2);
        biere1.setId(null);
        assertThat(biere1).isNotEqualTo(biere2);
    }
}
