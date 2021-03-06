package cz.jn91.plsbesafe.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindString;
import butterknife.ButterKnife;
import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.fragments.ExplanationFragment;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Main activity of the application
 */
public class MainActivity extends AppCompatActivity {
    @BindString(R.string.app_title)
    String appTitle;

    TestsFragment testsFragment;
    TestResult mDisplayedResult;

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
     *
     * @param result result of test
     */
    public void displayExplanation(TestResult result) {
        mDisplayedResult = result;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new ExplanationFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(result.getName());
        explanationShown = true;
    }

    /**
     * Callback for getting displayed result. This way it does not have to be passed to fragment.
     * @return TestResult to be displayed and used
     */
    public TestResult getDisplayedResult(){
        return mDisplayedResult;
    }

    @Override
    public void onBackPressed() {
        if (explanationShown) {
            displayTestsFragment();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Display test fragment
     */
    private void displayTestsFragment() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(appTitle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (testsFragment != null) {
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
                return super.onOptionsItemSelected(item);
        }
    }
}
