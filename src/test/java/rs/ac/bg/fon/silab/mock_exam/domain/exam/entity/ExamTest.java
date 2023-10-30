package rs.ac.bg.fon.silab.mock_exam.domain.exam.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {

    private Exam exam;

    @BeforeEach
    void setUp() {
        exam = new Exam("Matematika");
    }

    @Test
    void testNameGetterAndSetter() {
        exam.setName("Opsta informisanost");
        assertEquals("Opsta informisanost", exam.getName());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(exam.equals(exam));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(exam.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(exam.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        Exam anotherExam = new Exam("Matematika");
        anotherExam.setId(2L);
        exam.setId(1L);

        assertFalse(exam.equals(anotherExam));
    }

    @Test
    void testEqualsWithSameId() {
        Exam anotherExam = new Exam("Matematika");
        anotherExam.setId(1L);
        exam.setId(1L);

        assertTrue(exam.equals(anotherExam));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = exam.hashCode();
        exam.setName("Fizika");
        assertEquals(initialHashCode, exam.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        Exam anotherExam = new Exam("Matematika");
        anotherExam.setId(2L);
        exam.setId(1L);

        assertNotEquals(exam.hashCode(), anotherExam.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Exam{" +
                "id=null, name='" + exam.getName() + '\'' +
                '}';
        assertEquals(expectedString, exam.toString());
    }
}
