package vn.edu.usth.email2.InboxData;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import vn.edu.usth.email2.Messages;
import vn.edu.usth.email2.R;
import vn.edu.usth.email2.SentDetailActivity;

public class SendAdapter extends RecyclerView.Adapter<SendAdapter.MyViewHolder> {

    Context context;

    ArrayList<Messages> list;

    public SendAdapter(Context context, ArrayList<Messages> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_send_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Messages message = list.get(position);
        holder.receiver.setText(message.getReceiver());
        holder.title.setText(message.getTitle());
        holder.detail.setText(message.getDetail());

        Random mRandom = new Random();
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) holder.icon.getBackground()).setColor(color);
        holder.icon.setText(message.getReceiver().substring(0, 1).toUpperCase());
        int color2 = Color.argb(255,245,134,15);

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.star.getColorFilter() != null) {
                    holder.star.clearColorFilter();
                } else {
                    holder.star.setColorFilter(color2);
                }
            }
        });

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, SentDetailActivity.class);
                mIntent.putExtra("receiver", holder.receiver.getText().toString());
                mIntent.putExtra("title", holder.title.getText().toString());
                mIntent.putExtra("detail", holder.detail.getText().toString());
                mIntent.putExtra("icon", holder.icon.getText().toString());
                mIntent.putExtra("colorIcon", color);
                context.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView receiver, title, detail, icon;
        ImageView star;
        RelativeLayout mLayout;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            receiver = itemView.findViewById(R.id.sd_receiver);
            title = itemView.findViewById(R.id.sd_title);
            detail = itemView.findViewById(R.id.sd_detail);
            star = itemView.findViewById(R.id.starro);
            icon = itemView.findViewById(R.id.ic_receiver);
            mLayout = itemView.findViewById(R.id.sent_list);
        }
    }
}
