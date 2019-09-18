package com.sbt.lazy.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A History.
 */
@Entity
@Table(name = "history")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_dt")
    private Instant startDt;

    @Column(name = "end_dt")
    private Instant endDt;

    @Column(name = "risk_power")
    private Integer riskPower;

    @ManyToOne
    @JsonIgnoreProperties("histories")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDt() {
        return startDt;
    }

    public History startDt(Instant startDt) {
        this.startDt = startDt;
        return this;
    }

    public void setStartDt(Instant startDt) {
        this.startDt = startDt;
    }

    public Instant getEndDt() {
        return endDt;
    }

    public History endDt(Instant endDt) {
        this.endDt = endDt;
        return this;
    }

    public void setEndDt(Instant endDt) {
        this.endDt = endDt;
    }

    public Integer getRiskPower() {
        return riskPower;
    }

    public History riskPower(Integer riskPower) {
        this.riskPower = riskPower;
        return this;
    }

    public void setRiskPower(Integer riskPower) {
        this.riskPower = riskPower;
    }

    public Client getClient() {
        return client;
    }

    public History client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof History)) {
            return false;
        }
        return id != null && id.equals(((History) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "History{" +
            "id=" + getId() +
            ", startDt='" + getStartDt() + "'" +
            ", endDt='" + getEndDt() + "'" +
            ", riskPower=" + getRiskPower() +
            "}";
    }
}
