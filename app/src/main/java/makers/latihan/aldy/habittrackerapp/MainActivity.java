package makers.latihan.aldy.habittrackerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbHelper = new HabitDbHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_insert) {
            insertHabit();
            displayDatabaseInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertHabit() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ArrayList<String> habits = new ArrayList<String>();
        ArrayList<String> startdate = new ArrayList<String>();
        ArrayList<Integer> times = new ArrayList<Integer>();

        habits.add("Drink Water");
        habits.add("Walk My Dog");

        startdate.add("2017-01-30");
        startdate.add("2017-01-30");

        times.add(2);
        times.add(1);

        for(int i=0;i<2;i++){
            ContentValues cv = new ContentValues();
            cv.put(HabitContract.HabitEntity.COLUMN_HABIT_NAME, habits.get(i));
            cv.put(HabitContract.HabitEntity.COLUMN_START_DATE, startdate.get(i));
            cv.put(HabitContract.HabitEntity.COLUMN_TIMES, times.add(i));
            long newRowId = db.insert(HabitContract.HabitEntity.TABLE_NAME, null, cv);
        }

    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitContract.HabitEntity._ID,
                HabitContract.HabitEntity.COLUMN_HABIT_NAME,
                HabitContract.HabitEntity.COLUMN_START_DATE,
                HabitContract.HabitEntity.COLUMN_TIMES};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                HabitContract.HabitEntity.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
        TextView displayView = (TextView) findViewById(R.id.textTampil);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitContract.HabitEntity._ID + " - " +
                    HabitContract.HabitEntity.COLUMN_HABIT_NAME + " - " +
                    HabitContract.HabitEntity.COLUMN_START_DATE + " - " +
                    HabitContract.HabitEntity.COLUMN_TIMES + " - " + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntity._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntity.COLUMN_HABIT_NAME);
            int startDateColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntity.COLUMN_START_DATE);
            int timesColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntity.COLUMN_TIMES);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDate = cursor.getString(startDateColumnIndex);
                int currentTimes = cursor.getInt(timesColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentDate + " - " +
                        currentTimes));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
}
