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
import java.util.List;

public class reviewinputinfo_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, date, field, contents;
        ImageView imageView;

        MyViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.reviewname);
            date = view.findViewById(R.id.reviewdate);
            field = view.findViewById(R.id.reviewfield);
            imageView = view.findViewById(R.id.reviewprofile);
            contents = view.findViewById(R.id.contents);
        }
    }

    public ArrayList<reviewinputinfo_item> List;
    reviewinputinfo_adapter(ArrayList<reviewinputinfo_item> List){
        this.List = List;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewcustom_guin_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.name.setText(List.get(position).name);
        myViewHolder.date.setText(List.get(position).date);
        myViewHolder.field.setText(List.get(position).field);
        myViewHolder.imageView.setImageResource(List.get(position).imageView);
        myViewHolder.contents.setText(List.get(position).contents);
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}