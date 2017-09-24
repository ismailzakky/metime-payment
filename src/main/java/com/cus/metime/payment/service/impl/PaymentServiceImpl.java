package com.cus.metime.payment.service.impl;

import com.cus.metime.payment.service.PaymentService;
import com.cus.metime.payment.domain.Payment;
import com.cus.metime.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Payment.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Save a payment.
     *
     * @param payment the entity to save
     * @return the persisted entity
     */
    @Override
    public Payment save(Payment payment) {
        log.debug("Request to save Payment : {}", payment);
        return paymentRepository.save(payment);
    }

    /**
     *  Get all the payments.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        log.debug("Request to get all Payments");
        return paymentRepository.findAll();
    }

    /**
     *  Get one payment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Payment findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findOne(id);
    }

    /**
     *  Delete the  payment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.delete(id);
    }
}
