package com.sbt.lazy.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.sbt.lazy.domain.enumeration.OperationType;

/**
 * A ProductHistory.
 */
@Entity
@Table(name = "product_history")
public class ProductHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "operation_dt", nullable = false)
    private Instant operationDt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "opertation_type", nullable = false)
    private OperationType opertationType;

    @Column(name = "sum", precision = 21, scale = 2)
    private BigDecimal sum;

    @ManyToOne
    @JsonIgnoreProperties("productHistories")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOperationDt() {
        return operationDt;
    }

    public ProductHistory operationDt(Instant operationDt) {
        this.operationDt = operationDt;
        return this;
    }

    public void setOperationDt(Instant operationDt) {
        this.operationDt = operationDt;
    }

    public OperationType getOpertationType() {
        return opertationType;
    }

    public ProductHistory opertationType(OperationType opertationType) {
        this.opertationType = opertationType;
        return this;
    }

    public void setOpertationType(OperationType opertationType) {
        this.opertationType = opertationType;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public ProductHistory sum(BigDecimal sum) {
        this.sum = sum;
        return this;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Product getProduct() {
        return product;
    }

    public ProductHistory product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductHistory)) {
            return false;
        }
        return id != null && id.equals(((ProductHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductHistory{" +
            "id=" + getId() +
            ", operationDt='" + getOperationDt() + "'" +
            ", opertationType='" + getOpertationType() + "'" +
            ", sum=" + getSum() +
            "}";
    }
}
