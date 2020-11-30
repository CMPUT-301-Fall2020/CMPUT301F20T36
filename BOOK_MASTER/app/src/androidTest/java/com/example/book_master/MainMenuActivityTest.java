package com.example.book_master;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.widget.Button;
import android.widget.EditText;

import com.example.book_master.models.*;

import com.robotium.solo.Solo;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    @Test
    public void test_1_Sign_up() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnView((Button) solo.getView(R.id.activity_main_signup));
        solo.enterText((EditText) solo.getView(R.id.email_register), "IntentTesting_register@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_register), "testtest");
        solo.enterText((EditText) solo.getView(R.id.username_register), "IntentTesting_register");
        solo.enterText((EditText) solo.getView(R.id.contactInfo_register), "Contact for testing");
        solo.clickOnView((Button) solo.getView(android.R.id.button1));
        solo.assertCurrentActivity("Should be registered already. Check database info (may not be deleted).\n", MainActivity.class);


        solo.enterText((EditText) solo.getView(R.id.email_text), "IntentTesting_register@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_text), "testtest");
        solo.clickOnView(solo.getView(R.id.activity_main_login));
        solo.assertCurrentActivity("Should be registered already. Check database info (may not be deleted).\n", main_menu_activity.class);


        DBHelper.deleteAccount(solo.getCurrentActivity());
    }

    @Test
    public void test_2_UserInfo() {
        // start at MainActivity
        solo.assertCurrentActivity("Starting in wrong Activity", MainActivity.class);
        // enter email & password
        solo.enterText((EditText) solo.getView(R.id.email_text), "IntentTesting@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_text), "testtest");
        // enter to main_menu_activity
        solo.clickOnView(solo.getView(R.id.activity_main_login));
        solo.assertCurrentActivity("Login Failed.", main_menu_activity.class);
        // enter to check_list_activity
        solo.clickOnView(solo.getView(R.id.view_profile_button));
        Assert.assertTrue("Username is not equal", solo.searchText("IntentTesting"));
        Assert.assertTrue("Contact Info is not equal", solo.searchText("Contact for testing"));
        Assert.assertTrue("Email is not equal", solo.searchText("IntentTesting@gmail.com"));
    }

    @Test
    public void test_3_change_UserInfo() {
        // start at MainActivity
        solo.assertCurrentActivity("Starting in wrong Activity", MainActivity.class);
        // enter email & password
        solo.enterText((EditText) solo.getView(R.id.email_text), "IntentTesting@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_text), "testtest");
        // enter to main_menu_activity
        solo.clickOnView(solo.getView(R.id.activity_main_login));
        solo.assertCurrentActivity("Login Failed.", main_menu_activity.class);
        // enter to check_list_activity
        solo.clickOnView(solo.getView(R.id.view_profile_button));
        solo.clickOnView(solo.getView(R.id.profile_page_edit));
        solo.enterText((EditText) solo.getView(R.id.edit_profile_contact_info), "Changed Contact Info");
        solo.clickOnView(solo.getView(R.id.edit_profile_confirm));

        Assert.assertTrue("Contact Info didn't changed", solo.searchText("Changed Contact Info"));

        solo.clickOnView(solo.getView(R.id.profile_page_edit));
        solo.enterText((EditText) solo.getView(R.id.edit_profile_contact_info), "Contact for testing");
        solo.clickOnView(solo.getView(R.id.edit_profile_confirm));
    }

    @Test
    public void test_4_check_Book() {
        // start at MainActivity
        solo.assertCurrentActivity("Starting in wrong Activity", MainActivity.class);
        // enter email & password
        solo.enterText((EditText) solo.getView(R.id.email_text), "IntentTesting@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_text), "testtest");
        // enter to main_menu_activity
        solo.clickOnView(solo.getView(R.id.activity_main_login));
        solo.assertCurrentActivity("Login Failed.", main_menu_activity.class);
        // enter to check_list_activity
        solo.clickOnView(solo.getView(R.id.view_profile_button));
        solo.clickOnView(solo.getView(R.id.bottomNavigationView));
        solo.clickOnMenuItem("My Book");

//        solo.clickOnView(solo.getView(R.id.status_spinner));
//        solo.clickOnText(Book.AVAILABLE);
        solo.pressSpinnerItem(0, 1);
        Assert.assertTrue("Available Book is not found", solo.searchText("Focus on Vocabulary"));

//        solo.clickOnView(solo.getView(R.id.status_spinner));
//        solo.clickOnText(Book.REQUESTED);
        solo.pressSpinnerItem(0, 1);
        Assert.assertTrue("Requested Book is not found", solo.searchText("Advance Reading Power"));
//
//        solo.clickOnView(solo.getView(R.id.status_spinner));
//        solo.clickOnMenuItem(Book.ACCEPTED);
        solo.pressSpinnerItem(0, 1);
        Assert.assertTrue("Accepted Book is not found", solo.searchText("High Country Hearts"));
//
//        solo.clickOnView(solo.getView(R.id.status_spinner));
//        solo.clickOnMenuItem(Book.BORROWED);
        solo.pressSpinnerItem(0, 1);
        Assert.assertTrue("Borrowed Book is not found", solo.searchText("The Official Guide to the GRE General"));
//
//        solo.clickOnView(solo.getView(R.id.status_spinner));
//        solo.clickOnMenuItem(Book.CONFIRM_BORROWED);
        solo.pressSpinnerItem(0, 1);
        Assert.assertTrue("Confirm Borrowed Book is not found", solo.searchText("Chemistry: The Molecular Nature"));
//
//        solo.clickOnView(solo.getView(R.id.status_spinner));
//        solo.clickOnMenuItem(Book.RETURN);
//        solo.pressSpinnerItem(0, 1);
//        Assert.assertTrue("Confirm Borrowed Book is not found", solo.searchText("The Official Guide to GRE"));
    }

    @Test
    public void test_5_search_book() {
        // start at MainActivity
        solo.assertCurrentActivity("Starting in wrong Activity", MainActivity.class);
        // enter email & password
        solo.enterText((EditText) solo.getView(R.id.email_text), "IntentTesting@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_text), "testtest");
        // enter to main_menu_activity
        solo.clickOnView(solo.getView(R.id.activity_main_login));
        solo.assertCurrentActivity("Login Failed.", main_menu_activity.class);
        // enter to check_list_activity
        solo.clickOnView(solo.getView(R.id.Search_main_button));

        Assert.assertTrue("Intent Use book is not Exist", solo.searchText("For Intent Use"));

        solo.enterText((EditText) solo.getView(R.id.search_bar_keyword), "Intent");
        solo.clickOnView(solo.getView(R.id.search_bar_confirm));

        Assert.assertTrue("Intent Use book is not Exist after search", solo.searchText("For Intent Use"));

        solo.clickOnText("For Intent Use");

        Assert.assertTrue("Book title is wrong", solo.searchText("For Intent Use"));
        Assert.assertTrue("Book Author is wrong", solo.searchText("James Stewart"));
        Assert.assertTrue("Book Status is wrong", solo.searchText("AVAILABLE"));
        Assert.assertTrue("Book ISBN is wrong", solo.searchText("19037613"));
        Assert.assertTrue("Book Owner is wrong", solo.searchText("Shrike"));
    }

    @Test
    public void test_6_search_user_info() {
        // start at MainActivity
        solo.assertCurrentActivity("Starting in wrong Activity", MainActivity.class);
        // enter email & password
        solo.enterText((EditText) solo.getView(R.id.email_text), "IntentTesting@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.password_text), "testtest");
        // enter to main_menu_activity
        solo.clickOnView(solo.getView(R.id.activity_main_login));
        solo.assertCurrentActivity("Login Failed.", main_menu_activity.class);
        // enter to check_list_activity
        solo.clickOnView(solo.getView(R.id.Search_main_button));
        solo.clickOnView(solo.getView(R.id.bottomNavigationView));
        solo.clickOnMenuItem("Profile");

        solo.enterText((EditText) solo.getView(R.id.search_username_bar_keyword), "Shri");
        solo.clickOnView(solo.getView(R.id.search_username_bar_confirm));
        Assert.assertTrue("Shrike use exist", solo.searchText("Shrike"));
        solo.clickOnText("Shrike");

        solo.assertCurrentActivity("Not in profile activity", profile_description.class);
        Assert.assertTrue("Username is wrong", solo.searchText("Shrike"));
        Assert.assertTrue("Contact Info is wrong", solo.searchText("QAQ"));
        Assert.assertTrue("Email is wrong", solo.searchText("lyon233333@gmail.com"));
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
