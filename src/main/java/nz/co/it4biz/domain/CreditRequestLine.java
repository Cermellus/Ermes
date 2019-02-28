package nz.co.it4biz.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A CreditRequestLine.
 */
@Entity
@Table(name = "credit_request_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CreditRequestLine implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "credit_request_line_id", nullable = false)
    private Integer creditRequestLineId;

    @NotNull
    @Column(name = "credit_request_line_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal creditRequestLineAmount;

    @NotNull
    @Column(name = "credit_request_line_qty_credited", precision = 10, scale = 2, nullable = false)
    private BigDecimal creditRequestLineQtyCredited;

    @NotNull
    @Column(name = "credit_request_line_qty_return", precision = 10, scale = 2, nullable = false)
    private BigDecimal creditRequestLineQtyReturn;

    @Column(name = "credit_request_line_comment")
    private String creditRequestLineComment;

    @Lob
    @Column(name = "credit_request_line_image")
    private byte[] creditRequestLineImage;

    @Column(name = "credit_request_line_image_content_type")
    private String creditRequestLineImageContentType;

    @ManyToOne
    @JsonIgnoreProperties("creditRequestLines")
    private Product productId;

    @ManyToOne
    @JsonIgnoreProperties("creditRequestLines")
    private CreditReason creditReasonId;

    @ManyToOne
    @JsonIgnoreProperties("creditRequestLines")
    private CreditReturnType creditReturnTypeId;

    @ManyToOne
    @JsonIgnoreProperties("creditRequestLines")
    private CreditRequest creditRequestId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditRequestLineId() {
        return creditRequestLineId;
    }

    public CreditRequestLine creditRequestLineId(Integer creditRequestLineId) {
        this.creditRequestLineId = creditRequestLineId;
        return this;
    }

    public void setCreditRequestLineId(Integer creditRequestLineId) {
        this.creditRequestLineId = creditRequestLineId;
    }

    public BigDecimal getCreditRequestLineAmount() {
        return creditRequestLineAmount;
    }

    public CreditRequestLine creditRequestLineAmount(BigDecimal creditRequestLineAmount) {
        this.creditRequestLineAmount = creditRequestLineAmount;
        return this;
    }

    public void setCreditRequestLineAmount(BigDecimal creditRequestLineAmount) {
        this.creditRequestLineAmount = creditRequestLineAmount;
    }

    public BigDecimal getCreditRequestLineQtyCredited() {
        return creditRequestLineQtyCredited;
    }

    public CreditRequestLine creditRequestLineQtyCredited(BigDecimal creditRequestLineQtyCredited) {
        this.creditRequestLineQtyCredited = creditRequestLineQtyCredited;
        return this;
    }

    public void setCreditRequestLineQtyCredited(BigDecimal creditRequestLineQtyCredited) {
        this.creditRequestLineQtyCredited = creditRequestLineQtyCredited;
    }

    public BigDecimal getCreditRequestLineQtyReturn() {
        return creditRequestLineQtyReturn;
    }

    public CreditRequestLine creditRequestLineQtyReturn(BigDecimal creditRequestLineQtyReturn) {
        this.creditRequestLineQtyReturn = creditRequestLineQtyReturn;
        return this;
    }

    public void setCreditRequestLineQtyReturn(BigDecimal creditRequestLineQtyReturn) {
        this.creditRequestLineQtyReturn = creditRequestLineQtyReturn;
    }

    public String getCreditRequestLineComment() {
        return creditRequestLineComment;
    }

    public CreditRequestLine creditRequestLineComment(String creditRequestLineComment) {
        this.creditRequestLineComment = creditRequestLineComment;
        return this;
    }

    public void setCreditRequestLineComment(String creditRequestLineComment) {
        this.creditRequestLineComment = creditRequestLineComment;
    }

    public byte[] getCreditRequestLineImage() {
        return creditRequestLineImage;
    }

    public CreditRequestLine creditRequestLineImage(byte[] creditRequestLineImage) {
        this.creditRequestLineImage = creditRequestLineImage;
        return this;
    }

    public void setCreditRequestLineImage(byte[] creditRequestLineImage) {
        this.creditRequestLineImage = creditRequestLineImage;
    }

    public String getCreditRequestLineImageContentType() {
        return creditRequestLineImageContentType;
    }

    public CreditRequestLine creditRequestLineImageContentType(String creditRequestLineImageContentType) {
        this.creditRequestLineImageContentType = creditRequestLineImageContentType;
        return this;
    }

    public void setCreditRequestLineImageContentType(String creditRequestLineImageContentType) {
        this.creditRequestLineImageContentType = creditRequestLineImageContentType;
    }

    public Product getProductId() {
        return productId;
    }

    public CreditRequestLine productId(Product product) {
        this.productId = product;
        return this;
    }

    public void setProductId(Product product) {
        this.productId = product;
    }

    public CreditReason getCreditReasonId() {
        return creditReasonId;
    }

    public CreditRequestLine creditReasonId(CreditReason creditReason) {
        this.creditReasonId = creditReason;
        return this;
    }

    public void setCreditReasonId(CreditReason creditReason) {
        this.creditReasonId = creditReason;
    }

    public CreditReturnType getCreditReturnTypeId() {
        return creditReturnTypeId;
    }

    public CreditRequestLine creditReturnTypeId(CreditReturnType creditReturnType) {
        this.creditReturnTypeId = creditReturnType;
        return this;
    }

    public void setCreditReturnTypeId(CreditReturnType creditReturnType) {
        this.creditReturnTypeId = creditReturnType;
    }

    public CreditRequest getCreditRequestId() {
        return creditRequestId;
    }

    public CreditRequestLine creditRequestId(CreditRequest creditRequest) {
        this.creditRequestId = creditRequest;
        return this;
    }

    public void setCreditRequestId(CreditRequest creditRequest) {
        this.creditRequestId = creditRequest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreditRequestLine creditRequestLine = (CreditRequestLine) o;
        if (creditRequestLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditRequestLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditRequestLine{" +
            "id=" + getId() +
            ", creditRequestLineId=" + getCreditRequestLineId() +
            ", creditRequestLineAmount=" + getCreditRequestLineAmount() +
            ", creditRequestLineQtyCredited=" + getCreditRequestLineQtyCredited() +
            ", creditRequestLineQtyReturn=" + getCreditRequestLineQtyReturn() +
            ", creditRequestLineComment='" + getCreditRequestLineComment() + "'" +
            ", creditRequestLineImage='" + getCreditRequestLineImage() + "'" +
            ", creditRequestLineImageContentType='" + getCreditRequestLineImageContentType() + "'" +
            "}";
    }
}
