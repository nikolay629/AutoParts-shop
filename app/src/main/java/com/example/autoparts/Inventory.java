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

public class Inventory extends AppCompatActivity {
    List<String> list = new ArrayList<>();
    MyDbHelper database;
    private Intent intent;

    private  TextView idTextView;
    private  TextView partCategoryTextView;
    private  TextView partNameTextView;
    private  TextView partQuantityTextView;
    private  TextView partPriceTextView;

    private LinearLayout verticalLayout;
    private LinearLayout horizontalLayout;

    private Button updateButton;
    private Button deleteButton;
    private Button backButton;

    private int counter = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        verticalLayout = findViewById(R.id.verticalLayout);
        GetData();
    }

    public void GetData() {
        try {
            database = new MyDbHelper(this);
            list = database.selectParts();

            horizontalLayout = new LinearLayout(this);

            idTextView = new TextView(this);
            partCategoryTextView = new TextView(this);
            partNameTextView = new TextView(this);
            partQuantityTextView = new TextView(this);
            partPriceTextView = new TextView(this);

            partCategoryTextView.setGravity(Gravity.CENTER);
            partNameTextView.setGravity(Gravity.CENTER);
            partQuantityTextView.setGravity(Gravity.CENTER);
            partPriceTextView.setGravity(Gravity.CENTER);

            idTextView.setText("№");
            partCategoryTextView.setText("Part Category");
            partNameTextView.setText("Part Name");
            partQuantityTextView.setText("Quantity");
            partPriceTextView.setText("Price");

            horizontalLayout.addView(idTextView, 70, LinearLayout.LayoutParams.MATCH_PARENT);
            horizontalLayout.addView(partCategoryTextView, 500, LinearLayout.LayoutParams.MATCH_PARENT);
            horizontalLayout.addView(partNameTextView, 450, LinearLayout.LayoutParams.MATCH_PARENT);
            horizontalLayout.addView(partQuantityTextView, 200, LinearLayout.LayoutParams.MATCH_PARENT);
            horizontalLayout.addView(partPriceTextView, 200, LinearLayout.LayoutParams.MATCH_PARENT);
            verticalLayout.addView(horizontalLayout);

            if (!list.isEmpty()){
                for (String l : list) {

                    final String[] dataArray = l.split("\t");

                    horizontalLayout = new LinearLayout(this);
                    idTextView = new TextView(this);
                    partCategoryTextView = new TextView(this);
                    partNameTextView = new TextView(this);
                    partQuantityTextView = new TextView(this);
                    partPriceTextView = new TextView(this);

                    idTextView.setText(counter+"");
                    partCategoryTextView.setText(dataArray[1]);
                    partNameTextView.setText(dataArray[2]);
                    partQuantityTextView.setText(dataArray[3]);
                    partPriceTextView.setText(dataArray[4]);

                    horizontalLayout.addView(idTextView,70, LinearLayout.LayoutParams.MATCH_PARENT);
                    horizontalLayout.addView(partCategoryTextView, 500, LinearLayout.LayoutParams.MATCH_PARENT);
                    horizontalLayout.addView(partNameTextView, 450, LinearLayout.LayoutParams.MATCH_PARENT);
                    horizontalLayout.addView(partQuantityTextView, 200, LinearLayout.LayoutParams.MATCH_PARENT);
                    horizontalLayout.addView(partPriceTextView);
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
                    intent = new Intent(Inventory.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Exception: "+e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void Update(int id){
        intent = new Intent(this, UpdatePart.class);
        Bundle bundle = new Bundle();
        bundle.putInt("partId", id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void Delete(int id){
        database.deletePart(id);
        Toast.makeText(this, "You successfully delete part!", Toast.LENGTH_LONG)
                .show();
        intent = new Intent(this, Inventory.class);
        startActivity(intent);
    }
}