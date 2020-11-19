package kr.co.ilg.activity.workermanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capstone2.R;

import java.util.ArrayList;

public class WorkedLVAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private TextView field_name, job_name, date;
    private ArrayList<JobCareerLVItem> workedLVItems = new ArrayList<JobCareerLVItem>();

    public WorkedLVAdapter(Context context, ArrayList<JobCareerLVItem> workedLVItems) {
        mContext = context;
        this.workedLVItems = workedLVItems;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.workedlist_item, null);

        field_name = view.findViewById(R.id.field_name);
        job_name = view.findViewById(R.id.job_name);
        date = view.findViewById(R.id.date);

        field_name.setText(workedLVItems.get(position).getMyFieldName());
        job_name.setText(workedLVItems.get(position).getMyJob());
        date.setText(workedLVItems.get(position).getMyDate());

        return view;
    }

    @Override
    public int getCount() {
        return workedLVItems.size();
    }

    @Override
    public Object getItem(int position) {
        return workedLVItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
