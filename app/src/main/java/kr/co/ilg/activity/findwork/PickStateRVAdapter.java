package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;

import java.util.ArrayList;

import kr.co.ilg.activity.workermanage.GujicProfileForGuin;

public class PickStateRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context psContext;


    public interface OnItemClickListener1 {
        void onItemClick(View view, int position, String wk_email, String wk_name, boolean checked);
    }
    private OnItemClickListener1 mListener =null;

    public void setOnItemClickListener(OnItemClickListener1 listener){
        this.mListener = listener;
    } //리스너객체를 전달하는 메서드와 전달된 객체를 저장할 변수를 추가

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profileIV;
        TextView wkName, wkAge, wkPNum;
        ImageButton arrowRBtn;
        CheckBox checkWorker;

        MyViewHolder(View view){
            super(view);
            profileIV = view.findViewById(R.id.profileIV);
            wkName = view.findViewById(R.id.wkName);
            wkAge = view.findViewById(R.id.wkAge);
            wkPNum = view.findViewById(R.id.wkPNum);
            arrowRBtn = view.findViewById(R.id.arrowRBtn);
            checkWorker = view.findViewById(R.id.checkWorker);
        }
    }

    private ArrayList<PickStateRVItem> wkList;
    public PickStateRVAdapter(Context c, ArrayList<PickStateRVItem> wkList){
        this.psContext = c;
        this.wkList = wkList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pick_state_item, parent, false);

        return new PickStateRVAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PickStateRVAdapter.MyViewHolder myViewHolder = (PickStateRVAdapter.MyViewHolder) holder;

        myViewHolder.profileIV.setImageResource(wkList.get(position).pofileIMGId);
        myViewHolder.wkName.setText(wkList.get(position).wkName);
        myViewHolder.wkAge.setText(wkList.get(position).wkAge);
        myViewHolder.wkPNum.setText(wkList.get(position).wkPNum);

        if(wkList.get(position).is_check) {
            myViewHolder.checkWorker.setChecked(true);

        }
        else
        {
            myViewHolder.checkWorker.setChecked(false);

        }
        myViewHolder.checkWorker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListener.onItemClick(buttonView,position,wkList.get(position).wk_email, wkList.get(position).wkName,isChecked);
                //   if(isChecked)
                Log.d("zdzd","되냐고마냐고ㅡ");

            }
        });

        myViewHolder.arrowRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, GujicProfileForGuin.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wkList.size();
    }
}
