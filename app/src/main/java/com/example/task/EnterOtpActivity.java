package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class EnterOtpActivity extends AppCompatActivity {
    EditText enterOtp;
    Button verifyOtp;
    ProgressBar progressBar;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        enterOtp = findViewById(R.id.enterOtp);
        verifyOtp = findViewById(R.id.verifyOtp);
        progressBar = findViewById(R.id.progressbar);
        verificationId = getIntent().getStringExtra("verificationId");
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterOtp.getText().toString().trim().isEmpty()){
                    Toast.makeText(EnterOtpActivity.this, "Enter OTP! ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = enterOtp.getText().toString();
                if (verificationId != null){
                    progressBar.setVisibility(View.VISIBLE);
                    verifyOtp.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId,code);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            verifyOtp.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(EnterOtpActivity.this, "Invalid OTP!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

}
