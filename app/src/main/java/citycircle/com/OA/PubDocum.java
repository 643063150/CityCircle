package citycircle.com.OA;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;
import citycircle.com.Utils.PreferencesUtils;

/**
 * Created by admins on 2016/1/26.
 */
public class PubDocum extends Fragment {
    View view;
    ListView doculist;
    String url, urlstr;
    String username,uid;
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashmap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.document_lay, container, false);
        username= PreferencesUtils.getString(getActivity(), "oausername");
        uid = PreferencesUtils.getString(getActivity(), "oauid");
        url= GlobalVariables.oaurlstr+"Files.getList&username="+username+"&uid="+uid;
        intview();
        getdoulist();
        return view;
    }

    private void intview() {
        doculist = (ListView) view.findViewById(R.id.doculist);
    }

    private void getdoulist() {
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
                    break;
                case 2:
                    Toast.makeText(getActivity(),"网络似乎有问题了",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    break;

            }
        }
    };
    private void setArraylist(String str){

    }
}
