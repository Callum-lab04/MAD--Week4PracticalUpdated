package com.example.navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    TextView output;

    Button searchFor, callBtn, textBtn, send;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = findViewById(R.id.textOut_txt);
        send = findViewById(R.id.send_btn);

        searchFor = findViewById(R.id.search_btn);
        searchFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startWebActivity = new Intent(MainActivity.this, webActivity.class);
                startActivity(startWebActivity);

            }
        });

        callBtn = findViewById(R.id.call_btn);

        textBtn = findViewById(R.id.text_btn);
        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent textActivity = new Intent(MainActivity.this, TextActivity.class);
                startActivity(textActivity);
            }
        });

    }

    public void dialPhone(View view) {
        String number = "123456789";
        if (checkPermission("android.permission.CALL_PHONE") == false) {
            Toast.makeText(this, "No permission to call", Toast.LENGTH_SHORT).show();
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel: " + number));
            startActivity(callIntent);
        }
    }

    private boolean checkPermission(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
        return (permissionCheck == getPackageManager().PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    send.setEnabled(true);
                }
                return;
            }
        }
    }

    public void send(View view) {
        String phoneNumber = ((EditText) findViewById(R.id.number_txt)).getText().toString();
        String msg = ((EditText) findViewById(R.id.message_txt)).getText().toString();

        if (phoneNumber == null || phoneNumber.length() == 0 || msg == null || msg.length() == 0) {
            return;
        }
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
            Toast.makeText(this, "Your message has been sent", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
        }
    }
}



