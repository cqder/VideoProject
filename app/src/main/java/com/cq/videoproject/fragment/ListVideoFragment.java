package com.cq.videoproject.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cq.videoproject.R;
import com.cq.videoproject.activity.WatchActivity;
import com.cq.videoproject.constant.Constant;
import com.cq.videoproject.util.DBUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频的列表
 *
 * @author Administrator
 */
public class ListVideoFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        listView = (ListView) view.findViewById(R.id.ll_list);
        Log.w("test","onCreateView");
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.w("test","onActivityCreated");
        show_2();
    }

    /**
     * 显示视频列表
     */
    @SuppressLint("ResourceAsColor")
    private void show() {
        listView.removeAllViews();
        SharedPreferences preferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        String db = preferences.getString("table", Constant.TABLE);
        final Cursor cursor = DBUtil.query(mContext, db, null, null);
        int count = cursor.getCount();
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button[] buttons = new Button[count];
        Log.w("test", "count-> " + count);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);

                buttons[i] = new Button(mContext);
                buttons[i].setLayoutParams(params);
                // id = 0 uri = 1  name = 2
                buttons[i].setText(cursor.getString(2));
                buttons[i].setBackgroundColor(R.color.white);
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), WatchActivity.class);
                        intent.putExtra("path", cursor.getString(1));
                        startActivity(intent);
                    }
                });
                listView.addView(buttons[i]);
                Log.w("test", "i->" + i + " uri-> " + cursor.getString(1) + " name-> " + cursor.getString(2));
            }
        }
        Log.w("test", "data-> ");
    }


    public void show_2(){

        List<Map<String,String>> listDatas = new ArrayList<>();

        SharedPreferences preferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        String db = preferences.getString("table", Constant.TABLE);
        final Cursor cursor = DBUtil.query(mContext, db, null, null);
        int count = cursor.getCount();

        if (cursor.moveToFirst()){
            for (int i = 0 ; i < count ; i++){
                cursor.moveToPosition(i);
                Map<String,String> mapData =new HashMap<>();
                String videoName = cursor.getString(2);
                String videoUriStr = cursor.getString(1);
                mapData.put("name",videoName);
                mapData.put("uri",videoUriStr);
                listDatas.add(mapData);

                Log.w("test", "i->" + i + " uri-> " + cursor.getString(1) + " name-> " + cursor.getString(2));
            }
        }
        String[] datasStr = { "name","uri"} ;
        int[] items = {R.id.tv_item_name,R.id.tv_item_uri};
        SimpleAdapter adapter = new SimpleAdapter(mContext,listDatas,R.layout.my_item,datasStr,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        Log.w("test","onAttach");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        String uri = (String) ((TextView)view.findViewById(R.id.tv_item_uri)).getText();
        Intent intent = new Intent(getContext(), WatchActivity.class);
        intent.putExtra("uri", uri);
        startActivity(intent);
    }
}
