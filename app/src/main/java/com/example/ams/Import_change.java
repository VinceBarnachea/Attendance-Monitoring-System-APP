package com.example.ams;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Import_change extends Fragment {
    private EditText oldPass,newPass,confirmPass;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button okBtn;
    private ProgressDialog dialog;
    private AuthCredential credential;
    private String email, oldPassword,newPassword,confirmPassword,bagongPass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_changepss,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        oldPass = getView().findViewById(R.id.oldPassET);
        confirmPass = getView().findViewById(R.id.confirmPassET);

        newPass = getView().findViewById(R.id.newPassET);
        okBtn = (Button) getView().findViewById(R.id.btnOK);
        dialog = new ProgressDialog(getActivity());
        auth = FirebaseAuth.getInstance();


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword=newPass.getText().toString();
                oldPassword=oldPass.getText().toString();
                confirmPassword=confirmPass.getText().toString();



                if((oldPassword.isEmpty() && newPassword.isEmpty()) && confirmPassword.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill the fields", Toast.LENGTH_SHORT).show();
                }else if(oldPassword.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter your current password", Toast.LENGTH_SHORT).show();
                } else if(newPassword.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter your new password", Toast.LENGTH_SHORT).show();
                }else if(confirmPassword.isEmpty()){
                    Toast.makeText(getActivity(), "Please re-enter your new password", Toast.LENGTH_SHORT).show();
                }else if(!newPassword.equals(confirmPassword)){
                    Toast.makeText(getActivity(), "password didn't match", Toast.LENGTH_SHORT).show();
                }else{
                    user=auth.getCurrentUser();
                    email=user.getEmail();
                    credential=EmailAuthProvider.getCredential(email,oldPassword);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dialog.setMessage("Changing password, please wait");
                                dialog.show();

                                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       dialog.dismiss();
                                       Toast.makeText(getActivity(), "Your password has been changed", Toast.LENGTH_SHORT).show();

                                       startActivity( new Intent(getActivity(), HomeActivity.class));
                                   }else
                                       dialog.dismiss();
//                                        Toast.makeText(getActivity(), "Change password failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });

                }

            }
        });



    }
    }


