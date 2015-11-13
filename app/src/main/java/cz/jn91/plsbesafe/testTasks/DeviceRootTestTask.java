package cz.jn91.plsbesafe.testTasks;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.Util.RootUtil;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceRootTestTask extends BaseTestAsyncTask{

    public DeviceRootTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testRootText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testRootName);
    }

    @Override
    protected TestResult.Result doInBackground(Void... params) {
        if(RootUtil.isDeviceRooted()){
            return TestResult.Result.FAIL;
        } else {
            return TestResult.Result.OK;
        }
    }
}
