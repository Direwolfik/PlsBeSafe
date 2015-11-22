package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.graphics.drawable.Drawable;
import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Tests if Bluetooth is enabled
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class DeviceBluetoothTestTask extends BaseTestAsyncTask {

    private BluetoothAdapter mBluetoothAdapter;

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     */
    public DeviceBluetoothTestTask(TestsFragment fragment) {
        super(fragment);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testBluetoothText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testBluetoothName);
    }

    @Override
    protected String getPositiveName() {
        return context.getString(R.string.testBluetoothPositive);
    }

    @Override
    protected String getNegativeName() {
        return context.getString(R.string.testBluetoothNegative);
    }

    @Override
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(final Activity activity) {
                openSettingsDialog(Settings.ACTION_BLUETOOTH_SETTINGS, activity, getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_bluetooth_disabled_white_48dp);
            }
        };
    }

    @Override
    protected void onPreExecute() {
        /**
         *   This has to be done in UI thread because of Android bug
         *   http://stackoverflow.com/questions/5920578/bluetoothadapter-getdefaultadapter-throwing-runtimeexception-while-not-in-acti
         */

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected TestResult.Status doInBackground(Void... params) {
        if (mBluetoothAdapter == null) {
            return TestResult.Status.NOT_TESTED;
        } else {
            if (mBluetoothAdapter.isEnabled()) {
                return TestResult.Status.FAIL;
            }
            return TestResult.Status.OK;
        }
    }
}
