package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    // Data fields
    private final List<Tag> educations;
    private final List<Tag> internships;
    private final List<Tag> modules;
    private final List<Tag> ccas;
    private final List<Event> events;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address) {
        requireAllNonNull(name, phone, email, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.educations = new ArrayList<>();
        this.internships = new ArrayList<>();
        this.modules = new ArrayList<>();
        this.ccas = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    /**
     * Second constructor for Person.
     */
    public Person(Name name, Phone phone, Email email, Address address, List<Tag> educations, List<Tag> internships,
                  List<Tag> modules, List<Tag> ccas) {
        requireAllNonNull(name, phone, email, address, educations, internships, modules, ccas);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.educations = educations;
        this.internships = internships;
        this.modules = modules;
        this.ccas = ccas;
        this.events = new ArrayList<>();
    }

    /**
     * Third constructor for Person.
     */
    public Person(Person person, Event event) {
        requireAllNonNull(person, event);
        Set<Event> temp = new HashSet<>(person.getEvents());
        temp.add(event);
        this.name = person.name;
        this.phone = person.phone;
        this.email = person.email;
        this.address = person.address;
        this.educations = person.educations;
        this.internships = person.internships;
        this.modules = person.modules;
        this.ccas = person.ccas;
        this.events = new ArrayList<>(temp);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public List<Tag> getEducations() {
        return educations;
    }

    public List<Tag> getInternships() {
        return internships;
    }

    public List<Tag> getModules() {
        return modules;
    }

    public List<Tag> getCcas() {
        return ccas;
    }

    public List<Event> getEvents() {
        return events;
    }

    public Person addEvent(Event event) {
        return new Person(this, event);
    }

    public List<String> getEducationStrings() {
        return getEducations().stream().map(education -> education.getTagString())
                .collect(Collectors.toList());
    }

    public List<String> getInternshipStrings() {
        return getInternships().stream().map(internship -> internship.getTagString())
                .collect(Collectors.toList());
    }

    public List<String> getModuleStrings() {
        return getModules().stream().map(module -> module.getTagString())
                .collect(Collectors.toList());
    }

    public List<String> getCcaStrings() {
        return getCcas().stream().map(cca -> cca.getTagString())
                .collect(Collectors.toList());
    }
    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getEducations().equals(getEducations())
                && otherPerson.getInternships().equals(getInternships())
                && otherPerson.getModules().equals(getModules())
                && otherPerson.getCcas().equals(getCcas())
                && otherPerson.getEvents().equals(getEvents());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, educations, internships, modules, ccas, events);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Educations: ")
                .append(getEducations())
                .append("; Internships: ")
                .append(getInternships())
                .append("; Modules: ")
                .append(getModules())
                .append("; Ccas: ")
                .append(getCcas());

        return builder.toString();
    }

}
