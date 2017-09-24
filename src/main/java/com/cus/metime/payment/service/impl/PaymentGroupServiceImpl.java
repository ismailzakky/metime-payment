package com.cus.metime.payment.service.impl;

import com.cus.metime.payment.service.PaymentGroupService;
import com.cus.metime.payment.domain.PaymentGroup;
import com.cus.metime.payment.repository.PaymentGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PaymentGroup.
 */
@Service
@Transactional
public class PaymentGroupServiceImpl implements PaymentGroupService{

    private final Logger log = LoggerFactory.getLogger(PaymentGroupServiceImpl.class);

    private final PaymentGroupRepository paymentGroupRepository;

    public PaymentGroupServiceImpl(PaymentGroupRepository paymentGroupRepository) {
        this.paymentGroupRepository = paymentGroupRepository;
    }

    /**
     * Save a paymentGroup.
     *
     * @param paymentGroup the entity to save
     * @return the persisted entity
     */
    @Override
    public PaymentGroup save(PaymentGroup paymentGroup) {
        log.debug("Request to save PaymentGroup : {}", paymentGroup);
        return paymentGroupRepository.save(paymentGroup);
    }

    /**
     *  Get all the paymentGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentGroup> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentGroups");
        return paymentGroupRepository.findAll(pageable);
    }

    /**
     *  Get one paymentGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaymentGroup findOne(Long id) {
        log.debug("Request to get PaymentGroup : {}", id);
        return paymentGroupRepository.findOne(id);
    }

    /**
     *  Delete the  paymentGroup by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentGroup : {}", id);
        paymentGroupRepository.delete(id);
    }
}
