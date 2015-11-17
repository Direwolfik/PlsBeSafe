package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.Util.RootUtil;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Tests if device is rooted
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class DeviceRootTestTask extends BaseTestAsyncTask {

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     */
    public DeviceRootTestTask(TestsFragment fragment) {
        super(fragment);
    }

    @Override
    protected String getExplanation() {
        return context.getString(R.string.testRootText);
    }

    @Override
    protected String getName() {
        return context.getString(R.string.testRootName);
    }

    @Override
    protected TestResult.TestResolver getResolver() {
        return new TestResult.TestResolver() {
            @Override
            public void resolveProblem(Activity activity) {
                Snackbar.make(activity.findViewById(R.id.fragmentContainer), activity.getString(R.string.deviceRooted), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public Drawable getMenuIcon(Activity activity) {
                return activity.getResources().getDrawable(R.drawable.ic_security_white_48dp);
            }
        };
    }

    @Override
    protected TestResult.Status doInBackground(Void... params) {
        if (RootUtil.isDeviceRooted()) {
            return TestResult.Status.FAIL;
        } else {
            return TestResult.Status.OK;
        }
    }
}
