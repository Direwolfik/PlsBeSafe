package cz.jn91.plsbesafe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import cz.jn91.plsbesafe.R;

/**
 * Created by jn91 on 12.11.2015.
 */
public class ExplanationFragment extends Fragment {
    public static final String TEXT = "text";

    private String explanation;

    @Bind(R.id.tvText)
    TextView text;

    public static ExplanationFragment newInstance(String text) {
        ExplanationFragment explanationFragment = new ExplanationFragment();
        Bundle args = new Bundle();
        args.putString(TEXT, text);
        explanationFragment.setArguments(args);
        return explanationFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explanation,null);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        explanation = getArguments().getString(TEXT);
        text.setText(explanation);
    }

}
