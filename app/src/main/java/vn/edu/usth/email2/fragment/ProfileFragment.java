package vn.edu.usth.email2.fragment;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.BitSet;
import java.util.Objects;

import vn.edu.usth.email2.MainActivity;
import vn.edu.usth.email2.R;
import vn.edu.usth.email2.User;


public class ProfileFragment extends Fragment {

    private View v;

    private EditText edtname, edtPhone, edtemail;
    private Button updateprofile;
    private MainActivity mainActivity;
    private FirebaseAuth auth;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v =  inflater.inflate(R.layout.fragment_profile, container, false);
        
        initUI();
        auth = FirebaseAuth.getInstance();
        mainActivity = (MainActivity) getActivity();
        setUserInformation();
        click();
        return v;

    }

    private void click() {
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdate();
            }
        });
    }


    public void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }

        edtPhone.setText(user.getPhoneNumber());
        edtname.setText(user.getDisplayName());
        edtemail.setText(user.getEmail());
    }

    private void initUI() {
        edtemail = v.findViewById(R.id.editTextTextPersonName5);
        edtname = v.findViewById(R.id.editTextTextPersonName3);
        edtPhone = v.findViewById(R.id.editTextTextPersonName6);
        updateprofile = v.findViewById(R.id.button);


    }

    private void onClickUpdate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }else{
            String username = edtname.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String email = edtemail.getText().toString().trim();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mainActivity.information();
                            }
                        }
                    });

            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                               mainActivity.information();
                                userID = auth.getCurrentUser().getUid();
                                User user = new User(username, phone, email, userID);
                                rootNode = FirebaseDatabase.getInstance();
                                reference = rootNode.getReference("Users");

                                reference.child(userID).setValue(user);
                                Toast.makeText(getActivity(), "Update user successfully", Toast.LENGTH_SHORT).show();
                                mainActivity.information();
                            }
                        }
                    });
        }


    }

}