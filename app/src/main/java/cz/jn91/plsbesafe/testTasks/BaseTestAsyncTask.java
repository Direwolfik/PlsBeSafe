package cz.jn91.plsbesafe.testTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Abstract class which should be extended by all test cases
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public abstract class BaseTestAsyncTask extends AsyncTask<Void, Void, TestResult.Result> {
    TestsFragment fragment;
    Context context;
    TestTasksAdapter adapter;
    int position;

    /**
     * Creates new instance of test case
     *
     * @param fragment fragment in which this task is shown
     * @param adapter  adapter with test cases
     * @param position position of test in array, it is used in callback
     */
    public BaseTestAsyncTask(TestsFragment fragment, TestTasksAdapter adapter, int position) {
        this.fragment = fragment;
        context = fragment.getActivity();
        this.adapter = adapter;
        this.position = position;
        TestResult result = new TestResult(getExplanation(), TestResult.Result.READY, getName(), getResolver());
        adapter.add(result);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPreExecute() {
        adapter.getItem(position).setResult(TestResult.Result.IN_PROGRESS);
        adapter.notifyDataSetChanged();
    }

    /**
     * Returns the explanation text for this test case. This text should be used on explanation screen.
     *
     * @return String with explanation text
     */
    protected abstract String getExplanation();

    /**
     * Returns the name for this test case. This name should be used on test screen.
     *
     * @return String with explanation text
     */
    protected abstract String getName();

    /**
     * Returns the TestResolver for this test
     *
     * @return TestResolver that will resolve the FAIL result of this test
     */
    protected abstract TestResult.TestResolver getResolver();

    @Override
    protected void onPostExecute(TestResult.Result result) {
        adapter.getItem(position).setResult(result);
        adapter.notifyDataSetChanged();
        fragment.runNextTest();
    }

    /**
     * Opens the settings screen with based on given action
     *
     * @param action   String representation of action
     * @param activity Activity that should open dialog
     * @param icon     to be used - most probably from getMenuIcon(activity)
     */
    protected void openSettingsDialog(final String action, final Activity activity, Drawable icon) {
        new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.goToSettingsTitle))
                .setMessage(activity.getString(R.string.goToSettingsText))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(action);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.finish();
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //close dialog
                    }
                })
                .setIcon(icon)
                .show();
    }


}
