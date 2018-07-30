package com.keyeonacole.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;


/**
 * Created by keyeona on 7/28/18.
 */
@RunWith(AndroidJUnit4.class)
public class clickRecipeStartFragment {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);
    @Before
    public void init(){
        mActivityRule.getActivity().getFragmentManager().beginTransaction();
    }

    @Test
    public void clickRecipeStartFragment(){


        //Find the view
        //Perform action on the view
        Espresso.onView(withId(R.id.fragmentContainer)).check(matches(isEnabled()));
        //Espresso.onView(withId(R.id.recipe_name_tv)).check(matches(isEnabled()));

        //Check if the view does what you expected
        //Espresso.onView(withId(R.id.recipe_Title_tv)).check(matches(isDisplayed()));


    }

}