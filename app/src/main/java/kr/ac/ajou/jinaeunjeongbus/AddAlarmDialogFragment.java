package kr.ac.ajou.jinaeunjeongbus;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddAlarmDialogFragment extends DialogFragment {

    public static final String TAG = "AddAlarmDialogFragment";

    private ImageView closeButton;
    private Button checkButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_alarm_fragment, null);

        closeButton = view.findViewById(R.id.close_button);
        checkButton = view.findViewById(R.id.check_button);

        closeButton.setOnClickListener(v -> getFragmentManager().popBackStack());
//        checkButton.setOnClickListener(v -> getFragmentManager().popBackStack());
        checkButton.setOnClickListener(view1 -> {
            System.out.println("ffsssss");
            getFragmentManager().popBackStack();
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
