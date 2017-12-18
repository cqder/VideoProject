package com.cq.videoproject.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.cq.videoproject.R;
import com.cq.videoproject.activity.WatchActivity;
import com.cq.videoproject.constant.Constant;
import com.cq.videoproject.util.DBUtil;

/**
 * 添加视频
 *
 * @author Administrator
 */
public class AddVideoFragment extends Fragment {

    private Button buttonAddView, buttonChangeList, buttonChangePassword;
    private Context mContext;
    private EditText editTextPassword;
    private String password;
    private SharedPreferences preferences;
    private boolean psdflag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_add, container, false);
        buttonAddView = (Button) view.findViewById(R.id.bt_add_add);
        buttonChangeList = (Button) view.findViewById(R.id.bt_add_change);
        buttonChangePassword = (Button) view.findViewById(R.id.bt_add_change_password);
        editTextPassword = (EditText) view.findViewById(R.id.et_add_psd);

        preferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);

        buttonChangePassword.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initOnclickLis();
    }

    /**
     * 初始化监听
     */
    private void initOnclickLis() {
        buttonAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVideo();
            }
        });
        buttonChangeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeList();
            }
        });
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = preferences.getString(Constant.PASSWORD, "123456");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String etPsd = String.valueOf(editTextPassword.getText());
                if (password.equals(etPsd)) {
                    //密码正确
                    String nowTable = preferences.getString("table", Constant.TABLE);
                    SharedPreferences.Editor editor = preferences.edit();
                    if (Constant.TABLE_OWN.equals(nowTable)) {
                        editor.putString("table", Constant.TABLE);
                        editor.apply();
                    } else {
                        editor.putString("table", Constant.TABLE_OWN);
                        editor.apply();
                    }
                    editTextPassword.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
                    editTextPassword.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 换密码
     */
    private void changePassword() {
        final EditText editText1, editText2;

        password = preferences.getString(Constant.PASSWORD, "123456");
        psdflag = false;

        Log.w("test", "changePassword password->" + password);

        editText1 = new EditText(mContext);
        editText2 = new EditText(mContext);
        editText1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        editText2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        TableLayout tableLayout = new TableLayout(mContext);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String etText = String.valueOf(editText1.getText());
                if (password.equals(etText)) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(Constant.PASSWORD, etText);
                    editor.apply();
                    psdflag = true;
                }
            }
        });

        tableLayout.addView(editText1);
        tableLayout.addView(editText2);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("输入原密码和新密码");
        builder.setView(tableLayout);
        builder.setCancelable(false);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (psdflag) {
                    Toast.makeText(mContext, "密码修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "密码修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Uri uri = data.getData();
                String path = getPathFromUri(uri);
                String name = getFileName(path) ;
                String uriStr = (uri != null) ? uri.toString() : null;
                Log.w("test", "uri-> " + uriStr + " name -> " + name);
                // TODO: 2017\12\18 0018 判断是否重复 , 再添加

                ContentValues values = new ContentValues();
                values.put("uri", uriStr);
                values.put("name", name);
                values.put("path",path);
                String table = preferences.getString("table", Constant.TABLE);
                if (!DBUtil.insert(mContext, table, values)) {
                    Toast.makeText(mContext, "保存路径失败!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getContext(), WatchActivity.class);
                intent.putExtra("uri", uriStr);
                startActivity(intent);
            }
        }
    }

    /**
     * 获得视频文件的名字
     *
     * @param path path
     * @return 视频的名字
     */
    String getFileName(String path) {
        Log.w("test", "path -> " + path);
        String[] test = path.split("\\/");
        return test[test.length - 1];
    }

    /**
     * 开始跳转到选择视频
     */
    private void addVideo() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI), 0);
    }

    /**
     * 切换列表按钮点击事件
     */
    private void changeList() {
        editTextPassword.setVisibility(View.VISIBLE);
    }

    /**
     * 根据uri取得文件的路径
     *
     * @param uri 文件的uri
     * @return 文件的路径
     */
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
