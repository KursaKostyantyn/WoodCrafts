package io.teamchallenge.sosna.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordGeneratorUtil {

    private static final String KEYCLOAK_SPECIAL_CHARACTERS = "?!#%$";
    private static final Integer LOWER_CASE_NUMBER_CHARACTERS = 1;
    private static final Integer UPPER_CASE_NUMBER_CHARACTERS = 1;
    private static final Integer SPECIAL_CHARACTERS = 1;
    private static final Integer NUMBER_OF_DIGITS = 1;
    private static final Integer PASSWORD_LENGTH = 10;
    private static final String INSUFFICIENT_SPECIAL = "INSUFFICIENT_SPECIAL";

    public static String generatePassword() {
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        lowerCaseRule.setNumberOfCharacters(LOWER_CASE_NUMBER_CHARACTERS);
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        upperCaseRule.setNumberOfCharacters(UPPER_CASE_NUMBER_CHARACTERS);
        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        digitRule.setNumberOfCharacters(NUMBER_OF_DIGITS);

        CharacterData specialChars = getSpecialChars();
        CharacterRule specialRule = new CharacterRule(specialChars);
        specialRule.setNumberOfCharacters(SPECIAL_CHARACTERS);

        return new PasswordGenerator().generatePassword(PASSWORD_LENGTH, lowerCaseRule, upperCaseRule, digitRule, specialRule);
    }

    private static CharacterData getSpecialChars() {
        return new CharacterData() {
            @Override
            public String getErrorCode() {
                return INSUFFICIENT_SPECIAL;
            }

            @Override
            public String getCharacters() {
                return KEYCLOAK_SPECIAL_CHARACTERS;
            }
        };
    }
}
