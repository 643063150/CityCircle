package citycircle.com.Property;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;

/**
 * Created by admins on 2016/1/30.
 */
public class SelectHome extends Fragment implements OnItemClickListener, View.OnClickListener {
    private View view;
    Animation translateAnimation;
    int types;
    Button calm, village, building, unit, floor, room, addhouse;
    String url, urlstr, calmid, villageid, buildingid, unitid, floorid, roomid, addurl;
    ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashMap;
    String alretstr[];
    int type = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selecthome, container, false);
        intview();
        types = getActivity().getIntent().getIntExtra("types", 0);
//        Alertshow(new String[]{"其他按钮1", "其他按钮2", "其他按钮3"});
        return view;
    }

    private void intview() {
        translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(5));
        translateAnimation.setDuration(1000);
        calm = (Button) view.findViewById(R.id.calm);
        village = (Button) view.findViewById(R.id.village);
        building = (Button) view.findViewById(R.id.building);
        unit = (Button) view.findViewById(R.id.unit);
        floor = (Button) view.findViewById(R.id.floor);
        room = (Button) view.findViewById(R.id.room);
        addhouse = (Button) view.findViewById(R.id.addhouse);
        calm.setOnClickListener(this);
        village.setOnClickListener(this);
        building.setOnClickListener(this);
        unit.setOnClickListener(this);
        floor.setOnClickListener(this);
        room.setOnClickListener(this);
        addhouse.setOnClickListener(this);
    }

    private void getStr(final int type) {
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

    public void setArray(String str) {
        JSONObject jsonObject = JSON.parseObject(str);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        int a = jsonObject1.getIntValue("code");
        if (a == 0) {
            JSONArray jsonArray = jsonObject1.getJSONArray("info");
            alretstr = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                hashMap = new HashMap<>();
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                hashMap.put("id", jsonObject2.getString("id") == null ? "" : jsonObject2.getString("id"));
                hashMap.put("title", jsonObject2.getString("title") == null ? "" : jsonObject2.getString("title"));
                alretstr[i] = jsonObject2.getString("title");
                array.add(hashMap);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setArray(urlstr);
                    Alertshow(alretstr);
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

    private void Alertshow(String str[]) {
        new AlertView(null, null, null, null,
                str, getActivity(), AlertView.Style.Alert, this).show();
    }

    //弹出框点击
    @Override
    public void onItemClick(Object o, int i) {
        if (type == 0) {
            calmid = array.get(i).get("id");
            calm.setText(array.get(i).get("title"));
        } else if (type == 1) {
            villageid = array.get(i).get("id");
            village.setText(array.get(i).get("title"));
        } else if (type == 2) {
            buildingid = array.get(i).get("id");
            building.setText(array.get(i).get("title"));
        } else if (type == 3) {
            unitid = array.get(i).get("id");
            unit.setText(array.get(i).get("title"));
        } else if (type == 4) {
            floorid = array.get(i).get("id");
            floor.setText(array.get(i).get("title"));
        }else if (type==5){
            roomid=array.get(i).get("id");
            room.setText(array.get(i).get("title"));
            addhouse.setBackgroundResource(R.drawable.btn_bg);
        }
    }

    //按钮点击
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calm:
                array.clear();
                type = 0;
                url = GlobalVariables.urlstr + "Common.getHouseList&pid=0";
                getStr(0);
                break;
            case R.id.village:
                type = 1;
                if (calmid == null) {
                    calm.startAnimation(translateAnimation);
                } else {
                    array.clear();
                    url = GlobalVariables.urlstr + "Common.getHouseList&pid=" + calmid;
                    getStr(0);
                }
                break;
            case R.id.building:
                type = 2;
                if (villageid == null) {
                    village.startAnimation(translateAnimation);
                } else {
                    array.clear();
                    url = GlobalVariables.urlstr + "Common.getHouseList&pid=" + villageid;
                    getStr(0);
                }
                break;
            case R.id.unit:
                type = 3;
                if (buildingid == null) {
                    building.startAnimation(translateAnimation);
                } else {
                    array.clear();
                    url = GlobalVariables.urlstr + "Common.getHouseList&pid=" + buildingid;
                    getStr(0);
                }
                break;
            case R.id.floor:
                type = 4;
                if (unitid == null) {
                    unit.startAnimation(translateAnimation);
                } else {
                    array.clear();
                    url = GlobalVariables.urlstr + "Common.getHouseList&pid=" + unitid;
                    getStr(0);
                }
                break;
            case R.id.room:
                type = 5;
                if (floorid == null) {
                    floor.startAnimation(translateAnimation);
                } else {
                    array.clear();
                    url = GlobalVariables.urlstr + "Common.getHouseList&pid=" + floorid;
                    getStr(0);
                }
                break;
            case R.id.addhouse:
                break;
        }
    }
}
