package kr.ac.ajou.jinaeunjeongbus.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.Alarm;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE ALARM_TABLE( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " DEPT TEXT, DEST TEXT, DEST_TIME TEXT, ALARM_TERM TEXT);");
        System.out.println("테이블 생성");

    }

    public void addAlarm(Alarm alarm, SQLiteDatabase database){

        StringBuffer queryText = new StringBuffer();
        queryText.append(" INSERT INTO ALARM_TABLE ( ");
        queryText.append(" DEPT, DEST, DEST_TIME, ALARM_TERM ) ");
        queryText.append(" VALUES ( ?, ?, ?, ? ); ");

        database.execSQL(queryText.toString(), new Object[]{
                alarm.getDeparture(),
                alarm.getDestination(),
                alarm.getDestinationTime(),
                alarm.getAlarmTerm()
        });

        System.out.println("알람 추가");

    }

    public List<Alarm> getAllAlarmsData(SQLiteDatabase database){

        database = getReadableDatabase();

        Cursor cursor = database.rawQuery(" SELECT * FROM ALARM_TABLE ", null);

        List<Alarm> alarms = new ArrayList<>();
        Alarm alarm = null;


        while ( cursor.moveToNext() ){
            alarm = new Alarm(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
            alarms.add(alarm);
            System.out.println("Test3");
        }

        System.out.println("Get All Data Complete");
        return alarms;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }
}
