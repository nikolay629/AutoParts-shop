package com.example.autoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddParts extends AppCompatActivity {
    private Intent intent;
    MyDbHelper database;
    private final String[] categories = {
            "Air and Fuel Delivery",
            "Suspension",
            "Electrical, Lighting and Body",
            "Exhaust",
            "Brake",
            "Tire and Wheel",
            "HVAC",
            "Ignition",
            "Engine",
            "Electrical, Charging and Starting",
            "Emission Control",
            "Transmission"
    };
    private List<String> categoriesList = new ArrayList<>(Arrays.asList(categories));

    private EditText partNameEditText;
    private EditText partQuantityEditText;
    private EditText partPriceEditText;
    private Spinner categoriesSpinner;
    private Button addButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parts);
        categoriesSpinner = (Spinner) findViewById(R.id.categoriesSpinner);
        partNameEditText = findViewById(R.id.partNameEditText);
        partQuantityEditText = findViewById(R.id.quantityEditText);
        partPriceEditText = findViewById(R.id.partPriceEditText);
        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categoriesList);
        categoriesSpinner.setAdapter(spinnerArrayAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });
    }

    private void Add() {
        try {
            database = new MyDbHelper(this);
            database.insertParts(
                    categoriesSpinner.getSelectedItem().toString(),
                    partNameEditText.getText().toString(),
                    Integer.parseInt(partQuantityEditText.getText().toString()),
                    Float.parseFloat(partPriceEditText.getText().toString())
            );
            Toast.makeText(this, "You successfully add " + partNameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            partNameEditText.getText().clear();
            partQuantityEditText.getText().clear();
            partPriceEditText.getText().clear();
        } catch (Exception e){
            Toast.makeText(this, "Exception: "+e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void Cancel() {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}