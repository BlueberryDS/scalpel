package com.uwaffle.scalpel;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.otto.Subscribe;
import com.uwaffle.scalpel.dialog.DatePickerFragment;
import com.uwaffle.scalpel.dialog.DatePickerFragment.DateRecieved;
import com.uwaffle.scalpel.dialog.TimePickerFragment;
import com.uwaffle.scalpel.dialog.TimePickerFragment.TimeRecieved;

import static com.uwaffle.scalpel.MainActivity.getBus;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment implements OnClickListener {
    public static int PLACE_PICKER_REQUEST = 1;

    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment create.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance() {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create, container, false);

        v.findViewById(R.id.datepicker).setOnClickListener(this);
        v.findViewById(R.id.timepicker).setOnClickListener(this);
        v.findViewById(R.id.pickplace).setOnClickListener(this);

        getBus().register(this);

        return v;
    }

    @Subscribe public void processDate(final DateRecieved dateRecieved) {
        ((TextView) getView().findViewById(R.id.datepicker)).setText(dateRecieved.getDateString());
    }

    @Subscribe public void processTime(final TimeRecieved timeRecieved) {
        ((TextView) getView().findViewById(R.id.timepicker)).setText(timeRecieved.getTimeString());
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = DatePickerFragment.createInstance();
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = TimePickerFragment.createInstance();
        newFragment.show(getActivity().getFragmentManager(), "timePicker");
    }

    public void showPlacePickerDialog(View v) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datepicker:
                showDatePickerDialog(v);
                break;
            case R.id.timepicker:
                showTimePickerDialog(v);
                break;
            case R.id.pickplace:
                showPlacePickerDialog(v);
                break;
        }
    }
}
