package io.github.ilisevic.SOSbasic.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import io.github.ilisevic.SOSbasic.R;
import io.github.ilisevic.SOSbasic.service.SoundService;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSoundService extends Fragment {
    private Button btnSoundServiceStart, btnSoundServiceStop;
    ImageView imageView;

    public FragmentSoundService() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio, container, false);

        imageView = view.findViewById(R.id.imageview_audio);
        imageView.setBackgroundResource(R.drawable.audiow_off);
        btnSoundServiceStart = view.findViewById(R.id.btn_start_service);
        btnSoundServiceStop = view.findViewById(R.id.btn_stop_service);

        btnSoundServiceStart.setEnabled(true);

//Koordinisati dugmad - enabled disabled
        btnSoundServiceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), SoundService.class));
                btnSoundServiceStart.setEnabled(false);
                btnSoundServiceStop.setEnabled(true);
                imageView.setBackgroundResource(R.drawable.audiow);

            }
        });
        btnSoundServiceStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getContext(), SoundService.class));
                btnSoundServiceStart.setEnabled(true);
                imageView.setBackgroundResource(R.drawable.audiow_off);

            }
        });


        return view;
    }

    @Override
    public void onDetach() {
        getActivity().stopService(new Intent(getContext(), SoundService.class));

        btnSoundServiceStart.setEnabled(true);

        super.onDetach();
    }
}
