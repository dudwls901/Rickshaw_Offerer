package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;

import java.util.ArrayList;

public class FieldListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,place,date;

        MyViewHolder(View view){
            super(view);
            title=view.findViewById(R.id.title);
            date=view.findViewById(R.id.date);
            place=view.findViewById(R.id.place);


        }
    }

    private  Context context;
    private ArrayList<ListViewItem> workInfo;
    public FieldListAdapter(Context context, ArrayList<ListViewItem> workInfo){
        this.context=context;
        this.workInfo=workInfo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.work_list_custom2,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder=(MyViewHolder) holder;
        myViewHolder.title.setText(workInfo.get(position).title);
        myViewHolder.place.setText(workInfo.get(position).place);
        myViewHolder.date.setText(workInfo.get(position).date);




        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Context context=view.getContext();
//                Intent intent=new Intent(context,WorkInfoActivity.class);
//                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return  workInfo.size();
    }
}
