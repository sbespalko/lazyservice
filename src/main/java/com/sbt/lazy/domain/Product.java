package com.sbt.lazy.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "code", length = 20, nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "start_dt")
    private Instant startDt;

    @Column(name = "end_dt")
    private Instant endDt;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Client client;

    @OneToMany(mappedBy = "product")
    private Set<ProductHistory> histories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Product code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDt() {
        return startDt;
    }

    public Product startDt(Instant startDt) {
        this.startDt = startDt;
        return this;
    }

    public void setStartDt(Instant startDt) {
        this.startDt = startDt;
    }

    public Instant getEndDt() {
        return endDt;
    }

    public Product endDt(Instant endDt) {
        this.endDt = endDt;
        return this;
    }

    public void setEndDt(Instant endDt) {
        this.endDt = endDt;
    }

    public Client getClient() {
        return client;
    }

    public Product client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<ProductHistory> getHistories() {
        return histories;
    }

    public Product histories(Set<ProductHistory> productHistories) {
        this.histories = productHistories;
        return this;
    }

    public Product addHistory(ProductHistory productHistory) {
        this.histories.add(productHistory);
        productHistory.setProduct(this);
        return this;
    }

    public Product removeHistory(ProductHistory productHistory) {
        this.histories.remove(productHistory);
        productHistory.setProduct(null);
        return this;
    }

    public void setHistories(Set<ProductHistory> productHistories) {
        this.histories = productHistories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", startDt='" + getStartDt() + "'" +
            ", endDt='" + getEndDt() + "'" +
            "}";
    }
}
