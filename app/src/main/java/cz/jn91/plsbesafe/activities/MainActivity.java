package cz.jn91.plsbesafe.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.fragments.ExplanationFragment;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Main activity of the application
 */
public class MainActivity extends ActionBarActivity {
    @BindString(R.string.app_title)
    String appTitle;

    TestsFragment testsFragment;

    boolean explanationShown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //prepare the layout
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //add test fragment
        displayTestsFragment();

    }

    /**
     * display the explanation of given test result in separate fragment
     * @param result result of test
     */
    public void displayExplanation(TestResult result){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, ExplanationFragment.newInstance(result.getExplanation()));
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(result.getName());
        explanationShown = true;
    }

    @Override
    public void onBackPressed() {
        if(explanationShown){
            displayTestsFragment();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Display test fragment
     */
    private void displayTestsFragment(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(appTitle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(testsFragment != null) {
            fragmentTransaction.replace(R.id.fragmentContainer, testsFragment);
        } else {
            testsFragment = new TestsFragment();
            fragmentTransaction.replace(R.id.fragmentContainer, testsFragment);
        }
        fragmentTransaction.commit();
        explanationShown = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                onBackPressed();
                return true;
        }
    }
}
