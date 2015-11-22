package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Tests if GPS is used by the device
 *
 * Created by jn91 on 12.11.2015.
 */
public class DeviceLocationTestTask extends BaseTestAsyncTask {

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     */
    public DeviceLocationTestTask(TestsFragment fragment) {
        super(fragment);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testGPSText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testGPSName);
    }

    @Override
    protected String getPositiveName() {
        return context.getString(R.string.testGPSPositive);
    }

    @Override
    protected String getNegativeName() {
        return context.getString(R.string.testGPSNegative);
    }

    @Override
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(final Activity activity) {
                openSettingsDialog(Settings.ACTION_LOCATION_SOURCE_SETTINGS, activity, getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_gps_off_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Status doInBackground(Void... params) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){ //requires FINE_LOCATION permission on Android lower than 4.4
            return TestResult.Status.FAIL;
        } else {
            return TestResult.Status.OK;
        }
    }
}
