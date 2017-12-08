package com.example.mike0.eventsapp.main;

import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mike0.eventsapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

/**
 * Created by mike0 on 12/6/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    String eventTitle = "club";

    @Before
    public void setUp() throws Exception {
        CountingIdlingResource mainActivityIdlingResource = mActivityTestRule.getActivity().getEspressoIdlingResourceForMainActivity();

        registerIdlingResources(mainActivityIdlingResource);
    }

    @Test
    public void getTextFromDialogBoxShouldShowIntoMainActivity() {

        // Make sure dialog box is displayed
        onView(withText("Enter an Event Title")).inRoot(isDialog()).check(matches(isDisplayed()));

        // Type an event word search
        onView(withId(R.id.search_tv)).inRoot(isDialog()).perform(typeText(eventTitle));

        // Click Ok button to accept text input
        onView(withId(R.id.btn_ok)).inRoot(isDialog()).perform(click());

        // Assert that the text is shown in the MainActivity
        onView(withId(R.id.event_results_total)).check(matches(withText(containsString(eventTitle))));
    }
}