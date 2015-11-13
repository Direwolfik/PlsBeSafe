package cz.jn91.plsbesafe.testTasks;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.Util.LockType;
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
    protected TestResult.Result doInBackground(Void... params) {

//        int mode = LockType.getCurrent(context.getContentResolver());
//        if(mode == 1){
//            return TestResult.Result.FAIL;
//        } else {
//            return TestResult.Result.OK;
//        }

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
