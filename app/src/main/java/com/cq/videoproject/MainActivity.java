package com.cq.videoproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cq.videoproject.fragment.AddVideoFragment;
import com.cq.videoproject.fragment.ListVideoFragment;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;

/**
 * main 函数
 *
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {

    private Fragment listVideoFragment, addVideoFragment;
    private RadioButton rb_main_list, rb_main_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        initView();

    }

    /**
     * 初始化控件监听,以及布局.
     */
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

    /**
     * Radio group 点击事件
     */
    private class RgListener implements RadioGroup.OnCheckedChangeListener {

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

    /**
     * 跳转到 ListVideofragment
     */
    public void gotoListVideo() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (listVideoFragment != null){
            try{
                listVideoFragment.onDestroy();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        transaction.replace(R.id.fl_main, listVideoFragment);
        transaction.commit();
    }

    private void getPermission(){
        PermissionGen.needPermission(this, 100,
                new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                     int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){
        Toast.makeText(this, "没有权限!", Toast.LENGTH_SHORT).show();
    }
}
