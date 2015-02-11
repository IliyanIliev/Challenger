package com.example.user.myapplication.homepage.review;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.myapplication.R;
import com.example.user.myapplication.homepage.NavigationDrawerFragment;
import com.example.user.myapplication.homepage.achievements.AchievementsActivity;
import com.example.user.myapplication.homepage.challenges.ChallengesActivity;
import com.example.user.myapplication.homepage.logout.LogoutActivity;
import com.example.user.myapplication.homepage.profile.ProfileActivity;
import com.example.user.myapplication.homepage.profile.ProfileFragment;
import com.example.user.myapplication.homepage.ranking.RankingActivity;

import listeners.OnReviewViewPagerButtonClickListener;
import tools.Constants;


public class ReviewActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnReviewViewPagerButtonClickListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mTitle = Constants.DRAWER_MENU_ITEM_REVIEW;
        loadReviewFragment();
    }

    private void loadReviewFragment() {
        ReviewFragment fragment = new ReviewFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.review_container, fragment)
                .commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        loadChosenDrawerMenuItem(position);
    }

    private void loadChosenDrawerMenuItem(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                mTitle = Constants.DRAWER_MENU_ITEM_PROFILE;
                break;
            case 1:
                intent = new Intent(this, ChallengesActivity.class);
                startActivity(intent);
                mTitle = Constants.DRAWER_MENU_ITEM_CHALLENGES;
                break;
            case 2:
                intent = new Intent(this, AchievementsActivity.class);
                startActivity(intent);
                mTitle = Constants.DRAWER_MENU_ITEM_ACHIEVEMENTS;
                break;
            case 3:
                intent = new Intent(this, RankingActivity.class);
                startActivity(intent);
                mTitle = Constants.DRAWER_MENU_ITEM_RANKING;
                break;
            case 4:
                mTitle = Constants.DRAWER_MENU_ITEM_REVIEW;
                break;
            case 5:
                intent = new Intent(this, LogoutActivity.class);
                startActivity(intent);
                mTitle = Constants.DRAWER_MENU_ITEM_LOGOUT;
                break;
            default:
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void reloadViewPager() {
        ReviewFragment fragment = new ReviewFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.review_container, fragment).commit();
    }
}
