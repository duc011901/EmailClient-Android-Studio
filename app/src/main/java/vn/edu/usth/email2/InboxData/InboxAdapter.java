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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import vn.edu.usth.email2.IbDetailActivity;
import vn.edu.usth.email2.Messages;
import vn.edu.usth.email2.R;


public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder1>{

    Context context;

    ArrayList<Messages> list;

    private FirebaseDatabase fd;
    private DatabaseReference fvref, sdref;
    private FirebaseAuth auth;
    Messages messages;

    public InboxAdapter(Context context, ArrayList<Messages> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_inbox_list, parent, false);
        return new MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxAdapter.MyViewHolder1 holder, int position) {


        Messages message = list.get(position);
        holder.sender.setText(message.getSender());
        holder.title.setText(message.getTitle());
        holder.detail.setText(message.getDetail());
        Random mRandom = new Random();
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) holder.icon.getBackground()).setColor(color);
        int color2 = Color.argb(255,245,134,15);
        holder.icon.setText(message.getSender().substring(0, 1).toUpperCase());
        if(message.isStarCheck()){
            holder.star.setColorFilter(color2);
        }

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, IbDetailActivity.class);
                mIntent.putExtra("sender", holder.sender.getText().toString());
                mIntent.putExtra("title", holder.title.getText().toString());
                mIntent.putExtra("detail", holder.detail.getText().toString());
                mIntent.putExtra("icon", holder.icon.getText().toString());
                mIntent.putExtra("colorIcon", color);
                context.startActivity(mIntent);
            }
        });

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fd = FirebaseDatabase.getInstance();
                fvref = fd.getReference().child("fav");
                auth  = FirebaseAuth.getInstance();
                sdref = fd.getReference().child("sent");

                if (holder.star.getColorFilter() != null) {
                    holder.star.clearColorFilter();

                    sdref.setValue(messages);


                } else {
                    messages = new Messages();
                    messages.setSender(message.getSender());
                    messages.setTitle(message.getTitle());
                    messages.setDetail(message.getDetail());
                    messages.setReceiver(message.getReceiver());
                    messages.setStarCheck(true);
                    fvref.push().setValue(messages);


                    holder.star.setColorFilter(color2);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder{
        TextView sender, title, detail,icon;
        RelativeLayout mLayout;
        ImageView star;

        public MyViewHolder1(@NonNull View itemView){
            super(itemView);
            star = itemView.findViewById(R.id.starro);
            icon = itemView.findViewById(R.id.ic_sender);
            sender = itemView.findViewById(R.id.ib_sender);
            title = itemView.findViewById(R.id.ib_title);
            detail = itemView.findViewById(R.id.ib_detail);
            mLayout = itemView.findViewById(R.id.inbox_list);
        }
    }
}