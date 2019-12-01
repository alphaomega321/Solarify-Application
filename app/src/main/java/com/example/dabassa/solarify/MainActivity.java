package com.example.dabassa.solarify;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.logging.Logger;

import static android.widget.Toast.LENGTH_SHORT;
import static java.sql.Types.FLOAT;
import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Bill
    EditText bill;

    //Units
    EditText units;

    // Location and resident type
    Spinner spinner;
    Spinner spinner2;

    //GPS Button
    ImageView gpsImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.symbol);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        /*ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);*/


        bill = (EditText) findViewById(R.id.cost);
        //String bill_value = bill.getText().toString().trim();
        units = (EditText) findViewById(R.id.editText2);
        //String units_value = (String) units.getText().toString();
        spinner = (Spinner) findViewById(R.id.location);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        //GPS Button
        gpsImage = (ImageView) findViewById(R.id.gpsImage);

        //bill.setGravity(0);

        //Prevent keyboard opening by default
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.consumer_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);

        spinner.setOnItemSelectedListener(this);

        //Calculate Button
        Button calculate = (Button) findViewById(R.id.button);

//        gpsImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast toast = (Toast) Toast.makeText(getApplicationContext(), "HI", Toast.LENGTH_SHORT);
//                //toast.show();
//                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                System.out.println(location);
//                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                List<Address> addresses = null;
//                try {
//                    geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
//                String city = addresses.get(0).getLocality();
//                String state = addresses.get(0).getAdminArea();
//                String country = addresses.get(0).getCountryName();
//                String postalCode = addresses.get(0).getPostalCode();
//                String knownName = addresses.get(0).getFeatureName();
//
//                Toast toast = Toast.makeText(getApplicationContext(), state+"HI", Toast.LENGTH_SHORT);
//                toast.show();
//
//            }
//        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Overview.class);
                //startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                String bill_value = bill.getText().toString().trim();


                String units_value = (String) units.getText().toString();


                if(bill_value.isEmpty() && units_value.isEmpty())
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Enter bill value or no of units";
                    int duration = LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else if(spinner2.getSelectedItem().toString().equals("Select your resident type"))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Enter what you want to solarify", Toast.LENGTH_SHORT);
                    toast.show();
                }

                else if(spinner.getSelectedItem().toString().equals("Location"))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Enter your location to proceed", Toast.LENGTH_SHORT);
                    toast.show();
                }

                else
                {
                    String resident = spinner2.getSelectedItem().toString();
                    Float units = 0f;
                    Float sys_capacity;
                    Float weighted_avg=0f;
                    Float n=0f;
                    if(!bill_value.isEmpty())
                    {
                        Float bill_value_int = Float.parseFloat(bill_value);



                        if(resident.equals("Home") || resident.equals("Apartment")) {
                            Float fixed_charges = 290f;
                            Float units_charges = bill_value_int - fixed_charges;
                            Float slab_size[] = new Float[5];
                            Float unitCost[] = new Float[5];
                            slab_size[0] = 30f; slab_size[1] = 70f; slab_size[2] = 100f; slab_size[3] = 100f; slab_size[4] = 100f;
                            unitCost[0] = 3.5f; unitCost[1] = 4.95f; unitCost[2] = 6.5f; unitCost[3] = 7.55f; unitCost[4] = 7.6f;

                            Float temp = units_charges;
                            Integer i = 0;
                            while (i < slab_size.length && temp - slab_size[i] * unitCost[i] >= 0f) {
                                units += slab_size[i];
                                temp -= slab_size[i] * unitCost[i];
                                weighted_avg += unitCost[i];
                                i++;
                                n = n+1;
                            }

                            if (i < 5) {
                                units += temp / unitCost[i];
                                temp = 0f;
                                weighted_avg += unitCost[i];
                                n = n+1;
                                temp = 0f;
                            }

                            if (temp > 0) {
                                units += temp / 7.65f;
                                weighted_avg += 7.65f;
                                n = n+1;
                            }

                        }

                        else if(resident.equals("Office"))
                        {
                            Float fixed_charges = 70f;
                            Float units_charges = bill_value_int - fixed_charges;
                            Float temp = units_charges;
                            if(temp <= 387.5f) {
                                units += temp / 7.75f;
                                weighted_avg += 7.75f;
                                n = n+1;
                            }

                            else
                            {
                                units += 50;
                                temp -= 387.5f;
                                units += temp/8.75f;
                                weighted_avg += 7.5f + 8.75f;
                                n = n+2;
                            }
                        }

                        else
                        {
                            //Toast toast = Toast.makeText(getApplicationContext(), "HI", LENGTH_SHORT);
                            //toast.show();
                            //Float temp = units_charges;
                            //units += temp/4.25f;
                            //weighted_avg += 4.25f;
                            //n = n+1;
                            Float fixed_charges = 65f;
                            Float units_charges = bill_value_int - fixed_charges;
                            Float temp = units_charges;
                            if(temp <= 1350f) {
                                units += temp / 6.75f;
                                weighted_avg += 6.75f;
                                n = n+1;
                            }

                            else
                            {
                                units += 200;
                                temp -= 1350f;
                                units += temp/8f;
                                weighted_avg += 6.75f + 8f;
                                n = n+2;
                            }


                        }

                        //Toast toast = Toast.makeText(getApplicationContext(), sys_capacity.toString(), LENGTH_SHORT);
                        //toast.show();

                    }

                    else
                    {
                        units = Float.parseFloat(units_value);
                        Float temp = units;
                        if(resident.equals("Home") || resident.equals("Apartment"))
                        {
                            Float slab_size[] = new Float[5];
                            Float unitCost[] = new Float[5];
                            slab_size[0] = 30f; slab_size[1] = 70f; slab_size[2] = 100f; slab_size[3] = 100f; slab_size[4] = 100f;
                            unitCost[0] = 3.5f; unitCost[1] = 4.95f; unitCost[2] = 6.5f; unitCost[3] = 7.55f; unitCost[4] = 7.6f;
                            Integer i=0;
                            while(i<slab_size.length && temp>0)
                            {
                                weighted_avg += unitCost[i];
                                temp -= slab_size[i];
                                n = n+1;
                                i++;
                            }
                        }

                        else if(resident.equals("Office"))
                        {
                            if(temp<=50)
                            {
                                weighted_avg += 7.75f;
                                n += 1;
                            }

                            else
                            {
                                weighted_avg += 7.75f + 8.75f;
                                n += 2;
                            }

                        }

                        else
                        {
                            if(temp<=200) {
                                weighted_avg += 6.75f;
                                n += 1;
                            }

                            else{
                                weighted_avg += 6.75f + 8f;
                                n += 2;
                            }
                        }
                    }

                    sys_capacity = units/120f;
                    Float power_range[] = new  Float[6];
                    Float power_cost[] = new Float[6];
                    power_range[0]=2f; power_range[1]=3f; power_range[2]=5f; power_range[3]=7f; power_range[4]=9f; power_range[5]=10f;
                    power_cost[0]=100000f; power_cost[1]=90000f; power_cost[2]=86000f; power_cost[3]=80000f; power_cost[4]=76000f; power_cost[5]=70000f;
                    Integer j=0;
                    while(j<power_range.length && power_range[j]<sys_capacity) {
                        j++;
                    }

                    String data[] = new String[11];
                    data[0] = String.format("%.1f", sys_capacity) + " kilowatt-power";
                    Float area = sys_capacity*100;
                    data[1] = String.format("%.1f", area) + " square feet";
                    Float Cost;
                    if(j==6)
                    {
                        Cost = sys_capacity*66000f;
                        data[2] = "₹ "+String.format("%.1f",Cost);
                        //intent.putExtra("arg", temp2.toString());
                    }

                    else {
                        Cost = sys_capacity*power_cost[j];
                        data[2] = "₹ "+String.format("%.1f",Cost);
                        //intent.putExtra("arg", temp2.toString());
                    }

                    Float monthly_units= 30f*4f*sys_capacity;
                    Float yearly_units = 1200f*sys_capacity;
                    Float weighted_mean = weighted_avg/n; //units->monthlyunits
                    Float avg_yearly_savings = monthly_units * weighted_mean * 12f;
                    //Float payback_period = Cost/avg_yearly_savings;
                    Float co2 = sys_capacity*1150f;
                    Float trees = co2/21f;

                    Float[] arr_savings = new Float[25];
                    arr_savings[0] = avg_yearly_savings;
                    Float MU = monthly_units;
                    Float WA = weighted_mean;
                    for(int i=1; i<25; i++)
                    {
                        MU = MU - 0.01f*MU;
                        WA = WA + 0.07f*WA;
                        arr_savings[i] = MU * 12 * WA;
                    }

                    Float sum = 0f;
                    Integer payback_period = 0;
                    while(payback_period<arr_savings.length && sum<Cost)
                    {
                        sum += arr_savings[payback_period];
                        payback_period++;
                    }

                    Double IRR=0.99;
                    Double ans=0.0;
                    while(ans<Cost)
                    {
                        ans = 0.0;
                        for(int i=0; i<arr_savings.length; i++)
                        {
                            ans += arr_savings[i]/Math.pow(1+IRR,i+1);
                        }
                        IRR -= 0.01;
                    }

                    IRR = IRR*100;
                    //Toast toast = Toast.makeText(getApplicationContext(),IRR.toString(),Toast.LENGTH_SHORT);
                    //toast.show();

                    data[3] = String.format("%.1f", monthly_units) + " units per year";
                    data[4] = String.format("%.1f",yearly_units) + " units per year";
                    data[5] = "₹ "+String.format("%.1f",avg_yearly_savings);
                    data[6] = payback_period.toString() + " years";
                    data[7] = String.format("%.1f",co2) + " Kilograms";
                    data[8] = String.format("%.0f",trees);
                    data[9] = String.format("%.1f",IRR) + " %";
                    data[10] = spinner.getSelectedItem().toString();

                    intent.putExtra("arg", data);
                    startActivity(intent);
                }

            }
        });

        //RESET Button
        Button reset = (Button) findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bill.setText("");
                units.setText("");
                spinner.setSelection(0);
                spinner2.setSelection(0);
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}




