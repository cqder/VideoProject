package com.cq.videoproject;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private Fragment listVideoFragment, addVideoFragment;
    private RadioButton rb_main_list, rb_main_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    void initView() {

        rb_main_list = findViewById(R.id.rb_main_list);
        rb_main_add = findViewById(R.id.rb_mian_add);

        RadioGroup group = findViewById(R.id.rg_main);
        group.setOnCheckedChangeListener(new RgListener());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        listVideoFragment = new ListVideoFragment();
        transaction.add(R.id.fl_main, listVideoFragment);
        transaction.commit();
    }


    private class RgListener implements RadioGroup.OnCheckedChangeListener {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (i) {
                default:
                case R.id.rb_main_list:
                    if (listVideoFragment == null) {
                        listVideoFragment = new ListVideoFragment();
                    }
                    transaction.replace(R.id.fl_main, listVideoFragment);
                    break;
                case R.id.rb_mian_add:
                    if (addVideoFragment == null) {
                        addVideoFragment = new AddVideoFragment();
                    }
                    transaction.replace(R.id.fl_main, addVideoFragment);
                    break;
            }
            transaction.commit();
        }
    }


    public void gotoList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_main, listVideoFragment);
        transaction.commit();
    }
}
