package citycircle.com.Property;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.MyViews.MyGridView;
import citycircle.com.Property.PropertyAdapter.ComentAdapter;
import citycircle.com.Property.PropertyAdapter.ProImgAdapter;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;

/**
 * Created by admins on 2016/2/17.
 */
public class ComentInfo extends Activity {
    ListView comentlist;
    View headview;
    TextView type, time, content;
    MyGridView photogrid;
    String url, urlstr, id, contents, TYPE, picList;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> comarray = new ArrayList<HashMap<String, String>>();
    ProImgAdapter newPhotoAdapter;
    ComentAdapter adapter;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comentinfo);
//       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        headview = LayoutInflater.from(this).inflate(
                R.layout.comenthead, null);
        id = getIntent().getStringExtra("id");
        contents = getIntent().getStringExtra("content");
        TYPE = getIntent().getStringExtra("TYPE");
        picList = getIntent().getStringExtra("picList");
        url = GlobalVariables.urlstr + "Wuye.getFeedBackList&fid=" + id;
        intview();
        setComentlist();
        getComentstr();
    }

    private void intview() {
        back=(ImageView)findViewById(R.id.back);
        comentlist = (ListView) findViewById(R.id.comentlist);
        type = (TextView) headview.findViewById(R.id.type);
        time = (TextView) headview.findViewById(R.id.time);
        content = (TextView) headview.findViewById(R.id.content);
        photogrid = (MyGridView) headview.findViewById(R.id.photogrid);
        comentlist.addHeaderView(headview);
        content.setText(contents);
        getImgArray();
        newPhotoAdapter=new ProImgAdapter(array,ComentInfo.this);
        photogrid.setAdapter(newPhotoAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getComentstr() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpRequest httpRequest = new HttpRequest();
                urlstr = httpRequest.doGet(url);
                if (urlstr.equals("网络超时")) {
                    handler.sendEmptyMessage(2);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    getarray(urlstr);
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(ComentInfo.this, R.string.intent_error, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    break;
            }
        }
    };

    private void getImgArray() {
        JSONArray jsonArray = JSON.parseArray(picList);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            hashMap = new HashMap<>();
            hashMap.put("url", jsonObject.getString("url") == null ? "" : jsonObject.getString("url"));
            array.add(hashMap);
        }
    }
    private void getarray(String str){
        JSONObject jsonObject=JSON.parseObject(str);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        int a = jsonObject1.getIntValue("code");
        if (a == 0) {
            JSONArray jsonArray = jsonObject1.getJSONArray("info");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                hashMap = new HashMap<>();
                hashMap.put("id", jsonObject2.getString("id") == null ? "" : jsonObject2.getString("id"));
                hashMap.put("content", jsonObject2.getString("content") == null ? "" : jsonObject2.getString("content"));
                hashMap.put("uid", jsonObject2.getString("uid") == null ? "" : jsonObject2.getString("uid"));
                hashMap.put("create_time", jsonObject2.getString("create_time") == null ? "" : jsonObject2.getString("create_time"));
                comarray.add(hashMap);
            }
        }else {

        }

    }
    private void setComentlist(){
        adapter=new ComentAdapter(comarray,ComentInfo.this);
        comentlist.setAdapter(adapter);
    }
}
