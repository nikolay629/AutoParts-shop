package com.example.autoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBill extends AppCompatActivity {
    MyDbHelper database;

    private EditText clientNameEditText;
    private EditText clientAddressEditText;
    private EditText partNameEditText;
    private EditText quantityEditText;
    private EditText priceEditText;

    private Button updateButton;
    private Button backButton;

    private Bundle bundle;
    private Intent intent;

    private String[] billElements;
    private float partPrice = -1;
    private float billPrice = -1;
    private int billId;
    private int partId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bill);
        database = new MyDbHelper(this);

        bundle = getIntent().getExtras();
        billId = bundle.getInt("id");

        clientNameEditText = findViewById(R.id.clientNameEditText);
        clientAddressEditText = findViewById(R.id.clientAddressEditText);
        partNameEditText = findViewById(R.id.partNameEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        priceEditText = findViewById(R.id.priceEditText);
        updateButton = findViewById(R.id.updateButton);
        backButton = findViewById(R.id.backButton);

        GetBill();

        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(quantityEditText.getText().toString().equals("")){
                    priceEditText.setText("");
                } else{
                    billPrice = partPrice * Integer.parseInt(quantityEditText.getText().toString());
                    priceEditText.setText(billPrice+"");
                }
            }
        });

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

    private void GetBill() {
        String bill = database.selectBills(billId);
        billElements = bill.split("\t");

        clientNameEditText.setText(billElements[1]);
        clientAddressEditText.setText(billElements[2]);
        partNameEditText.setText(billElements[3]);
        quantityEditText.setText(billElements[4]);
        priceEditText.setText(billElements[5]);
        partId = Integer.parseInt(billElements[6]);

        partPrice = Float.parseFloat(billElements[5]) / Integer.parseInt(billElements[4]);

    }

    private void Update() {
        String part = database.selectParts(partId);
        String[] partElements = part.split("\t");
        int partQuantity = Integer.parseInt(partElements[3]) + Integer.parseInt(billElements[4]);


        if(partQuantity < Integer.parseInt(quantityEditText.getText().toString())){
            Toast.makeText(this, "Don't have this quantity! \n" +
                    "We have " + partQuantity + " in inventory!", Toast.LENGTH_LONG).show();
            return;
        }

        database.updatePart(
                partId,
                partElements[1],
                partElements[2],
                partQuantity - Integer.parseInt(quantityEditText.getText().toString()),
                Float.parseFloat(partElements[4])
        );

        database.updateBill(
                billId,
                clientNameEditText.getText().toString(),
                clientAddressEditText.getText().toString(),
                Integer.parseInt(quantityEditText.getText().toString()),
                Float.parseFloat(priceEditText.getText().toString()),
                partId
        );

        Toast.makeText(this, "You update bill successfully", Toast.LENGTH_LONG).show();

        Back();
    }

    private void Back() {
        intent = new Intent(this, AllBills.class);
        startActivity(intent);
    }
}