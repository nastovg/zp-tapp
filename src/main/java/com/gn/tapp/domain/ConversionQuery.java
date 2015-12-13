package com.gn.tapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ConversionQuery.
 */
@Entity
@Table(name = "conversion_query")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConversionQuery implements Serializable {

    private static final long serialVersionUID = -157034304585816602L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 1000000000)
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "from_currency", length = 3, nullable = false)
    private String fromCurrency;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "to_currency", length = 3, nullable = false)
    private String toCurrency;

    @NotNull
    @Column(name = "conversion_date", nullable = false)
    private ZonedDateTime conversionDate;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public ZonedDateTime getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(ZonedDateTime conversionDate) {
        this.conversionDate = conversionDate;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConversionQuery conversionQuery = (ConversionQuery) o;
        return Objects.equals(id, conversionQuery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConversionQuery{" +
                "id=" + id +
                ", amount='" + amount + "'" +
                ", fromCurrency='" + fromCurrency + "'" +
                ", toCurrency='" + toCurrency + "'" +
                ", conversionDate='" + conversionDate + "'" +
                ", createdOn='" + createdOn + "'" +
                '}';
    }
}
