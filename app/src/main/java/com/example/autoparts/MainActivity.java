package com.example.autoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDbHelper database;

    private Button marketButton;
    private Button allBillsButton;
    private Button addPartsButton;
    private Button inventoryButton;

    private Intent intent;

    protected void Select() {
        try {
            database = new MyDbHelper(this);
            database.createTables();
        } catch (Exception e){
            Toast.makeText(this, "Exception: "+e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        }finally {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Select();

        marketButton = (Button) findViewById(R.id.marketButton);
        addPartsButton = (Button) findViewById(R.id.addPartButton);
        inventoryButton = findViewById(R.id.inventoryButton);
        allBillsButton = findViewById(R.id.allBillsButton);

        marketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMarket();
            }
        });

        addPartsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddPartsActivity();
            }
        });

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenInventory();
            }
        });

        allBillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAllBillsActivity();
            }
        });
    }

    private void OpenMarket(){
        intent = new Intent(this, Market.class);
        startActivity(intent);
    }

    private void OpenAddPartsActivity() {
        intent = new Intent(this, AddParts.class);
        startActivity(intent);
    }

    private void OpenInventory() {
        intent = new Intent(this, Inventory.class);
        startActivity(intent);
    }

    private void OpenAllBillsActivity() {
        intent = new Intent(this, AllBills.class);
        startActivity(intent);
    }
}