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
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;
import citycircle.com.Utils.PreferencesUtils;

/**
 * Created by admins on 2016/1/30.
 */
public class SelectHome extends Fragment implements View.OnClickListener {
    private View view;
    Button Property, door, homenu;
    Animation translateAnimation;
    int type = 0;
    Button addhouse;
    String url, urlstr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selecthome, container, false);
        intview();
        return view;
    }

    private void intview() {
        translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(5));
        translateAnimation.setDuration(1000);
        Property = (Button) view.findViewById(R.id.Property);
        Property.setOnClickListener(this);
        door = (Button) view.findViewById(R.id.door);
        door.setOnClickListener(this);
        homenu = (Button) view.findViewById(R.id.homenu);
        homenu.setOnClickListener(this);
        addhouse = (Button) view.findViewById(R.id.addhouse);
        addhouse.setOnClickListener(this);
    }

    private void geturlstr() {
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
                    JSONObject jsonObject = JSON.parseObject(urlstr);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    if (jsonObject1.getIntValue("code") == 0) {
                        Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
                        GlobalVariables.Propertyid="";
                        GlobalVariables.doorid="";
                        GlobalVariables.homenuid="";
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), jsonObject1.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    Toast.makeText(getActivity(), R.string.intent_error, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getActivity(), "结果未知", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
       if (Property.getText().equals("点击选择小区")){
           if (GlobalVariables.Propertyid.equals("")){

           }else {
               Property.setText(GlobalVariables.Property);
           }
       }else if (Property.getText().equals(GlobalVariables.Property)){

       }else {
           Property.setText(GlobalVariables.Property);
           door.setText("点击选择门牌");
           homenu.setText("点击选择室号");
           GlobalVariables.doorid="";
           GlobalVariables.homenuid="";
           type = 0;
           addhouse.setBackgroundResource(R.color.graly);
       }
        if (door.getText().equals("点击选择门牌")){
           if (GlobalVariables.doorid.equals("")){

           }else {
               door.setText(GlobalVariables.door);
           }
        }else if (door.getText().equals(GlobalVariables.door)){

        }else {
            door.setText(GlobalVariables.door);
            homenu.setText("点击选择室号");
            GlobalVariables.homenuid="";
            type = 0;
            addhouse.setBackgroundResource(R.color.graly);
        }
        if(GlobalVariables.homenuid.equals("")){
            type = 0;
            addhouse.setBackgroundResource(R.color.graly);
        }else {
            homenu.setText(GlobalVariables.homenu);
            type = 1;
            addhouse.setBackgroundResource(R.color.butbg);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.Property:
                intent.putExtra("type", 1);
                intent.setClass(getActivity(), SelectMessage.class);
                getActivity().startActivity(intent);
                break;
            case R.id.door:
                if (GlobalVariables.Propertyid.equals("")) {
                    Property.startAnimation(translateAnimation);
                } else {
                    intent.putExtra("type", 2);
                    intent.setClass(getActivity(), SelectMessage.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.homenu:
                if (GlobalVariables.Propertyid.equals("")) {
                    Property.startAnimation(translateAnimation);
                } else if (GlobalVariables.doorid.equals("")) {
                    door.startAnimation(translateAnimation);
                } else {
                    intent.putExtra("type", 3);
                    intent.setClass(getActivity(), SelectMessage.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.addhouse:
                if (type == 1) {
                   String uid= PreferencesUtils.getString(getActivity(), "userid");
                    String username=PreferencesUtils.getString(getActivity(),"username");
                    url = GlobalVariables.urlstr + "User.addHouse&uid="+uid+"&username="+username+"&houseid=" + GlobalVariables.homenuid;
                    geturlstr();
                }
                break;
        }
    }
}
