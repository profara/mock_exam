package rs.ac.bg.fon.silab.mock_exam.domain.city.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    private City city;
    private City anotherCity;

    @BeforeEach
    void setUp() {
        city = new City(11000L, "Belgrade");
        anotherCity = new City(21000L, "Novi Sad");
    }

    @Test
    void testDefaultConstructor() {
        City defaultCity = new City();
        assertNull(defaultCity.getZipCode());
        assertNull(defaultCity.getName());
    }

    @Test
    void testParameterizedConstructor() {
        assertEquals(11000L, city.getZipCode());
        assertEquals("Belgrade", city.getName());
    }

    @Test
    void testZipCodeGetterAndSetter() {
        city.setZipCode(31000L);
        assertEquals(31000L, city.getZipCode());
    }

    @Test
    void testNameGetterAndSetter() {
        city.setName("Subotica");
        assertEquals("Subotica", city.getName());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(city.equals(city));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(city.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(city.equals(obj));
    }

    @Test
    void testEqualsWithDifferentZipCode() {
        assertFalse(city.equals(anotherCity));
    }

    @Test
    void testEqualsWithSameZipCode() {
        City sameZipCity = new City(11000L, "SomeCity");

        assertTrue(city.equals(sameZipCity));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = city.hashCode();
        city.setName("ChangedName");
        assertEquals(initialHashCode, city.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        assertNotEquals(city.hashCode(), anotherCity.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "City{" +
                "zipCode=" + city.getZipCode() +
                ", name='" + city.getName() + '\'' +
                '}';
        assertEquals(expectedString, city.toString());
    }
}
