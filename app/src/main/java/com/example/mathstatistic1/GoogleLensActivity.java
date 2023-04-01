package com.example.mathstatistic1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GoogleLensActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnTranslate, btnPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_lens);

        btnTranslate = findViewById(R.id.btnTranslate);
        btnPhoto = findViewById(R.id.btnPhoto);

        btnTranslate.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (v.getId() == R.id.btnTranslate) {
            intent.setComponent(new ComponentName(
                        "com.google.android.apps.translate",
                        "com.google.android.apps.translate.TranslateActivity"
            ));
        } else if (v.getId() == R.id.btnPhoto) {
           intent.setComponent(new ComponentName(
                        "com.google.android.apps.photos",
                        "com.google.android.apps.photos.home.HomeActivity"
           ));
        }
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Данное приложение не установлено на вашем устройстве", Toast.LENGTH_LONG).show();
        }
    }
}