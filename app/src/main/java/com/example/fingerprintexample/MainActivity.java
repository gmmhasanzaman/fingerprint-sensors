package com.example.fingerprintexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Button authBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainActivity activity = this;

        authBtn = findViewById(R.id.authBtn);

        final Executor executor = Executors.newSingleThreadExecutor();

        final BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Authentication")
                .setSubtitle("Subtitile")
                .setDescription("Please touch your finger")
                .setNegativeButton("Cancel", executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).build();

        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                   @Override
                   public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                       activity.runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               });
            }
        });
    }
}
