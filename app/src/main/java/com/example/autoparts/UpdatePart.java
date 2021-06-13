package com.example.autoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePart extends AppCompatActivity {
    MyDbHelper database;
    private Intent intent;
    private Bundle bundle;

    private EditText partCategoryEditText;
    private EditText partNameEditText;
    private EditText partQuantityEditText;
    private EditText partPriceEditText;

    private Button updateButton;
    private Button backButton;

    private int partId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_part);
        database = new MyDbHelper(this);
        bundle = new Bundle();
        bundle = getIntent().getExtras();
        partId = bundle.getInt("partId");

        partCategoryEditText = findViewById(R.id.partCategoryEditText);
        partNameEditText = findViewById(R.id.partNameEditText);
        partQuantityEditText = findViewById(R.id.partQuantityEditText);
        partPriceEditText = findViewById(R.id.partPriceEditText);
        updateButton = findViewById(R.id.updateButton);
        backButton = findViewById(R.id.backButton);
        GetData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back();
            }
        });
    }

    private void GetData(){
        String part = database.selectParts(partId);
        String[] partElements = part.split("\t");
        partCategoryEditText.setText(partElements[1]);
        partNameEditText.setText(partElements[2]);
        partQuantityEditText.setText(partElements[3]);
        partPriceEditText.setText(partElements[4]);
    }

    private void Update(){
        database.updatePart(
                partId,
                partCategoryEditText.getText().toString(),
                partNameEditText.getText().toString(),
                Integer.parseInt(partQuantityEditText.getText().toString()),
                Float.parseFloat(partPriceEditText.getText().toString())
        );
        Toast.makeText(this, "You update successfully!", Toast.LENGTH_LONG)
                .show();
        Back();
    }

    private void Back() {
        intent = new Intent(this, Inventory.class);
        startActivity(intent);
    }
}