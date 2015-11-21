package cz.jn91.plsbesafe.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.Snackbar;
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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.jn91.plsbesafe.R;
import cz.jn91.plsbesafe.TestResult;
import cz.jn91.plsbesafe.activities.MainActivity;

/**
 * Adapter that is used for the purpose of displaying test cases.
 * <p/>
 * Created by jn91 on 12.11.2015.
 */
public class TestTasksAdapter extends ArrayAdapter<TestResult> {

    Animation pulse;

    /**
     * Default ArrayAdapter constructor
     *
     * @param context     context to be used
     * @param resource    layout of items
     * @param testResults list of test results
     */
    public TestTasksAdapter(Context context, int resource, List<TestResult> testResults) {
        super(context, resource, testResults);
        pulse = AnimationUtils.loadAnimation(context, R.anim.pulse);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final TestResult result = getItem(position);
        final ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.test_result_view, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.tvText.setText(result.getName());
        switch (result.getStatus()) {
            case FAIL:
                holder.tvText.setText(result.getNegativeName());
                holder.llBackground.setBackgroundDrawable(getCardBackgroundWithColor(R.color.red));
                holder.pbLoading.setVisibility(View.GONE);
                holder.ivResult.setVisibility(View.VISIBLE);
                holder.ivResult.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_highlight_off_white_48dp));
                holder.ivInfo.setVisibility(View.VISIBLE);
                holder.ivInfo.startAnimation(pulse);
                holder.ivInfo.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getContext()).displayExplanation(result);
                    }
                });
                break;

            case NOT_TESTED:
                holder.tvText.setText(result.getName());
                holder.llBackground.setBackgroundDrawable(getCardBackgroundWithColor(R.color.orange));
                holder.pbLoading.setVisibility(View.GONE);
                holder.ivResult.setVisibility(View.VISIBLE);
                holder.ivResult.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_report_white_48dp));
                holder.ivResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(holder.llBackground, getContext().getString(R.string.notSupported), Snackbar.LENGTH_LONG).show();
                    }
                });
                holder.ivResult.startAnimation(pulse);
                holder.ivInfo.setVisibility(View.INVISIBLE);
                break;

            case OK:
                holder.tvText.setText(result.getPositiveName());
                holder.llBackground.setBackgroundDrawable(getCardBackgroundWithColor(R.color.green));
                holder.pbLoading.setVisibility(View.GONE);
                holder.ivResult.setVisibility(View.VISIBLE);
                holder.ivResult.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_done_white_48dp));
                holder.ivInfo.setVisibility(View.INVISIBLE);
                break;

            case READY:
                holder.tvText.setText(result.getName());
                holder.llBackground.setBackgroundDrawable(getCardBackgroundWithColor(R.color.grey));
                holder.pbLoading.setVisibility(View.GONE);
                holder.ivResult.setVisibility(View.VISIBLE);
                holder.ivResult.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_cached_white_48dp));
                holder.ivInfo.setVisibility(View.INVISIBLE);
                break;

            case IN_PROGRESS:
                holder.tvText.setText(result.getName());
                holder.llBackground.setBackgroundDrawable(getCardBackgroundWithColor(R.color.grey));
                holder.pbLoading.setVisibility(View.VISIBLE);
                holder.ivResult.setVisibility(View.GONE);
                holder.ivInfo.setVisibility(View.INVISIBLE);
                break;
        }
        return view;
    }

    /**
     * Returns drawable card background colored by given color
     *
     * @param color to be used
     * @return Drawable that can be used as background
     */
    private Drawable getCardBackgroundWithColor(int color) {
        LayerDrawable bgDrawable = (LayerDrawable) getContext().getResources().getDrawable(R.drawable.card_background);
        final GradientDrawable shape = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.item_background);
        shape.setColor(getContext().getResources().getColor(color));
        return bgDrawable;
    }

    /**
     * View Holder pattern for items
     */
    static class ViewHolder {
        @Bind(R.id.pbLoading)
        ProgressBar pbLoading;
        @Bind(R.id.ivResult)
        ImageView ivResult;
        @Bind(R.id.ivInfo)
        ImageView ivInfo;
        @Bind(R.id.tvText)
        TextView tvText;
        @Bind(R.id.llBackground)
        LinearLayout llBackground;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
