package citycircle.com.Property;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.Property.PropertyAdapter.HomeListAdapter;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;
import citycircle.com.Utils.ImageUtils;
import citycircle.com.Utils.PreferencesUtils;

/**
 * Created by admins on 2016/1/30.
 */
public class PropertyHome extends Activity implements View.OnClickListener {
    ImageView back;
    TextView messages, DOC, meeting, phonenumber;
    private MenuDrawer mDrawer;
    ImageView head;
    com.nostra13.universalimageloader.core.ImageLoader ImageLoader;
    DisplayImageOptions options;
    citycircle.com.Utils.ImageUtils ImageUtils;
    ImageLoadingListener animateFirstListener;
    String url, urlstr, uid, username;
    ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashMap;
    HomeListAdapter adapter;
    ListView houselist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY, Position.RIGHT);
        mDrawer.setContentView(R.layout.pro_home);
        mDrawer.setMenuView(R.layout.left_meun);
        mDrawer.setMaxAnimationDuration(R.anim.woniu_list_item);
        uid = PreferencesUtils.getString(PropertyHome.this, "userid");
        username = PreferencesUtils.getString(PropertyHome.this, "username");
        url = GlobalVariables.urlstr + "user.getHouseList&uid=" + uid + "&username=" + username;
        intview();
        getHuose();

    }

    private void intview() {
        houselist = (ListView) findViewById(R.id.houselist);
        head = (ImageView) findViewById(R.id.head);
        back = (ImageView) findViewById(R.id.loginback);
        back.setOnClickListener(this);
        messages = (TextView) findViewById(R.id.messages);
        DOC = (TextView) findViewById(R.id.DOC);
        phonenumber = (TextView) findViewById(R.id.phonenumber);
        meeting = (TextView) findViewById(R.id.meeting);
        messages.setOnClickListener(this);
        DOC.setOnClickListener(this);
        meeting.setOnClickListener(this);
        phonenumber.setOnClickListener(this);
        ImageUtils = new ImageUtils();
        ImageLoader = ImageLoader.getInstance();
        ImageLoader.init(ImageLoaderConfiguration.createDefault(this));
        animateFirstListener = new ImageUtils.AnimateFirstDisplayListener();
        options = ImageUtils.setCirclelmageOptions();
        String headurl = PreferencesUtils.getString(this, "headimage");
        ImageLoader.displayImage(headurl, head, options,
                animateFirstListener);
        houselist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawer.closeMenu();
                PreferencesUtils.putString(PropertyHome.this,"houseids",array.get(position).get("houseid"));
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.loginback:
                finish();
                break;
            case R.id.messages:
                intent.setClass(PropertyHome.this, Information.class);
                PropertyHome.this.startActivity(intent);
                break;
            case R.id.phonenumber:
                intent.setClass(PropertyHome.this, AddHome.class);
                PropertyHome.this.startActivity(intent);
                break;
            case R.id.meeting:
                intent.setClass(PropertyHome.this, MyHouse.class);
                PropertyHome.this.startActivity(intent);
                break;
            case R.id.DOC:
                intent.setClass(PropertyHome.this, FeeBack.class);
                PropertyHome.this.startActivity(intent);
                break;
        }
    }

    private void getHuose() {
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
                    setArray(urlstr);
                    setHouselist();
                    break;
                case 2:
                    Toast.makeText(PropertyHome.this, R.string.intent_error, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    break;
            }
        }
    };

    private void setArray(String str) {
        JSONObject jsonObject = JSON.parseObject(str);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        int a = jsonObject1.getIntValue("code");
        if (a == 0) {
            JSONArray jsonArray = jsonObject1.getJSONArray("info");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                hashMap = new HashMap<>();
                hashMap.put("id", jsonObject2.getString("id") == null ? "" : jsonObject2.getString("id"));
                hashMap.put("houseid", jsonObject2.getString("houseid") == null ? "" : jsonObject2.getString("houseid"));
                hashMap.put("xiaoqu", jsonObject2.getString("xiaoqu") == null ? "" : jsonObject2.getString("xiaoqu"));
                hashMap.put("louhao", jsonObject2.getString("louhao") == null ? "" : jsonObject2.getString("louhao"));
                hashMap.put("fanghao", jsonObject2.getString("fanghao") == null ? "" : jsonObject2.getString("fanghao"));
                array.add(hashMap);
            }
        } else {
            handler.sendEmptyMessage(3);
        }

    }

    private void setHouselist() {
        adapter = new HomeListAdapter(array, PropertyHome.this, handler);
        houselist.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        array.clear();
        getHuose();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (mDrawer.isMenuVisible()){
                mDrawer.closeMenu();
            }else {
                finish();
            }
        }
        return false;
    }
}
