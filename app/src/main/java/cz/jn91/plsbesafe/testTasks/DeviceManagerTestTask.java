package cz.jn91.plsbesafe.testTasks;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceManagerTestTask extends BaseTestAsyncTask{
    public DeviceManagerTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testDeviceManagerText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testDeviceManagerName);
    }

    @Override
    protected TestResult.Result doInBackground(Void... params) {
        return null;
    }
}
