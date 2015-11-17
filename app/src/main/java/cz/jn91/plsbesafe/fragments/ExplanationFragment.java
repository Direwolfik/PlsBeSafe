package cz.jn91.plsbesafe.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.activities.MainActivity;

/**
 * Fragment containing explanation of test case
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class ExplanationFragment extends Fragment {
    public static final String RESULT = "result";

    private TestResult mResult;

    @Bind(R.id.tvText)
    TextView text;

    /**
     * Creates new instance of ExplanationFragment using given test result as and attribute
     *
     * @param result TestResult of test for which information should be displayed
     * @return new instance of ExplanationFragment
     */
    public static ExplanationFragment newInstance(TestResult result) {
        ExplanationFragment explanationFragment = new ExplanationFragment();
        Bundle args = new Bundle();
        args.putSerializable(RESULT, result);
        explanationFragment.setArguments(args);
        return explanationFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explanation, null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this, view);
        mResult = (TestResult) getArguments().getSerializable(RESULT);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        text.setText(mResult.getExplanation());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.explanation_menu, menu);
        if (menu != null) {
            MenuItem item = menu.findItem(R.id.menuResolveProblem);
            if (item != null) {
                item.setIcon(mResult.getResolver().getMenuIcon(getActivity()));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuResolveProblem:
                mResult.getResolver().resolveProblem(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
