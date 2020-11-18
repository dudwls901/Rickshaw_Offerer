package kr.co.ilg.activity.workermanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capstone2.R;

import java.util.ArrayList;

public class JobCareerLVAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private TextView jobTv;
    private TextView careerTv;
    private ArrayList<JobCareerLVItem> jobCareerLVItems = new ArrayList<JobCareerLVItem>();

    public JobCareerLVAdapter(Context context, ArrayList<JobCareerLVItem> jobCareerLVItems) {
        mContext = context;
        this.jobCareerLVItems = jobCareerLVItems;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.job_career_item, null);

        jobTv = view.findViewById(R.id.jobTv);
        careerTv = view.findViewById(R.id.careerTv);

        jobTv.setText(jobCareerLVItems.get(position).getMyJob());
        careerTv.setText(jobCareerLVItems.get(position).getMyCareer());

        return view;
    }

    @Override
    public int getCount() {
        return jobCareerLVItems.size();
    }

    @Override
    public Object getItem(int position) {
        return jobCareerLVItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
