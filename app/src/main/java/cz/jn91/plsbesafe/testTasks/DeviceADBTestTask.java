package cz.jn91.plsbesafe.testTasks;

import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceADBTestTask extends BaseTestAsyncTask{
    public DeviceADBTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
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
    protected TestResult.Result doInBackground(Void... params) {
        if(Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) == 1) {
            return TestResult.Result.FAIL;
        } else {
            return TestResult.Result.OK;
        }
    }
}
