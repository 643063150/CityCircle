package citycircle.com.Property;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.Property.PropertyAdapter.InfoAdapter;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;

/**
 * Created by admins on 2016/1/30.
 */
public class SelectMessage extends Activity {
    EditText keyed;
    ListView content;
    int type;
    TextView title;
    ImageView back;
    String url, urlstr;
    ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashMap;
    InfoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectmessage);
        intview();
        type = getIntent().getIntExtra("type", 1);
        if (type == 2) {
            keyed.setHint("根据门牌号查询 (如：xx号)");
            title.setText("选择门牌");
            url=GlobalVariables.urlstr+"Common.getHouseList&pid=80&type=1";
        } else if (type == 3) {
            keyed.setHint("根据房屋室号查询 (如：xx室)");
            title.setText("选择室号");
            url=GlobalVariables.urlstr+"Common.getHouseList&pid=82&type=2";
        } else {
            url = GlobalVariables.urlstr + "Common.getHouseList&pid=0&type=0";
        }
        setAdapter();
        getList();
    }

    private void intview() {
        keyed = (EditText) findViewById(R.id.keyed);
        content = (ListView) findViewById(R.id.content);
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getList() {
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
                    setArray(urlstr);
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(SelectMessage.this,R.string.intent_error,Toast.LENGTH_SHORT).show();
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
                hashMap=new HashMap<>();
                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                hashMap.put("id",jsonObject2.getString("id") == null ? "" : jsonObject2.getString("id"));
                hashMap.put("title",jsonObject2.getString("title") == null ? "" : jsonObject2.getString("title"));
                array.add(hashMap);
            }
        }
    }

    public void setAdapter() {
        adapter=new InfoAdapter(array,SelectMessage.this);
        content.setAdapter(adapter);
    }
}
