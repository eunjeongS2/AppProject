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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.dataParse.Address;
import kr.ac.ajou.jinaeunjeongbus.dataParse.CoordinatesFinder;
import kr.ac.ajou.jinaeunjeongbus.dataParse.WalkRequiredTimeFinder;
import kr.ac.ajou.jinaeunjeongbus.database.DBHelper;
import kr.ac.ajou.jinaeunjeongbus.search.OnBusStopLoadListener;

import static kr.ac.ajou.jinaeunjeongbus.R.layout.support_simple_spinner_dropdown_item;

public class AddAlarmDialogFragment extends DialogFragment implements OnCoordinatesLoadListener, OnWalkRequiredTimeLoadListener, OnBusRequiredTimeLoadListener, OnBusStopLoadListener {

    public static final String TAG = "AddAlarmDialogFragment";

    private int getHour;
    private int getMinute;

    private EditText departurePlaceEditText;
    private EditText destinationPlaceEditText;
    private AutoCompleteTextView departureStopEditText;
    private AutoCompleteTextView destinationStopEditText;
    private EditText alarmHour;
    private EditText alarmMinute;
    private EditText alarmTermEditText;
    private AutoCompleteTextView busNameEditText;

    private TextView getDepartureButton;
    private TextView getDestinationButton;
    private TextView getDepartureStopButton;
    private TextView getDestinationStopButton;
    private TextView getBusButton;

    private DBHelper dbHelper;

    private OnAlarmListener onAlarmListener;
    private OnDialogResultListener onDialogResultListener;
    private String departureWalkRequiredTime;
    private String destinationWalkRequiredTime;

    private String busRequiredTime;
    private Address walkDepartureAddress;
    private Address walkDestinationStopAddress;

    private Address walkDepartureStopAddress;
    private Address walkDestinationAddress;

    private Alarm curAlarm;
    public void setOnAlarmListener(OnAlarmListener onAlarmListener) {
        this.onAlarmListener = onAlarmListener;
    }

    public void setAlarm(Alarm alarm) {
        this.curAlarm = alarm;
    }

    public void setDialogResult(OnDialogResultListener onDialogResultListener) {
        this.onDialogResultListener = onDialogResultListener;
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

        getDepartureButton = view.findViewById(R.id.search_departure_btn);
        getDestinationButton = view.findViewById(R.id.search_destination_btn);
        getDepartureStopButton = view.findViewById(R.id.search_departure_stop_btn);
        getDestinationStopButton = view.findViewById(R.id.search_destination_stop_btn);
        getBusButton = view.findViewById(R.id.search_bus_btn);

        alarmHour = view.findViewById(R.id.hour_editText);
        alarmMinute = view.findViewById(R.id.minute_editText);

        String stringId = null;

        if (curAlarm != null) {

            departurePlaceEditText.setText(curAlarm.getDeparturePlace());
            destinationPlaceEditText.setText(curAlarm.getDestinationPlace());
            departureStopEditText.setText(curAlarm.getDepartureStop());
            destinationStopEditText.setText(curAlarm.getDestinationStop());
            alarmTermEditText.setText(curAlarm.getAlarmTerm());
            busNameEditText.setText(curAlarm.getBusName());
            alarmHour.setText(curAlarm.getArriveTime().substring(0, 2));
            alarmMinute.setText(curAlarm.getArriveTime().substring(2, 4));

            stringId = curAlarm.getArriveTime();
        }

        String[] strings = {};

        getDestinationStopButton.setOnClickListener(v->{
            ArrayAdapter<String> destinationAdapter = new ArrayAdapter<>(
                    getActivity(),
                    support_simple_spinner_dropdown_item,
                    strings
            );
            destinationStopEditText.setAdapter(destinationAdapter);
            destinationStopEditText.showDropDown();
        });

        getDepartureStopButton.setOnClickListener(v ->{
            ArrayAdapter<String> departureAdapter = new ArrayAdapter<>(
                    getActivity(),
                    support_simple_spinner_dropdown_item,
                    strings
            );
            departureStopEditText.setAdapter(departureAdapter);
            departureStopEditText.showDropDown();
        });

        getBusButton.setOnClickListener(v ->{
            ArrayAdapter<String> busAdapter = new ArrayAdapter<>(
                    getActivity(),
                    support_simple_spinner_dropdown_item,
                    strings
            );
            busNameEditText.setAdapter(busAdapter);
            busNameEditText.showDropDown();
        });


        Calendar c = Calendar.getInstance();
        getHour = c.get(Calendar.HOUR_OF_DAY);

        walkDepartureAddress = new Address("수원시영통구망포동마젤란아파트 1101동 1004호", "37.241157", "127.062392");
        walkDestinationStopAddress = new Address("수원시영통구망포동늘푸른벽산아파트 116동 1004호", "37.239289", "127.059803");

        try {
            new CoordinatesFinder(this, "센트럴하이츠정류장").execute();
            new WalkRequiredTimeFinder(this, walkDepartureAddress, walkDestinationStopAddress).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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

        closeButton.setOnClickListener(v -> dismiss());
        String finalStringId = stringId;

        checkButton.setOnClickListener((View view1) -> {
            @SuppressLint("DefaultLocale") String time = String.format("%02d%02d",getHour,getMinute);
            if (curAlarm == null) {
                Alarm alarm = new Alarm(0, String.valueOf(departurePlaceEditText.getText()),
                        String.valueOf(departureStopEditText.getText()), "3", "4",
                        String.valueOf(destinationPlaceEditText.getText()), String.valueOf(destinationStopEditText.getText()),
                        String.valueOf(busNameEditText.getText()), "7", time, String.valueOf(alarmTermEditText.getText()));
                alarm.setOn(true);
                dbHelper.createAlarm(alarm);
                onAlarmListener.onAlarmDialogResult(alarm);
            } else {
                curAlarm = new Alarm(0, String.valueOf(departurePlaceEditText.getText()),
                        String.valueOf(departureStopEditText.getText()), "3", "4",
                        String.valueOf(destinationPlaceEditText.getText()), String.valueOf(destinationStopEditText.getText()),
                        String.valueOf(busNameEditText.getText()), "7", time, String.valueOf(alarmTermEditText.getText()));
                curAlarm.setOn(true);

                dbHelper.updateAlarm(finalStringId, curAlarm);
                onDialogResultListener.onConfirm();
            }

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

    @Override
    public void onWalkRequiredTimeLoad(String WalkDepartureAddressName, String requiredTime) {
        System.out.println("requiredTime ="+requiredTime);


        if(WalkDepartureAddressName.equals(walkDepartureAddress.getAddressName())){
            departureWalkRequiredTime = requiredTime;
        }else if (WalkDepartureAddressName.equals(walkDestinationStopAddress.getAddressName())){
            destinationWalkRequiredTime = requiredTime;
        }
    }

    @Override
    public void onCoordinatesLoad(Address address) {

        System.out.println("address ="+address.getAddressName()+address.getAddressLatitude()+address.getAddressLongitude());

        if(address.getAddressName().equals(String.valueOf(departurePlaceEditText.getText()))){
            walkDepartureAddress = address;
        }else if (address.getAddressName().equals(String.valueOf(departureStopEditText.getText()))){
            walkDestinationStopAddress = address;
        }else if(address.getAddressName().equals(String.valueOf(destinationPlaceEditText.getText()))){
            walkDestinationAddress = address;
        }else if(address.getAddressName().equals(String.valueOf(destinationStopEditText.getText()))){
            walkDestinationStopAddress = address;
        }

        if(walkDepartureAddress !=null && walkDestinationStopAddress !=null){
            try {
                new WalkRequiredTimeFinder(this, walkDepartureAddress, walkDestinationStopAddress).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if(walkDepartureStopAddress !=null && walkDestinationAddress !=null) {
            try {
                new WalkRequiredTimeFinder(this, walkDepartureStopAddress, walkDestinationAddress).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBusRequiredTimeLoad(String requiredTime) {
        busRequiredTime = requiredTime;
    }


    @Override
    public void onSearchComplete(List<BusStop> searchResult) {

    }

    @Override
    public void onBusStopCoordinatesLoad(List<Address> addressList) {

    }

}
