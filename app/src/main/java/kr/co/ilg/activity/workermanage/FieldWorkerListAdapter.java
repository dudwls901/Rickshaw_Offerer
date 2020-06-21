package kr.co.ilg.activity.workermanage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
//import android.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.PickStateRVItem;

public class FieldWorkerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
Context context;
    Context psContext;
    View dialogView;
    Button btnPay, btnReview;
    Intent intent;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profileIV;
        TextView wkName, wkAge, wkPNum;
        ImageButton arrowRBtn;

        MyViewHolder(View view) {
            super(view);
            profileIV = view.findViewById(R.id.profileIV);
            wkName = view.findViewById(R.id.wkName);
            wkAge = view.findViewById(R.id.wkAge);
            wkPNum = view.findViewById(R.id.wkPNum);
            arrowRBtn = view.findViewById(R.id.arrowRBtn);

        }
    }

    private ArrayList<PickStateRVItem> wkList;

    public FieldWorkerListAdapter(Context c, ArrayList<PickStateRVItem> wkList) {
        this.psContext = c;
        this.wkList = wkList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.field_workerlist_custom, parent, false);

        return new FieldWorkerListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FieldWorkerListAdapter.MyViewHolder myViewHolder = (FieldWorkerListAdapter.MyViewHolder) holder;

        myViewHolder.profileIV.setImageResource(wkList.get(position).pofileIMGId);
        myViewHolder.wkName.setText(wkList.get(position).wkName);
        myViewHolder.wkAge.setText(wkList.get(position).wkAge);
        myViewHolder.wkPNum.setText(wkList.get(position).wkPNum);

        myViewHolder.arrowRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, GujicProfileForGuin.class);
                context.startActivity(intent);
            }
        });
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context =v.getContext();
                final AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dialogView = View.inflate(context, R.layout.field_workerlist_dialog, null);
                dlg.setView(dialogView);
                btnPay = dialogView.findViewById(R.id.btnPay);
                btnReview = dialogView.findViewById(R.id.btnReview);

                dlg.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context =v.getContext();
                        intent = new Intent(context, PayActivity.class);
                        context.startActivity(intent);
                    }
                });

                btnReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        context =v.getContext();
                        final AlertDialog.Builder dlg1 = new AlertDialog.Builder(context);
                        dialogView = View.inflate(context, R.layout.writeworkerreview_dialog, null);
                        dlg1.setView(dialogView);

                        dlg1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dlg1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });dlg1.show();
                    }
                });
              dlg.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return wkList.size();
    }
}
