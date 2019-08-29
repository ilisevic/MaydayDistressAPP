package com.boilerplatecode.SOSbasic.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.boilerplatecode.SOSbasic.R;

public class FragmentSound extends Fragment {
View view;
Button btn;
TextView tv;

public MediaPlayer mpSoundSos;
    public FragmentSound()
    {



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.c_fragment, container,false);
        btn = view.findViewById(R.id.button);
        tv= view.findViewById(R.id.textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        tv.setText("tekst okinut iz dugmeta");
        Toast.makeText(getActivity(), "okinuto iz dugmeta", Toast.LENGTH_LONG).show();

                mpSoundSos = MediaPlayer.create(getContext(), R.raw.sosmorsecode1);
                mpSoundSos.start();

            }
        });


        return view;
    }
}
