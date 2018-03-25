package com.example.saksheeagarwal.bookkeep1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Spinner;

import java.util.ArrayList;

public class SeeRec extends AppCompatActivity {


    Button See;
    Spinner branch, sem;
    ListView lv;
    ArrayList<String> booklist;

    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seerecom);

        branch = (Spinner)findViewById(R.id.spinner3);
        sem = (Spinner)findViewById(R.id.spinner);

        See = (Button) findViewById(R.id.seerec);
        lv = (ListView) findViewById(R.id.lv);






        See.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booklist = new ArrayList<String>();
                String bra = branch.getSelectedItem().toString();
                String se = sem.getSelectedItem().toString();
                int s= Integer.parseInt(se);
                Cursor c = database.rawQuery("SELECT * FROM books WHERE TRIM(branch) = '"+bra.trim()+"' AND semester = "+s, new String[]{});


                if (c.moveToFirst()) {
                    do {
                        String id = c.getString(0);
                        String bname = c.getString(1);
                        String Auth = c.getString(6);
                        booklist.add("ID :"+id+ "\nName :" + bname + "\nAuthor :" + Auth );
                    }
                    while (c.moveToNext());

                    c.close();
                    ArrayAdapter<String> arrayAdapter =
                            new ArrayAdapter<String>(SeeRec.this,android.R.layout.simple_list_item_1, booklist);
                    lv.setAdapter(arrayAdapter);
                }}
        });


    }
}
