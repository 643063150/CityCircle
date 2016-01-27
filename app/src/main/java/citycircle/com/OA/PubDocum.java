package citycircle.com.OA;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import citycircle.com.OA.OAAdapter.DocumentAdapter;
import citycircle.com.OA.entities.FileInfo;
import citycircle.com.OA.service.DownloadService;
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
    DocumentAdapter adapter;
    FileInfo fileInfo ;
    private ArrayList<FileInfo> mFileInfoList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.document_lay, container, false);
        username= PreferencesUtils.getString(getActivity(), "oausername");
        uid = PreferencesUtils.getString(getActivity(), "oauid");
        url= GlobalVariables.oaurlstr+"Files.getList&username="+username+"&uid="+uid;
        intview();
        setDoculist();
        getdoulist();
        return view;
    }

    private void intview() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        filter.addAction(DownloadService.ACTION_FINISHED);
        getActivity().registerReceiver(mReceiver, filter);
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
                    setArraylist(urlstr);
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(getActivity(),"网络似乎有问题了",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getActivity(),"暂时没有内容",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
    private void setArraylist(String str){
        JSONObject jsonObject= JSON.parseObject(str);
        JSONObject jsonObject1=jsonObject.getJSONObject("data");
        int a=jsonObject1.getIntValue("code");
        if (a==0){
            JSONArray jsonArray=jsonObject1.getJSONArray("info");
            for (int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                String url=jsonObject2.getString("url")== null ? ""
                        : jsonObject2.getString("url");
                String name=jsonObject2.getString("name")== null ? ""
                        : jsonObject2.getString("name");
                fileInfo = new FileInfo(i,url, name, 0, 0);
//                hashmap=new HashMap<>();
//                hashmap.put("id",jsonObject2.getString("id")== null ? ""
//                        : jsonObject2.getString("id"));
//                hashmap.put("name",jsonObject2.getString("name")== null ? ""
//                        : jsonObject2.getString("name"));
//                hashmap.put("url",jsonObject2.getString("url")== null ? ""
//                        : jsonObject2.getString("url"));
//                arraylist.add(hashmap);
                mFileInfoList.add(fileInfo);
            }
        }else {

        }
    }

    public void setDoculist() {
        adapter=new DocumentAdapter(getActivity(),mFileInfoList);
        doculist.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction()))
            {
                int finised = intent.getIntExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                adapter.updateProgress(id, finised);
                Log.i("mReceiver", id + "-finised = " + finised);
            }
            else if (DownloadService.ACTION_FINISHED.equals(intent.getAction()))
            {
                // 下载结束
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                adapter.updateProgress(fileInfo.getId(), 100);
                Toast.makeText(getActivity(),
                        mFileInfoList.get(fileInfo.getId()).getFileName() + "下载完毕",
                        Toast.LENGTH_SHORT).show();

            }
        }
    };
}
