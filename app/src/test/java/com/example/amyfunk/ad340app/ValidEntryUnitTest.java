package com.example.amyfunk.ad340app;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/**
 * Unit tests for the {@link MainActivity} .
 */
public class ValidEntryUnitTest {
    private MainActivity mActivity = new MainActivity();

    @Test
    public void validInput_ReturnsTrue() {
        assertThat(mActivity.inputIsValid("chowder"), is(true));
    }

    @Test
    public void emptyInput_ReturnsFalse() {
        assertThat(mActivity.inputIsValid(""), is(false));
    }
}
