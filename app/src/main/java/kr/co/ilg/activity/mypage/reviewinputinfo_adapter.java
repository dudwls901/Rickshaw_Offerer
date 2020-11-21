package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.ilg.activity.login.Sharedpreference;
import kr.co.ilg.activity.workermanage.GujicProfileForGuin;

public class reviewinputinfo_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public interface OnItemClickListener {
        void onItemClick(View view, int position, String bnum, String jpNum, String workerEmail, String dt);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    } //리스너객체를 전달하는 메서드와 전달된 객체를 저장할 변수를 추가

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, date, field, contents, dltBtn, writeDate;

        MyViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.reviewname);
            date = view.findViewById(R.id.reviewdate);
            field = view.findViewById(R.id.reviewfield);
            contents = view.findViewById(R.id.contents);
            dltBtn = view.findViewById(R.id.dltBtn);
            writeDate = view.findViewById(R.id.writeDate);
        }
    }

    public ArrayList<reviewinputinfo_item> List;
    reviewinputinfo_adapter(Context c, ArrayList<reviewinputinfo_item> List){
        this.List = List;
        this.context = c;
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
        myViewHolder.contents.setText(List.get(position).contents);
        myViewHolder.writeDate.setText(List.get(position).wr_datetime);
        String dt = List.get(position).wr_datetime;
        String workerEmail = List.get(position).workerEmail;
        String jpNum = List.get(position).jpNum;
        String bnum = Sharedpreference.get_business_reg_num(context,"business_reg_num","managerinfo");

        Log.d("=======================", bnum + jpNum + workerEmail + dt);

        myViewHolder.dltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, position, bnum,jpNum, workerEmail, dt); // 뷰홀더 밖에서 리스너처리를 하게끔 연결
            }
        });

/*
        myViewHolder.dltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener aListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean DeleteWRSuccess = jResponse.getBoolean("DeleteWRSuccess");
                            if (DeleteWRSuccess) {
                                Toast.makeText(context, "리뷰가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, ReviewmanageActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "리뷰 삭제 실패 : DB Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                DeleteReviewRequest deleteReviewRequest = new DeleteReviewRequest("WR", bnum, jpNum, workerEmail, dt, aListener);  // Request 처리 클래스

                RequestQueue queue1 = Volley.newRequestQueue(context);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue1.add(deleteReviewRequest);
            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}