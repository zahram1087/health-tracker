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

    private static final String mStringToBeTyped = "Clicks: " + 1;

    @Rule
    public ActivityTestRule<FingerExercise> mActivityRule =
            new ActivityTestRule<>(FingerExercise.class);

    @Test
    public void changeText_sameActivity() {

        // check mateches dispplay.
        onView(allOf(withId(R.id.editText), withText("Clicks: "+1)))
                .perform(click())
                .check(matches(isDisplayed()));

        //onView(allOf(withId(R.id.my_view), withText("Hello!")))
    }

}