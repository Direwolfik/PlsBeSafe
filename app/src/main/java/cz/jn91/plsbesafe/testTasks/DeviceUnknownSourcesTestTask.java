package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Tests if Unknown Sources are enabled
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class DeviceUnknownSourcesTestTask extends BaseTestAsyncTask {

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     */
    public DeviceUnknownSourcesTestTask(TestsFragment fragment) {
        super(fragment);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testUnknownText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testUnknownName);
    }

    @Override
    protected String getPositiveName() {
        return context.getString(R.string.testUnknownPositive);
    }

    @Override
    protected String getNegativeName() {
        return context.getString(R.string.testUnknownNegative);
    }

    @Override
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(final Activity activity) {
                openSettingsDialog(Settings.ACTION_SECURITY_SETTINGS, activity, getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_get_app_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Status doInBackground(Void... params) {
        try {
            boolean isNonPlayAppAllowed = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS) == 1;
            if (isNonPlayAppAllowed) {
                return TestResult.Status.FAIL;
            } else {
                return TestResult.Status.OK;
            }
        } catch (Exception e) {
            return TestResult.Status.NOT_TESTED;
        }
    }
}
