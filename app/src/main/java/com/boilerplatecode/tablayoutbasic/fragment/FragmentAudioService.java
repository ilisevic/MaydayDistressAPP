package com.boilerplatecode.tablayoutbasic.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.boilerplatecode.tablayoutbasic.R;
import com.boilerplatecode.tablayoutbasic.service.SoundService;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAudioService extends Fragment {
    private Button btnStart, btnStop;

    public FragmentAudioService() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_audio, container, false);


        btnStart = view.findViewById(R.id.btn_start_service);
        btnStop = view.findViewById(R.id.btn_stop_service);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), SoundService.class));

            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getContext(), SoundService.class));
            }
        });


        return view;
    }


}
