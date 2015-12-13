package com.gn.tapp.web.rest;

import com.gn.tapp.Application;
import com.gn.tapp.domain.ConversionQuery;
import com.gn.tapp.repository.ConversionQueryRepository;
import com.gn.tapp.service.ConversionQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.hasItem;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConversionQueryResource REST controller.
 * @see ConversionQueryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ConversionQueryResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;
    private static final String DEFAULT_FROM_CURRENCY = "AAA";
    private static final String UPDATED_FROM_CURRENCY = "BBB";
    private static final String DEFAULT_TO_CURRENCY = "AAA";
    private static final String UPDATED_TO_CURRENCY = "BBB";

    private static final ZonedDateTime DEFAULT_CONVERSION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final String DEFAULT_CONVERSION_DATE_STR = dateTimeFormatter.format(DEFAULT_CONVERSION_DATE);
    private static final ZonedDateTime UPDATED_CONVERSION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final String DEFAULT_CREATED_ON_STR = dateTimeFormatter.format(DEFAULT_CREATED_ON);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    @Inject
    private ConversionQueryRepository conversionQueryRepository;

    @Inject
    private ConversionQueryService conversionQueryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restConversionQueryMockMvc;

    private ConversionQuery conversionQuery;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConversionQueryResource conversionQueryResource = new ConversionQueryResource();
        ReflectionTestUtils.setField(conversionQueryResource, "conversionQueryService", conversionQueryService);
        this.restConversionQueryMockMvc = MockMvcBuilders.standaloneSetup(conversionQueryResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        conversionQuery = new ConversionQuery();
        conversionQuery.setAmount(DEFAULT_AMOUNT);
        conversionQuery.setFromCurrency(DEFAULT_FROM_CURRENCY);
        conversionQuery.setToCurrency(DEFAULT_TO_CURRENCY);
        conversionQuery.setConversionDate(DEFAULT_CONVERSION_DATE);
        conversionQuery.setCreatedOn(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createConversionQuery() throws Exception {
        int databaseSizeBeforeCreate = conversionQueryRepository.findAll().size();

        // Create the ConversionQuery

        restConversionQueryMockMvc.perform(post("/api/conversionQuerys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conversionQuery)))
                .andExpect(status().isCreated());

        // Validate the ConversionQuery in the database
        List<ConversionQuery> conversionQuerys = conversionQueryRepository.findAll();
        assertThat(conversionQuerys).hasSize(databaseSizeBeforeCreate + 1);
        ConversionQuery testConversionQuery = conversionQuerys.get(conversionQuerys.size() - 1);
        assertThat(testConversionQuery.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testConversionQuery.getFromCurrency()).isEqualTo(DEFAULT_FROM_CURRENCY);
        assertThat(testConversionQuery.getToCurrency()).isEqualTo(DEFAULT_TO_CURRENCY);
        assertThat(testConversionQuery.getConversionDate()).isEqualTo(DEFAULT_CONVERSION_DATE);
        assertThat(testConversionQuery.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionQueryRepository.findAll().size();
        // set the field null
        conversionQuery.setAmount(null);

        // Create the ConversionQuery, which fails.

        restConversionQueryMockMvc.perform(post("/api/conversionQuerys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conversionQuery)))
                .andExpect(status().isBadRequest());

        List<ConversionQuery> conversionQuerys = conversionQueryRepository.findAll();
        assertThat(conversionQuerys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionQueryRepository.findAll().size();
        // set the field null
        conversionQuery.setFromCurrency(null);

        // Create the ConversionQuery, which fails.

        restConversionQueryMockMvc.perform(post("/api/conversionQuerys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conversionQuery)))
                .andExpect(status().isBadRequest());

        List<ConversionQuery> conversionQuerys = conversionQueryRepository.findAll();
        assertThat(conversionQuerys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionQueryRepository.findAll().size();
        // set the field null
        conversionQuery.setToCurrency(null);

        // Create the ConversionQuery, which fails.

        restConversionQueryMockMvc.perform(post("/api/conversionQuerys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conversionQuery)))
                .andExpect(status().isBadRequest());

        List<ConversionQuery> conversionQuerys = conversionQueryRepository.findAll();
        assertThat(conversionQuerys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConversionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionQueryRepository.findAll().size();
        // set the field null
        conversionQuery.setConversionDate(null);

        // Create the ConversionQuery, which fails.

        restConversionQueryMockMvc.perform(post("/api/conversionQuerys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conversionQuery)))
                .andExpect(status().isBadRequest());

        List<ConversionQuery> conversionQuerys = conversionQueryRepository.findAll();
        assertThat(conversionQuerys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversionQueryRepository.findAll().size();
        // set the field null
        conversionQuery.setCreatedOn(null);

        // Create the ConversionQuery, which fails.

        restConversionQueryMockMvc.perform(post("/api/conversionQuerys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conversionQuery)))
                .andExpect(status().isBadRequest());

        List<ConversionQuery> conversionQuerys = conversionQueryRepository.findAll();
        assertThat(conversionQuerys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConversionQuerys() throws Exception {
        // Initialize the database
        conversionQueryRepository.saveAndFlush(conversionQuery);

        // Get all the conversionQuerys
        restConversionQueryMockMvc.perform(get("/api/conversionQuerys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(conversionQuery.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
                .andExpect(jsonPath("$.[*].fromCurrency").value(hasItem(DEFAULT_FROM_CURRENCY.toString())))
                .andExpect(jsonPath("$.[*].toCurrency").value(hasItem(DEFAULT_TO_CURRENCY.toString())))
                .andExpect(jsonPath("$.[*].conversionDate").value(hasItem(DEFAULT_CONVERSION_DATE_STR)))
                .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON_STR)));
    }

    @Test
    @Transactional
    public void getConversionQuery() throws Exception {
        // Initialize the database
        conversionQueryRepository.saveAndFlush(conversionQuery);

        // Get the conversionQuery
        restConversionQueryMockMvc.perform(get("/api/conversionQuerys/{id}", conversionQuery.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(conversionQuery.getId().intValue()))
                .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
                .andExpect(jsonPath("$.fromCurrency").value(DEFAULT_FROM_CURRENCY.toString()))
                .andExpect(jsonPath("$.toCurrency").value(DEFAULT_TO_CURRENCY.toString()))
                .andExpect(jsonPath("$.conversionDate").value(DEFAULT_CONVERSION_DATE_STR))
                .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON_STR));
    }

    @Test
    @Transactional
    public void getNonExistingConversionQuery() throws Exception {
        // Get the conversionQuery
        restConversionQueryMockMvc.perform(get("/api/conversionQuerys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversionQuery() throws Exception {
        // Initialize the database
        conversionQueryRepository.saveAndFlush(conversionQuery);

        int databaseSizeBeforeUpdate = conversionQueryRepository.findAll().size();

        // Update the conversionQuery
        conversionQuery.setAmount(UPDATED_AMOUNT);
        conversionQuery.setFromCurrency(UPDATED_FROM_CURRENCY);
        conversionQuery.setToCurrency(UPDATED_TO_CURRENCY);
        conversionQuery.setConversionDate(UPDATED_CONVERSION_DATE);
        conversionQuery.setCreatedOn(UPDATED_CREATED_ON);

        restConversionQueryMockMvc.perform(put("/api/conversionQuerys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(conversionQuery)))
                .andExpect(status().isOk());

        // Validate the ConversionQuery in the database
        List<ConversionQuery> conversionQuerys = conversionQueryRepository.findAll();
        assertThat(conversionQuerys).hasSize(databaseSizeBeforeUpdate);
        ConversionQuery testConversionQuery = conversionQuerys.get(conversionQuerys.size() - 1);
        assertThat(testConversionQuery.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testConversionQuery.getFromCurrency()).isEqualTo(UPDATED_FROM_CURRENCY);
        assertThat(testConversionQuery.getToCurrency()).isEqualTo(UPDATED_TO_CURRENCY);
        assertThat(testConversionQuery.getConversionDate()).isEqualTo(UPDATED_CONVERSION_DATE);
        assertThat(testConversionQuery.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void deleteConversionQuery() throws Exception {
        // Initialize the database
        conversionQueryRepository.saveAndFlush(conversionQuery);

        int databaseSizeBeforeDelete = conversionQueryRepository.findAll().size();

        // Get the conversionQuery
        restConversionQueryMockMvc.perform(delete("/api/conversionQuerys/{id}", conversionQuery.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ConversionQuery> conversionQuerys = conversionQueryRepository.findAll();
        assertThat(conversionQuerys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
