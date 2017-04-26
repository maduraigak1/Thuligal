package com.iyaltamizh.thuligal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity implements
        AdapterView.OnItemSelectedListener {

    Integer[] Tower_no = { 1,2,3,4,5,6,7,8,9,10, };
    Integer[] Door_no = { 100,101,102,103,104,105,106,107,108,109,110, };
    Integer[] Can_Count = { 1,2,3,4,5,6,7,8,9,10,  };
    Button book_now =(Button)findViewById(R.id.book_now);
    //Button Book_now= (Button) findViewById(R.id.book_now);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner tower_no = (Spinner) findViewById(R.id.tower_no);
        Spinner door_no = (Spinner) findViewById(R.id.door_no);
        Spinner can_count = (Spinner) findViewById(R.id.can_count);
        book_now = (Button)findViewById(R.id.book_now);
        tower_no.setOnItemSelectedListener(this);
        door_no.setOnItemSelectedListener(this);
        can_count.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Tower_no);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        tower_no.setAdapter(aa);
        ArrayAdapter aa2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Door_no);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        door_no.setAdapter(aa2);
        ArrayAdapter aa3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Can_Count);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        can_count.setAdapter(aa3);

        ArrayAdapter adapter = new ArrayAdapter<Integer>(this, R.layout.bookhistory, R.id.tower_no,Tower_no);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter<Integer>(this, R.layout.bookhistory, R.id.door_no,Door_no);


        listView.setAdapter(adapter1);
        ArrayAdapter adapter2 = new ArrayAdapter<Integer>(this, R.layout.bookhistory, R.id.can_count,Can_Count);


        //listView.setAdapter(adapter2);
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(),Can_Count[position] ,Toast.LENGTH_LONG).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
