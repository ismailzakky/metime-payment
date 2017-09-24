package com.cus.metime.payment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.cus.metime.payment.domain.enumeration.Bank;

import com.cus.metime.payment.domain.enumeration.Status;

/**
 * A PaymentGroup.
 */
@Entity
@Table(name = "payment_group")
public class PaymentGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank")
    private Bank bank;

    @Column(name = "book_number")
    private Integer bookNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "unique_code")
    private Integer uniqueCode;

    @OneToMany(mappedBy = "payment")
    @JsonIgnore
    private Set<Payment> payments = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public PaymentGroup bank(Bank bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Integer getBookNumber() {
        return bookNumber;
    }

    public PaymentGroup bookNumber(Integer bookNumber) {
        this.bookNumber = bookNumber;
        return this;
    }

    public void setBookNumber(Integer bookNumber) {
        this.bookNumber = bookNumber;
    }

    public Status getStatus() {
        return status;
    }

    public PaymentGroup status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getUniqueCode() {
        return uniqueCode;
    }

    public PaymentGroup uniqueCode(Integer uniqueCode) {
        this.uniqueCode = uniqueCode;
        return this;
    }

    public void setUniqueCode(Integer uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public PaymentGroup payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public PaymentGroup addPayments(Payment payment) {
        this.payments.add(payment);
        payment.setPaymentGroup(this);
        return this;
    }

    public PaymentGroup removePayments(Payment payment) {
        this.payments.remove(payment);
        payment.setPaymentGroup(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
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
        PaymentGroup paymentGroup = (PaymentGroup) o;
        if (paymentGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentGroup{" +
            "id=" + getId() +
            ", bank='" + getBank() + "'" +
            ", bookNumber='" + getBookNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", uniqueCode='" + getUniqueCode() + "'" +
            "}";
    }
}
