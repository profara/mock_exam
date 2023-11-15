package rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    private UserRole userRole;

    @BeforeEach
    void setUp() {
        userRole = new UserRole("ROLE_USER");
    }

    @Test
    void testDefaultConstructor() {
        UserRole defaultRole = new UserRole();
        assertNull(defaultRole.getId());
        assertNull(defaultRole.getName());
    }

    @Test
    void testParameterizedConstructor() {
        assertEquals("ROLE_USER", userRole.getName());
    }

    @Test
    void testIdGetterAndSetter() {
        userRole.setId(1L);
        assertEquals(1L, userRole.getId());
    }

    @Test
    void testNameGetterAndSetter() {
        userRole.setName("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", userRole.getName());
    }

    @Test
    void testEqualsWithSameObject() {
        assertTrue(userRole.equals(userRole));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(userRole.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Object obj = new Object();
        assertFalse(userRole.equals(obj));
    }

    @Test
    void testEqualsWithDifferentId() {
        UserRole anotherRole = new UserRole("ROLE_USER");
        anotherRole.setId(2L);
        userRole.setId(1L);

        assertFalse(userRole.equals(anotherRole));
    }

    @Test
    void testEqualsWithSameId() {
        UserRole anotherRole = new UserRole("ROLE_USER");
        anotherRole.setId(1L);
        userRole.setId(1L);

        assertTrue(userRole.equals(anotherRole));
    }

    @Test
    void testHashCodeConsistency() {
        int initialHashCode = userRole.hashCode();
        userRole.setName("ROLE_CHANGED");
        assertEquals(initialHashCode, userRole.hashCode());
    }

    @Test
    void testHashCodeDifference() {
        UserRole anotherRole = new UserRole("ROLE_USER");
        anotherRole.setId(2L);
        userRole.setId(1L);

        assertNotEquals(userRole.hashCode(), anotherRole.hashCode());
    }

    @Test
    void testToString() {
        String expectedString = "UserRole{" +
                "id=null, name='" + userRole.getName() + '\'' +
                '}';
        assertEquals(expectedString, userRole.toString());
    }
}
