package com.example.mathstatistic1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton rbPhoto, rbTranslate;
    EditText etKoefVariac;
    Button btnSave, btnReset;

    DBHelper dbHelper;
    double koefVariac;
    String googleLensChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //инициализациализируем объекты и вешаем на них обработчики
        etKoefVariac = findViewById(R.id.etKoefVariac);
        rbPhoto = findViewById(R.id.rbPhoto);
        rbTranslate = findViewById(R.id.rbTranslate);
        btnSave = findViewById(R.id.btnSave);
        btnReset = findViewById(R.id.btnReset);

        btnSave.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        // Считываем текущие настройки
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("settingsValues", null, null, null, null, null, null);
        c.moveToFirst();
        int koefVariacColIndex = c.getColumnIndex("koefVariac");
        int googleLensChoiceColIndex = c.getColumnIndex("googleLensChoice");
        koefVariac = c.getDouble(koefVariacColIndex);
        googleLensChoice = c.getString(googleLensChoiceColIndex);
        c.close();
        db.close();

        // Отображаем текущие настройки
        switch(googleLensChoice) {
            case "Translate":
                rbTranslate.setChecked(true);
                break;
            case "Photo":
                rbPhoto.setChecked(true);
                break;
        }
        etKoefVariac.setText("" + koefVariac);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Считываем в переменные значения полей/радиобаттонов на экране
        double koefVariac = Double.parseDouble(etKoefVariac.getText().toString());
        String googleLensChoice = rbPhoto.isChecked()? "Photo" : rbTranslate.isChecked()? "Translate" : "None";

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (id == R.id.btnSave) {
            Log.d(MainActivity.LOG_TAG, "---Update db---");

            ContentValues cv = new ContentValues();
            cv.put("koefVariac", koefVariac);
            cv.put("googleLensChoice", googleLensChoice);

            long rowId = db.update("settingsValues", cv, null, null);
            Log.d(MainActivity.LOG_TAG, "row updated, ID = " + rowId + " koefVariac = " + koefVariac + " googleLensChoice = " + googleLensChoice);
        } else if (id == R.id.btnReset) {
            ContentValues cv = new ContentValues();
            cv.put("koefVariac", 10);
            cv.put("googleLensChoice", "None");

            long rowId = db.update("settingsValues", cv, null, null);
            Log.d(MainActivity.LOG_TAG, "row updated, ID = " + rowId + " koefVariac = " + koefVariac + " googleLensChoice = " + googleLensChoice);
            finish();
        }

        db.close();
    }
}