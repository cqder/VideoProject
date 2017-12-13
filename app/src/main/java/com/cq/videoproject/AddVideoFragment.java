package com.cq.videoproject;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddVideoFragment extends Fragment {

    private Button buttonAddView;

    public AddVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_add, container, false);
        buttonAddView = view.findViewById(R.id.bt_add_add);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOnclickLis();
    }

    void initOnclickLis() {
        buttonAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVideo();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Uri uri = data.getData();
                Log.w("test", "uri-> "+uri.toString()+" request code -> 0" + getPathFromUri(uri));
                String path = getPathFromUri(uri);

                Intent intent =new Intent(getContext(),WatchActivity.class);
                intent.putExtra("path",uri.toString());
                startActivity(intent);
            }
        }
    }

    String getFileName(String path) {

        String reg = "^(\\w+) \\ (\\w+.mp4)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(path);
        return matcher.group(2);
    }

    private void addVideo() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI), 0);
    }


    private String getPathFromUri(Uri uri) {
        CursorLoader cursorLoader = new CursorLoader(getContext(), uri, null, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int actualImageColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        String path = "";
        if (cursor.moveToFirst()) {
            path = cursor.getString(actualImageColumnIndex);
        }
        cursor.close();
        return path;
    }

}
