package com.cus.metime.payment.repository;

import com.cus.metime.payment.domain.PaymentGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PaymentGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentGroupRepository extends JpaRepository<PaymentGroup, Long> {

}
