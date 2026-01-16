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


//Button button = findViewById(R.id.add_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) { //called when activity is first created
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //activity_main.xml defines behaviour and logic for screen actions (i.e. something when button is clicked)
        setContentView(R.layout.activity_main); //function connects to layout file activity_main.xml

        //????like margins?
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //City ListView Widget ID referenced from layout file activity_main.xml
        cityList = findViewById(R.id.city_list);
        //define array
        String [] cities = {"Edmonton", "Calgary", "Toronto", "Winnipeg", "London"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        /*adapter takes in input and converts to another type of output
        this = current activity, content file data list is to be displayed and looks like content.xml */
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        //whatever is passed to adapter is connected to the list
        cityList.setAdapter(cityAdapter); //use what is in city adapter and display accordingly


        //Find the Views
        Button addCityButton = findViewById(R.id.add_city_button);
        LinearLayout addCityLayout = findViewById(R.id.add_city_layout);
        EditText addCityField = findViewById(R.id.add_city_field);
        Button confirmButton = findViewById(R.id.confirm_button);
        Button deleteCityButton = findViewById(R.id.delete_city_button); //?

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
            }
        });

        //"Add City" Shows the input box
        addCityButton.setOnClickListener(v -> {
            addCityLayout.setVisibility(View.VISIBLE);
        });


        //"Confirm" adds the city to the list
        confirmButton.setOnClickListener(v -> {
            String cityName = addCityField.getText().toString();
            if (!cityName.isEmpty()) {
                dataList.add(cityName); //add input city name to ArrayList
                cityAdapter.notifyDataSetChanged(); //Refresh ListView
                addCityField.setText(""); //Clear Input
                addCityLayout.setVisibility(View.GONE); //Hide input again
            }
        });


    }
}