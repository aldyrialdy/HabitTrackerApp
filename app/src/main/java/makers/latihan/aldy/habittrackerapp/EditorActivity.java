package makers.latihan.aldy.habittrackerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText, mTimesEditText;
    private TextView mStartDateEditText;
    private Button btnSave, btnCancel;
    private HabitDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNameEditText = (EditText) findViewById(R.id.editHabit);
        mStartDateEditText = (TextView) findViewById(R.id.editDate);
        mTimesEditText = (TextView) findViewById(R.id.editTimes);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(EditorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveData() {
        String nameHabit = mNameEditText.getText().toString().trim();
        String startDate = mStartDateEditText.getText().toString().trim();
        int times = Integer.parseInt(mTimesEditText.getText().toString().trim());

        mDbHelper = new HabitDbHelper(this);

        db = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HabitContract.HabitEntity.COLUMN_HABIT_NAME, nameHabit);
        cv.put(HabitContract.HabitEntity.COLUMN_START_DATE, startDate);
        cv.put(HabitContract.HabitEntity.COLUMN_TIMES, times);

        long newRowId = db.insert(HabitContract.HabitEntity.TABLE_NAME, null, cv);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving habits", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

    }
}
