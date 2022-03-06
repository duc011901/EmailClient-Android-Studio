package vn.edu.usth.email2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.edu.usth.email2.R;


public class ChangePasswordFragment extends Fragment {

    private View view;
    private EditText edtpassword, edtconfirm;
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        edtconfirm = view.findViewById(R.id.editTextTextPersonName8);
        edtpassword = view.findViewById(R.id.editTextTextPersonName7);
        btn = view.findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword();
            }
        });




        return view;
    }

    private void ChangePassword() {
        String password = edtpassword.getText().toString().trim();
        String confirm = edtconfirm.getText().toString().trim();

        if(!password.equals(confirm)){
            Toast.makeText(getActivity(), "Password do not match", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String newPassword;
            newPassword = password;

            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Password change successfully!", Toast.LENGTH_SHORT).show();
                            }else {
                                Reauthenticate();
                            }
                        }
                    });
        }
        }

        private void Reauthenticate(){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


            AuthCredential credential = EmailAuthProvider
                    .getCredential("user@example.com", "password1234");

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                ChangePassword();
                            }
                        }
                    });
        }

}