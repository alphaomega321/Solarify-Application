package com.example.dabassa.solarify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Overview extends AppCompatActivity {

    Button dR;
    Button back;
    TextView bEven;
    TextView yearly;
    TextView IRR;
    Button BACK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String[] passedArg= getIntent().getStringArrayExtra("arg");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.symbol);
        dR = findViewById(R.id.detailsButton);
        back = findViewById(R.id.backButton);
        bEven = findViewById(R.id.breakEven);
        IRR = findViewById(R.id.irr);
        yearly = findViewById(R.id.yearly);
        BACK = findViewById(R.id.backButton);

        bEven.setText(passedArg[6]);
        yearly.setText(passedArg[5]);
        IRR.setText(passedArg[9]);

        dR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Overview.this, SavingsActivity.class);
                intent.putExtra("arg", passedArg);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Overview.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
