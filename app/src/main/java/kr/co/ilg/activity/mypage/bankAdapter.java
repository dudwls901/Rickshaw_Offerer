package kr.co.ilg.activity.mypage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;

import java.util.ArrayList;

public class bankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date,fieldname, bankIO, banktime, bankbalane, bankIOcount;

        MyViewHolder(View view){
            super(view);
            fieldname = view.findViewById(R.id.fieldname);
            date = view.findViewById(R.id.time);
            banktime = view.findViewById(R.id.banktime);
            bankIO = view.findViewById(R.id.bankIO);
            bankIOcount = view.findViewById(R.id.bankIOcount);
            bankbalane = view.findViewById(R.id.bankbalane);

        }
    }

    public ArrayList<bankitem> List;
    bankAdapter(ArrayList<bankitem> List){
        this.List = List;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.banckrecords,parent,false);
        return new bankAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bankAdapter.MyViewHolder myViewHolder = (bankAdapter.MyViewHolder) holder;

        myViewHolder.date.setText(List.get(position).date);
        myViewHolder.fieldname.setText(List.get(position).fieldname);
        myViewHolder.banktime.setText(List.get(position).banktime);
        myViewHolder.bankIO.setText(List.get(position).bankIO);
        myViewHolder.bankIOcount.setText(List.get(position).bankIOcount);
        myViewHolder.bankbalane.setText(List.get(position).bankbalane);

    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}








