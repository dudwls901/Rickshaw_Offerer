package kr.co.ilg.activity.workermanage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
    Button btnPay, btnReview, btnCall;
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


        //출퇴근, 구직자 이메일, 구직자 이름 가져오기, 지급여부
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
                btnCall = dialogView.findViewById(R.id.btnCall);
               //TODO 디비읽어서 TEXT 출근 완료,퇴근 완료로 바꾸기
                TextView in_tv = dialogView.findViewById(R.id.in_tv);
                TextView out_tv = dialogView.findViewById(R.id.out_tv);

                if(wkList.get(position).mf_is_choolgeun.equals("1"))
                {
                    in_tv.setText("출근완료");
                }
                if(wkList.get(position).mf_is_toigeun.equals("1"))
                {
                    out_tv.setText("퇴근완료");
                }

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
                        intent.putExtra("wkName",wkList.get(position).wkName);
                        intent.putExtra("worker_bankname",wkList.get(position).worker_bankname);
                        intent.putExtra("worker_bankaccount",wkList.get(position).worker_bankaccount);
                        intent.putExtra("wk_email",wkList.get(position).wk_email);
                        intent.putExtra("field_code",wkList.get(position).field_code);
                        context.startActivity(intent);
                    }
                });

                btnReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        context =v.getContext();
                        intent = new Intent(context, UserReviewWriteActivity.class);
                        intent.putExtra("worker_name",wkList.get(position).wkName);
                        intent.putExtra("worker_email",wkList.get(position).wk_email);
                        context.startActivity(intent);
                    }
                });

                btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + wkList.get(position).wkPNum);
                        intent = new Intent(Intent.ACTION_DIAL, uri);
                        context.startActivity(intent);
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
