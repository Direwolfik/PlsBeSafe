package cz.jn91.plsbesafe.testTasks;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceEncryptionTestTask extends BaseTestAsyncTask {

    public DeviceEncryptionTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testEncryptionText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testEncryptionName);
    }

    @Override
    protected TestResult.Result doInBackground(Void... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            final DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            if (dpm != null) {
                int encryptionStatus = dpm.getStorageEncryptionStatus();
                if(encryptionStatus == dpm.ENCRYPTION_STATUS_ACTIVE){
                    return TestResult.Result.OK;
                }
            }
            return TestResult.Result.FAIL;
        }
        return TestResult.Result.NOT_TESTED;
    }

}
