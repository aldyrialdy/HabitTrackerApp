package makers.latihan.aldy.habittrackerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aldyrialdy on 30/01/17.
 */
public class HabitDbHelper extends SQLiteOpenHelper{
    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    private static final String DB_NAME = "habits.db";

    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context,DB_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_HABITS = "CREATE TABLE " + HabitContract.HabitEntity.TABLE_NAME + " ("
                + HabitContract.HabitEntity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.HabitEntity.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitContract.HabitEntity.COLUMN_START_DATE + " TEXT, "
                + HabitContract.HabitEntity.COLUMN_TIMES + " INTEGER NOT NULL DEFAULT 0);";

        sqLiteDatabase.execSQL(SQL_HABITS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
