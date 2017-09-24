package com.cus.metime.payment.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.cus.metime.payment.domain.enumeration.Type;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private Type type;

    @ManyToOne
    private PaymentGroup paymentGroup;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public Payment amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public Payment type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public PaymentGroup getPaymentGroup() {
        return paymentGroup;
    }

    public Payment paymentGroup(PaymentGroup paymentGroup) {
        this.paymentGroup = paymentGroup;
        return this;
    }

    public void setPaymentGroup(PaymentGroup paymentGroup) {
        this.paymentGroup = paymentGroup;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment payment = (Payment) o;
        if (payment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", amount='" + getAmount() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
