package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import java.util.List;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Check if device is connected to encrypted network
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class DeviceWiFiEncryptedTestTask extends BaseTestAsyncTask {

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     */
    public DeviceWiFiEncryptedTestTask(TestsFragment fragment) {
        super(fragment);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testWifiSecurText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testWifiSecurName);
    }

    @Override
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(final Activity activity) {
                openSettingsDialog(Settings.ACTION_WIFI_SETTINGS, activity, getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_wifi_lock_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Status doInBackground(Void... params) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> networkList = wifi.getScanResults();

        WifiInfo wi = wifi.getConnectionInfo();
        String currentSSID = wi.getSSID();
        currentSSID = currentSSID.replaceAll("\"", "");

        if (networkList != null) {
            for (ScanResult network : networkList) {
                String ssid = network.SSID.replaceAll("\"", "");
                if (currentSSID.equals(ssid)) {
                    String Capabilities = network.capabilities;
                    if (Capabilities.contains("WPA2") || Capabilities.contains("WPA") || Capabilities.contains("WEP")) {
                        return TestResult.Status.OK;
                    } else {
                        return TestResult.Status.FAIL;
                    }
                }
            }
        }
        return TestResult.Status.NOT_TESTED;
    }
}
