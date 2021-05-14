

package com.spreetail.service;

        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.InjectMocks;
        import org.mockito.junit.MockitoJUnitRunner;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.Stack;

        import static com.spreetail.constants.Constants.*;
        import static org.hamcrest.MatcherAssert.assertThat;
        import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

    @InjectMocks
    private Service service;

    @Before // Add values to map before each test case executes
    public void before() {
        service = new Service();
        service.function(new String[]{CLEAR});
        service.function(new String[]{ADD, FRUIT, ORANGE});
        service.function(new String[]{ADD, FRUIT, APPLE});
    }

    @Test // Test Case for ADD function
    public void testADD() {
        assertThat("ADD existing Key and Value", service.function
                (new String[]{ADD, FRUIT, APPLE}), is(Arrays.asList(DUPLICATE_VALUE)));
        service.function(new String[]{CLEAR});
        assertThat("ADD new Key and Value", service.function
                (new String[]{"add", FRUIT, APPLE}), is(Arrays.asList(ADDED)));
        assertThat("ADD Same Key different Value", service.function
                (new String[]{ADD, FRUIT, ORANGE}), is(Arrays.asList(ADDED)));

    }

    @Test // Test Case for KEYS function
    public void testKEYS() {
        assertThat("Test case for KEYS function", service.function
                (new String[]{KEYS}), is(Arrays.asList(FRUIT)));
        service.function(new String[]{CLEAR});
        assertThat("Empty KEYS", service.function
                (new String[]{KEYS}), is(Arrays.asList(EMPTY_SET)));
    }

    @Test // Test Case for MEMBERS function
    public void testMembers() {
        assertThat("Return all values of KEY(FRUIT)", service.function
                (new String[]{MEMBERS, FRUIT}), is(Arrays.asList(ORANGE, APPLE)));
        service.function(new String[]{CLEAR});
        assertThat("Empty Set with no Keys and Values", service.function
                (new String[]{MEMBERS, FRUIT}), is(Arrays.asList(ERROR_KEY)));
    }

    @Test // Test Case for REMOVE function
    public void testRemove() {
        assertThat("Remove Fruit value(APPLE)", service.function
                (new String[]{REMOVE, FRUIT, APPLE}), is(Arrays.asList(REMOVED)));
        assertThat("Remove KEY which don't exists", service.function
                (new String[]{REMOVE, CARROT, APPLE}), is(Arrays.asList(ERROR_KEY)));
        assertThat("Remove VALUE which don't exists", service.function
                (new String[]{REMOVE, FRUIT, APPLE}), is(Arrays.asList(ERROR_VALUE)));
        assertThat("Passing invalid Argument", service.function
                (new String[]{REMOVE, FRUIT}), is(Arrays.asList(INVALID_ARGS)));
    }

    @Test // Test Case for REMOVEALL function
    public void testRemoveAll() {
        assertThat("RemoveALL values of KEY(Fruit)", service.function
                (new String[]{REMOVE_ALL, FRUIT}), is(Arrays.asList(REMOVED)));
        assertThat("RemoveALL KEY which don't exists", service.function
                (new String[]{REMOVE_ALL, CARROT, APPLE}), is(Arrays.asList(ERROR_KEY)));
    }

    @Test // Test Case for CLEAR function
    public void testCLEAR() {
        assertThat("CLEAR all", service.function
                (new String[]{CLEAR}), is(Arrays.asList(CLEARED)));
        assertThat("CLEAR empty set", service.function
                (new String[]{CLEAR}), is(Arrays.asList(CLEARED)));
    }

    @Test // Test Case for KEYEXISTS function
    public void testKeyExists() {
        assertThat("Return TRUE if key exists", service.function
                (new String[]{KEY_EXISTS, FRUIT}), is(Arrays.asList("true")));
        assertThat("Return FALSE if key don't exists", service.function
                (new String[]{KEY_EXISTS, VEGETABLE}), is(Arrays.asList("false")));
    }

    @Test // Test Case for VALUEEXISTS function
    public void testValueExists() {
        assertThat("Return TRUE if Value key exists", service.function
                (new String[]{VALUE_EXISTS, FRUIT, APPLE}), is(Arrays.asList("true")));
        assertThat("Return FALSE if Value key don't exists", service.function
                (new String[]{VALUE_EXISTS, FRUIT, CARROT}), is(Arrays.asList("false")));
    }

    @Test // Test Case for ALLMEMBERS function
    public void testAllMembers() {
        service.function(new String[]{ADD, VEGETABLE, CARROT});
        assertThat("Return Values all KEYS that exists", service.function
                (new String[]{ALL_MEMBERS}), is(Arrays.asList(ORANGE, APPLE, CARROT)));
        service.function(new String[]{CLEAR});
        assertThat("Return empty set when key don't exists", service.function
                (new String[]{ALL_MEMBERS}), is(Arrays.asList(EMPTY_SET)));
    }

    @Test // Test Case for ITEMS function
    public void testItems() {
        service.function(new String[]{ADD, VEGETABLE, CARROT});
        List<String> result = new ArrayList<>();
        result.add(FRUIT + ": " + ORANGE);
        result.add(FRUIT + ": " + APPLE);
        result.add(VEGETABLE + ": " + CARROT);
        assertThat("Return all KEYS and its Values", service.function
                (new String[]{ITEMS}), is(result));
        service.function(new String[]{CLEAR});
        assertThat("Return empty set when key don't exists", service.function
                (new String[]{ITEMS}), is(Arrays.asList(EMPTY_SET)));
    }

    @Test // Test Case for HISTORY function
    public void testHistory() {
        Stack result = new Stack();
        result.add(ADD+" "+FRUIT+" "+ORANGE);
        service.processArguments(ADD+" "+FRUIT+" "+ORANGE);
        assertThat("Return List<String> when Function is valid", service.function
                (new String[]{HISTORY}), is(result));
    }

    @Test // Test Case for DEFAULT case
    public void testDefault() {
        assertThat("Return Function not valid", service.function
                (new String[]{CARROT}), is(Arrays.asList(CARROT.concat(Function_NOT_FOUND))));
    }

    @Test // Test Case for ProcessArguments case
    public void testProcessArguments() {
        assertThat("Return Invalid Argument when Function not valid", service.processArguments(CARROT),
                is(Arrays.asList(INVALID_ARGS)));
        assertThat("Return List<String> when Function is valid",
                service.processArguments(ALL_MEMBERS), is(Arrays.asList(ORANGE, APPLE)));
    }
}
