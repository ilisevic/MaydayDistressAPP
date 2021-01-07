package io.github.ilisevic.SOSbasic.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import io.github.ilisevic.SOSbasic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWhistle extends Fragment {
    MediaPlayer mediaPlayer;
    ImageView imageView;
    ToggleButton tb;
    //Boolean bool= false;

    public FragmentWhistle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.whistle);
        mediaPlayer.setLooping(true);
        View view = inflater.inflate(R.layout.fragment_whistle, container, false);
        imageView = view.findViewById(R.id.imageview_whistle);
        imageView.setBackgroundResource(R.drawable.whistlew_off);
        tb = view.findViewById(R.id.btn_whistler);
        tb.setText("START WHISTLE");
        tb.setTextOn("STOP WHISTLE");
        tb.setTextOff("START WHISTLE");

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO logika za paljenje i gašenje

                whistler(isChecked);


            }
        });


        return view;
    }


    void whistler(Boolean bul) {


        if (bul) {

            //  mediaPlayer.setLooping(true);
            mediaPlayer.start();

            imageView.setBackgroundResource(R.drawable.whistlew);
        } else {
            //  mediaPlayer.setLooping(false);
            mediaPlayer.pause();
            imageView.setBackgroundResource(R.drawable.whistlew_off);
//            mediaPlayer.release();
//            mediaPlayer=null;
        }

        return;

    }
}
