package com.anioncode.spojrzyj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodzinka on 2019-01-17.
 */


public class Addnew extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

///Zmienne
    private Double Leweoko= 0.0;
    private Double Praweoko= 0.0;
    private String Typ="";
    String Data="";


    int day=0 ;
    int month=0 ;
    int year =0;
///ELEMENTY LAYOUTU
    EditText editText1;
    EditText editText2;
    DatePicker datePicker;
    FloatingActionButton floatingActionButton;
    Spinner spinner;
///KLASY
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);

        final SharedPreferences sharedPref = getPreferences(this.MODE_PRIVATE);
        spinner(sharedPref);
        datePicker= findViewById(R.id.datePicker);

        editText1=findViewById(R.id.okol);
        editText2=findViewById(R.id.okop);


        String txtokolewe = sharedPref.getString("okolewe", "");
        String txtokoprawe = sharedPref.getString("okoprawe", "");


        editText1.setText(txtokolewe);
        editText2.setText(txtokoprawe);

       // Praweoko= Double.parseDouble(String.valueOf(editText2.getText()));
      //  Praweoko= Double.valueOf(String.valueOf(editText2.getText()));

        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("okolewe", String.valueOf(editText1.getText()));
                editor.commit();

                SharedPreferences.Editor editorx = sharedPref.edit();
                editorx.putString("okoprawe", String.valueOf(editText2.getText()));
                editorx.commit();

                SharedPreferences.Editor editorxx = sharedPref.edit();
                editorxx.putString("typ", Typ);
                editorxx.commit();


                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth() + 1;
                year = datePicker.getYear();

                Data=day+"."+month+"."+year;

                if(editText1.getText().length() > 0)
                {
                    Leweoko= Double.parseDouble(editText1.getText().toString());
                    Praweoko= Double.parseDouble(editText2.getText().toString());
                }

                AddData(Leweoko,Praweoko,Typ,Data);
                Intent intent = new Intent(Addnew.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void AddData(Double one,Double two, String three,String four) {
        boolean insertData = false;
        mDatabaseHelper = new DatabaseHelper(this);
             insertData = mDatabaseHelper.addData(one,two,three,four);

            if (insertData) {
                toastMessage("Data Successfully Inserted!");
            } else {
                toastMessage("Something went wrong");
            }

     

      
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Addnew.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void spinner( SharedPreferences sharedPreferences){

        spinner=findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener( Addnew.this);

        List<String> categories = new ArrayList<String>();
//        categories.add("Jednodniowe");
//        categories.add("Dwutygodniowe");
//        categories.add("Miesięczne");
//        categories.add("Kwartalne");
//        categories.add("Roczne");

        categories.add(getString(R.string.jednodniowe));
        categories.add(getString(R.string.dwutygodniowe));
        categories.add(getString(R.string.miesieczne));
        categories.add(getString(R.string.kwartalne));
        categories.add(getString(R.string.roczne));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        String compareValue = sharedPreferences.getString("typ", "");
       // String compareValue = String.valueOf(trees.getWysokosc());

        if (compareValue != null) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            spinner.setSelection(spinnerPosition);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        Typ=adapterView.getItemAtPosition(i).toString();




      //  Toast.makeText(getApplicationContext(),"Wybrałeś "+ Typ+" "+Leweoko+" "+Praweoko,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
