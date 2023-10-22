package rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidateTest {

    private Candidate candidate;
    private UserProfile mockUserProfile;
    private School mockSchool;
    private City mockCity;

    @BeforeEach
    void setUp() {
        mockUserProfile = mock(UserProfile.class);
        mockSchool = mock(School.class);
        mockCity = mock(City.class);
        candidate = new Candidate("John", "Doe", true, mockUserProfile, mockSchool, "123 Street", mockCity);
    }

    @Test
    void testNameGetterAndSetter() {
        candidate.setName("Jane");
        assertEquals("Jane", candidate.getName());
    }

    @Test
    void testSurnameGetterAndSetter() {
        candidate.setSurname("Smith");
        assertEquals("Smith", candidate.getSurname());
    }

    @Test
    void testAttendedPreparationGetterAndSetter() {
        candidate.setAttendedPreparation(false);
        assertFalse(candidate.isAttendedPreparation());
    }

    @Test
    void testAddressGetterAndSetter() {
        candidate.setAddress("456 Lane");
        assertEquals("456 Lane", candidate.getAddress());
    }

    @Test
    void testUserProfileGetterAndSetter() {
        UserProfile anotherMockProfile = mock(UserProfile.class);
        candidate.setUserProfile(anotherMockProfile);
        assertEquals(anotherMockProfile, candidate.getUserProfile());
    }

    @Test
    void testSchoolGetterAndSetter() {
        School anotherMockSchool = mock(School.class);
        candidate.setSchool(anotherMockSchool);
        assertEquals(anotherMockSchool, candidate.getSchool());
    }

    @Test
    void testCityGetterAndSetter() {
        City anotherMockCity = mock(City.class);
        candidate.setCity(anotherMockCity);
        assertEquals(anotherMockCity, candidate.getCity());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(candidate.equals(candidate));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(candidate.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(candidate.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        Candidate anotherCandidate = new Candidate("John", "Doe", true, mockUserProfile, mockSchool, "123 Street", mockCity);
        anotherCandidate.setId(2L);
        candidate.setId(1L);

        assertFalse(candidate.equals(anotherCandidate));
    }

    @Test
    void testEqualsWithSameId() {
        Candidate anotherCandidate = new Candidate("John", "Doe", true, mockUserProfile, mockSchool, "123 Street", mockCity);
        anotherCandidate.setId(1L);
        candidate.setId(1L);

        assertTrue(candidate.equals(anotherCandidate));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = candidate.hashCode();
        candidate.setName("Ella");
        assertEquals(initialHashCode, candidate.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        Candidate anotherCandidate = new Candidate("John", "Doe", true, mockUserProfile, mockSchool, "123 Street", mockCity);
        anotherCandidate.setId(2L);
        candidate.setId(1L);

        assertNotEquals(candidate.hashCode(), anotherCandidate.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "Candidate{" +
                "id=null, name='" + candidate.getName() + '\'' +
                ", surname='" + candidate.getSurname() + '\'' +
                ", attendedPreparation=" + candidate.isAttendedPreparation() +
                ", address='" + candidate.getAddress() + '\'' +
                ", userProfile=" + mockUserProfile +
                ", school=" + mockSchool +
                ", city=" + mockCity +
                '}';
        assertEquals(expectedString, candidate.toString());
    }
}
