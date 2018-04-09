package com.example.saksheeagarwal.bookkeep1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfilePage extends AppCompatActivity {



    SQLiteDatabase database;
    String naam;
    ArrayList<String> booklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        ListView listv = findViewById(R.id.lvv);
        TextView name = findViewById(R.id.textView);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            naam = extras.getString("name").toString();


        }

        name.setText(naam);
        try {
            database = openOrCreateDatabase("Bookkeep", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS Dues (user VARCHAR(50) ,bookname VARCHAR(100) , dueDate VARCHAR(50));");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        try {
            database.execSQL("INSERT INTO Dues VALUES('f','pujari','20-4-2015')");
            database.execSQL("INSERT INTO Dues VALUES('f','kindberg','25-4-2015')");
        } catch (Exception e) {

            // if (e.toString().contains("code 1555"))
            //Toast.makeText(getApplicationContext(), "Not inserted", Toast.LENGTH_SHORT).show();
        }

        booklist = new ArrayList<String>();
        Cursor c = database.rawQuery("SELECT * FROM Dues WHERE user='f'", null);
        if (c.moveToFirst()) {
            do {
                String bookname = c.getString(0);
                String date = c.getString(1);

                booklist.add("Book name: : "+bookname+ "\nDue date : " + date );
            }
            while (c.moveToNext());

            c.close();

            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(ProfilePage.this,android.R.layout.simple_list_item_1, booklist);
            listv.setAdapter(arrayAdapter);



        }

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                cal.set(Calendar.DAY_OF_MONTH, 15);
                cal.set(Calendar.MONTH, 4);
                cal.set(Calendar.YEAR, 2018);
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", false);
                intent.putExtra("rrule", "FREQ=DAILY");
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", "Return Book to the library");
                startActivity(intent);
            }
        });





    }
}
