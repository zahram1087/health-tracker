package com.mohamed.health_tracker;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;
import android.content.Context;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
//import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
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