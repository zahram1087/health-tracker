package com.mohamed.health_tracker;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
//import androidx.test.core.app.ApplicationProvider;
import org.junit.runner.RunWith;

//import static com.google.common.truth.Truth.assertThat;
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FingerExerciseTest {
    //https://developer.android.com/training/testing/espresso/basics

    @Rule
    public ActivityTestRule<FingerExercise> mActivityRule =
            new ActivityTestRule<>(FingerExercise.class);

    @Test
    public void buttonText() {

        // check mateches dispplay.
        onView(allOf(withId(R.id.button), withText("INCREASE")))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testingClicksCounted() {

        for (int i = 1; i < 101; i++) {
            onView(allOf(withId(R.id.button), withText("INCREASE")))
                    .perform(click());

            if (i % 50 == 0) {
                onView(allOf(withId(R.id.editText), withText("YOUR SUPER STRONG")))
                        .check(matches(isDisplayed()));
            } else if (i % 10 == 0) {
                onView(allOf(withId(R.id.editText), withText("WOW SUPER FINGER!")))
                        .check(matches(isDisplayed()));
            } else {
                onView(allOf(withId(R.id.editText), withText("Clicks: " + i)))
                        .check(matches(isDisplayed()));
            }

        }
    }
}