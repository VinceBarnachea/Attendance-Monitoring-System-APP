package com.example.ams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText mEmailEt,mPasswordEt,mRepasswordET,mUsernameET;
    Button mRegisterBtn;
    TextView mHave_Account;

    String username,ngaran, mEmail;


    private FirebaseAuth mAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mEmailEt=findViewById(R.id.emailEt);
        mPasswordEt=findViewById(R.id.passwordEt);
        mRegisterBtn=findViewById(R.id.registerBtn);
        mRepasswordET=findViewById(R.id.repasswordEt);
        mHave_Account=findViewById(R.id.have_account);
        mUsernameET=findViewById(R.id.usernameEt);


        mAuth = FirebaseAuth.getInstance();


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering user");

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =mEmailEt.getText().toString().trim();
                String password =mPasswordEt.getText().toString().trim();
                String repassword=mRepasswordET.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailEt.setError("invalid input");
                    mEmailEt.setFocusable(true);
                }else if(password.length()<6) {
                    mPasswordEt.setError("Password length at least 6 characters");
                    mPasswordEt.setFocusable(true);
                }else if(!(repassword.equals(password))) {
                    Toast.makeText(RegisterActivity.this, "Password don't matched!", Toast.LENGTH_SHORT).show();

                }else if(repassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your password", Toast.LENGTH_SHORT).show();

                }else
                    registerUser(email,password);
                }
        });
        mHave_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void registerUser(String email, String password){
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                username=mUsernameET.getText().toString();
                final String email=mEmailEt.getText().toString();

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    user=mAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Registered.....+\n"+user.getEmail(), Toast.LENGTH_SHORT).show();

                    UserProfileChangeRequest profileUpdates=new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();

                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                String user_id=mAuth.getCurrentUser().getUid();

                                DatabaseReference current_user= FirebaseDatabase.getInstance().getReference().child(user_id);
                                current_user.child("userDetails").child("name").push().setValue(username);

                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();

                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
