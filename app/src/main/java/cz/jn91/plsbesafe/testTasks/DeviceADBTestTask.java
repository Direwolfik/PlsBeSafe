package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Tests if the ADB debug mode is active
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class DeviceADBTestTask extends BaseTestAsyncTask {

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     */
    public DeviceADBTestTask(TestsFragment fragment) {
        super(fragment);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testADBText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testADBName);
    }

    @Override
    protected String getPositiveName() {
        return context.getString(R.string.testADBPositive);
    }

    @Override
    protected String getNegativeName() {
        return context.getString(R.string.testADBNegative);
    }

    @Override
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(final Activity activity) {
                openSettingsDialog(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS, activity, getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_usb_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Status doInBackground(Void... params) {
        if(Build.VERSION.SDK_INT > 17){ //needed due to deprecation of Settings.Secure.ADB_ENABLED
            if(Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED,0) == 1){
                return TestResult.Status.FAIL;
            } else {
                return TestResult.Status.OK;
            }
        } else {
            if (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) == 1) {
                return TestResult.Status.FAIL;
            } else {
                return TestResult.Status.OK;
            }
        }
    }
}
