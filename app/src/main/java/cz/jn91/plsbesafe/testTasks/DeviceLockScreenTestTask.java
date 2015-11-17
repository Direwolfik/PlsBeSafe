package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Tests if lockscreen is used by the device
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class DeviceLockScreenTestTask extends BaseTestAsyncTask {

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     */
    public DeviceLockScreenTestTask(TestsFragment fragment) {
        super(fragment);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testLockScreenText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testLockScreenName);
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
                return activity.getResources().getDrawable(R.drawable.ic_lock_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Status doInBackground(Void... params) {

        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.android.internal.widget.LockPatternUtils");
            Constructor<?> constructor = clazz.getConstructor(Context.class);
            constructor.setAccessible(true);
            Object utils = constructor.newInstance(context);
            Method method = clazz.getMethod("isSecure");
            boolean isSecure = (Boolean) method.invoke(utils);
            if (isSecure) {
                return TestResult.Status.OK;
            } else {
                return TestResult.Status.FAIL;
            }
        } catch (Exception e) {
            return TestResult.Status.NOT_TESTED;
        }
    }
}
