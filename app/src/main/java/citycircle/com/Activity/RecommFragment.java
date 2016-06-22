package citycircle.com.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import citycircle.com.Adapter.NetworkImageHolderView;
import citycircle.com.Adapter.RecomAdapter;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.Loadmore;
import okhttp3.Call;

/**
 * Created by admins on 2016/5/31.
 */
public class RecommFragment extends Fragment {
    View view, headview;
    SwipeRefreshLayout swipeRefreshLayout;
    ConvenientBanner fristbannerbanner;
    ListView listView;
    String url, bannerurl;
    int page = 1;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashMap;
    RecomAdapter adapter;
    Loadmore loadmore;
    private List<String> networkImages;

    public static RecommFragment instance() {
        RecommFragment view = new RecommFragment();
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.newhomelist, null);
        url = GlobalVariables.urlstr + "News.getListTJ&perNumber=10&page=" + page;
        bannerurl = GlobalVariables.urlstr + "News.getGuanggao&typeid=83";
        intview();
        setAdapter();
        getJson();
        getbannerjson();
        return view;
    }

    private void intview() {
        loadmore = new Loadmore();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Refresh);
        headview = LayoutInflater.from(getActivity()).inflate(R.layout.headbanner, null);
        fristbannerbanner = (ConvenientBanner) headview.findViewById(R.id.fristbannerbanner);
        listView = (ListView) view.findViewById(R.id.mylist);
        listView.addHeaderView(headview);
        loadmore.loadmore(listView);
        loadmore.setMyPopwindowswListener(new Loadmore.LoadmoreList() {
            @Override
            public void loadmore() {
                page++;
                url = GlobalVariables.urlstr + "News.getListTJ&perNumber=10&page=" + page;
                getJson();
            }
        });
    }

    private void getJson() {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(getActivity(), R.string.intent_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                setArray(response);
                adapter.notifyDataSetChanged();
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
                hashMap.put("category_id", jsonObject2.getString("category_id") == null ? "" : jsonObject2.getString("category_id"));
                arrayList.add(hashMap);
            }

        } else {
            if (page != 1) {
                page--;
            }
            Toast.makeText(getActivity(), R.string.nomore, Toast.LENGTH_SHORT).show();
        }
    }

    private void getbannerjson() {
        OkHttpUtils.get().url(bannerurl).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listView.removeHeaderView(headview);
            }

            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                if (jsonObject1.getIntValue("code") == 0) {
                    networkImages = new ArrayList<String>();
                    JSONArray jsonArray = jsonObject1.getJSONArray("info");
                    for (int i=0;i<jsonArray.size();i++){
                        JSONObject jsonObject2=jsonArray.getJSONObject(i);
                        networkImages.add(jsonObject2.getString("picurl"));
                    }
                    fristbannerbanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                        @Override
                        public NetworkImageHolderView createHolder() {
                            return new NetworkImageHolderView();
                        }
                    }, networkImages);
                } else {
                    listView.removeHeaderView(headview);
                }
            }
        });
    }

    private void setAdapter() {
        adapter = new RecomAdapter(arrayList, getActivity());
        listView.setAdapter(adapter);
    }
}
