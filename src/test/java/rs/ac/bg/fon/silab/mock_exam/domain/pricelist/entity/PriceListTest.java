package rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class PriceListTest {

    private PriceList priceList;

    @BeforeEach
    void setUp() {
        priceList = new PriceList(Year.of(2021));
    }

    @Test
    void testYearGetterAndSetter() {
        Year newYear = Year.of(2022);
        priceList.setYear(newYear);
        assertEquals(newYear, priceList.getYear());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(priceList.equals(priceList));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(priceList.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(priceList.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        PriceList anotherPriceList = new PriceList(Year.of(2021));
        anotherPriceList.setId(2L);
        priceList.setId(1L);

        assertFalse(priceList.equals(anotherPriceList));
    }

    @Test
    void testEqualsWithSameId() {
        PriceList anotherPriceList = new PriceList(Year.of(2021));
        anotherPriceList.setId(1L);
        priceList.setId(1L);

        assertTrue(priceList.equals(anotherPriceList));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = priceList.hashCode();
        priceList.setYear(Year.of(2023));
        assertEquals(initialHashCode, priceList.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        PriceList anotherPriceList = new PriceList(Year.of(2021));
        anotherPriceList.setId(2L);
        priceList.setId(1L);

        assertNotEquals(priceList.hashCode(), anotherPriceList.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "PriceList{" +
                "id=null, year=" + priceList.getYear() +
                '}';
        assertEquals(expectedString, priceList.toString());
    }
}
