package com.sbt.lazy.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 160)
    @Column(name = "name", length = 160, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "client")
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<History> histories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Client name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Client addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Client addAddress(Address address) {
        this.addresses.add(address);
        address.setClient(this);
        return this;
    }

    public Client removeAddress(Address address) {
        this.addresses.remove(address);
        address.setClient(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Client products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Client addProduct(Product product) {
        this.products.add(product);
        product.setClient(this);
        return this;
    }

    public Client removeProduct(Product product) {
        this.products.remove(product);
        product.setClient(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<History> getHistories() {
        return histories;
    }

    public Client histories(Set<History> histories) {
        this.histories = histories;
        return this;
    }

    public Client addHistory(History history) {
        this.histories.add(history);
        history.setClient(this);
        return this;
    }

    public Client removeHistory(History history) {
        this.histories.remove(history);
        history.setClient(null);
        return this;
    }

    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
