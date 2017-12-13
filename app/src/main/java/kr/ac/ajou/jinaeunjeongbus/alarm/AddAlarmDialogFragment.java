package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.database.DBHelper;

public class AddAlarmDialogFragment extends DialogFragment {

    public static final String TAG = "AddAlarmDialogFragment";

    private int getHour;
    private int getMinute;

    private EditText departurePlaceEditText;
    private EditText destinationPlaceEditText;
    private EditText departureStopEditText;
    private EditText destinationStopEditText;
    private EditText alarmHour;
    private EditText alarmMinute;
    private EditText alarmTermEditText;
    private EditText busNameEditText;

    private TextView getDepartureBtn;
    private TextView getDestinationBtn;
    private TextView getDepartureStopBtn;
    private TextView getDestinationStopBtn;
    private TextView getBusBtn;

    private DBHelper dbHelper;

    private OnAlarmListener onAlarmListener;

    public void setOnAlarmListener(OnAlarmListener onAlarmListener) {
        this.onAlarmListener = onAlarmListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.add_alarm_fragment, null);
        dbHelper = new DBHelper(getActivity());

        ImageView closeButton = view.findViewById(R.id.close_button);
        Button checkButton = view.findViewById(R.id.check_button);

        departurePlaceEditText = view.findViewById(R.id.departure_search_editText);
        destinationPlaceEditText = view.findViewById(R.id.destination_search_editText);
        departureStopEditText = view.findViewById(R.id.departure_stop_search_editText);
        destinationStopEditText = view.findViewById(R.id.destination_stop_search_editText);
        alarmTermEditText = view.findViewById(R.id.alarm_term_editText);
        busNameEditText = view.findViewById(R.id.bus_search_editText);

        getDepartureBtn = view.findViewById(R.id.search_departure_btn);
        getDestinationBtn = view.findViewById(R.id.search_destination_btn);
        getDepartureStopBtn = view.findViewById(R.id.search_departure_stop_btn);
        getDestinationStopBtn = view.findViewById(R.id.search_destination_stop_btn);
        getBusBtn = view.findViewById(R.id.search_bus_btn);

        getBusBtn.setOnClickListener(v ->{
            //go to bus_search_fragment
        });

        alarmHour = view.findViewById(R.id.hour_editText);
        Calendar c = Calendar.getInstance();
        getHour = c.get(Calendar.HOUR_OF_DAY);

        alarmHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0)
                    getHour = Integer.parseInt(charSequence.toString());
                else
                    getHour = -1;

                if (getHour > 23) {
                    getHour = 23;
                    alarmHour.setText("" + getHour);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        alarmMinute = view.findViewById(R.id.minute_editText);
        getMinute = c.get(Calendar.MINUTE);

        alarmMinute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0)
                    getMinute = Integer.parseInt(charSequence.toString());
                else
                    getMinute = -1;

                if (getMinute > 59) {
                    getMinute = 59;
                    alarmMinute.setText("" + getMinute);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        String departureNo;
        String departureId;
        String busId;

        @SuppressLint("DefaultLocale") String time = String.format("%02d%02d",getHour,getMinute);

        closeButton.setOnClickListener(v -> getFragmentManager().popBackStack());
        checkButton.setOnClickListener((View view1) -> {
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
