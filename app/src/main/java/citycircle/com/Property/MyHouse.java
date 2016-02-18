package citycircle.com.Property;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;

/**
 * Created by 飞侠 on 2016/2/18.
 */
public class MyHouse extends Activity {
    SwipeRefreshLayout Refresh;
    ListView houselist;
    ImageView back;
    String url, urlstr;
    ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myhouse);
        url = GlobalVariables.urlstr + "user.getHouseList&uid=5&username=cheng";
        intview();
        gethouselist(0);
    }

    private void intview() {
        back=(ImageView)findViewById(R.id.back);
        Refresh = (SwipeRefreshLayout) findViewById(R.id.Refresh);
        houselist = (ListView) findViewById(R.id.houselist);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void gethouselist(final int type) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpRequest httpRequest = new HttpRequest();
                if (urlstr.equals("网络超时")) {
                    handler.sendEmptyMessage(2);
                } else {
                    handler.sendEmptyMessage(3);
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
                    setArray(urlstr);
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
    };
private void setArray(String str){
    JSONObject jsonObject= JSON.parseObject(str);
    JSONObject jsonObject1=jsonObject.getJSONObject("data");
    int a=jsonObject1.getIntValue("code");
    if (a==0){
        JSONArray jsonArray=jsonObject1.getJSONArray("info");
        for (int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject2=jsonArray.getJSONObject(i);
            hashMap=new HashMap<>();
            hashMap.put("houseid",jsonObject2.getString("id") == null ? "" : jsonObject2.getString("houseid"));
            hashMap.put("xiaoqu",jsonObject2.getString("xiaoqu") == null ? "" : jsonObject2.getString("xiaoqu"));
            hashMap.put("louhao",jsonObject2.getString("louhao") == null ? "" : jsonObject2.getString("louhao"));
            hashMap.put("fanghao",jsonObject2.getString("fanghao") == null ? "" : jsonObject2.getString("fanghao"));
            array.add(hashMap);
        }
    }else {

    }

}
}
