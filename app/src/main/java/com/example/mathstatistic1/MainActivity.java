package com.example.mathstatistic1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URI;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnVariacRow, btnGoogleLens, btnSettings, btnHelp, btnAuthor;
    public final static String LOG_TAG = "my";

    DBHelper dbHelper;
    String googleLensChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        btnVariacRow = findViewById(R.id.btnVariacRow);
        btnGoogleLens = findViewById(R.id.btnGoogleLens);
        btnSettings = findViewById(R.id.btnSettings);
        btnHelp = findViewById(R.id.btnHelp);
        btnAuthor = findViewById(R.id.btnAuthor);

        btnVariacRow.setOnClickListener(this);
        btnGoogleLens.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnAuthor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("settingsValues", null, null, null, null, null, null);
        c.moveToFirst();
        int googleLensChoiceColIndex = c.getColumnIndex("googleLensChoice");
        googleLensChoice = c.getString(googleLensChoiceColIndex);
        c.close();
        db.close();


        if (view.getId() == R.id.btnVariacRow) {
            intent = new Intent(MainActivity.this, VariacRowActivity.class);
        } else if (view.getId() == R.id.btnGoogleLens) {
            if (Objects.equals(googleLensChoice, "None")) {
                intent = new Intent(MainActivity.this, GoogleLensActivity.class);
            } else if (Objects.equals(googleLensChoice, "Translate")) {
                intent.setComponent(new ComponentName(
                        "com.google.android.apps.translate",
                        "com.google.android.apps.translate.TranslateActivity"
                ));
            } else if (Objects.equals(googleLensChoice, "Photo")) {
                intent.setComponent(new ComponentName(
                        "com.google.android.apps.photos",
                        "com.google.android.apps.photos.home.HomeActivity"
                ));
            }
        } else if (view.getId() == R.id.btnSettings) {
            intent = new Intent(MainActivity.this, SettingsActivity.class);
        } else if (view.getId() == R.id.btnHelp) {
            String videoId = "OMIeIjC7C2I";

            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + videoId));
            try {
                startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                startActivity(webIntent);
            }
        } else if (view.getId() == R.id.btnAuthor) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vk.com/xbox561"));
        }
        startActivity(intent);
    }


}