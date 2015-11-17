package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceLocationTestTask extends BaseTestAsyncTask {
    public DeviceLocationTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
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
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(final Activity activity) {
                openSettingsDialog(Settings.ACTION_LOCATION_SOURCE_SETTINGS, activity, getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_settings_input_antenna_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Result doInBackground(Void... params) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return TestResult.Result.FAIL;
        } else {
            return TestResult.Result.OK;
        }
    }
}
