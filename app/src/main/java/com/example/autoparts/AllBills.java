package com.example.autoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AllBills extends AppCompatActivity {
    List<String> list = new ArrayList<>();
    MyDbHelper database;

    private Intent intent;

    private TextView idTextView;
    private  TextView clientNameTextView;
    private  TextView clientAddressTextView;
    private  TextView partNameTextView;
    private  TextView billQuantityTextView;
    private  TextView billPriceTextView;

    private LinearLayout verticalLayout;
    private LinearLayout horizontalLayout;

    private Button updateButton;
    private Button deleteButton;
    private Button backButton;

    private int counter = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bills);

        verticalLayout = findViewById(R.id.verticalLayout);
        GetData();
    }

    public void GetData() {
        try {
            database = new MyDbHelper(this);
            list = database.selectBills();

            horizontalLayout = new LinearLayout(this);

            idTextView = new TextView(this);
            clientNameTextView = new TextView(this);
            clientAddressTextView = new TextView(this);
            partNameTextView = new TextView(this);
            billQuantityTextView = new TextView(this);
            billPriceTextView = new TextView(this);

            clientNameTextView.setGravity(Gravity.CENTER);
            clientAddressTextView.setGravity(Gravity.CENTER);
            partNameTextView.setGravity(Gravity.CENTER);
            billQuantityTextView.setGravity(Gravity.CENTER);
            billPriceTextView.setGravity(Gravity.CENTER);

            idTextView.setText("№");
            clientNameTextView.setText("Name");
            clientAddressTextView.setText("Address");
            partNameTextView.setText("Part Name");
            billQuantityTextView.setText("Quantity");
            billPriceTextView.setText("Price");

            horizontalLayout.addView(idTextView, 100, LinearLayout.LayoutParams.MATCH_PARENT);
            horizontalLayout.addView(clientNameTextView, 270, LinearLayout.LayoutParams.MATCH_PARENT);
            horizontalLayout.addView(clientAddressTextView, 270, LinearLayout.LayoutParams.MATCH_PARENT);
            horizontalLayout.addView(partNameTextView, 370, LinearLayout.LayoutParams.MATCH_PARENT);
            horizontalLayout.addView(billQuantityTextView, 200, LinearLayout.LayoutParams.MATCH_PARENT);
            horizontalLayout.addView(billPriceTextView, 200, LinearLayout.LayoutParams.MATCH_PARENT);
            verticalLayout.addView(horizontalLayout);


            if (!list.isEmpty()){
                for (String l : list) {

                    final String[] dataArray = l.split("\t");

                    horizontalLayout = new LinearLayout(this);
                    idTextView = new TextView(this);
                    clientNameTextView = new TextView(this);
                    clientAddressTextView = new TextView(this);
                    partNameTextView = new TextView(this);
                    billQuantityTextView = new TextView(this);
                    billPriceTextView = new TextView(this);

                    idTextView.setText(counter+"");
                    clientNameTextView.setText(dataArray[1]);
                    clientAddressTextView.setText(dataArray[2]);
                    partNameTextView.setText(dataArray[3]);
                    billQuantityTextView.setText(dataArray[4]);
                    billPriceTextView.setText(dataArray[5]);

                    horizontalLayout.addView(idTextView,100, LinearLayout.LayoutParams.MATCH_PARENT);
                    horizontalLayout.addView(clientNameTextView, 270, LinearLayout.LayoutParams.MATCH_PARENT);
                    horizontalLayout.addView(clientAddressTextView, 270, LinearLayout.LayoutParams.MATCH_PARENT);
                    horizontalLayout.addView(partNameTextView, 370, LinearLayout.LayoutParams.MATCH_PARENT);
                    horizontalLayout.addView(billQuantityTextView, 200, LinearLayout.LayoutParams.MATCH_PARENT);
                    horizontalLayout.addView(billPriceTextView);
                    verticalLayout.addView(horizontalLayout);

                    horizontalLayout = new LinearLayout(this);
                    horizontalLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    updateButton = new Button(this);
                    updateButton.setText("Update");
                    deleteButton = new Button(this);
                    deleteButton.setText("Delete");
                    horizontalLayout.addView(deleteButton);
                    horizontalLayout.addView(updateButton);
                    verticalLayout.addView(horizontalLayout);

                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Update(Integer.parseInt(dataArray[0].toString()));
                        }
                    });

                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Delete(Integer.parseInt(dataArray[0].toString()));
                        }
                    });

                    counter++;
                }
            }
            horizontalLayout = new LinearLayout(this);
            horizontalLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            backButton = new Button(this);
            backButton.setText("Back");
            horizontalLayout.addView(backButton);
            verticalLayout.addView(horizontalLayout);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(AllBills.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Exception: "+e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }


    private void Update(int id){
        intent = new Intent(this, UpdateBill.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void Delete(int id){
        database.deleteBill(id);
        intent = new Intent(this, AllBills.class);
        startActivity(intent);
    }
}