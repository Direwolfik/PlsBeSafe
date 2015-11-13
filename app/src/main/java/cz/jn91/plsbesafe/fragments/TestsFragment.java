package cz.jn91.plsbesafe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import cz.jn91.plsbesafe.testTasks.DeviceManagerTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceNFCTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceRootTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceUnknowSourcesTestTask;
import cz.jn91.plsbesafe.testTasks.DeviceWiFiEncryptedTestTask;

/**
 * Created by jn91 on 12.11.2015.
 */
public class TestsFragment extends Fragment {
    @Bind(R.id.lvTests)
    ListView lvTests;
    @Bind(R.id.btRunTest)
    Button btRunTest;

    List<TestResult> testResults;
    List<BaseTestAsyncTask> testAsyncTasks;

    private int currentTest = 0;

    TestTasksAdapter testsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        testResults = new ArrayList<>();
        testsAdapter = new TestTasksAdapter(getActivity(),R.layout.test_result_view,testResults);

        prepareTests();

        lvTests.setAdapter(testsAdapter);

        testsAdapter.notifyDataSetChanged();

        runNextTest();
    }


    /**
     * Prepares the tests by creating the collection of async test cases
     */
    private void prepareTests() {
        testAsyncTasks = new ArrayList<>();
        testAsyncTasks.add(new DeviceEncryptionTestTask(this, testsAdapter, testAsyncTasks.size()));
        testAsyncTasks.add(new DeviceRootTestTask(this, testsAdapter, testAsyncTasks.size()));
        testAsyncTasks.add(new DeviceLockScreenTestTask(this, testsAdapter, testAsyncTasks.size()));
        testAsyncTasks.add(new DeviceBluetoothTestTask(this, testsAdapter, testAsyncTasks.size()));
   //     testAsyncTasks.add(new DeviceManagerTestTask(this,testsAdapter,testAsyncTasks.size()));
        testAsyncTasks.add(new DeviceADBTestTask(this,testsAdapter,testAsyncTasks.size()));
        testAsyncTasks.add(new DeviceUnknowSourcesTestTask(this,testsAdapter,testAsyncTasks.size()));
        testAsyncTasks.add(new DeviceWiFiEncryptedTestTask(this,testsAdapter,testAsyncTasks.size()));
        testAsyncTasks.add(new DeviceHotspotTestTask(this, testsAdapter, testAsyncTasks.size()));
        testAsyncTasks.add(new DeviceNFCTestTask(this,testsAdapter,testAsyncTasks.size()));
        testAsyncTasks.add(new DeviceLocationTestTask(this,testsAdapter,testAsyncTasks.size()));
    }


    /**
     * Callback method for the completed async tascs. This method will run another test or set the current test counter to 0 if there are no more tests.
     */
    public void runNextTest(){
        btRunTest.setEnabled(false);
        if(currentTest < testAsyncTasks.size()) {
            testAsyncTasks.get(currentTest).execute();
            currentTest++;
        }
        else {
            btRunTest.setEnabled(true);
            currentTest = 0;
        };
    }

    @OnClick(R.id.btRunTest)
    public void testAgain(){
        testsAdapter.clear();
        prepareTests();
        testsAdapter.notifyDataSetChanged();
        runNextTest();
    }
}
