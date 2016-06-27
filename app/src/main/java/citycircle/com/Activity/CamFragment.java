package citycircle.com.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.Adapter.Camadapter;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.Loadmore;
import okhttp3.Call;

/**
 * Created by admins on 2016/5/31.
 */
public class CamFragment extends Fragment {
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    String url, bannerurl;
    Loadmore loadmore;
    Camadapter adapter;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashMap;
    int page = 1;
    public static CamFragment instance() {
        CamFragment view = new CamFragment();
        return view;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.newhomelist,null);
        url= GlobalVariables.urlstr+"News.getList&category_id=98&perNumber=10&page="+page;
        intview();
        setAdapter();
        getJson(0);
        return view;
    }
    private void intview(){
        loadmore = new Loadmore();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Refresh);
        listView = (ListView) view.findViewById(R.id.mylist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id", arrayList.get(position ).get("id"));
                intent.putExtra("title", arrayList.get(position ).get("title"));
                intent.putExtra("description", arrayList.get(position ).get("description"));
                intent.putExtra("url", arrayList.get(position).get("url"));
                intent.setClass(getActivity(), NewsInfoActivity.class);
                getActivity().startActivity(intent);
            }
        });
        loadmore.loadmore(listView);
        loadmore.setMyPopwindowswListener(new Loadmore.LoadmoreList() {
            @Override
            public void loadmore() {
                page++;
                url= GlobalVariables.urlstr+"News.getList&category_id=98&perNumber=10&page="+page;
                getJson(0);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                url= GlobalVariables.urlstr+"News.getList&category_id=98&perNumber=10&page="+page;
                getJson(1);
            }
        });
    }
    private void setArray(String str) {
        JSONObject jsonObject = JSON.parseObject(str);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        if (jsonObject1.getIntValue("code") == 0) {
            JSONArray jsonArray = jsonObject1.getJSONArray("info");
            for (int i = 0; i < jsonArray.size(); i++) {
                hashMap = new HashMap<>();
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                hashMap.put("id", jsonObject2.getString("id") == null ? "" : jsonObject2.getString("id"));
                hashMap.put("title", jsonObject2.getString("title") == null ? "" : jsonObject2.getString("title"));
                hashMap.put("description", jsonObject2.getString("description") == null ? "" : jsonObject2.getString("description"));
                hashMap.put("view", jsonObject2.getString("view") == null ? "" : jsonObject2.getString("view"));
                hashMap.put("url", jsonObject2.getString("url") == null ? "" : jsonObject2.getString("url"));
                hashMap.put("name", jsonObject2.getString("name") == null ? "" : jsonObject2.getString("name"));
                hashMap.put("s_time", jsonObject2.getString("s_time") == null ? "" : jsonObject2.getString("s_time"));
                hashMap.put("e_time", jsonObject2.getString("e_time") == null ? "" : jsonObject2.getString("e_time"));
                arrayList.add(hashMap);
            }

        } else {
            if (page != 1) {
                page--;
            }
            Toast.makeText(getActivity(), R.string.nomore, Toast.LENGTH_SHORT).show();
        }
    }
    private void getJson(final int type) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), R.string.intent_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                if (type==1){
                    arrayList.clear();
                }
                setArray(response);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void setAdapter() {
        adapter = new Camadapter(arrayList, getActivity());
        listView.setAdapter(adapter);
    }
}
