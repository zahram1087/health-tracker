package com.mohamed.health_tracker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExcerciseDiaryTest {

    @Rule
    public ActivityTestRule<ExcerciseDiary> mActivityRule =
            new ActivityTestRule<>(ExcerciseDiary.class);

    @Test
    public void buttonTextSubmit() {
        //testing button Naming
        // check mateches dispplay.
        onView(allOf(withId(R.id.save), withText("SUBMIT")))
                .check(matches(isDisplayed()));
    }

    //TESTING IF EDIT TEXTS EXIST FOR CAPTURING USER INPUT
    @Test
    public void hintMatchesTitleTextEdit(){
        onView(withId(R.id.title)).check(matches(withHint("Title")));
    }

    @Test
    public void hintMatchesQuantityTextEdit(){
        onView(withId(R.id.quantity)).check(matches(withHint("enter quantity")));
    }

    @Test
    public void hintMatchesDescriptionTextEdit(){
        onView(withId(R.id.description)).check(matches(withHint("description")));
    }


}