package com.sbt.lazy.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.sbt.lazy.domain.enumeration.OperationType;

/**
 * A DTO for the {@link com.sbt.lazy.domain.ProductHistory} entity.
 */
public class ProductHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant operationDt;

    @NotNull
    private OperationType opertationType;

    private BigDecimal sum;


    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOperationDt() {
        return operationDt;
    }

    public void setOperationDt(Instant operationDt) {
        this.operationDt = operationDt;
    }

    public OperationType getOpertationType() {
        return opertationType;
    }

    public void setOpertationType(OperationType opertationType) {
        this.opertationType = opertationType;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductHistoryDTO productHistoryDTO = (ProductHistoryDTO) o;
        if (productHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductHistoryDTO{" +
            "id=" + getId() +
            ", operationDt='" + getOperationDt() + "'" +
            ", opertationType='" + getOpertationType() + "'" +
            ", sum=" + getSum() +
            ", product=" + getProductId() +
            "}";
    }
}
