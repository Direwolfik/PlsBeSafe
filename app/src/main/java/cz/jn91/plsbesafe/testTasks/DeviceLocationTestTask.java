package cz.jn91.plsbesafe.testTasks;

import android.content.Context;
import android.location.LocationManager;

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
    protected TestResult.Result doInBackground(Void... params) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return TestResult.Result.FAIL;
        } else {
            return TestResult.Result.OK;
        }
    }
}
