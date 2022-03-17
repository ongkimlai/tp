package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.CcaContainsKeywordsPredicateAnd;
import seedu.address.model.person.EducationContainsKeywordsPredicateAnd;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.InternshipContainsKeywordsPredicateAnd;
import seedu.address.model.person.ModuleContainsKeywordsPredicateAnd;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

public class FindAndPredicateParser {

    /**
     * Parses a FindPersonDescriptor into a single predicate that is true if any of the predicates in each field
     * of the descriptor are satisfied. A field is satisfied if any item from the list appears. The combined predicate
     * is satisfied if all the fields are satisfied.
     *
     * @param personDescriptor an object describing the predicate list for each field.
     * @return a FindCommand to be executed.
     */
    public FindCommand parse(FindCommand.FindPersonDescriptor personDescriptor) {
        List<Predicate<Person>> predicateList = new ArrayList<>();

        personDescriptor.getStringNames().ifPresent(names ->
                predicateList.add(new NameContainsKeywordsPredicate(names)));
        personDescriptor.getStringPhones().ifPresent(phones ->
                predicateList.add(new PhoneContainsKeywordsPredicate(phones)));
        personDescriptor.getStringEmails().ifPresent(emails ->
                predicateList.add(new EmailContainsKeywordsPredicate(emails)));
        personDescriptor.getStringAddresses().ifPresent(list ->
                predicateList.add(new AddressContainsKeywordsPredicate(list)));

        personDescriptor.getStringEducations().ifPresent(list ->
                predicateList.add(new EducationContainsKeywordsPredicateAnd(list)));
        personDescriptor.getStringInternships().ifPresent(list ->
                predicateList.add(new InternshipContainsKeywordsPredicateAnd(list)));
        personDescriptor.getStringModules().ifPresent(list ->
                predicateList.add(new ModuleContainsKeywordsPredicateAnd(list)));
        personDescriptor.getStringCcas().ifPresent(list ->
                predicateList.add(new CcaContainsKeywordsPredicateAnd(list)));

        Predicate<Person> predicate = predicateList.stream().reduce(x->true, Predicate::and);

        return new FindCommand(predicate);
    }
}
