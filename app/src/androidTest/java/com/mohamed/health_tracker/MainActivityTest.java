package com.mohamed.health_tracker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    //source: https://developer.android.com/training/testing/ui-testing/espresso-testing
    //added:  androidTestImplementation 'androidx.test.espresso:espresso-intents:3.1.0'
    @Rule
    public IntentsTestRule<MainActivity> mIntentsRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void goToFingerExcercise() {

        onView(withId(R.id.button4)).perform(click());
        intended(hasComponent(Timer.class.getName()));

    }

    @Test
    public void goToTimer() {
        onView(withId(R.id.button3)).perform(click());
        intended(hasComponent(FingerExercise.class.getName()));

    }

    @Test
    public void goToExcerciseDiary() {
        onView(withId(R.id.button7)).perform(click());
        intended(hasComponent(ExcerciseDiary.class.getName()));
    }

    //TESTING FOR BUTTON PRESENCE
    @Test
    public void buttonTextToTimer() {
        //testing button Naming
        // check mateches dispplay.
        onView(allOf(withId(R.id.button4), withText("TIMER")))
                .check(matches(isDisplayed()));
    }
    @Test
    public void buttonTextToFinger() {
        //testing button Naming
        // check mateches dispplay.
        onView(allOf(withId(R.id.button3), withText("FINGER")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void buttonTextToDiary() {
        //testing button Naming
        // check mateches dispplay.
        onView(allOf(withId(R.id.button7), withText("DIARY")))
                .check(matches(isDisplayed()));
    }


}

