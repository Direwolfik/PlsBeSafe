package cz.jn91.plsbesafe.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.activities.MainActivity;

/**
 * Created by jn91 on 12.11.2015.
 */
public class TestTasksAdapter extends ArrayAdapter<TestResult>{

    Animation pulse;

    public TestTasksAdapter(Context context, int resource, List<TestResult> testResults) {
        super(context, resource, testResults);
        pulse = AnimationUtils.loadAnimation(context, R.anim.pulse);
    }

    @Override public View getView(int position, View view, ViewGroup parent) {
        final TestResult result = getItem(position);
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.test_result_view, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.tvText.setText(result.getName());
        switch (result.getResult()){
            case FAIL:
                holder.llBackground.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
                holder.pbLoading.setVisibility(View.GONE);
                holder.ivResult.setVisibility(View.VISIBLE);
                holder.ivResult.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_highlight_off_white_48dp));
                holder.ivInfo.setVisibility(View.VISIBLE);
                holder.ivInfo.startAnimation(pulse);
                holder.ivInfo.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        ((MainActivity)getContext()).displayExplanation(result);
                    }
                });
                break;

            case NOT_TESTED:
                holder.llBackground.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_orange_dark));
                holder.pbLoading.setVisibility(View.GONE);
                holder.ivResult.setVisibility(View.VISIBLE);
                holder.ivResult.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_report_problem_white_48dp));
                holder.ivResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), getContext().getString(R.string.notSupported), Toast.LENGTH_SHORT).show();
                    }
                });
                holder.ivResult.startAnimation(pulse);
                holder.ivInfo.setVisibility(View.INVISIBLE);
                break;

            case OK:
                holder.llBackground.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
                holder.pbLoading.setVisibility(View.GONE);
                holder.ivResult.setVisibility(View.VISIBLE);
                holder.ivResult.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_done_white_48dp));
                holder.ivInfo.setVisibility(View.INVISIBLE);
                break;

            case READY:
                holder.llBackground.setBackgroundColor(getContext().getResources().getColor(android.R.color.darker_gray));
                holder.pbLoading.setVisibility(View.GONE);
                holder.ivResult.setVisibility(View.VISIBLE);
                holder.ivResult.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_assignment_white_48dp));
                holder.ivInfo.setVisibility(View.INVISIBLE);
                break;

            case IN_PROGRESS:
                holder.llBackground.setBackgroundColor(getContext().getResources().getColor(android.R.color.darker_gray));
                holder.pbLoading.setVisibility(View.VISIBLE);
                holder.ivResult.setVisibility(View.GONE);
                holder.ivInfo.setVisibility(View.INVISIBLE);
                break;
        }
        return view;
    }

    static class ViewHolder{
        @Bind(R.id.pbLoading) ProgressBar pbLoading;
        @Bind(R.id.ivResult) ImageView ivResult;
        @Bind(R.id.ivInfo) ImageView ivInfo;
        @Bind(R.id.tvText) TextView tvText;
        @Bind(R.id.llBackground) LinearLayout llBackground;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
