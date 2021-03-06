package com.macbitsgoa.ard.activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.DrawerMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.macbitsgoa.ard.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for MainActivity
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private Context context;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testClassName() throws Exception {
        final String[] expected = new String[]{
                "MainActivity",
        };
        assertArrayEquals("Class name error", expected,
                new String[]{activityTestRule.getActivity().getClass().getSimpleName()});
    }

    @Test
    public void testParentActivityName() throws Exception {
        final String[] expected = new String[]{
                "AppCompatActivity",
        };
        assertArrayEquals("Parent class is wrong", expected,
                new String[]{activityTestRule.getActivity()
                        .getClass().getSuperclass().getSimpleName()});
    }

    @Test
    public void testDrawerOpenClose() throws Exception {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open())
                .check(matches(DrawerMatchers.isOpen()));

        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.close())
                .check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open(Gravity.START))
                .check(matches(DrawerMatchers.isOpen(Gravity.START)));

    }

    @Test
    public void testBackPressOnOpenedDrawer() throws Exception {
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open(GravityCompat.START));
        Espresso.pressBack();
        onView(withId(R.id.drawer_layout))
                .check(matches(DrawerMatchers.isClosed(GravityCompat.START)));
    }

    @Test
    public void testOptionsMenuItems() throws Exception {
        onView(withText("Settings")).check(doesNotExist());
        openActionBarOverflowOrOptionsMenu(context);
        onView(withText("Settings")).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void testDrawerItems() throws Exception {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Import")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Gallery")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Slideshow")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Manage")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Share")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Send")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed()));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("null")).check(doesNotExist());

    }

    @Test
    public void testFragmentFrameIsVisible() throws Exception {
        onView(withId(R.id.frame_content_main)).check(matches(isDisplayed()));
    }

    @Test
    public void testBottomNavIsDisplayed() throws Exception {
        onView(withId(R.id.bottom_nav_activity_main)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.bottom_nav_home),
                withContentDescription("Home"), isDisplayed()))
                .perform(click());
        onView(withId(R.id.fragment_home_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_faq_layout)).check(doesNotExist());
        onView(withId(R.id.fragment_chat_layout)).check(doesNotExist());

        onView(allOf(withId(R.id.bottom_nav_faq),
                withContentDescription("F.A.Q."), isDisplayed()))
                .perform(click());
        onView(withId(R.id.fragment_faq_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_home_layout)).check(doesNotExist());
        onView(withId(R.id.fragment_chat_layout)).check(doesNotExist());
        onView(withId(R.id.tabLayout_fragment_faq)).check(matches(isDisplayed()));
        onView(withId(R.id.viewPager_fragment_faq)).check(matches(isDisplayed()));
        onView(withText("General")).check(matches(isDisplayed()));


        onView(allOf(withId(R.id.bottom_nav_chat),
                withContentDescription("Chat"), isDisplayed()))
                .perform(click());
        onView(withId(R.id.fragment_chat_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_home_layout)).check(doesNotExist());
        onView(withId(R.id.fragment_faq_layout)).check(doesNotExist());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };

    }

    @Test
    public void testDrawerHeader() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar_activity_main)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.nav_drawer_image),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation_header_container),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.nav_drawer_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation_header_container),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nav_drawer_subtitle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation_header_container),
                                        0),
                                2),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        pressBack();
    }

}
