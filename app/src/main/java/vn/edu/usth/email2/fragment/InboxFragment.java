package vn.edu.usth.email2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.edu.usth.email2.InboxData.InboxAdapter;


import vn.edu.usth.email2.Messages;
import vn.edu.usth.email2.R;


public class InboxFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference reference;
    InboxAdapter inboxAdapter;
    ArrayList<Messages> list;
    private FirebaseUser auth;
    String useremail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        recyclerView = view.findViewById(R.id.recycle_inbox);
        reference = FirebaseDatabase.getInstance().getReference("sent");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        list = new ArrayList<>();
        inboxAdapter = new InboxAdapter(getContext(), list);
        recyclerView.setAdapter(inboxAdapter);

        auth = FirebaseAuth.getInstance().getCurrentUser();

        useremail = auth.getEmail();

        Query query = reference.orderByChild("receiver").equalTo(useremail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Messages message = dataSnapshot.getValue(Messages.class);
                    list.add(message);
                    Collections.reverse(list);
                }
                inboxAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }
}