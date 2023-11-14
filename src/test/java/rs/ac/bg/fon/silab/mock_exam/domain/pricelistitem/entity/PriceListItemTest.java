package rs.ac.bg.fon.silab.mock_exam.domain.pricelistitem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.mock_exam.domain.currency.entity.Currency;
import rs.ac.bg.fon.silab.mock_exam.domain.exam.entity.Exam;
import rs.ac.bg.fon.silab.mock_exam.domain.pricelist.entity.PriceList;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceListItemTest {

    private PriceListItem priceListItem;
    private PriceList mockPriceList;
    private Currency mockCurrency;
    private Exam mockExam;
    private BigDecimal testPrice;
    private boolean testPrivileged;

    @BeforeEach
    void setUp() {
        mockPriceList = mock(PriceList.class);
        mockCurrency = mock(Currency.class);
        mockExam = mock(Exam.class);
        testPrice = new BigDecimal("100.00");
        testPrivileged = true;
        priceListItem = new PriceListItem(mockPriceList, testPrice, testPrivileged, mockCurrency, mockExam);
    }

    @Test
    void testPriceListGetterAndSetter() {
        PriceList anotherMockPriceList = mock(PriceList.class);
        priceListItem.setPriceList(anotherMockPriceList);
        assertEquals(anotherMockPriceList, priceListItem.getPriceList());
    }

    @Test
    void testPriceGetterAndSetter() {
        BigDecimal newPrice = new BigDecimal("200.00");
        priceListItem.setPrice(newPrice);
        assertEquals(newPrice, priceListItem.getPrice());
    }

    @Test
    void testPrivilegedGetterAndSetter() {
        priceListItem.setPrivileged(false);
        assertFalse(priceListItem.isPrivileged());
    }

    @Test
    void testCurrencyGetterAndSetter() {
        Currency anotherMockCurrency = mock(Currency.class);
        priceListItem.setCurrency(anotherMockCurrency);
        assertEquals(anotherMockCurrency, priceListItem.getCurrency());
    }

    @Test
    void testExamGetterAndSetter() {
        Exam anotherMockExam = mock(Exam.class);
        priceListItem.setExam(anotherMockExam);
        assertEquals(anotherMockExam, priceListItem.getExam());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(priceListItem.equals(priceListItem));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(priceListItem.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(priceListItem.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        PriceListItem anotherPriceListItem = new PriceListItem(mockPriceList, testPrice, testPrivileged, mockCurrency, mockExam);
        anotherPriceListItem.setId(2L);
        priceListItem.setId(1L);

        assertFalse(priceListItem.equals(anotherPriceListItem));
    }

    @Test
    void testEqualsWithSameId() {
        PriceListItem anotherPriceListItem = new PriceListItem(mockPriceList, testPrice, testPrivileged, mockCurrency, mockExam);
        anotherPriceListItem.setId(1L);
        priceListItem.setId(1L);

        assertTrue(priceListItem.equals(anotherPriceListItem));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = priceListItem.hashCode();
        priceListItem.setPrice(new BigDecimal("300.00"));
        assertEquals(initialHashCode, priceListItem.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        PriceListItem anotherPriceListItem = new PriceListItem(mockPriceList, testPrice, testPrivileged, mockCurrency, mockExam);
        anotherPriceListItem.setId(2L);
        priceListItem.setId(1L);

        assertNotEquals(priceListItem.hashCode(), anotherPriceListItem.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "PriceListItem{" +
                "id=null, priceList=" + mockPriceList +
                ", price=" + testPrice +
                ", privileged=" + testPrivileged +
                ", currency=" + mockCurrency +
                ", exam=" + mockExam +
                '}';
        assertEquals(expectedString, priceListItem.toString());
    }
}
