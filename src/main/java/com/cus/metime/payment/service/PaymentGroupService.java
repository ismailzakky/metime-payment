package com.cus.metime.payment.service;

import com.cus.metime.payment.domain.PaymentGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PaymentGroup.
 */
public interface PaymentGroupService {

    /**
     * Save a paymentGroup.
     *
     * @param paymentGroup the entity to save
     * @return the persisted entity
     */
    PaymentGroup save(PaymentGroup paymentGroup);

    /**
     *  Get all the paymentGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaymentGroup> findAll(Pageable pageable);

    /**
     *  Get the "id" paymentGroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaymentGroup findOne(Long id);

    /**
     *  Delete the "id" paymentGroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
