package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.Locale;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.alarm.Alarm;
import kr.ac.ajou.jinaeunjeongbus.alarm.OnAlarmListener;
import kr.ac.ajou.jinaeunjeongbus.database.DBHelper;

public class AddAlarmDialogFragment extends DialogFragment {

    public static final String TAG = "AddAlarmDialogFragment";

    private ImageView closeButton;
    private Button checkButton;

    private EditText departurePlaceEditText;
    private EditText destinationPlaceEditText;
    private EditText departureStopEditText;
    private EditText destinationStopEditText;
    private TimePicker destinationTimePicker;
    private EditText alarmTermEditText;
    private EditText busNameEditText;

    private DBHelper dbHelper;

    private OnAlarmListener onAlarmListener;

    public void setOnAlarmListener(OnAlarmListener onAlarmListener) {
        this.onAlarmListener = onAlarmListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_alarm_fragment, null);
        dbHelper = new DBHelper(getActivity());

        closeButton = view.findViewById(R.id.close_button);
        checkButton = view.findViewById(R.id.check_button);

        departurePlaceEditText = view.findViewById(R.id.departure_search_editText);
        destinationPlaceEditText = view.findViewById(R.id.destination_search_editText);
        departureStopEditText = view.findViewById(R.id.departure_stop_search_editText);
        destinationStopEditText = view.findViewById(R.id.destination_stop_search_editText);
        destinationTimePicker = view.findViewById(R.id.destination_timePicker);
        alarmTermEditText = view.findViewById(R.id.alarm_term_editText);
        busNameEditText = view.findViewById(R.id.bus_search_editText);

        String departureNo;
        String departureId;
        String busId;

        int cHour = 0;
        int cMin = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cHour = destinationTimePicker.getHour();
            cMin = destinationTimePicker.getMinute() + 1;
        } else {
            cHour = destinationTimePicker.getCurrentHour();
            cMin = destinationTimePicker.getCurrentMinute() + 1;
        }

        System.out.println(cHour + " " + cMin);

        String time = String.format("%02d%02d",cHour,cMin);
        closeButton.setOnClickListener(v -> getFragmentManager().popBackStack());
        checkButton.setOnClickListener(view1 -> {
            Alarm alarm = new Alarm(0,String.valueOf(departurePlaceEditText.getText()),
                    String.valueOf(departureStopEditText.getText()),"3","4",
                    String.valueOf(destinationPlaceEditText.getText()),String.valueOf(destinationStopEditText.getText()),
                    String.valueOf(busNameEditText.getText()),"7",time, String.valueOf(alarmTermEditText.getText()));
            alarm.setOn(true);
            dbHelper.createAlarm(alarm);
            onAlarmListener.onAlarmDialogResult(alarm);
            dismiss();
        });

        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                getFragmentManager().popBackStack();
                return true;
            }
            return false;
        });

        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}
