package com.example.autoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Market extends AppCompatActivity {
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
    private String[] partNames = {};

    private List<String> categoriesList = new ArrayList<>(Arrays.asList(categories));
    private ArrayList<String> partNamesList;
    private List<String> partList = new ArrayList<>();

    private EditText clientNameEditText;
    private EditText clientAddressEditText;
    private Spinner partNamesSpinner;
    private Spinner categoriesSpinner;
    private EditText quantityEditText;
    private TextView billPriceTextView;

    private Button buyButton;
    private Button cancelButton;

    private Intent intent;

    private float partPrice = 0;
    private int partId = -1;
    private int partQuantity = 0;
    private String partCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        database = new MyDbHelper(this);

        clientNameEditText = findViewById(R.id.clientNameEditText);
        clientAddressEditText = findViewById(R.id.clientAddressEditText);
        categoriesSpinner = findViewById(R.id.categoriesSpinner);
        partNamesSpinner = findViewById(R.id.partNamesSpinner);
        quantityEditText = findViewById(R.id.quantityEditText);
        billPriceTextView = findViewById(R.id.billPriceTextView);

        buyButton = findViewById(R.id.buyButton);
        cancelButton = findViewById(R.id.cancelButton);

        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categoriesList);
        categoriesSpinner.setAdapter(categoryArrayAdapter);


        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetPartName(categoriesSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!(categoriesSpinner.getSelectedItem().toString().equals("Do not have product from this category!"))
                    && !(quantityEditText.getText().toString().equals(""))) {
                    billPriceTextView.setText(partPrice * Integer.parseInt(quantityEditText.getText().toString()) + "");
                } else {
                    billPriceTextView.setText("");
                }
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buy();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });
    }

    public void GetPartName(String category){
        partList = database.selectPartByCategory(category);
        partNamesList = new ArrayList<>();
        if(partList.isEmpty()) {
            partNamesList.add("Do not have product from this category!");
        } else {
            for (String s : partList) {
                String[] data = s.split("\t");
                if(Integer.parseInt(data[3].trim()) > 0)
                    partNamesList.add(data[2]);
            }
        }

        ArrayAdapter<String> partNamesArrayAdapter = new ArrayAdapter<>(Market.this, R.layout.support_simple_spinner_dropdown_item, partNamesList);
        partNamesSpinner.setAdapter(partNamesArrayAdapter);

        partNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(partNamesSpinner.getSelectedItem().equals("Do not have product from this category!")) {
                    billPriceTextView.setText("");
                } else {
                    for (String s : partList) {
                        String[] data = s.split("\t");
                        if(partNamesSpinner.getSelectedItem().equals(data[2])) {
                            partId = Integer.parseInt(data[0].trim());
                            partCategory  = data[1].trim();
                            partQuantity = Integer.parseInt(data[3].trim());
                            partPrice = Float.parseFloat(data[4]);
                        }
                    }
                    if(quantityEditText.getText().toString().equals("")){
                        billPriceTextView.setText("");
                    } else{
                        billPriceTextView.setText(partPrice * Integer.parseInt(quantityEditText.getText().toString()) + "");
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void Buy(){
        if(clientNameEditText.getText().toString().isEmpty()
                || clientAddressEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please insert name and address!", Toast.LENGTH_LONG).show();
            return;
        }

        if(partNamesSpinner.getSelectedItem().toString().equals("Do not have product from this category!")){
            Toast.makeText(this, "Don't have product from this category! \n Please choose another category.", Toast.LENGTH_LONG).show();
            return;
        }

        if(quantityEditText.getText().toString().equals("")){
            Toast.makeText(this, "Please insert quantity!", Toast.LENGTH_LONG).show();
            return;
        }

        int billQuantity = partQuantity - Integer.parseInt(quantityEditText.getText().toString());
        if(billQuantity < 0){
            Toast.makeText(this, "Have " + partQuantity + " from this part!", Toast.LENGTH_LONG).show();
            return;
        }

        database.updatePart(
                partId,
                partCategory,
                partNamesSpinner.getSelectedItem().toString(),
                billQuantity,
                partPrice
                );


        database.insertBills(
                clientNameEditText.getText().toString(),
                clientAddressEditText.getText().toString(),
                partId,
                Integer.parseInt(quantityEditText.getText().toString()),
                Float.parseFloat(billPriceTextView.getText().toString())
        );
        Toast.makeText(this, "You buy successfully!", Toast.LENGTH_LONG).show();

        clientAddressEditText.setText("");
        clientNameEditText.setText("");
        quantityEditText.setText("");
    }

    private void Cancel(){
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}