package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERNSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;



/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MULTIPLE_WORDS = "Each value must be limited to one word\n"
            + "Eg: find n/Alex n/Ho instead of n/Alex Ho";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @return a FindCommand object that will execute the search.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_CCA, PREFIX_EDUCATION, PREFIX_MODULE, PREFIX_INTERNSHIP);

        boolean isAndSearch = false;

        if (argMultimap.getPreamble().equals("-s")) {
            isAndSearch = true;
        } else if (!argMultimap.getPreamble().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FindPersonDescriptor personDescriptor = new FindCommand.FindPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            personDescriptor.setNames(ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME)));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            personDescriptor.setPhones(ParserUtil.parsePhones(argMultimap.getAllValues(PREFIX_PHONE)));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            personDescriptor.setEmails(ParserUtil.parseEmails(argMultimap.getAllValues(PREFIX_EMAIL)));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            personDescriptor.setAddresses(ParserUtil.parseAddresses(argMultimap.getAllValues(PREFIX_ADDRESS)));
        }
        if (argMultimap.getValue(PREFIX_CCA).isPresent()) {
            List<Tag> cca = ParserUtil.parseTagsForFind(argMultimap.getAllValues(PREFIX_CCA), Tag.CCA);
            personDescriptor.setCcas(cca);
        }
        if (argMultimap.getValue(PREFIX_EDUCATION).isPresent()) {
            List<Tag> education = ParserUtil.parseTagsForFind(argMultimap.getAllValues(PREFIX_EDUCATION),
                    Tag.EDUCATION);
            personDescriptor.setEducations(education);
        }
        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            List<Tag> module = ParserUtil.parseTagsForFind(argMultimap.getAllValues(PREFIX_MODULE), Tag.MODULE);
            personDescriptor.setModules(module);
        }
        if (argMultimap.getValue(PREFIX_INTERNSHIP).isPresent()) {
            List<Tag> internship = ParserUtil.parseTagsForFind(argMultimap.getAllValues(PREFIX_INTERNSHIP),
                    Tag.INTERNSHIP);
            personDescriptor.setInternships(internship);
        }

        if (!personDescriptor.isAnyFieldPresent()) {
            throw new ParseException(FindCommand.MESSAGE_NO_PARAMETERS);
        }

        if (isAndSearch) {
            return new FindAndPredicateParser().parse(personDescriptor);
        } else {
            return new FindOrPredicateParser().parse(personDescriptor);
        }
    }

    //    private List<Tag> splitAndTrim(List<Tag> tags) {
    //        List<Tag> modifiedList = new ArrayList<>();
    //        for (Tag t : tags) {
    //            if (t.tagName.split(" ").length > 1) {
    //
    //            }
    //            modifiedList.add(t);
    //        }
    //    }

}
