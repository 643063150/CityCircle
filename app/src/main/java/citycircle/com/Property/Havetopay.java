package citycircle.com.Property;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.R;
import citycircle.com.Utils.HttpRequest;

/**
 * Created by 飞侠 on 2016/2/23.
 */
public class Havetopay extends Fragment {
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    ListView list;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;
    String url, urlstr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.mycity,container,false);
        return view;
    }
    private void intview() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Refresh);
        list = (ListView) view.findViewById(R.id.list);
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
}
