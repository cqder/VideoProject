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
import android.widget.Button;
import android.widget.LinearLayout;

import com.cq.videoproject.R;
import com.cq.videoproject.activity.WatchActivity;
import com.cq.videoproject.constant.Constant;
import com.cq.videoproject.util.DBUtil;

/**
 * 视频的列表
 *
 * @author Administrator
 */
public class ListVideoFragment extends Fragment {

    private LinearLayout listView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        listView = view.findViewById(R.id.ll_list);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        show();
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
