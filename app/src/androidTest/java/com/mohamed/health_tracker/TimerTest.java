package com.mohamed.health_tracker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TimerTest {
    @Rule
    public ActivityTestRule<Timer> mActivityRule =
            new ActivityTestRule<>(Timer.class);

    //rule for testing intent:
    @Rule
    public IntentsTestRule<Timer> mIntentsRule =
            new IntentsTestRule<>(Timer.class);
    @Test
    public void textContent(){

        onView(allOf(withId(R.id.textView), withText("Timer")))
                .check(matches(isDisplayed()));
    }


    @Test
    public void buttonText() {
        //testing button Naming
        // check mateches dispplay.
        onView(allOf(withId(R.id.button2), withText("RESET")))
                .check(matches(isDisplayed()));
    }



}