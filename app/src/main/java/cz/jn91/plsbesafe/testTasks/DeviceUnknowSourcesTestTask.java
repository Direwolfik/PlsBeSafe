package cz.jn91.plsbesafe.testTasks;

import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceUnknowSourcesTestTask extends BaseTestAsyncTask {
    public DeviceUnknowSourcesTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
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
    protected TestResult.Result doInBackground(Void... params) {
        try{
            boolean isNonPlayAppAllowed = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS) == 1;
            if(isNonPlayAppAllowed){
                return TestResult.Result.FAIL;
            } else {
                return TestResult.Result.OK;
            }
        } catch (Exception e){
            return TestResult.Result.NOT_TESTED;
        }
    }
}