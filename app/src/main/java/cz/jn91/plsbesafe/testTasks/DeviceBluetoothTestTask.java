package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceBluetoothTestTask extends BaseTestAsyncTask{
    public DeviceBluetoothTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
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
    protected TestResult.Result doInBackground(Void... params) {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            return TestResult.Result.NOT_TESTED;
        } else {
            if (mBluetoothAdapter.isEnabled()) {
                return TestResult.Result.FAIL;
            }
            return TestResult.Result.OK;
        }
    }
}
