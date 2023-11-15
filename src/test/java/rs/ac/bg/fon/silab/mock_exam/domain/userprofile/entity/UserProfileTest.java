package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileTest {

    private UserProfile userProfile;
    private UserRole mockUserRole;

    @BeforeEach
    void setUp() {
        mockUserRole = mock(UserRole.class);
        userProfile = new UserProfile("john.doe@example.com", "password123", true, mockUserRole);
        userProfile.setId(1L);
    }

    @Test
    void testDefaultConstructor() {
        UserProfile defaultUserProfile = new UserProfile();
        assertNull(defaultUserProfile.getId());
        assertNull(defaultUserProfile.getEmail());
        assertNull(defaultUserProfile.getPassword());
        assertFalse(defaultUserProfile.isEnabled());
        assertNull(defaultUserProfile.getUserRole());
    }

    @Test
    void testParameterizedConstructor() {
        assertEquals("john.doe@example.com", userProfile.getEmail());
        assertEquals("password123", userProfile.getPassword());
        assertTrue(userProfile.isEnabled());
        assertEquals(mockUserRole, userProfile.getUserRole());
        assertNotNull(userProfile.getId());
    }

    @Test
    void testIdGetterAndSetter() {
        userProfile.setId(2L);
        assertEquals(2L, userProfile.getId());
    }

    @Test
    void testEmailGetterAndSetter() {
        userProfile.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", userProfile.getEmail());
    }

    @Test
    void testPasswordGetterAndSetter() {
        userProfile.setPassword("newPassword");
        assertEquals("newPassword", userProfile.getPassword());
    }

    @Test
    void testEnabledGetterAndSetter() {
        userProfile.setEnabled(false);
        assertFalse(userProfile.isEnabled());
    }

    @Test
    void testUserRoleGetterAndSetter() {
        UserRole anotherMockUserRole = mock(UserRole.class);
        userProfile.setUserRole(anotherMockUserRole);
        assertEquals(anotherMockUserRole, userProfile.getUserRole());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(userProfile.equals(userProfile));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(userProfile.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(userProfile.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        UserProfile anotherUserProfile = new UserProfile("john.doe@example.com", "password123", true, mockUserRole);
        anotherUserProfile.setId(2L);

        assertFalse(userProfile.equals(anotherUserProfile));
    }

    @Test
    void testEqualsWithSameId() {
        UserProfile sameIdUserProfile = new UserProfile("jane.doe@example.com", "newPassword", false, mockUserRole);
        sameIdUserProfile.setId(1L);

        assertTrue(userProfile.equals(sameIdUserProfile));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = userProfile.hashCode();
        userProfile.setEmail("ChangedEmail");
        assertEquals(initialHashCode, userProfile.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        UserProfile anotherUserProfile = new UserProfile("john.doe@example.com", "password123", true, mockUserRole);
        anotherUserProfile.setId(2L);

        assertNotEquals(userProfile.hashCode(), anotherUserProfile.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "UserProfile{" +
                "id=" + userProfile.getId() +
                ", email='" + userProfile.getEmail() + '\'' +
                ", password='[PROTECTED]'" +
                ", enabled=" + userProfile.isEnabled() +
                ", userRole=" + mockUserRole +
                '}';
        assertEquals(expectedString, userProfile.toString().replaceAll("password='.*'", "password='[PROTECTED]'"));
    }
}

