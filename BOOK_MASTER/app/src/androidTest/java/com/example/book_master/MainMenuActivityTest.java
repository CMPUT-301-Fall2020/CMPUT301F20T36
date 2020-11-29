package com.example.book_master;

import android.app.Activity;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.robotium.solo.Solo;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class for LoginActivity, all UI tests are written here
 * Apply Robotium test framework
 */
@RunWith(AndroidJUnit4.class)
public class MainMenuActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Login to the main menu, click if the user info is recorded
     */
    @Test
    public void checkUserInfo() {
        // start at MainActivity
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        // enter email & password
        solo.enterText((EditText) solo.getView(R.id.email_text), "lyon233333@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_text), "ntd10086");
        // enter to main_menu_activity
        solo.clickOnView(solo.getView(R.id.activity_main_login));
        solo.assertCurrentActivity("Wrong Activity", main_menu_activity.class);
        // enter to check_list_activity
        solo.clickOnView(solo.getView(R.id.view_profile_button));
        solo.assertCurrentActivity("Wrong Activity", profile_description_activity.class);
        assertTrue(solo.waitForText("QAQ", 1, 2000));
    }

    /**
     * Login to the main menu, click if the recorded book is in the checklist
     */
    @Test
    public void checkBookList() {
        // start at MainActivity
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        // enter email & password
        solo.enterText((EditText) solo.getView(R.id.email_text), "lyon233333@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_text), "ntd10086");
        // enter to main_menu_activity
        solo.clickOnView(solo.getView(R.id.activity_main_login));
        solo.assertCurrentActivity("Wrong Activity", main_menu_activity.class);
        // enter to check_list_activity
        solo.clickOnView(solo.getView(R.id.CHECKMYLIST_button));
        solo.assertCurrentActivity("Wrong Activity", check_list_activity.class);
        assertTrue(solo.waitForText("for the intent test, DO NOT modify", 1, 2000));
    }

    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
