package rs.ac.bg.fon.silab.mock_exam.domain.payment.entity;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;

import java.math.BigDecimal;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

/**
 * Represents a payment for a mock entrance exam application.
 * This entity stores information about the payment, including reference number, creditor account, amount, payment purpose, and associated application.
 *
 * <p> This class includes methods for accessing and modifying payment details
 * It also overrides the {@code equals}, {@code hashCode}, {@code toString} methods from the {@code Object} class.</p>
 */
@Entity
public class Payment {

    /**
     * Unique identifier for the payment. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference number of the payment. It is a required field.
     */
    @Column(name = REFERENCE_NUMBER_COLUMN_NAME, columnDefinition = "VARCHAR(45)", nullable = false)
    private String referenceNumber;

    /**
     * Creditor account number for the payment. It is a required field.
     */
    @Column(name = CREDITOR_ACCOUNT_COLUMN_NAME, columnDefinition = "VARCHAR(45)",nullable = false)
    private String creditorAccount;

    /**
     * The amount that needs to be paid for the payment. It is a required field.
     */
    @Column(columnDefinition = "DECIMAL(15,2)",nullable = false)
    private BigDecimal amount;

    /**
     * Purpose of the payment. It is a required field.
     */
    @Column(name = PAYMENT_PURPOSE_COLUMN_NAME, columnDefinition = "VARCHAR(100)", nullable = false)
    private String paymentPurpose;

    /**
     * Model number for the payment.
     */
    private int model;

    /**
     * The application associated with this payment. The association is mandatory.
     */
    @OneToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_APPLICATION)
    private Application application;

    /**
     * Default constructor required by JPA. It is not intended for direct use.
     */
    public Payment() {
    }

    /**
     * Constructs a new Payment with specified details.
     *
     * @param referenceNumber the reference number of the payment
     * @param creditorAccount the creditor account number
     * @param amount the amount to be paid
     * @param paymentPurpose the purpose of the payment
     * @param model the model number for the payment
     * @param application the associated application
     */
    public Payment(String referenceNumber, String creditorAccount, BigDecimal amount, String paymentPurpose, int model, Application application) {
        this.referenceNumber = referenceNumber;
        this.creditorAccount = creditorAccount;
        this.amount = amount;
        this.paymentPurpose = paymentPurpose;
        this.model = model;
        this.application = application;
    }

    /**
     * Gets the unique identifier of the payment.
     *
     * @return the identifier of the payment
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the payment.
     *
     * @param id the identifier to be set for the payment
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the reference number of the payment.
     *
     * @return the reference number of the payment
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * Sets the reference number of the payment.
     *
     * @param referenceNumber the reference number to be set for the payment
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Gets the creditor account number of the payment.
     *
     * @return the creditor account number of the payment
     */
    public String getCreditorAccount() {
        return creditorAccount;
    }

    /**
     * Sets the creditor account number for the payment.
     *
     * @param creditorAccount the creditor account number to be set for the payment
     */
    public void setCreditorAccount(String creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    /**
     * Gets the amount that needs to be paid for the payment.
     *
     * @return the amount of the payment
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the amount that needs to be paid for the payment.
     *
     * @param amount the amount to be set for the payment
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the purpose for the payment.
     *
     * @return the payment purpose
     */
    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    /**
     * Sets the purpose for the payment.
     *
     * @param paymentPurpose the payment purpose to be set
     */
    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    /**
     * Gets the model number of the payment.
     *
     * @return the model number of the payment
     */
    public int getModel() {
        return model;
    }

    /**
     * Sets the model number for the payment.
     *
     * @param model the model number to be set for the payment
     */
    public void setModel(int model) {
        this.model = model;
    }

    /**
     * Gets the application associated with this payment.
     *
     * @return the application associated with this payment
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets the application associated with this payment.
     *
     * @param application the application to be associated with this payment
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Indicates whether some other {@link Payment} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} - if this Payment is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id);
    }

    /**
     * Calculates hash code based on the id.
     *
     * @return hash code value for this object based on the id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
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
