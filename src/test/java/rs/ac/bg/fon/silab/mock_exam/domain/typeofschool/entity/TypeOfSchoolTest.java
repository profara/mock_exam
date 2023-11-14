package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeOfSchoolTest {

    private TypeOfSchool typeOfSchool;
    private TypeOfSchool anotherTypeOfSchool;

    @BeforeEach
    void setUp() {
        typeOfSchool = new TypeOfSchool("Gimnazija");
        typeOfSchool.setId(1L);
        anotherTypeOfSchool = new TypeOfSchool("Ekonomska");
        anotherTypeOfSchool.setId(2L);
    }

    @Test
    void testDefaultConstructor() {
        TypeOfSchool defaultTypeOfSchool = new TypeOfSchool();
        assertNull(defaultTypeOfSchool.getId());
        assertNull(defaultTypeOfSchool.getName());
    }

    @Test
    void testParameterizedConstructor() {
        TypeOfSchool parameterizedTypeOfSchool = new TypeOfSchool("Gimnazija");
        assertEquals("Gimnazija", parameterizedTypeOfSchool.getName());
        assertNull(parameterizedTypeOfSchool.getId());
    }

    @Test
    void testIdGetterAndSetter() {
        typeOfSchool.setId(3L);
        assertEquals(3L, typeOfSchool.getId());
    }

    @Test
    void testNameGetterAndSetter() {
        typeOfSchool.setName("Medicinska");
        assertEquals("Medicinska", typeOfSchool.getName());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(typeOfSchool.equals(typeOfSchool));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(typeOfSchool.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(typeOfSchool.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        assertFalse(typeOfSchool.equals(anotherTypeOfSchool));
    }

    @Test
    void testEqualsWithSameId() {
        TypeOfSchool sameIdTypeOfSchool = new TypeOfSchool("Gimnazija");
        sameIdTypeOfSchool.setId(1L);

        assertTrue(typeOfSchool.equals(sameIdTypeOfSchool));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = typeOfSchool.hashCode();
        typeOfSchool.setName("Promenjeno ime");
        assertEquals(initialHashCode, typeOfSchool.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        assertNotEquals(typeOfSchool.hashCode(), anotherTypeOfSchool.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "TypeOfSchool{" +
                "id=" + typeOfSchool.getId() +
                ", name='" + typeOfSchool.getName() + '\'' +
                '}';
        assertEquals(expectedString, typeOfSchool.toString());
    }
}
