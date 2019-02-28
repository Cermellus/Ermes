package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the CreditRequestLine entity.
 */
public class CreditRequestLineDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer creditRequestLineId;

    @NotNull
    private BigDecimal creditRequestLineAmount;

    @NotNull
    private BigDecimal creditRequestLineQtyCredited;

    @NotNull
    private BigDecimal creditRequestLineQtyReturn;

    private String creditRequestLineComment;

    @Lob
    private byte[] creditRequestLineImage;

    private String creditRequestLineImageContentType;

    private Long productIdId;

    private Long creditReasonIdId;

    private Long creditReturnTypeIdId;

    private Long creditRequestIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditRequestLineId() {
        return creditRequestLineId;
    }

    public void setCreditRequestLineId(Integer creditRequestLineId) {
        this.creditRequestLineId = creditRequestLineId;
    }

    public BigDecimal getCreditRequestLineAmount() {
        return creditRequestLineAmount;
    }

    public void setCreditRequestLineAmount(BigDecimal creditRequestLineAmount) {
        this.creditRequestLineAmount = creditRequestLineAmount;
    }

    public BigDecimal getCreditRequestLineQtyCredited() {
        return creditRequestLineQtyCredited;
    }

    public void setCreditRequestLineQtyCredited(BigDecimal creditRequestLineQtyCredited) {
        this.creditRequestLineQtyCredited = creditRequestLineQtyCredited;
    }

    public BigDecimal getCreditRequestLineQtyReturn() {
        return creditRequestLineQtyReturn;
    }

    public void setCreditRequestLineQtyReturn(BigDecimal creditRequestLineQtyReturn) {
        this.creditRequestLineQtyReturn = creditRequestLineQtyReturn;
    }

    public String getCreditRequestLineComment() {
        return creditRequestLineComment;
    }

    public void setCreditRequestLineComment(String creditRequestLineComment) {
        this.creditRequestLineComment = creditRequestLineComment;
    }

    public byte[] getCreditRequestLineImage() {
        return creditRequestLineImage;
    }

    public void setCreditRequestLineImage(byte[] creditRequestLineImage) {
        this.creditRequestLineImage = creditRequestLineImage;
    }

    public String getCreditRequestLineImageContentType() {
        return creditRequestLineImageContentType;
    }

    public void setCreditRequestLineImageContentType(String creditRequestLineImageContentType) {
        this.creditRequestLineImageContentType = creditRequestLineImageContentType;
    }

    public Long getProductIdId() {
        return productIdId;
    }

    public void setProductIdId(Long productId) {
        this.productIdId = productId;
    }

    public Long getCreditReasonIdId() {
        return creditReasonIdId;
    }

    public void setCreditReasonIdId(Long creditReasonId) {
        this.creditReasonIdId = creditReasonId;
    }

    public Long getCreditReturnTypeIdId() {
        return creditReturnTypeIdId;
    }

    public void setCreditReturnTypeIdId(Long creditReturnTypeId) {
        this.creditReturnTypeIdId = creditReturnTypeId;
    }

    public Long getCreditRequestIdId() {
        return creditRequestIdId;
    }

    public void setCreditRequestIdId(Long creditRequestId) {
        this.creditRequestIdId = creditRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditRequestLineDTO creditRequestLineDTO = (CreditRequestLineDTO) o;
        if (creditRequestLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditRequestLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditRequestLineDTO{" +
            "id=" + getId() +
            ", creditRequestLineId=" + getCreditRequestLineId() +
            ", creditRequestLineAmount=" + getCreditRequestLineAmount() +
            ", creditRequestLineQtyCredited=" + getCreditRequestLineQtyCredited() +
            ", creditRequestLineQtyReturn=" + getCreditRequestLineQtyReturn() +
            ", creditRequestLineComment='" + getCreditRequestLineComment() + "'" +
            ", creditRequestLineImage='" + getCreditRequestLineImage() + "'" +
            ", productId=" + getProductIdId() +
            ", creditReasonId=" + getCreditReasonIdId() +
            ", creditReturnTypeId=" + getCreditReturnTypeIdId() +
            ", creditRequestId=" + getCreditRequestIdId() +
            "}";
    }
}
