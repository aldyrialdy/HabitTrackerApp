package makers.latihan.aldy.habittrackerapp;

import android.provider.BaseColumns;

/**
 * Created by aldyrialdy on 30/01/17.
 */
public class HabitContract {
    private HabitContract(){

    }

    public static final class HabitEntity implements BaseColumns{
        public final static String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_NAME = "name";
        public final static String COLUMN_START_DATE = "start_date";
//        public final static String COLUMN_END_DATE = "end_date";
        public final static String COLUMN_TIMES = "number_of_times";

    }

}
