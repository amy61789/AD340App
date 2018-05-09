package com.example.amyfunk.ad340app;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.amyfunk.ad340app.MainActivity;
import com.example.amyfunk.ad340app.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class TextEntryUITest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void uiElementsExist() {
        onView(withId(R.id.editText))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button))
                .check(matches(isDisplayed()));
    }

    @Test
    public void validTextEntry() {
        // Type text and then press the button.
        onView(withId(R.id.editText))
                .perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

    }
}