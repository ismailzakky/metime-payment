package com.cus.metime.payment.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cus.metime.payment.domain.Payment;
import com.cus.metime.payment.service.PaymentService;
import com.cus.metime.payment.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Payment.
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    private static final String ENTITY_NAME = "payment";

    private final PaymentService paymentService;

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * POST  /payments : Create a new payment.
     *
     * @param payment the payment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payment, or with status 400 (Bad Request) if the payment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payments")
    @Timed
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) throws URISyntaxException {
        log.debug("REST request to save Payment : {}", payment);
        if (payment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new payment cannot already have an ID")).body(null);
        }
        Payment result = paymentService.save(payment);
        return ResponseEntity.created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payments : Updates an existing payment.
     *
     * @param payment the payment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payment,
     * or with status 400 (Bad Request) if the payment is not valid,
     * or with status 500 (Internal Server Error) if the payment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payments")
    @Timed
    public ResponseEntity<Payment> updatePayment(@RequestBody Payment payment) throws URISyntaxException {
        log.debug("REST request to update Payment : {}", payment);
        if (payment.getId() == null) {
            return createPayment(payment);
        }
        Payment result = paymentService.save(payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payments : get all the payments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payments in body
     */
    @GetMapping("/payments")
    @Timed
    public List<Payment> getAllPayments() {
        log.debug("REST request to get all Payments");
        return paymentService.findAll();
        }

    /**
     * GET  /payments/:id : get the "id" payment.
     *
     * @param id the id of the payment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payment, or with status 404 (Not Found)
     */
    @GetMapping("/payments/{id}")
    @Timed
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        log.debug("REST request to get Payment : {}", id);
        Payment payment = paymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payment));
    }

    /**
     * DELETE  /payments/:id : delete the "id" payment.
     *
     * @param id the id of the payment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payments/{id}")
    @Timed
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.debug("REST request to delete Payment : {}", id);
        paymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
