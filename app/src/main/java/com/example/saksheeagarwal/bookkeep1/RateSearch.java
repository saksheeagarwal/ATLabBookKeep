package com.example.saksheeagarwal.bookkeep1;

/**
 * Created by saksheeagarwal on 3/23/18.
 */

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class RateSearch extends AppCompatActivity {

    EditText name;

    Button viewButton;

    SQLiteDatabase database;
    ListView lv;
    ArrayList<String> booklist;
    //ArrayAdapter<String> arrayAdapter;

    boolean listOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratesearch);

   final Context context = this;
        name=(EditText)findViewById(R.id.name2);

        viewButton=(Button)findViewById(R.id.submit2);

        lv = (ListView) findViewById(R.id.lv);
        //allStudents=new ArrayList<>();


        try {
            database=openOrCreateDatabase("Bookkeep",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS books (id VARCHAR(100) PRIMARY KEY, bookName varchar(20), branch varchar(20),semester integer, subject varchar(20),rate integer,Author varchar(20));");
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        try {
            database.execSQL("INSERT INTO books VALUES('itc602','Voice over IP Fundamentals','it',6,'cnw',4,'M.Morris Mano')");
            database.execSQL("INSERT INTO books VALUES('itc603','Data Warehousing and Data Mining','it',6,'dwdm',3,'Arun Pujari')");
            database.execSQL("INSERT INTO books VALUES('ict443','Unified Modeling Language User Guide by Grady Booch','it',4,'SE',3,'Grady Booch')");
            Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            if (e.toString().contains("code 1555"))
                Toast.makeText(getApplicationContext(), "Not inserted", Toast.LENGTH_SHORT).show();
        }

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                booklist = new ArrayList<String>();
                String Author=name.getText().toString();

                Cursor c = database.rawQuery("SELECT * FROM books WHERE TRIM(Author) = '"+Author.trim()+"' OR TRIM(bookName) = '"+Author.trim()+"'", new String[]{});
                System.out.println("SELECT * FROM books WHERE TRIM(Author) = '"+Author.trim()+"' OR TRIM(bookName) = '"+Author.trim()+"'");
                if (c.moveToFirst()) {
                    do {
                        String id = c.getString(0);
                        String bname = c.getString(1);
                        String Auth = c.getString(6);
                        booklist.add("ID :"+id+ "\nName :" + bname + "\nAuthor :" + Auth );
                    }
                    while (c.moveToNext());

                    c.close();

                    // This is the array adapter, it takes the context of the activity as a
                    // first parameter, the type of list view as a second parameter and your
                    // array as a third parameter.
                    ArrayAdapter<String> arrayAdapter =
                            new ArrayAdapter<String>(RateSearch.this,android.R.layout.simple_list_item_1, booklist);
                    lv.setAdapter(arrayAdapter);



                }}
        });
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // Item position is in the variable position.

//               RateSearch selItem = (RateSearch) lv.getSelectedItem();
//                String value= selItem.toString();

                //Toast.makeText(getApplicationContext(),booklist.get(position).toString(),Toast.LENGTH_LONG).show();

               final Dialog rankDialog = new Dialog(RateSearch.this, R.style.FullHeightDialog);
                rankDialog.setContentView(R.layout.rank_dialog);
                rankDialog.setCancelable(true);
                final RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                ratingBar.setRating(5);

                TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                text.setText("Enter Rating");

                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float rating = ratingBar.getRating();
                        Toasty.success(getApplicationContext(), "Successfully Rated!", Toast.LENGTH_SHORT, true).show();
                        rankDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rankDialog.show();

            }
        });




    }
}

