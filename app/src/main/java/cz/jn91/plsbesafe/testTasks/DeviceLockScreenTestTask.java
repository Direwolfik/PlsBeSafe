package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public class DeviceLockScreenTestTask extends BaseTestAsyncTask {
    public DeviceLockScreenTestTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        super(fragment, adapter, position);
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
                openSettingsDialog(Settings.ACTION_SECURITY_SETTINGS,activity,getMenuIcon(activity));
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_lock_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Result doInBackground(Void... params) {

        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.android.internal.widget.LockPatternUtils");
            Constructor<?> constructor = clazz.getConstructor(Context.class);
            constructor.setAccessible(true);
            Object utils = constructor.newInstance(context);
            Method method = clazz.getMethod("isSecure");
            boolean isSecure = (Boolean) method.invoke(utils);
            if (isSecure) {
                return TestResult.Result.OK;
            } else {
                return TestResult.Result.FAIL;
            }
        } catch (Exception e) {
            return TestResult.Result.NOT_TESTED;
        }
    }
}
