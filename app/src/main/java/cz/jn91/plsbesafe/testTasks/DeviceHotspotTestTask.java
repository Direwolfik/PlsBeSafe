package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import java.lang.reflect.Method;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Tests if wi-fi hotspot is active
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class DeviceHotspotTestTask extends BaseTestAsyncTask {

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     */
    public DeviceHotspotTestTask(TestsFragment fragment) {
        super(fragment);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testHotspotText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testHotspotName);
    }

    @Override
    protected String getPositiveName() {
        return context.getString(R.string.testHotspotPositive);
    }

    @Override
    protected String getNegativeName() {
        return context.getString(R.string.testHotspotNegative);
    }

    @Override
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(final Activity activity) {
                openSettingsDialog(Settings.ACTION_WIRELESS_SETTINGS, activity, getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_settings_input_antenna_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Status doInBackground(Void... params) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        try {
            final Method method = wifi.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true); //in the case of visibility change in future APIs
            boolean result = (Boolean) method.invoke(wifi);
            if (result) {
                return TestResult.Status.FAIL;
            } else {
                return TestResult.Status.OK;
            }
        } catch (Exception e) {
            return TestResult.Status.NOT_TESTED;
        }

    }
}
