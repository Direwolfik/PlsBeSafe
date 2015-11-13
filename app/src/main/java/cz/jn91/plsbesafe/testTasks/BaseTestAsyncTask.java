package cz.jn91.plsbesafe.testTasks;

import android.content.Context;
import android.os.AsyncTask;

import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.fragments.TestsFragment;

/**
 * Created by jn91 on 12.11.2015.
 */
public abstract class BaseTestAsyncTask extends AsyncTask<Void, Void,TestResult.Result> {
    TestsFragment fragment;
    Context context;
    TestTasksAdapter adapter;
    int position;

    public BaseTestAsyncTask(TestsFragment fragment,TestTasksAdapter adapter, int position){
        this.fragment = fragment;
        context = fragment.getActivity();
        this.adapter = adapter;
        this.position = position;
        TestResult result = new TestResult(getExplanation(), TestResult.Result.READY, getName());
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
     * @return String with explanation text
     */
    protected abstract String getExplanation();

    /**
     * Returns the name for this test case. This name should be used on test screen.
     * @return String with explanation text
     */
    protected abstract String getName();

    @Override
    protected void onPostExecute(TestResult.Result result) {
        adapter.getItem(position).setResult(result);
        adapter.notifyDataSetChanged();
        fragment.runNextTest();
    }
}
