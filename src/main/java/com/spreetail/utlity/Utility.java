package com.spreetail.utlity;

import java.util.Arrays;
import java.util.List;

import static com.spreetail.constants.Constants.*;

public class Utility {

    public static boolean minLengthThree(String[] input) {
        return (Arrays.asList(ADD, VALUE_EXISTS).contains(input[0].toUpperCase()) && input.length >= 3);
    }

    public static boolean minLengthTwo(String[] input) {
        return (Arrays.asList(MEMBERS, KEY_EXISTS, REMOVE, REMOVE_ALL).contains(input[0].toUpperCase()) && input.length >= 2);
    }

    public static boolean onlyOne(String[] input) {
        return (Arrays.asList(HISTORY, KEYS, CLEAR, ITEMS, ALL_MEMBERS, HELP).contains(input[0].toUpperCase()) && input.length == 1);
    }

    public static List<String> helpFunction() {
        return Arrays.asList(ADD, KEYS, MEMBERS, REMOVE, REMOVE_ALL, CLEAR,
                KEY_EXISTS, VALUE_EXISTS, ALL_MEMBERS, ITEMS, HISTORY);
    }
}
