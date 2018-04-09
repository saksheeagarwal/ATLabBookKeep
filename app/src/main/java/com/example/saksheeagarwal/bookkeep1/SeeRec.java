package com.example.saksheeagarwal.bookkeep1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
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
        database=openOrCreateDatabase("Bookkeep",MODE_PRIVATE,null);

        branch = (Spinner)findViewById(R.id.spinner3);
        sem = (Spinner)findViewById(R.id.spinner);

        See = (Button) findViewById(R.id.seerec);
        lv = (ListView) findViewById(R.id.lv);

        final ArrayList<String> branchh = new ArrayList<String>();
        branchh.add("it");
        branchh.add("CS");
      final  ArrayList<String> semm = new ArrayList<String>();
        semm.add("6");
        semm.add("7");
        ArrayAdapter<String> branchadapter = new ArrayAdapter<String>(SeeRec.this
                ,android.R.layout.simple_spinner_item,branchh);
        ArrayAdapter<String> semadapter = new ArrayAdapter<String>(SeeRec.this
                ,android.R.layout.simple_spinner_item,semm);
        branch.setAdapter(branchadapter);
        sem.setAdapter(semadapter);

        branch.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int i, long l) {
                // TODO Auto-generated method stub
//                Toast.makeText(SeeRec.this,"You Selected : "
//                        + branchh.get(i)+" Level ",Toast.LENGTH_SHORT).show();

            }
            // If no option selected
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        sem.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int i, long l) {
                // TODO Auto-generated method stub
//                Toast.makeText(SeeRec.this,"You Selected : "
//                        + semm.get(i)+" Level ",Toast.LENGTH_SHORT).show();

            }
            // If no option selected
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });







        See.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booklist = new ArrayList<String>();
                String bra = branch.getSelectedItem().toString();
                String se = sem.getSelectedItem().toString();

                System.out.println("SELECT * FROM books WHERE TRIM(branch) = '"+bra.trim()+"' AND TRIM(semester) = '"+se+"'");
                Cursor c = database.rawQuery("SELECT * FROM books WHERE TRIM(branch) = '"+bra.trim()+"' AND TRIM(semester) = '"+se+"'", new String[]{});


                if (c.moveToFirst()) {
                    do {
                        System.out.println("did reac");
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
