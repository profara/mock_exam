package rs.ac.bg.fon.silab.mock_exam.domain.currency.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    private Currency currency;

    @BeforeEach
    void setUp() {
        currency = new Currency("Dinar", "RSD");
    }

    @Test
    void testNameGetterAndSetter() {
        currency.setName("Euro");
        assertEquals("Euro", currency.getName());
    }

    @Test
    void testCodeGetterAndSetter() {
        currency.setCode("EUR");
        assertEquals("EUR", currency.getCode());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(currency.equals(currency));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(currency.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(currency.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        Currency anotherCurrency = new Currency("Dinar", "RSD");
        anotherCurrency.setId(2L);
        currency.setId(1L);

        assertFalse(currency.equals(anotherCurrency));
    }

    @Test
    void testEqualsWithSameId() {
        Currency anotherCurrency = new Currency("Dinar", "RSD");
        anotherCurrency.setId(1L);
        currency.setId(1L);

        assertTrue(currency.equals(anotherCurrency));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = currency.hashCode();
        currency.setName("Yen");
        assertEquals(initialHashCode, currency.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        Currency anotherCurrency = new Currency("Dinar", "RSD");
        anotherCurrency.setId(2L);
        currency.setId(1L);

        assertNotEquals(currency.hashCode(), anotherCurrency.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Currency{" +
                "id=null, name='" + currency.getName() + '\'' +
                ", code='" + currency.getCode() + '\'' +
                '}';
        assertEquals(expectedString, currency.toString());
    }
}
