package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Tests if encryption is used in given device
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class DeviceEncryptionTestTask extends BaseTestAsyncTask {

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     * @param adapter  adapter with test cases
     * @param position position of test in array, it is used in callback
     */
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
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(final Activity activity) {
                openSettingsDialog(Settings.ACTION_SECURITY_SETTINGS, activity, getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_screen_lock_portrait_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Result doInBackground(Void... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            final DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            if (dpm != null) {
                int encryptionStatus = dpm.getStorageEncryptionStatus();
                if (encryptionStatus == dpm.ENCRYPTION_STATUS_ACTIVE) {
                    return TestResult.Result.OK;
                }
            }
            return TestResult.Result.FAIL;
        }
        return TestResult.Result.NOT_TESTED;
    }

}
