package com.cus.metime.payment.web.rest;

import com.cus.metime.payment.PaymentApp;

import com.cus.metime.payment.config.SecurityBeanOverrideConfiguration;

import com.cus.metime.payment.domain.PaymentGroup;
import com.cus.metime.payment.repository.PaymentGroupRepository;
import com.cus.metime.payment.service.PaymentGroupService;
import com.cus.metime.payment.web.rest.errors.ExceptionTranslator;

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

import com.cus.metime.payment.domain.enumeration.Bank;
import com.cus.metime.payment.domain.enumeration.Status;
/**
 * Test class for the PaymentGroupResource REST controller.
 *
 * @see PaymentGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PaymentApp.class, SecurityBeanOverrideConfiguration.class})
public class PaymentGroupResourceIntTest {

    private static final Bank DEFAULT_BANK = Bank.BCA;
    private static final Bank UPDATED_BANK = Bank.MANDIRI;

    private static final Integer DEFAULT_BOOK_NUMBER = 1;
    private static final Integer UPDATED_BOOK_NUMBER = 2;

    private static final Status DEFAULT_STATUS = Status.PROCESSING;
    private static final Status UPDATED_STATUS = Status.PENDING;

    private static final Integer DEFAULT_UNIQUE_CODE = 1;
    private static final Integer UPDATED_UNIQUE_CODE = 2;

    @Autowired
    private PaymentGroupRepository paymentGroupRepository;

    @Autowired
    private PaymentGroupService paymentGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentGroupMockMvc;

    private PaymentGroup paymentGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentGroupResource paymentGroupResource = new PaymentGroupResource(paymentGroupService);
        this.restPaymentGroupMockMvc = MockMvcBuilders.standaloneSetup(paymentGroupResource)
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
    public static PaymentGroup createEntity(EntityManager em) {
        PaymentGroup paymentGroup = new PaymentGroup()
            .bank(DEFAULT_BANK)
            .bookNumber(DEFAULT_BOOK_NUMBER)
            .status(DEFAULT_STATUS)
            .uniqueCode(DEFAULT_UNIQUE_CODE);
        return paymentGroup;
    }

    @Before
    public void initTest() {
        paymentGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentGroup() throws Exception {
        int databaseSizeBeforeCreate = paymentGroupRepository.findAll().size();

        // Create the PaymentGroup
        restPaymentGroupMockMvc.perform(post("/api/payment-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentGroup)))
            .andExpect(status().isCreated());

        // Validate the PaymentGroup in the database
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentGroup testPaymentGroup = paymentGroupList.get(paymentGroupList.size() - 1);
        assertThat(testPaymentGroup.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testPaymentGroup.getBookNumber()).isEqualTo(DEFAULT_BOOK_NUMBER);
        assertThat(testPaymentGroup.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentGroup.getUniqueCode()).isEqualTo(DEFAULT_UNIQUE_CODE);
    }

    @Test
    @Transactional
    public void createPaymentGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentGroupRepository.findAll().size();

        // Create the PaymentGroup with an existing ID
        paymentGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentGroupMockMvc.perform(post("/api/payment-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentGroup)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentGroup in the database
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaymentGroups() throws Exception {
        // Initialize the database
        paymentGroupRepository.saveAndFlush(paymentGroup);

        // Get all the paymentGroupList
        restPaymentGroupMockMvc.perform(get("/api/payment-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK.toString())))
            .andExpect(jsonPath("$.[*].bookNumber").value(hasItem(DEFAULT_BOOK_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].uniqueCode").value(hasItem(DEFAULT_UNIQUE_CODE)));
    }

    @Test
    @Transactional
    public void getPaymentGroup() throws Exception {
        // Initialize the database
        paymentGroupRepository.saveAndFlush(paymentGroup);

        // Get the paymentGroup
        restPaymentGroupMockMvc.perform(get("/api/payment-groups/{id}", paymentGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentGroup.getId().intValue()))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK.toString()))
            .andExpect(jsonPath("$.bookNumber").value(DEFAULT_BOOK_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.uniqueCode").value(DEFAULT_UNIQUE_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentGroup() throws Exception {
        // Get the paymentGroup
        restPaymentGroupMockMvc.perform(get("/api/payment-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentGroup() throws Exception {
        // Initialize the database
        paymentGroupService.save(paymentGroup);

        int databaseSizeBeforeUpdate = paymentGroupRepository.findAll().size();

        // Update the paymentGroup
        PaymentGroup updatedPaymentGroup = paymentGroupRepository.findOne(paymentGroup.getId());
        updatedPaymentGroup
            .bank(UPDATED_BANK)
            .bookNumber(UPDATED_BOOK_NUMBER)
            .status(UPDATED_STATUS)
            .uniqueCode(UPDATED_UNIQUE_CODE);

        restPaymentGroupMockMvc.perform(put("/api/payment-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentGroup)))
            .andExpect(status().isOk());

        // Validate the PaymentGroup in the database
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeUpdate);
        PaymentGroup testPaymentGroup = paymentGroupList.get(paymentGroupList.size() - 1);
        assertThat(testPaymentGroup.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testPaymentGroup.getBookNumber()).isEqualTo(UPDATED_BOOK_NUMBER);
        assertThat(testPaymentGroup.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentGroup.getUniqueCode()).isEqualTo(UPDATED_UNIQUE_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentGroup() throws Exception {
        int databaseSizeBeforeUpdate = paymentGroupRepository.findAll().size();

        // Create the PaymentGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentGroupMockMvc.perform(put("/api/payment-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentGroup)))
            .andExpect(status().isCreated());

        // Validate the PaymentGroup in the database
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaymentGroup() throws Exception {
        // Initialize the database
        paymentGroupService.save(paymentGroup);

        int databaseSizeBeforeDelete = paymentGroupRepository.findAll().size();

        // Get the paymentGroup
        restPaymentGroupMockMvc.perform(delete("/api/payment-groups/{id}", paymentGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentGroup.class);
        PaymentGroup paymentGroup1 = new PaymentGroup();
        paymentGroup1.setId(1L);
        PaymentGroup paymentGroup2 = new PaymentGroup();
        paymentGroup2.setId(paymentGroup1.getId());
        assertThat(paymentGroup1).isEqualTo(paymentGroup2);
        paymentGroup2.setId(2L);
        assertThat(paymentGroup1).isNotEqualTo(paymentGroup2);
        paymentGroup1.setId(null);
        assertThat(paymentGroup1).isNotEqualTo(paymentGroup2);
    }
}
