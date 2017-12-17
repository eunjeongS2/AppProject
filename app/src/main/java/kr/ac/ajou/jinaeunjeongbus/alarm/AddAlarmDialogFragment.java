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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.dataParse.Address;
import kr.ac.ajou.jinaeunjeongbus.dataParse.BusIdFinder;
import kr.ac.ajou.jinaeunjeongbus.dataParse.BusRequiredTimeFinder;
import kr.ac.ajou.jinaeunjeongbus.dataParse.BusStopFinder;
import kr.ac.ajou.jinaeunjeongbus.dataParse.CoordinatesFinder;
import kr.ac.ajou.jinaeunjeongbus.dataParse.WalkRequiredTimeFinder;
import kr.ac.ajou.jinaeunjeongbus.database.DBHelper;
import kr.ac.ajou.jinaeunjeongbus.search.OnBusLoadListener;
import kr.ac.ajou.jinaeunjeongbus.search.OnBusStopLoadListener;

import static kr.ac.ajou.jinaeunjeongbus.R.layout.support_simple_spinner_dropdown_item;

public class AddAlarmDialogFragment extends DialogFragment implements OnCoordinatesLoadListener, OnWalkRequiredTimeLoadListener, OnBusRequiredTimeLoadListener, OnBusStopLoadListener, OnBusLoadListener {

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

    private EditText departureRequiredTimeEditText;
    private EditText busRequiredTimeEditText;
    private EditText destinationRequiredTimeEditText;

    private DBHelper dbHelper;

    private OnAlarmListener onAlarmListener;
    private OnDialogResultListener onDialogResultListener;
    private String departureWalkRequiredTime;
    private String destinationWalkRequiredTime;
    private String busRequiredTime;

    private Address departureAddress;
    private Address destinationStopAddress;

    private Address departureStopAddress;
    private Address destinationAddress;

    private List<BusStop> departureBusStops;
    private List<BusStop> destinationBusStops;

    private List<Address> departureBusStopAddresses;
    private List<Address> destinationBusStopAddresses;

    private BusStop departureBusStop;
    private BusStop destinationBusStop;

    private String[] busStrings;
    private String[] departureBusStopStrings;
    private String[] destinationBusStopStrings;

    private List<Bus> busList;
    private Bus bus;


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

    private static String timestamp() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm", Locale.KOREA);
        return dateFormat.format(date);
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

        departureRequiredTimeEditText = view.findViewById(R.id.departure_required_time_textView);
        busRequiredTimeEditText = view.findViewById(R.id.stops_required_time_textView);
        destinationRequiredTimeEditText = view.findViewById(R.id.destination_required_time_textView);


        TextView getDepartureButton = view.findViewById(R.id.search_departure_btn);
        TextView getDestinationButton = view.findViewById(R.id.search_destination_btn);
        TextView getDepartureStopButton = view.findViewById(R.id.search_departure_stop_btn);
        TextView getDestinationStopButton = view.findViewById(R.id.search_destination_stop_btn);
        TextView getBusButton = view.findViewById(R.id.search_bus_btn);


        departureStopEditText.setText("신논현역");
        destinationStopEditText.setText("강남역");
        departurePlaceEditText.setText("서울특별시 강남구 역삼동");
        destinationPlaceEditText.setText("서울특별시 서초구 서초4동 1309-10");
        alarmHour = view.findViewById(R.id.hour_editText);
        alarmMinute = view.findViewById(R.id.minute_editText);

        String stringId = null;

        alarmHour = view.findViewById(R.id.hour_editText);
        Calendar c = Calendar.getInstance();
        getHour = c.get(Calendar.HOUR_OF_DAY);

        departureBusStops = new ArrayList<>();
        destinationBusStops = new ArrayList<>();

        departureBusStopAddresses = new ArrayList<>();
        destinationBusStopAddresses = new ArrayList<>();

        departureBusStop = new BusStop();
        departureBusStop.setBusStopId(null);
        destinationBusStop = new BusStop();
        destinationBusStop.setBusStopId(null);

        busList = new ArrayList<>();
        bus = new Bus();
        bus.setNumber(null);

        departureAddress = new Address();
        departureAddress.setAddressName(null);

        departureStopAddress = new Address();
        departureStopAddress.setAddressName(null);

        destinationAddress = new Address();
        destinationAddress.setAddressName(null);

        destinationStopAddress = new Address();
        destinationStopAddress.setAddressName(null);

        busStrings = new String[]{};
        departureBusStopStrings = new String[]{};
        destinationBusStopStrings = new String[]{};

        if (curAlarm != null) {

            departurePlaceEditText.setText(curAlarm.getDeparturePlaceName());
            departureAddress.setAddressName(curAlarm.getDeparturePlaceName());

            destinationPlaceEditText.setText(curAlarm.getDestinationPlaceName());
            destinationAddress.setAddressName(curAlarm.getDestinationPlaceName());

            departureStopEditText.setText(curAlarm.getDepartureStop());
            departureBusStop.setBusStopName(curAlarm.getDepartureStop());
            departureBusStop.setBusStopId(curAlarm.getDepartureStopId());
            departureBusStop.setDistinctNumber(curAlarm.getDepartureStopNo());

            destinationStopEditText.setText(curAlarm.getDestinationStop());
            destinationBusStop.setBusStopName(curAlarm.getDestinationStop());
            destinationBusStop.setBusStopId(curAlarm.getDepartureStopId());
            destinationBusStop.setDistinctNumber(curAlarm.getDestinationStopNo());

            alarmTermEditText.setText(curAlarm.getAlarmTerm());
            busNameEditText.setText(curAlarm.getBusName());
            bus.setNumber(curAlarm.getBusName());
            bus.setId(curAlarm.getBusId());
            alarmHour.setText(curAlarm.getArriveTime().substring(0, 2));
            alarmMinute.setText(curAlarm.getArriveTime().substring(2, 4));
            busRequiredTimeEditText.setText(curAlarm.getBusRequiredTime());
            busRequiredTime = curAlarm.getBusRequiredTime();
            departureRequiredTimeEditText.setText(curAlarm.getDepartureRequiredTime());
            departureWalkRequiredTime = curAlarm.getDepartureRequiredTime();
            destinationRequiredTimeEditText.setText(curAlarm.getDestinationRequiredTime());
            destinationWalkRequiredTime = curAlarm.getDestinationRequiredTime();

            stringId = curAlarm.getAlarmId();
        }

        getDepartureButton.setOnClickListener(v -> {
            try {
                new CoordinatesFinder(this, String.valueOf(departurePlaceEditText.getText())).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            computeDepartureRequiredTime();

            if(curAlarm!=null){
                curAlarm.setDeparturePlaceName(String.valueOf(departurePlaceEditText.getText()));
            }
        });

        getDepartureStopButton.setOnClickListener(v ->{
            try {
                new BusStopFinder(this, String.valueOf(departureStopEditText.getText())).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });

        departureStopEditText.setOnClickListener(v ->{
            ArrayAdapter<String> departureAdapter = new ArrayAdapter<>(
                    getActivity(),
                    support_simple_spinner_dropdown_item,
                    departureBusStopStrings
            );
            if(departureBusStopStrings.length != 0){
                departureStopEditText.setAdapter(departureAdapter);
                departureStopEditText.showDropDown();
            }else
                departureBusStopStrings = new String []{"검색결과가 없습니다"};
        });

        departureStopEditText.setOnItemClickListener((adapterView, view12, i, l) -> {
            if(departureBusStops.size()!=0){
                departureBusStop = departureBusStops.get(i);
                departureStopAddress = departureBusStopAddresses.get(i);
                System.out.println(departureStopAddress.getAddressLatitude());

                computeDepartureRequiredTime();
                computeBusRequiredTime();

                if(curAlarm!=null){
                    curAlarm.setDepartureStop(departureBusStop.getBusStopName());
                    curAlarm.setDepartureStopId(departureBusStop.getBusStopId());
                    curAlarm.setDepartureStopNo(departureBusStop.getDistinctNumber());
                }
            }

        });

        getDestinationButton.setOnClickListener(v -> {
            try {
                new CoordinatesFinder(this, String.valueOf(destinationPlaceEditText.getText())).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            computeDestinationRequiredTime();
            if(curAlarm!=null){
                curAlarm.setDestinationPlaceName(String.valueOf(destinationPlaceEditText.getText()));
            }

        });


        getDestinationStopButton.setOnClickListener(v->{
            try {
                new BusStopFinder(this, String.valueOf(destinationStopEditText.getText())).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        destinationStopEditText.setOnClickListener(v ->{
            ArrayAdapter<String> departureAdapter = new ArrayAdapter<>(
                    getActivity(),
                    support_simple_spinner_dropdown_item,
                    destinationBusStopStrings
            );
            if(destinationBusStopStrings.length != 0){
                destinationStopEditText.setAdapter(departureAdapter);
                destinationStopEditText.showDropDown();
            }else
                destinationBusStopStrings = new String[]{"검색결과가 없습니다"};
        });

        destinationStopEditText.setOnItemClickListener((adapterView, view12, i, l) -> {
            if(destinationBusStops.size()!=0){

                destinationBusStop = destinationBusStops.get(i);
                destinationStopAddress = destinationBusStopAddresses.get(i);
                System.out.println(destinationStopAddress.getAddressLatitude());

                computeDestinationRequiredTime();
                computeBusRequiredTime();

                if(curAlarm!=null){
                    curAlarm.setDestinationStop(destinationBusStop.getBusStopName());
                    curAlarm.setDestinationStopId(destinationBusStop.getBusStopId());
                    curAlarm.setDepartureStopNo(destinationBusStop.getDistinctNumber());
                }
            }

        });

        busNameEditText.setOnClickListener(v -> {
            ArrayAdapter<String> busAdapter = new ArrayAdapter<>(
                    getActivity(),
                    support_simple_spinner_dropdown_item,
                    busStrings
            );
            if(busStrings.length != 0){
                busNameEditText.setAdapter(busAdapter);
                busNameEditText.showDropDown();
            }else
                busStrings = new String[]{"검색결과가 없습니다"};

        });

        getHour = c.get(Calendar.HOUR_OF_DAY);

        busNameEditText.setOnItemClickListener((adapterView, view12, i, l) -> {
            if(busList.size()!=0){
                bus = busList.get(i);
                computeBusRequiredTime();

                if(curAlarm!=null){
                    curAlarm.setBusName(bus.getNumber());
                    curAlarm.setBusId(bus.getId());
                }
            }
        });

        getBusButton.setOnClickListener(v ->{
            try {
                new BusIdFinder(this, String.valueOf(busNameEditText.getText())).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("busclick");
        });

        alarmHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void afterTextChanged(Editable editable) {}
        });

        closeButton.setOnClickListener(v -> dismiss());
        String finalStringId = stringId;

        checkButton.setOnClickListener((View view1) -> {
            System.out.println(getHour + ":"+getMinute);

            @SuppressLint("DefaultLocale") String time = String.format("%02d%02d",getHour,getMinute);
            if(departureAddress.getAddressName() != null && departureBusStop.getBusStopId() != null && destinationAddress.getAddressName() != null && destinationBusStop.getBusStopId()!=null && bus.getNumber() !=null){
                if (curAlarm == null) {

                    Alarm alarm = new Alarm(timestamp(), departureAddress.getAddressName(),
                            departureBusStop.getBusStopName(), departureBusStop.getBusStopId(), departureBusStop.getDistinctNumber(),
                            destinationAddress.getAddressName(),
                            destinationBusStop.getBusStopName(),
                            bus.getNumber(), bus.getId(),
                            time, String.valueOf(alarmTermEditText.getText()),
                            String.valueOf(busRequiredTimeEditText.getText()), String.valueOf(departureRequiredTimeEditText.getText()), String.valueOf(destinationRequiredTimeEditText.getText()));
                    alarm.setOn(true);

                    dbHelper.createAlarm(alarm);
                    onAlarmListener.onAlarmDialogResult(alarm);
                } else {
                    curAlarm.setArriveTime(time);
                    curAlarm.setAlarmTerm(String.valueOf(alarmTermEditText.getText()));
                    curAlarm.setOn(true);
                    dbHelper.updateAlarm(finalStringId, curAlarm);
                    onDialogResultListener.onConfirm();

                }
                dismiss();
            }

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

    private void computeDepartureRequiredTime(){
        if(departureAddress.getAddressLatitude() !=null && departureStopAddress.getAddressLatitude() !=null){
            try {
                new WalkRequiredTimeFinder(this, departureAddress, departureStopAddress).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void computeDestinationRequiredTime(){
        if(destinationStopAddress.getAddressLatitude() !=null && destinationAddress.getAddressLatitude() !=null) {
            try {
                new WalkRequiredTimeFinder(this, destinationStopAddress, destinationAddress).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    private void computeBusRequiredTime(){
        if(bus.getNumber() != null && departureBusStop.getBusStopId() != null && destinationBusStop.getBusStopId()!= null) {
            try {
//                new BusRequiredTimeFinder(this, bus.getNumber(), departureBusStop.getBusStopId(), destinationBusStop.getBusStopId()).execute();
                new BusRequiredTimeFinder(this, bus.getId(), "228001174", "203000125").execute();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onWalkRequiredTimeLoad(String walkDepartureAddressName, String requiredTime) {
        System.out.println("requiredTime ="+requiredTime);

        int requiredTimeInteger = Integer.parseInt(requiredTime)/60+1;
        if(walkDepartureAddressName.equals(departureAddress.getAddressName())){
            departureWalkRequiredTime = requiredTime;
            departureRequiredTimeEditText.setText(requiredTimeInteger+" 분");

        }else if (walkDepartureAddressName.equals(destinationStopAddress.getAddressName())){
            destinationWalkRequiredTime = requiredTime;
            destinationRequiredTimeEditText.setText(requiredTimeInteger+" 분");
        }

    }

    @Override
    public void onCoordinatesLoad(Address address) {
        if(address.getAddressName()==null){
            return;
        }

        if(address.getAddressName().equals(String.valueOf(departurePlaceEditText.getText()))){
            departureAddress = address;
        }else if(address.getAddressName().equals(String.valueOf(destinationPlaceEditText.getText()))){
            destinationAddress = address;
        }

    }

    @Override
    public void onBusRequiredTimeLoad(String requiredTime) {
        busRequiredTime = requiredTime;
        busRequiredTimeEditText.setText(requiredTime+" 분");
    }


    @Override
    public void onBusStopSearchComplete(List<BusStop> searchResult) {
        if(searchResult.size()==0){
            return;
        }

        String strings[] = new String[searchResult.size()];
        System.out.println(searchResult.get(0).getBusStopName());

        for(int i=0; i<searchResult.size(); i++){
            strings[i] = searchResult.get(i).getBusStopName()+"("+searchResult.get(i).getBusStopId()+")";
        }

        if(searchResult.get(0).getBusStopName().contains(departureStopEditText.getText())){
            departureBusStopStrings = strings;
            departureBusStops = searchResult;
        }else if(searchResult.get(0).getBusStopName().contains(destinationStopEditText.getText())){
            destinationBusStopStrings = strings;
            destinationBusStops = searchResult;
        }

    }

    @Override
    public void onBusStopCoordinatesLoad(List<Address> addressList) {
        if(addressList.size()==0){
            return;
        }

        if(addressList.get(0).getAddressName().contains(String.valueOf(departureStopEditText.getText()))){
            departureBusStopAddresses = addressList;
        }else if(addressList.get(0).getAddressName().contains(String.valueOf(destinationStopEditText.getText()))){
            destinationBusStopAddresses = addressList;
        }

    }

    @Override
    public void onSearchComplete(List<Bus> searchResult) {
        if(searchResult.size()==0){
            return;
        }

        busList = searchResult;
        busStrings = new String[searchResult.size()];

        for(int i=0; i<searchResult.size(); i++){
            busStrings[i] = searchResult.get(i).getNumber();
        }
    }

}
