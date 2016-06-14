package citycircle.com.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import citycircle.com.Activity.VipcardInfo;
import citycircle.com.Adapter.VipAdapter;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.PreferencesUtils;
import okhttp3.Call;

/**
 * Created by admins on 2016/5/31.
 */
public class VipCardFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView allclass;
    ImageView back;
    ListView listView;
    String url;
    String category_id="0",typeid="0";
    int page=1;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashMap;
    VipAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vipcard, null);
        int a = PreferencesUtils.getInt(getActivity(), "land");
        if (a==0){
            url=GlobalVariables.urlstr+"Hyk.getList&category_id="+category_id+"&typeid="+typeid+"&page="+page;
        }else {

        }
        intview();
        setadapter();
        getjson();
        return view;
    }

    private void intview() {
        allclass = (TextView) view.findViewById(R.id.allclass);
        allclass.setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.viplist);
    }

    private void getjson() {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(getActivity(),R.string.intent_error,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                setarray(response);
                adapter.notifyDataSetChanged();
            }
        });
    }
private void setarray(String str){
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
            hashMap.put("orlq", jsonObject2.getString("orlq") == null ? "" : jsonObject2.getString("orlq"));
            arrayList.add(hashMap);
        }

    } else {
        if (page != 1) {
            page--;
        }
        Toast.makeText(getActivity(), R.string.nomore, Toast.LENGTH_SHORT).show();
    }
}
    private void setadapter(){
        adapter=new VipAdapter(arrayList,getActivity());
        listView.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allclass:
                Intent intent = new Intent();
                intent.setClass(getActivity(), VipcardInfo.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
