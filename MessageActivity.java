package com.example.kundan6singh.testproject.Filter.MessageActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kundan6singh.testproject.Filter.FilterActivity;
import com.example.kundan6singh.testproject.Filter.FilterListAdapter;
import com.example.kundan6singh.testproject.Filter.Product;
import com.example.kundan6singh.testproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Kundan6.Singh on 20-12-2018.
 */

public class MessageActivity extends AppCompatActivity {
    MessageModel messageModelObj;
    ArrayList<MessageModel> alMessageModel = new ArrayList<>();
    TextView tvMsgNotFound;
    RecyclerView rvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        tvMsgNotFound = (TextView) findViewById(R.id.tvMsgNotFound);
        rvMsg = (RecyclerView) findViewById(R.id.rvMsg);
        getJsonData();


    }

    private void getJsonData() {
        try {
            alMessageModel.clear();
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("Message");

            for (int i = 0; i < m_jArry.length(); i++) {
                messageModelObj = new MessageModel();
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("MSGTITLE"));
                String msg_title = jo_inside.getString("MSGTITLE");
                String msg_desc = jo_inside.getString("MSGDESC");
                String msg_date = jo_inside.getString("DATE");
                String msg_linkType = jo_inside.getString("LINKTYPE");
                String msg_typeName = jo_inside.getString("TYPENAME");
                String msg_ButtonName = jo_inside.getString("BUTTONNAME");
                messageModelObj.setMessageTitle(msg_title);
                messageModelObj.setMessageDesc(msg_desc);
                messageModelObj.setMessageDate(msg_date);
                messageModelObj.setMessageLinkType(msg_linkType);
                messageModelObj.setMessageTypeName(msg_typeName);
                messageModelObj.setMessageButtonName(msg_ButtonName);
                alMessageModel.add(messageModelObj);
            }
            if (alMessageModel.size() > 0) {
                tvMsgNotFound.setVisibility(View.GONE);
                rvMsg.setVisibility(View.VISIBLE);
                populateRecyclerView();
            } else {
                tvMsgNotFound.setVisibility(View.VISIBLE);
                rvMsg.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateRecyclerView() {
        rvMsg.setHasFixedSize(true);
        final MessageAdapter messageListAdapter = new MessageAdapter(MessageActivity.this, alMessageModel);
       // rvMsg.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL,10));
        rvMsg.setAdapter(messageListAdapter);
        rvMsg.setLayoutManager(new LinearLayoutManager(this));
        rvMsg.setItemAnimator(new DefaultItemAnimator());
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("message.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
