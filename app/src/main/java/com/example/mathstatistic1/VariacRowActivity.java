package com.example.mathstatistic1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class VariacRowActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton rbNumber, rbInterval, rbYes, rbNo;
    Button btnSolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variac_row);

        rbNumber = findViewById(R.id.rbNumber);
        rbInterval = findViewById(R.id.rbInterval);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        btnSolve = findViewById(R.id.btnSolve);

        btnSolve.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(VariacRowActivity.this, rbNumber.isChecked()?
                rbYes.isChecked()? NumberCompleteActivity.class : NumberActivity.class :
                rbYes.isChecked()? IntervalCompleteActivity.class : IntervalActivity.class);
        startActivity(intent);
    }
}