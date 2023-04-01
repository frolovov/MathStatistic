package com.example.mathstatistic1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class IntervalActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et1;
    Button btnResult;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);

        et1 = findViewById(R.id.et1);
        btnResult = findViewById(R.id.btnResult);
        tvResult = findViewById(R.id.tvResult);

        btnResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SolveIntervalRow sir = new SolveIntervalRow(MyFormat.commaToDot(et1.getText().toString()).split(" "));
        Log.d("my", "1");
        sir.convertStringArrayToDoubleArray();
        Log.d("my", "2");
        sir.min();
        Log.d("my", "3");
        sir.max();
        Log.d("my", "4");
        sir.findIntervalSize();
        Log.d("my", "5");
        sir.createIntervalArray();
        Log.d("my", "6");
        sir.countValuesInEachInterval();
        Log.d("my", "7");
        sir.findInputedValuesLength();
        Log.d("my", "8");
        sir.calcSredArefm();
        Log.d("my", "9");
        sir.calcDisp();
        Log.d("my", "10");
        sir.calcSigma();
        Log.d("my", "11");
        sir.maxAmountIntervals();
        Log.d("my", "12");
        sir.calcModa();
        Log.d("my", "13");
        sir.medianInterval();
        Log.d("my", "14");
        sir.sumAmountsBeforeMedianInterval();
        Log.d("my", "15");
        sir.calcMediana();
        Log.d("my", "16");
        Double koefVariac = sir.calcKoefVariac();
        Log.d("my", "17");

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("settingsValues", null, null, null, null, null, null);
        c.moveToFirst();
        Double koefVariacForHomogeneity = c.getDouble(c.getColumnIndexOrThrow("koefVariac"));
        c.close();
        dbHelper.close();

        tvResult.setText(sir.Results() + (koefVariacForHomogeneity > koefVariac? " - однородный" : " - неоднородный"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Intent intent = new Intent();
        if (id == R.id.itemGoogleLens) {

            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.query("settingsValues", null, null, null, null, null, null);
            c.moveToFirst();
            String googleLensChoice = c.getString(c.getColumnIndexOrThrow("googleLensChoice"));

            if (Objects.equals(googleLensChoice, "Translate")) {
                intent.setComponent( new ComponentName(
                        "com.google.android.apps.translate",
                        "com.google.android.apps.translate.TranslateActivity"));
            } else if (Objects.equals(googleLensChoice, "Photo")) {
                intent.setComponent(new ComponentName(
                        "com.google.android.apps.photos",
                        "com.google.android.apps.photos.home.HomeActivity"));
            } else {
                intent.setClass(this, GoogleLensActivity.class);
            }

            dbHelper.close();
            c.close();

            startActivity(intent);
        } else if (id == R.id.itemHelp) {
            String videoId = "OMIeIjC7C2I";

            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + videoId));
            try {
                startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                startActivity(webIntent);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}