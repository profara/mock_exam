package rs.ac.bg.fon.silab.mock_exam.entities;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;

import java.math.BigDecimal;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = REFERENCE_NUMBER_COLUMN_NAME, columnDefinition = "VARCHAR(45)", nullable = false)
    private String referenceNumber;
    @Column(name = CREDITOR_ACCOUNT_COLUMN_NAME, columnDefinition = "VARCHAR(45)",nullable = false)
    private String creditorAccount;
    @Column(columnDefinition = "DECIMAL(15,2)",nullable = false)
    private BigDecimal amount;
    @Column(name = PAYMENT_PURPOSE_COLUMN_NAME, columnDefinition = "VARCHAR(100)", nullable = false)
    private String paymentPurpose;
    private int model;
    @OneToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_APPLICATION)
    private Application application;

    public Payment() {
    }

    public Payment(String referenceNumber, String creditorAccount, BigDecimal amount, String paymentPurpose, int model, Application application) {
        this.referenceNumber = referenceNumber;
        this.creditorAccount = creditorAccount;
        this.amount = amount;
        this.paymentPurpose = paymentPurpose;
        this.model = model;
        this.application = application;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(String creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", creditorAccount='" + creditorAccount + '\'' +
                ", amount=" + amount +
                ", paymentPurpose='" + paymentPurpose + '\'' +
                ", model=" + model +
                ", application=" + application +
                '}';
    }
}
