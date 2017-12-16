package kr.ac.ajou.jinaeunjeongbus.database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.Alarm;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BUS_ALARM_11_DB";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + Alarm.TABLE+ "("
                + Alarm.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Alarm.KEY_DEPARTURE_NAME + " TEXT, "
                + Alarm.KEY_DEPARTURE_STOP + " TEXT, "
                + Alarm.KEY_DEPARTURE_NO + " TEXT, "
                + Alarm.KEY_DEPARTURE_ID + " TEXT, "
                + Alarm.KEY_DESTINATION_NAME + " TEXT, "
                + Alarm.KEY_DESTINATION_STOP + " TEXT, "
                + Alarm.KEY_BUS_NAME + " TEXT, "
                + Alarm.KEY_BUS_ID + " TEXT, "
                + Alarm.KEY_DESTINATION_TIME + " TEXT, "
                + Alarm.KEY_ALARM_TERM + " TEXT, "
                + Alarm.KEY_ALARM_ISON + " BOOL" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        System.out.println("데이터베이스 생성");
    }

    public void createAlarm(Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Alarm.KEY_DEPARTURE_NAME, alarm.getDeparturePlace());
        values.put(Alarm.KEY_DEPARTURE_STOP, alarm.getDepartureStop());
        values.put(Alarm.KEY_DEPARTURE_NO, alarm.getDepartureNo());
        values.put(Alarm.KEY_DEPARTURE_ID, alarm.getDepartureId());
        values.put(Alarm.KEY_DESTINATION_NAME, alarm.getDestinationPlace());
        values.put(Alarm.KEY_DESTINATION_STOP, alarm.getDestinationStop());
        values.put(Alarm.KEY_BUS_NAME, alarm.getBusName());
        values.put(Alarm.KEY_BUS_ID, alarm.getBusId());
        values.put(Alarm.KEY_DESTINATION_TIME, alarm.getArriveTime());
        values.put(Alarm.KEY_ALARM_TERM, alarm.getAlarmTerm());
        values.put(Alarm.KEY_ALARM_ISON, alarm.getOn());

        db.insert(Alarm.TABLE, null, values);
        System.out.println("alarm 생성");
        db.close();

    }

    public Alarm findAlarmByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {Alarm.KEY_ID, Alarm.KEY_DEPARTURE_NAME,
                Alarm.KEY_DEPARTURE_STOP, Alarm.KEY_DEPARTURE_NO, Alarm.KEY_DEPARTURE_ID,
                Alarm.KEY_DESTINATION_NAME, Alarm.KEY_DESTINATION_STOP,
                Alarm.KEY_BUS_NAME, Alarm.KEY_BUS_ID,
                Alarm.KEY_DESTINATION_TIME, Alarm.KEY_ALARM_TERM, Alarm.KEY_ALARM_ISON
        };

        String selection = Alarm.KEY_ID + "=?";
        String[] selectId = {id.toString()};

        Cursor cursor = db.query(Alarm.TABLE, columns, selection, selectId, null, null, null);

        boolean found = cursor.moveToNext();

        if(!found) return null;

        String departurePlace = cursor.getString(1);
        String departureStop = cursor.getString(3);
        String departureNo = cursor.getString(4);
        String departureId = cursor.getString(5);
        String destinationPlace = cursor.getString(6);
        String destinationStop = cursor.getString(7);
        String busName = cursor.getString(8);
        String busId = cursor.getString(9);
        String arriveTime = cursor.getString(10);
        String alarmTerm = cursor.getString(11);
        Boolean alarmIsOn = cursor.getInt(12) > 0;

        cursor.close();

        Alarm alarm = new Alarm(0, departurePlace, departureStop, departureNo
                , departureId, destinationPlace, destinationStop, busName, busId,arriveTime, alarmTerm);
        alarm.setOn(alarmIsOn);

        return alarm;
    }

    public List<Alarm> findAll(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {Alarm.KEY_ID, Alarm.KEY_DEPARTURE_NAME,
                Alarm.KEY_DEPARTURE_STOP, Alarm.KEY_DEPARTURE_NO, Alarm.KEY_DEPARTURE_ID,
                Alarm.KEY_DESTINATION_NAME, Alarm.KEY_DESTINATION_STOP,
                Alarm.KEY_BUS_NAME, Alarm.KEY_BUS_ID,
                Alarm.KEY_DESTINATION_TIME, Alarm.KEY_ALARM_TERM, Alarm.KEY_ALARM_ISON
        };

        @SuppressLint("Recycle") Cursor cursor = db.query(Alarm.TABLE, columns, null, null, null, null, null);

        List<Alarm> alarms = new ArrayList<>();

        while(cursor.moveToNext()){
            String departurePlace = cursor.getString(1);
            String departureStop = cursor.getString(2);
            String departureNo = cursor.getString(3);
            String departureId = cursor.getString(4);
            String destinationPlace = cursor.getString(5);
            String destinationStop = cursor.getString(6);
            String busName = cursor.getString(7);
            String busId = cursor.getString(8);
            String arriveTime = cursor.getString(9);
            String alarmTerm = cursor.getString(10);
            Boolean alarmIsOn = false;

            Alarm alarm = new Alarm(0, departurePlace, departureStop, departureNo
            , departureId, destinationPlace, destinationStop, busName, busId,arriveTime, alarmTerm);

            alarm.setOn(alarmIsOn);

            alarms.add(alarm);
        }
        return alarms;
    }

    public void updateAlarm(String arriveID, Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        String UPDATE_TABLE = "UPDATE "
                + Alarm.TABLE+ " SET "
                + Alarm.KEY_DEPARTURE_NAME + " = '"+alarm.getDeparturePlace()+"' , "
                + Alarm.KEY_DEPARTURE_STOP + " = '"+alarm.getDepartureStop()+"' , "
                + Alarm.KEY_DEPARTURE_NO + " = '"+alarm.getDepartureNo()+"' , "
                + Alarm.KEY_DEPARTURE_ID + " = '"+alarm.getDepartureId()+"' , "
                + Alarm.KEY_DESTINATION_NAME + " = '"+alarm.getDestinationPlace()+"' , "
                + Alarm.KEY_DESTINATION_STOP + " = '"+alarm.getDestinationStop()+"' , "
                + Alarm.KEY_BUS_NAME + " = '"+alarm.getBusName()+"' , "
                + Alarm.KEY_BUS_ID + " = '"+alarm.getBusId()+"' , "
                + Alarm.KEY_DESTINATION_TIME + " = '"+alarm.getArriveTime()+"' , "
                + Alarm.KEY_ALARM_TERM + " = '"+alarm.getAlarmTerm()+"' , "
                + Alarm.KEY_ALARM_ISON + " = '" + alarm.getOn() + "'"
                + " WHERE " + Alarm.KEY_DESTINATION_TIME + " = '" + arriveID + "'";

        db.execSQL(UPDATE_TABLE);

        System.out.println("update data");
    }

    public void deleteAlarm(String arriveID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectId = {arriveID};

        db.delete(Alarm.TABLE, Alarm.KEY_DESTINATION_TIME + "=?", selectId);
        System.out.println("Delete Data");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
