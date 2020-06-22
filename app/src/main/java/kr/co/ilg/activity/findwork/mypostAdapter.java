package kr.co.ilg.activity.findwork;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;

import java.util.ArrayList;

public class mypostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context vcontext;
    View dialogView;
    Button btnWorkInfo, btnSupply, btnPick;
    Intent intent;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, pay, job, place, office, current_people, total_people;
        LinearLayout linear1;
        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            pay = view.findViewById(R.id.pay);
            job = view.findViewById(R.id.job);
            place = view.findViewById(R.id.place);
            office = view.findViewById(R.id.office);
            current_people = view.findViewById(R.id.current_people);
            total_people = view.findViewById(R.id.total_people);
            linear1=view.findViewById(R.id.linear1);
        }
    }

    private Context context;
    private ArrayList<ListViewItem> workInfo;

    public mypostAdapter(Context context, ArrayList<ListViewItem> workInfo) {
        this.context = context;
        this.workInfo = workInfo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_list_custom, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (workInfo.get(position).urgency == false) {
            myViewHolder.title.setText(workInfo.get(position).title);
            myViewHolder.date.setText(workInfo.get(position).date);
            myViewHolder.pay.setText(workInfo.get(position).pay);
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);
            myViewHolder.current_people.setText(workInfo.get(position).current_people);
            myViewHolder.total_people.setText(workInfo.get(position).total_people);
        }
        else if (workInfo.get(position).urgency == true) {
            myViewHolder.title.setText(workInfo.get(position).title);
            myViewHolder.date.setText(workInfo.get(position).date);
            myViewHolder.pay.setText(workInfo.get(position).pay);
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);
            myViewHolder.current_people.setText(workInfo.get(position).current_people);
            myViewHolder.total_people.setText(workInfo.get(position).total_people);
            myViewHolder.linear1.setBackgroundColor(context.getResources().getColor(R.color.UrgencyColor));

        }
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context = view.getContext();
                final AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dialogView = View.inflate(context, R.layout.myworkwritingdialog, null);
                dlg.setView(dialogView);
                btnWorkInfo = dialogView.findViewById(R.id.btnWorkInfo);
                btnSupply = dialogView.findViewById(R.id.btnSupply);
                btnPick = dialogView.findViewById(R.id.btnPick);
                dlg.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                btnWorkInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context = v.getContext();
                        intent = new Intent(context, WorkInfoActivity.class);
                        context.startActivity(intent);
                    }
                });
                btnSupply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context = v.getContext();
                        intent = new Intent(context, ApplyStateActivity.class);
                        context.startActivity(intent);
                    }
                });
                btnPick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context = v.getContext();
                        intent = new Intent(context, PickStateActivity.class);
                        context.startActivity(intent);
                    }
                }); dlg.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return workInfo.size();
    }
}
