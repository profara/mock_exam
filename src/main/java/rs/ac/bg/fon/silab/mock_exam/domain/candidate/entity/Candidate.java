package rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity;

import jakarta.persistence.*;
import rs.ac.bg.fon.silab.mock_exam.domain.city.entity.City;
import rs.ac.bg.fon.silab.mock_exam.domain.school.entity.School;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;

import java.util.Objects;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.*;

/**
 * Represents a candidate that applies for a mock entrance exam.
 * This entity is linked to {@link UserProfile}, {@link School}, and {@link City} entities.
 * It tracks personal information such as name, surname, address, and whether the candidate attended preparation classes.
 *
 * <p> This class includes methods to get and set its fields.
 * It also overrides the {@code equals}, {@code hashCode}, {@code toString} methods from the {@code Object} class.</p>
 */
@Entity
public class Candidate {

    /**
     * Unique identifier for the candidate. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the candidate. It is a required field.
     */
    @Column(columnDefinition = "VARCHAR(45)", nullable = false)
    private String name;

    /**
     * Surname of the candidate. It is a required field.
     */
    @Column(columnDefinition = "VARCHAR(45)", nullable = false)
    private String surname;

    /**
     * Flag indicating whether the candidate has attended preparation classes.
     */
    @Column(name = ATTENDED_PREPARATION_COLUMN_NAME,columnDefinition = "TINYINT(1)", nullable = false)
    private boolean attendedPreparation;

    /**
     * Address of the candidate. It is a required field.
     */
    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String address;

    /**
     * The user profile associated with this candidate. The association is mandatory.
     */
    @OneToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_USER_PROFILE)
    private UserProfile userProfile;

    /**
     * The school associated with this candidate. The association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_SCHOOL)
    private School school;

    /**
     * The city associated with this candidate. The association is mandatory.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = FOREIGN_KEY_CITY)
    private City city;

    /**
     * Default constructor required by JPA. It is not intended for direct use.
     */
    public Candidate() {
    }

    /**
     * Creates a new candidate with specified personal and related entity details.
     *
     * @param name the name of the candidate, not null
     * @param surname the surname of the candidate, not null
     * @param attendedPreparation a boolean flag indicating if the candidate attended preparation classes
     * @param userProfile the user profile associated with the candidate, not null
     * @param school the school associated with the candidate, not null
     * @param address the address of the candidate, not null
     * @param city the city associated with the candidate, not null
     */
    public Candidate(String name, String surname, boolean attendedPreparation, UserProfile userProfile, School school, String address, City city) {
        this.name = name;
        this.surname = surname;
        this.attendedPreparation = attendedPreparation;
        this.userProfile = userProfile;
        this.school = school;
        this.address = address;
        this.city = city;
    }

    /**
     * Gets the unique identifier of the candidate.
     *
     * @return the identifier of the candidate
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the candidate.
     *
     * @param id the identifier to be set for the candidate
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the candidate.
     *
     * @return the name of the candidate
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the candidate.
     *
     * @param name the name to be set for the candidate, not null
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the candidate.
     *
     * @return the surname of the candidate
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the candidate.
     *
     * @param surname the surname to be set for the candidate, not null
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the address of the candidate.
     *
     * @return the address of the candidate
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the candidate.
     *
     * @param address the address to be set for the candidate, not null
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Checks if the candidate has attended preparation classes.
     *
     * @return true if the candidate has attended preparation classes, false otherwise
     */
    public boolean isAttendedPreparation() {
        return attendedPreparation;
    }

    /**
     * Sets whether the candidate has attended preparation classes.
     *
     * @param attendedPreparation a boolean indicating if the candidate attended preparation classes
     */
    public void setAttendedPreparation(boolean attendedPreparation) {
        this.attendedPreparation = attendedPreparation;
    }

    /**
     * Gets the user profile associated with the candidate.
     *
     * @return the user profile linked to the candidate
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Sets the user profile associated with the candidate.
     *
     * @param userProfile the user profile to be linked to the candidate, not null
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Gets the school associated with the candidate.
     *
     * @return the school linked to the candidate
     */
    public School getSchool() {
        return school;
    }

    /**
     * Sets the school associated with the candidate.
     *
     * @param school the school to be linked to the candidate, not null
     */
    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * Gets the city associated with the candidate.
     *
     * @return the city linked to the candidate
     */
    public City getCity() {
        return city;
    }

    /**
     * Sets the city associated with the candidate.
     *
     * @param city the city to be linked to the candidate, not null
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Indicates whether some other {@link Candidate} is "equal to" this one based on the id.
     *
     * @param o the reference object with which to compare
     * @return
     *
     * <ul>
     *     <li>{@code true} - if this Candidate is the same as the object argument</li>
     *     <li>{@code false} - otherwise</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Objects.equals(id, candidate.id);
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
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", attendedPreparation=" + attendedPreparation +
                ", address='" + address + '\'' +
                ", userProfile=" + userProfile +
                ", school=" + school +
                ", city=" + city +
                '}';
    }
}
