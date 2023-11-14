package rs.ac.bg.fon.silab.mock_exam.domain.school.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {

    private School school;
    private School anotherSchool;
    private City city;
    private TypeOfSchool typeOfSchool;

    @BeforeEach
    void setUp() {
        city = new City(11000L, "Beograd");
        typeOfSchool = new TypeOfSchool("Gimnazija");
        school = new School(12345L, "Prva beogradska gimnazija", typeOfSchool, city);
        anotherSchool = new School(54321L, "Druga beogradska gimnazija", typeOfSchool, city);
    }

    @Test
    void testDefaultConstructor() {
        School defaultSchool = new School();
        assertNull(defaultSchool.getCode());
        assertNull(defaultSchool.getName());
        assertNull(defaultSchool.getTypeOfSchool());
        assertNull(defaultSchool.getCity());
    }

    @Test
    void testParameterizedConstructor() {
        assertEquals(12345L, school.getCode());
        assertEquals("Prva beogradska gimnazija", school.getName());
        assertEquals(typeOfSchool, school.getTypeOfSchool());
        assertEquals(city, school.getCity());
    }

    @Test
    void testCodeGetterAndSetter() {
        school.setCode(67890L);
        assertEquals(67890L, school.getCode());
    }

    @Test
    void testNameGetterAndSetter() {
        school.setName("Nova skola");
        assertEquals("Nova skola", school.getName());
    }

    @Test
    void testTypeOfSchoolGetterAndSetter() {
        TypeOfSchool anotherTypeOfSchool = new TypeOfSchool("Ekonomska");
        school.setTypeOfSchool(anotherTypeOfSchool);
        assertEquals(anotherTypeOfSchool, school.getTypeOfSchool());
    }

    @Test
    void testCityGetterAndSetter() {
        City anotherCity = new City(22000L, "Nis");
        school.setCity(anotherCity);
        assertEquals(anotherCity, school.getCity());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(school.equals(school));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(school.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(school.equals(obj));
    }

    @Test
    void testEqualsWithDifferentCode() {
        assertFalse(school.equals(anotherSchool));
    }

    @Test
    void testEqualsWithSameCode() {
        School sameCodeSchool = new School(12345L, "Other School", typeOfSchool, city);
        assertTrue(school.equals(sameCodeSchool));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = school.hashCode();
        school.setName("ChangedName");
        assertEquals(initialHashCode, school.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        assertNotEquals(school.hashCode(), anotherSchool.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "School{" +
                "code=" + school.getCode() +
                ", name='" + school.getName() + '\'' +
                ", typeOfSchool=" + typeOfSchool +
                ", city=" + city +
                '}';
        assertEquals(expectedString, school.toString());
    }
}

