package com.cq.videoproject.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showList();
    }

    /**
     * 显示视频列表
     */
    public void showList(){

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
                String videoPath = cursor.getString(3);
                mapData.put("name",videoName);
                mapData.put("path",videoPath);
                listDatas.add(mapData);
                Log.w("test", "i->" + i + " uri-> " + cursor.getString(1) + " name-> " + cursor.getString(2)+" path-> "+videoPath);
            }
        }
        String[] datasStr = { "name","path"} ;
        int[] items = {R.id.tv_item_name,R.id.tv_item_path};
        SimpleAdapter adapter = new SimpleAdapter(mContext,listDatas,R.layout.my_item,datasStr,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int i, long l) {

        AlertDialog.Builder builder =new AlertDialog.Builder(mContext);
        builder.setTitle("选择操作");
        builder.setSingleChoiceItems(new String[]{"打开", "删除"}, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    String uri = (String) ((TextView)view.findViewById(R.id.tv_item_path)).getText();
                    Intent intent = new Intent(getContext(), WatchActivity.class);
                    intent.putExtra("uri", uri);
                    startActivity(intent);
                }else{
                    //todo 删除

                }
            }
        });


    }
}
