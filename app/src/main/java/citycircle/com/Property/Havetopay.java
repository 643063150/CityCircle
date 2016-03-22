package citycircle.com.Property;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.Property.PropertyAdapter.PaysAdapter;
import citycircle.com.R;
import citycircle.com.Utils.HttpRequest;
import citycircle.com.Utils.PreferencesUtils;

/**
 * Created by 飞侠 on 2016/2/23.
 */
public class Havetopay extends Fragment {
    View view;
//    SwipeRefreshLayout swipeRefreshLayout;
    ListView list;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;
    String url, urlstr, uid, username, houseid, type,fangid;
    PaysAdapter paysAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.money,container,false);
        intview();
        setArrayList();
        uid = PreferencesUtils.getString(getActivity(), "userid");
        username = PreferencesUtils.getString(getActivity(), "username");
//        houseid = PreferencesUtils.getString(getActivity(), "houseids");
        fangid=PreferencesUtils.getString(getActivity(),"fanghaoid");
        type = "1";
        url="http://101.201.169.38/api/Public/Found/?service=wuye.getPayList&uid="+uid+"&username="+username+"&fanghaoid="+fangid+"&type="+type+"&status=1";
        getList();
        return view;
    }
    private void intview() {
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Refresh);
        list = (ListView) view.findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),PayMoney.class);
                intent.putExtra("type", type);
                intent.putExtra("money",arrayList.get(position).get("money"));
                intent.putExtra("id",arrayList.get(position).get("id"));
                intent.putExtra("bak",arrayList.get(position).get("bak"));
                intent.putExtra("create_time",arrayList.get(position).get("create_time"));
                intent.putExtra("status","1");
                getActivity().startActivity(intent);
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
                    getarray(urlstr);
                    paysAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(getActivity(), R.string.intent_error, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getActivity(), R.string.nomore, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void getarray(String str) {
        JSONObject jsonObject = JSON.parseObject(str);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        int a = jsonObject1.getIntValue("code");
        if (a == 0) {
            JSONArray jsonArray = jsonObject1.getJSONArray("info");
            for (int i = 0; i < jsonArray.size(); i++) {
                hashMap = new HashMap<>();
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                hashMap.put("id", jsonObject2.getString("id") == null ? "" : jsonObject2.getString("id"));
                hashMap.put("money", jsonObject2.getString("money") == null ? "" : jsonObject2.getString("money"));
                hashMap.put("create_time", jsonObject2.getString("create_time") == null ? "" : jsonObject2.getString("create_time"));
                hashMap.put("bak", jsonObject2.getString("bak") == null ? "" : jsonObject2.getString("bak"));
                arrayList.add(hashMap);
            }
        } else {

        }

    }
    private void setArrayList(){
        paysAdapter=new PaysAdapter(arrayList,getActivity());
        list.setAdapter(paysAdapter);
    }
}
