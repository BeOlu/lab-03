package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //Declare the variables so that you will be able to reference them later.
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedPosition = -1; //use variable to track which item is selected
    boolean isEditing = false; //track if we are editing or adding


    @Override
    protected void onCreate(Bundle savedInstanceState) { //called when activity is first created
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        String [] cities = {"Edmonton", "Calgary", "Toronto", "Winnipeg", "London"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);


        //Find the Views
        Button addCityButton = findViewById(R.id.add_city_button);
        Button editCityButton = findViewById(R.id.edit_city_button);
        Button deleteCityButton = findViewById(R.id.delete_city_button);
        LinearLayout addCityLayout = findViewById(R.id.add_city_layout);
        EditText addCityField = findViewById(R.id.add_city_field);
        Button confirmButton = findViewById(R.id.confirm_button);

        //Track Selection in the List
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Toast.makeText(MainActivity.this, "Selected: " + dataList.get(position), Toast.LENGTH_SHORT).show();
        });

        //Set the Click Listener for the Delete Button
        deleteCityButton.setOnClickListener(v -> {
            if (selectedPosition != -1 && selectedPosition < dataList.size()) {
                dataList.remove(selectedPosition); //remove from the list
                cityAdapter.notifyDataSetChanged(); //refresh the ListView
                selectedPosition = -1; //reset the selection
            } else {
                Toast.makeText(MainActivity.this, "Please select a city to delete", Toast.LENGTH_SHORT).show();
            }
        });

        //"Add City" Shows the input box
        addCityButton.setOnClickListener(v -> {
            isEditing = false;
            addCityField.setText("");
            addCityLayout.setVisibility(View.VISIBLE);
        });

        //"Edit City" Shows the input box with selected city name
        editCityButton.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                isEditing = true;
                addCityField.setText(dataList.get(selectedPosition));
                addCityLayout.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(MainActivity.this, "Please select a city to edit", Toast.LENGTH_SHORT).show();
            }
        });


        //"Confirm" adds or updates the city in the list
        confirmButton.setOnClickListener(v -> {
            String cityName = addCityField.getText().toString();
            if (!cityName.isEmpty()) {
                if (isEditing && selectedPosition != -1) {
                    dataList.set(selectedPosition, cityName); //update existing
                    isEditing = false;
                } else {
                    dataList.add(cityName); //add new
                }
                cityAdapter.notifyDataSetChanged(); //Refresh ListView
                addCityField.setText(""); //Clear Input
                addCityLayout.setVisibility(View.GONE); //Hide input again
                selectedPosition = -1; //Reset selection
            }
        });


    }
}