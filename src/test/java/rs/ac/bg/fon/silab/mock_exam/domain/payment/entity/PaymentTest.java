package rs.ac.bg.fon.silab.mock_exam.domain.payment.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.mock_exam.domain.application.entity.Application;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentTest {

    private Payment payment;
    private Application mockApplication;

    @BeforeEach
    void setUp() {
        mockApplication = mock(Application.class);
        payment = new Payment("REF12345", "ACC12345", new BigDecimal("100.00"), "Test Purpose", 17, mockApplication);
    }

    @Test
    void testReferenceNumberGetterAndSetter() {
        payment.setReferenceNumber("REF67890");
        assertEquals("REF67890", payment.getReferenceNumber());
    }

    @Test
    void testCreditorAccountGetterAndSetter() {
        payment.setCreditorAccount("ACC67890");
        assertEquals("ACC67890", payment.getCreditorAccount());
    }

    @Test
    void testAmountGetterAndSetter() {
        payment.setAmount(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("200.00"), payment.getAmount());
    }

    @Test
    void testPaymentPurposeGetterAndSetter() {
        payment.setPaymentPurpose("Another Purpose");
        assertEquals("Another Purpose", payment.getPaymentPurpose());
    }

    @Test
    void testModelGetterAndSetter() {
        payment.setModel(19);
        assertEquals(19, payment.getModel());
    }

    @Test
    void testApplicationGetterAndSetter() {
        Application anotherMockApplication = mock(Application.class);
        payment.setApplication(anotherMockApplication);
        assertEquals(anotherMockApplication, payment.getApplication());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(payment.equals(payment));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(payment.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(payment.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        Payment anotherPayment = new Payment("REF12345", "ACC12345", new BigDecimal("100.00"), "Test Purpose", 17, mockApplication);
        anotherPayment.setId(2L);
        payment.setId(1L);

        assertFalse(payment.equals(anotherPayment));
    }

    @Test
    void testEqualsWithSameId() {
        Payment anotherPayment = new Payment("REF12345", "ACC12345", new BigDecimal("100.00"), "Test Purpose", 17, mockApplication);
        anotherPayment.setId(1L);
        payment.setId(1L);

        assertTrue(payment.equals(anotherPayment));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = payment.hashCode();
        payment.setReferenceNumber("REF54321");
        assertEquals(initialHashCode, payment.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        Payment anotherPayment = new Payment("REF12345", "ACC12345", new BigDecimal("100.00"), "Test Purpose", 17, mockApplication);
        anotherPayment.setId(2L);
        payment.setId(1L);

        assertNotEquals(payment.hashCode(), anotherPayment.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Payment{" +
                "id=" + payment.getId() +
                ", referenceNumber='" + payment.getReferenceNumber() + '\'' +
                ", creditorAccount='" + payment.getCreditorAccount() + '\'' +
                ", amount=" + payment.getAmount() +
                ", paymentPurpose='" + payment.getPaymentPurpose() + '\'' +
                ", model=" + payment.getModel() +
                ", application=" + mockApplication +
                '}';
        assertEquals(expectedString, payment.toString());
    }
}
