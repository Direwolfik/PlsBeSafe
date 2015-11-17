package cz.jn91.plsbesafe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.adapters.TestTasksAdapter;
import cz.jn91.plsbesafe.testTasks.BaseTestAsyncTask;
import cz.jn91.plsbesafe.testTasks.DeviceADBTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceBluetoothTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceEncryptionTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceHotspotTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceLocationTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceLockScreenTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceNFCTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceRootTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceUnknownSourcesTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceWiFiEncryptedTestTask;

/**
 * Fragment displaying the tests
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class TestsFragment extends Fragment {
    @Bind(R.id.lvTests)
    ListView lvTests;

    List<TestResult> testResults;
    List<BaseTestAsyncTask> testAsyncTasks;

    private int currentTest = 0;

    TestTasksAdapter testsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        testResults = new ArrayList<>();
        testsAdapter = new TestTasksAdapter(getActivity(), R.layout.test_result_view, testResults);
        setHasOptionsMenu(true);

        prepareTests();

        lvTests.setAdapter(testsAdapter);
        testsAdapter.notifyDataSetChanged();
    }


    /**
     * Prepares the tests by creating the collection of async test cases
     */
    private void prepareTests() {
        testAsyncTasks = new ArrayList<>();
        testAsyncTasks.add(new DeviceEncryptionTestTask(this));
        testAsyncTasks.add(new DeviceRootTestTask(this));
        testAsyncTasks.add(new DeviceLockScreenTestTask(this));
        testAsyncTasks.add(new DeviceBluetoothTestTask(this));
        testAsyncTasks.add(new DeviceADBTestTask(this));
        testAsyncTasks.add(new DeviceUnknownSourcesTestTask(this));
        testAsyncTasks.add(new DeviceWiFiEncryptedTestTask(this));
        testAsyncTasks.add(new DeviceHotspotTestTask(this));
        testAsyncTasks.add(new DeviceNFCTestTask(this));
        testAsyncTasks.add(new DeviceLocationTestTask(this));
    }


    /**
     * Callback method for the completed async tascs. This method will run another test or set the current test counter to 0 if there are no more tests.
     */
    private void runNextTest() {
        if (currentTest < testAsyncTasks.size()) {
            testAsyncTasks.get(currentTest).execute();
            testsAdapter.getItem(currentTest).setStatus(TestResult.Status.IN_PROGRESS);
            testsAdapter.notifyDataSetChanged();
        } else {
            currentTest = 0;
        }
    }

    /**
     * Callback to pass the result of the test and run next test
     * @param status new status of test
     */
    public void passResult(TestResult.Status status){
        testsAdapter.getItem(currentTest).setStatus(status);
        testsAdapter.notifyDataSetChanged();
        currentTest++;
        runNextTest();
    }

    /**
     * Callback to add new test that should be shown in fragment
     * @param result newly inititated result
     */
    public void addNewResult(TestResult result){
        testsAdapter.add(result);
        testsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tests_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRunRests:
                testsAdapter.clear();
                prepareTests();
                testsAdapter.notifyDataSetChanged();
                runNextTest();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
