package seedu.duke.parser;

import seedu.duke.exception.AniException;

public abstract class CommandParser {
    //Shared Constants by Parsers
    protected static final String NAME_PARAM = "n";
    protected static final String GENRE_PARAM = "g";
    protected static final int FIELD_SPLIT_LIMIT = 2;
    protected static final String SPLIT_WHITESPACE = " ";
    protected static final String NOT_RECOGNISED = " is not recognised!";
    protected static final String PARAMETER_ERROR_HEADER = "Parameter : -";
    protected static final String REQUIRE_ADDITIONAL_FIELD = " requires an additional field";
    protected static final String TOO_MUCH_FIELDS = " has too much fields";
    protected static final String NO_PARAMETER_PROVIDED = "No parameter provided";

    private static final String INTEGER_REGEX = "^\\d+$";

    /**
     * Splits the parameters into individual parts for parsing.
     *
     * @param parameter unprocessed parameter strings
     * @return parameters in parts
     */
    protected String[] parameterSplitter(String parameter) {
        return parameter.split("-");
    }

    /**
     * Checks if there is a parameter set.
     *
     * @param paramParts Array of parameters
     * @throws AniException if there is no parameter provided
     */

    protected void paramIsSetCheck(String[] paramParts) throws AniException {
        if (paramParts.length < 2) {
            throw new AniException(NO_PARAMETER_PROVIDED);
        }
    }

    /**
     * Checks if parameter has a single additional field or not.
     *
     * @param paramParts the param to check
     * @throws AniException if the parameter is missing the additional field
     */
    protected void paramFieldCheck(String[] paramParts) throws AniException {
        String invalidParameter = PARAMETER_ERROR_HEADER + paramParts[0] + REQUIRE_ADDITIONAL_FIELD;
        if (paramParts.length < 2) {
            throw new AniException(invalidParameter);
        }
        if (paramParts[1].trim().isEmpty()) {
            throw new AniException(invalidParameter);
        }
    }

    /**
     * Checks if parameter has more than one field.
     *
     * @param paramParts the param to check
     * @throws AniException if the parameter has too many fields
     */
    protected void paramExtraFieldCheck(String[] paramParts) throws AniException {
        if (paramParts.length > 2) {
            String invalidParameter = PARAMETER_ERROR_HEADER + paramParts[0] + TOO_MUCH_FIELDS;
            throw new AniException(invalidParameter);
        }
    }

    /**
     * Checks if the string is an integer.
     *
     * @param checkStr the string to check
     * @return true if it can be parsed into an integer
     */
    protected boolean isInt(String checkStr) {
        return checkStr.matches(INTEGER_REGEX);
    }
}
