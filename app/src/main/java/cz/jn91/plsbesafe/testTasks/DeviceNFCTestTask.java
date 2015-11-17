package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.provider.Settings;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Tests if NFC is enabled on the device
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class DeviceNFCTestTask extends BaseTestAsyncTask {

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     * @param adapter  adapter with test cases
     * @param position position of test in array, it is used in callback
     */
    public DeviceNFCTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testNFCText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testNFCName);
    }

    @Override
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(final Activity activity) {
                openSettingsDialog(Settings.ACTION_NFC_SETTINGS, activity, getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_nfc_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Result doInBackground(Void... params) {
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
                NfcAdapter nfcAdapter = manager.getDefaultAdapter();

                if (nfcAdapter != null && nfcAdapter.isEnabled()) {
                    return TestResult.Result.FAIL;
                } else {
                    return TestResult.Result.OK;
                }
            } else {
                return TestResult.Result.NOT_TESTED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return TestResult.Result.NOT_TESTED;
        }
    }
}
