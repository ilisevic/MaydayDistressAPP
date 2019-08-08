package com.boilerplatecode.tablayoutbasic;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tablayout);
        appBarLayout= findViewById(R.id.appbar);
        viewPager=findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Dodavanje fragmenta
        adapter.addFragment(new FragmentA(), "Fragment GPS LOCATION");
        adapter.addFragment(new FragmentB(), "Fragment FLASH");
        adapter.addFragment(new FragmentC(), "Fragment AUDIO WARNING");
        //podešavanje adaptera
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);







        
    }
}
