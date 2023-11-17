package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.entity.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.USER_PROFILE_TABLE_NAME;
import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.FOREIGN_KEY_USER_ROLE;

/**
 * Represents a user's profile in the system.
 * This entity is linked to the {@link UserRole} entity.
 * It implements {@link UserDetails} for Spring Security integration.
 *
 * <p>This class includes methods to get and set its fields, along with UserDetails implemented methods.
 * It also overrides the {@code equals}, {@code hashCode}, {@code toString} methods from the {@code Object} class.</p>
 */
@Entity
@Table(name = USER_PROFILE_TABLE_NAME)
public class UserProfile implements UserDetails {

    /**
     * Unique identifier for the user profile. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's email. It is unique and a required field.
     */
    @Column(columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
    private String email;

    /**
     * User's password. It is a required field.
     */
    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String password;

    /**
     * Indicates whether the user is enabled or not. It is a required field.
     */
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private boolean enabled;

    /**
     * The role of the user. The association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_USER_ROLE)
    private UserRole userRole;

    /**
     * Default constructor required by JPA.
     */
    public UserProfile() {
    }

    /**
     * Creates a new user profile with the specified email and password.
     *
     * @param email    User's email, not null
     * @param password User's password, not null
     */
    public UserProfile(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Creates a new user profile with specified email, password, enabled status, and user role.
     *
     * @param email     User's email, not null
     * @param password  User's password, not null
     * @param enabled   Indicates whether the user is enabled or not
     * @param userRole  User's role, not null
     */
    public UserProfile(String email, String password, boolean enabled, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.userRole = userRole;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the unique identifier of the user profile.
     *
     * @return the identifier of the user profile
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user profile.
     *
     * @param id the identifier to be set for the user profile
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to be set for the user, not null
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the authorities (roles) of the user.
     *
     * @return a collection of authorities granted to the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.getName()));
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the username (email) of the user.
     *
     * @return the username (email) of the user
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account is expired.
     *
     * @return {@code true} since the account is never considered expired in this implementation
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     *
     * @return {@code true} since the account is never considered locked in this implementation
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials are expired.
     *
     * @return {@code true} since the credentials are never considered expired in this implementation
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Gets the enabled status of the user.
     *
     * @return
     * <ul>
     *     <li>{@code true} - if the user is enabled</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to be set for the user, not null
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * Sets the role of the user.
     *
     * @param userRole the role to be set for the user, not null
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * Indicates whether some other {@link UserProfile} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     * <ul>
     *     <li>{@code true} - if this UserProfile is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Calculates hash code based on the id.
     *
     * @return hash code value for this object based on the id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of the user profile.
     *
     * @return a string representation of the user profile
     */
    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", userRole=" + userRole +
                '}';
    }
}
