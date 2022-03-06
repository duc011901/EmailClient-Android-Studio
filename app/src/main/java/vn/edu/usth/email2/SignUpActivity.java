package vn.edu.usth.email2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private String userID;

    private EditText edtEmail, edtPassword, edtConfirm, edtUserName, edtPhoneNum;
    private FirebaseAuth auth;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.editTextTextEmailAddress2);
        edtPassword = findViewById(R.id.editTextTextPassword2);
        edtConfirm = findViewById(R.id.editTextTextPassword3);
        edtPhoneNum = findViewById(R.id.editTextTextPersonName4);
        edtUserName = findViewById(R.id.editTextTextPersonName2);

    }

    public void CreateUser(View view) {

        onClickSignUp();
    }

    private void onClickSignUp() {
        String Email = edtEmail.getText().toString().trim();
        String Password = edtPassword.getText().toString().trim();
        String ConfirmPassword = edtConfirm.getText().toString().trim();
        String UserName = edtUserName.getText().toString().trim();
        String Phone = edtPhoneNum.getText().toString().trim();
        if(Email.isEmpty() || Password.isEmpty() || UserName.isEmpty()){
            Toast.makeText(this, "This field is required!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
            startActivity(intent);
        }if(!Password.equals(ConfirmPassword)){
            Toast.makeText(this, "Password do not matched! Please, refill!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
            startActivity(intent);
        }if(Phone.isEmpty()){
            Toast.makeText(this, "Please enter the phone number!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
            startActivity(intent);
        }else{
            auth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = auth.getCurrentUser().getUid();
                                User user = new User(UserName, Phone, Password, Email, userID);
                                rootNode = FirebaseDatabase.getInstance();
                                reference = rootNode.getReference("Users");

                                reference.child(userID).setValue(user);

                                Toast.makeText(SignUpActivity.this, "User sign up successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

}