package com.boilerplatecode.SOSbasic.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.boilerplatecode.SOSbasic.R;
import com.boilerplatecode.SOSbasic.service.FlashServiceThread2;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFlashService extends Fragment {
    View view;
    Button btnFlashServiceOn, btnFlashServiceOff;


    public FragmentFlashService() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__flash_service, container, false);

///TODO provjeriti ovo ispod, dodano 30.8.2019.


        ///kraj dodavanja 30.8.2019.
        btnFlashServiceOn = view.findViewById(R.id.btn_flash_start_service);
        btnFlashServiceOff = view.findViewById(R.id.btn_flash_stop_service);


        btnFlashServiceOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//TODO ovo izmjenjeno ispod 6.9.2019
                //    getActivity().startService(new Intent(getActivity(), FlashService.class));


                // FlashServiceThread2.flashRunning= true;
                getActivity().startService(new Intent(getActivity(), FlashServiceThread2.class));


            }
        });

        btnFlashServiceOff.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // FlashServiceThread2.flashRunning= false;
                //TODO ne radi gašenje servisa iz nekog razloga
                //  getActivity().stopService(new Intent(getContext(), FlashService.class));

                getActivity().stopService(new Intent(getContext(), FlashServiceThread2.class));

                // fst2.switchFlashligtOff();

                //  fst2.stopThread();

            }
        });


        return view;
    }


}
