package com.sbt.lazy.web.rest;

import com.sbt.lazy.LazyserviceApp;
import com.sbt.lazy.domain.ProductHistory;
import com.sbt.lazy.repository.ProductHistoryRepository;
import com.sbt.lazy.service.ProductHistoryService;
import com.sbt.lazy.service.dto.ProductHistoryDTO;
import com.sbt.lazy.service.mapper.ProductHistoryMapper;
import com.sbt.lazy.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.sbt.lazy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sbt.lazy.domain.enumeration.OperationType;
/**
 * Integration tests for the {@link ProductHistoryResource} REST controller.
 */
@SpringBootTest(classes = LazyserviceApp.class)
public class ProductHistoryResourceIT {

    private static final Instant DEFAULT_OPERATION_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPERATION_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_OPERATION_DT = Instant.ofEpochMilli(-1L);

    private static final OperationType DEFAULT_OPERTATION_TYPE = OperationType.PUT;
    private static final OperationType UPDATED_OPERTATION_TYPE = OperationType.TAKE;

    private static final BigDecimal DEFAULT_SUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUM = new BigDecimal(2);
    private static final BigDecimal SMALLER_SUM = new BigDecimal(1 - 1);

    @Autowired
    private ProductHistoryRepository productHistoryRepository;

    @Autowired
    private ProductHistoryMapper productHistoryMapper;

    @Autowired
    private ProductHistoryService productHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProductHistoryMockMvc;

    private ProductHistory productHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductHistoryResource productHistoryResource = new ProductHistoryResource(productHistoryService);
        this.restProductHistoryMockMvc = MockMvcBuilders.standaloneSetup(productHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductHistory createEntity(EntityManager em) {
        ProductHistory productHistory = new ProductHistory()
            .operationDt(DEFAULT_OPERATION_DT)
            .opertationType(DEFAULT_OPERTATION_TYPE)
            .sum(DEFAULT_SUM);
        return productHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductHistory createUpdatedEntity(EntityManager em) {
        ProductHistory productHistory = new ProductHistory()
            .operationDt(UPDATED_OPERATION_DT)
            .opertationType(UPDATED_OPERTATION_TYPE)
            .sum(UPDATED_SUM);
        return productHistory;
    }

    @BeforeEach
    public void initTest() {
        productHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductHistory() throws Exception {
        int databaseSizeBeforeCreate = productHistoryRepository.findAll().size();

        // Create the ProductHistory
        ProductHistoryDTO productHistoryDTO = productHistoryMapper.toDto(productHistory);
        restProductHistoryMockMvc.perform(post("/api/product-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductHistory in the database
        List<ProductHistory> productHistoryList = productHistoryRepository.findAll();
        assertThat(productHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductHistory testProductHistory = productHistoryList.get(productHistoryList.size() - 1);
        assertThat(testProductHistory.getOperationDt()).isEqualTo(DEFAULT_OPERATION_DT);
        assertThat(testProductHistory.getOpertationType()).isEqualTo(DEFAULT_OPERTATION_TYPE);
        assertThat(testProductHistory.getSum()).isEqualTo(DEFAULT_SUM);
    }

    @Test
    @Transactional
    public void createProductHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productHistoryRepository.findAll().size();

        // Create the ProductHistory with an existing ID
        productHistory.setId(1L);
        ProductHistoryDTO productHistoryDTO = productHistoryMapper.toDto(productHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductHistoryMockMvc.perform(post("/api/product-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductHistory in the database
        List<ProductHistory> productHistoryList = productHistoryRepository.findAll();
        assertThat(productHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOperationDtIsRequired() throws Exception {
        int databaseSizeBeforeTest = productHistoryRepository.findAll().size();
        // set the field null
        productHistory.setOperationDt(null);

        // Create the ProductHistory, which fails.
        ProductHistoryDTO productHistoryDTO = productHistoryMapper.toDto(productHistory);

        restProductHistoryMockMvc.perform(post("/api/product-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductHistory> productHistoryList = productHistoryRepository.findAll();
        assertThat(productHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpertationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productHistoryRepository.findAll().size();
        // set the field null
        productHistory.setOpertationType(null);

        // Create the ProductHistory, which fails.
        ProductHistoryDTO productHistoryDTO = productHistoryMapper.toDto(productHistory);

        restProductHistoryMockMvc.perform(post("/api/product-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductHistory> productHistoryList = productHistoryRepository.findAll();
        assertThat(productHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductHistories() throws Exception {
        // Initialize the database
        productHistoryRepository.saveAndFlush(productHistory);

        // Get all the productHistoryList
        restProductHistoryMockMvc.perform(get("/api/product-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationDt").value(hasItem(DEFAULT_OPERATION_DT.toString())))
            .andExpect(jsonPath("$.[*].opertationType").value(hasItem(DEFAULT_OPERTATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM.intValue())));
    }
    
    @Test
    @Transactional
    public void getProductHistory() throws Exception {
        // Initialize the database
        productHistoryRepository.saveAndFlush(productHistory);

        // Get the productHistory
        restProductHistoryMockMvc.perform(get("/api/product-histories/{id}", productHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productHistory.getId().intValue()))
            .andExpect(jsonPath("$.operationDt").value(DEFAULT_OPERATION_DT.toString()))
            .andExpect(jsonPath("$.opertationType").value(DEFAULT_OPERTATION_TYPE.toString()))
            .andExpect(jsonPath("$.sum").value(DEFAULT_SUM.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductHistory() throws Exception {
        // Get the productHistory
        restProductHistoryMockMvc.perform(get("/api/product-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductHistory() throws Exception {
        // Initialize the database
        productHistoryRepository.saveAndFlush(productHistory);

        int databaseSizeBeforeUpdate = productHistoryRepository.findAll().size();

        // Update the productHistory
        ProductHistory updatedProductHistory = productHistoryRepository.findById(productHistory.getId()).get();
        // Disconnect from session so that the updates on updatedProductHistory are not directly saved in db
        em.detach(updatedProductHistory);
        updatedProductHistory
            .operationDt(UPDATED_OPERATION_DT)
            .opertationType(UPDATED_OPERTATION_TYPE)
            .sum(UPDATED_SUM);
        ProductHistoryDTO productHistoryDTO = productHistoryMapper.toDto(updatedProductHistory);

        restProductHistoryMockMvc.perform(put("/api/product-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the ProductHistory in the database
        List<ProductHistory> productHistoryList = productHistoryRepository.findAll();
        assertThat(productHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProductHistory testProductHistory = productHistoryList.get(productHistoryList.size() - 1);
        assertThat(testProductHistory.getOperationDt()).isEqualTo(UPDATED_OPERATION_DT);
        assertThat(testProductHistory.getOpertationType()).isEqualTo(UPDATED_OPERTATION_TYPE);
        assertThat(testProductHistory.getSum()).isEqualTo(UPDATED_SUM);
    }

    @Test
    @Transactional
    public void updateNonExistingProductHistory() throws Exception {
        int databaseSizeBeforeUpdate = productHistoryRepository.findAll().size();

        // Create the ProductHistory
        ProductHistoryDTO productHistoryDTO = productHistoryMapper.toDto(productHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductHistoryMockMvc.perform(put("/api/product-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductHistory in the database
        List<ProductHistory> productHistoryList = productHistoryRepository.findAll();
        assertThat(productHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductHistory() throws Exception {
        // Initialize the database
        productHistoryRepository.saveAndFlush(productHistory);

        int databaseSizeBeforeDelete = productHistoryRepository.findAll().size();

        // Delete the productHistory
        restProductHistoryMockMvc.perform(delete("/api/product-histories/{id}", productHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductHistory> productHistoryList = productHistoryRepository.findAll();
        assertThat(productHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductHistory.class);
        ProductHistory productHistory1 = new ProductHistory();
        productHistory1.setId(1L);
        ProductHistory productHistory2 = new ProductHistory();
        productHistory2.setId(productHistory1.getId());
        assertThat(productHistory1).isEqualTo(productHistory2);
        productHistory2.setId(2L);
        assertThat(productHistory1).isNotEqualTo(productHistory2);
        productHistory1.setId(null);
        assertThat(productHistory1).isNotEqualTo(productHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductHistoryDTO.class);
        ProductHistoryDTO productHistoryDTO1 = new ProductHistoryDTO();
        productHistoryDTO1.setId(1L);
        ProductHistoryDTO productHistoryDTO2 = new ProductHistoryDTO();
        assertThat(productHistoryDTO1).isNotEqualTo(productHistoryDTO2);
        productHistoryDTO2.setId(productHistoryDTO1.getId());
        assertThat(productHistoryDTO1).isEqualTo(productHistoryDTO2);
        productHistoryDTO2.setId(2L);
        assertThat(productHistoryDTO1).isNotEqualTo(productHistoryDTO2);
        productHistoryDTO1.setId(null);
        assertThat(productHistoryDTO1).isNotEqualTo(productHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productHistoryMapper.fromId(null)).isNull();
    }
}
