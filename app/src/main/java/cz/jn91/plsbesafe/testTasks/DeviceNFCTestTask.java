package cz.jn91.plsbesafe.testTasks;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceNFCTestTask extends BaseTestAsyncTask {
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
