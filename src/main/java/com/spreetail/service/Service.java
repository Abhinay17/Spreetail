package com.spreetail.service;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.spreetail.constants.Constants.*;
import static com.spreetail.utlity.Utility.*;

public class Service {
    // This is Map will store key values as this is static all operations are preformed on this single instance
    private static Map<String, ArrayList<String>> dictionary = new HashMap<>();
    private static Stack history = new Stack();

    /*
     * this method will pass command line arguments
     * @params String
     * @return list<String>
     */
    public List<String> processArguments(String argValue) {
        Service serviceClass = new Service();
        String[] strArray = argValue.split(" ");
        history.push(argValue);
        /*
         * will perform validation check on arguments, if validation check fails
         * @return as list<Invalid Argument>
         */
        return (minLengthThree(strArray) || minLengthTwo(strArray) || onlyOne(strArray)) ? serviceClass.function(strArray)
                : new ArrayList<>(Collections.singletonList(INVALID_ARGS));
    }

    /*
     * this method will perform action on the argument's
     * @params String[]
     * @return list<String>
     */
    public List<String> function(String[] argValues) {
        String Identifier = argValues[0].toUpperCase(Locale.forLanguageTag("en-US"));
        List<String> result = new ArrayList<>();

        switch (Identifier) {
            /*
             * Add a member to a collection for a given key.
             * Displays an error if the value already existed in the collection.
             */
            case ADD:
                result = addList(argValues);
                break;

            // Returns all the keys in the dictionary
            case KEYS:
                result = (!dictionary.isEmpty()) ? dictionary.keySet().stream().collect(Collectors.toList())
                        : Arrays.asList(EMPTY_SET);
                break;

            // List all VALUES of given key from dictionary
            case MEMBERS:
                result = (!CollectionUtils.isEmpty(dictionary.get(argValues[1]))) ? dictionary.get(argValues[1])
                        .stream().collect(Collectors.toList()) : Arrays.asList(ERROR_KEY);
                break;

            /*
             * Removes a value from a key. If the last value is removed from the key,
             * they key is removed from the dictionary.
             * If the key or value does not exist, displays an error
             */
            case REMOVE:
                result = (!CollectionUtils.isEmpty(dictionary.get(argValues[1]))) ? removeList(argValues) :
                        Arrays.asList(ERROR_KEY);
                break;

            /*
             * Removes all value for a key and removes the key from the dictionary.
             * Returns an error if the key does not exist
             */
            case REMOVE_ALL:
                if (!CollectionUtils.isEmpty(dictionary.get(argValues[1]))) {
                    dictionary.remove(argValues[1]);
                    result.add(REMOVED);
                } else
                    result.add(ERROR_KEY);
                break;

            // Removes all keys and all values from the dictionary.
            case CLEAR:
                if (!CollectionUtils.isEmpty(dictionary))
                    dictionary.clear();
                result.add(CLEARED);
                break;

            // Returns True if a key exists in dictionary or False.
            case KEY_EXISTS:
                boolean key = dictionary.containsKey(argValues[1]);
                result.add(String.valueOf(key));
                break;

            // Returns True if a Value exists for a Key in dictionary or False.
            case VALUE_EXISTS:
                boolean value = (dictionary.containsKey(argValues[1]) && dictionary.get(argValues[1]).contains(
                        argValues[2]));
                result.add(String.valueOf(value));
                break;

            // Returns all the values in the dictionary. Returns nothing if there are none.
            case ALL_MEMBERS:
                if (!CollectionUtils.isEmpty(dictionary)) {
                    List<String> finalResult = result;
                    dictionary.forEach((x, y) -> finalResult.addAll(y));
                } else {
                    result.add(EMPTY_SET);
                }
                break;

            /*
             * Returns all keys in the dictionary and all of their values.
             * Returns nothing if there are none
             */
            case ITEMS:
                result = (!CollectionUtils.isEmpty(dictionary)) ? itemsList(dictionary) : Arrays.asList(EMPTY_SET);
                break;

            case HISTORY:
                result = (!CollectionUtils.isEmpty(history)) ? history : Arrays.asList(EMPTY_SET);
                break;

            case HELP:
                result = helpFunction();
                break;

            // this block will execute when given Function not found above
            default:
                result.add(argValues[0].concat(Function_NOT_FOUND));
        }
        return result;
    }

    // Process ADD from @function method
    private List<String> addList(String[] input) {
        if (!dictionary.containsKey(input[1])) {
            dictionary.put(input[1], new ArrayList<>(Arrays.asList(input[2])));
        } else {
            if (dictionary.get(input[1]).contains(input[2])) return Arrays.asList(DUPLICATE_VALUE);
            dictionary.get(input[1]).add(input[2]);
        }
        return Arrays.asList(ADDED);
    }

    // Process REMOVE from @function method
    private List<String> removeList(String[] input) {
        if (input.length < 3) return Arrays.asList(INVALID_ARGS);
        if (!dictionary.get(input[1]).contains(input[2]))
            return Arrays.asList(ERROR_VALUE);
        dictionary.get(input[1]).remove(input[2]);
        if (dictionary.get(input[1]).isEmpty())
            dictionary.remove(input[1]);
        return Arrays.asList(REMOVED);
    }

    // Process ITEMS from @function method
    private List<String> itemsList(Map<String, ArrayList<String>> ItemsMap) {
        List<String> finalItemList = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> fullMap : ItemsMap.entrySet()) {
            for (String valueList : fullMap.getValue()) {
                finalItemList.add(fullMap.getKey().concat(": ").concat(valueList));
            }
        }
        return finalItemList;
    }


}
