package cz.jn91.plsbesafe.testTasks;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceHotspotTestTask extends BaseTestAsyncTask{
    public DeviceHotspotTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
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
    protected TestResult.Result doInBackground(Void... params) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        try {
            final Method method = wifi.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true); //in the case of visibility change in future APIs
            boolean result =  (Boolean) method.invoke(wifi);
            if(result){
                return TestResult.Result.FAIL;
            } else {
                return TestResult.Result.OK;
            }
        } catch (Exception e){
            return TestResult.Result.NOT_TESTED;
        }

    }
}
