package citycircle.com.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.Adapter.MyVipcardAdapter;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.Loadmore;
import citycircle.com.Utils.PreferencesUtils;
import okhttp3.Call;

/**
 * Created by admins on 2016/6/3.
 */
public class MyVipcard extends Activity {
    TextView title;
    ImageView back;
    SwipeRefreshLayout Refresh;
    ListView viplist;
    String uesename,url;
    int page=1;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashMap;
    MyVipcardAdapter adapter;
    Loadmore loadmore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopvipcard);
        uesename= PreferencesUtils.getString(MyVipcard.this,"username");
        url=GlobalVariables.urlstr+"hyk.getuserlist&username="+uesename+"&page="+page;
        intview();
        setAdapter();
        getJson();
    }
    private void intview(){
        loadmore = new Loadmore();
        title=(TextView)findViewById(R.id.titile);
        back=(ImageView)findViewById(R.id.back);
        Refresh=(SwipeRefreshLayout)findViewById(R.id.Refresh);
        viplist=(ListView)findViewById(R.id.viplist);
        loadmore.loadmore(viplist);
        loadmore.setMyPopwindowswListener(new Loadmore.LoadmoreList() {
            @Override
            public void loadmore() {
                page++;
                url=GlobalVariables.urlstr+"hyk.getuserlist&username="+uesename+"&page="+page;
                getJson();
            }
        });
        viplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id", arrayList.get(position).get("id"));
                intent.setClass(MyVipcard.this, VipcardInfo.class);
                MyVipcard.this.startActivity(intent);
            }
        });
    }
    private void getJson(){
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(MyVipcard.this,R.string.intent_error,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                setarray(response);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void setarray(String str) {
        JSONObject jsonObject = JSON.parseObject(str);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        if (jsonObject1.getIntValue("code") == 0) {
            JSONArray jsonArray = jsonObject1.getJSONArray("info");
            for (int i = 0; i < jsonArray.size(); i++) {
                hashMap = new HashMap<>();
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                hashMap.put("id", jsonObject2.getString("id") == null ? "" : jsonObject2.getString("id"));
                hashMap.put("color", jsonObject2.getString("color") == null ? "" : jsonObject2.getString("color"));
                hashMap.put("logo", jsonObject2.getString("logo") == null ? "" : jsonObject2.getString("logo"));
                hashMap.put("shopname", jsonObject2.getString("shopname") == null ? "" : jsonObject2.getString("shopname"));
                hashMap.put("type", jsonObject2.getString("type") == null ? "" : jsonObject2.getString("type"));
                arrayList.add(hashMap);
            }

        } else {
            if (page != 1) {
                page--;
            }
            Toast.makeText(MyVipcard.this, R.string.nomore, Toast.LENGTH_SHORT).show();
        }
    }
    private void setAdapter(){
        adapter=new MyVipcardAdapter(arrayList,MyVipcard.this);
        viplist.setAdapter(adapter);
    }
}
