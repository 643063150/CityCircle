package citycircle.com.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;
import citycircle.com.Utils.ImageUtils;
import citycircle.com.Utils.PreferencesUtils;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by admins on 2015/11/24.
 */
public class Loading extends Activity {
    int frist, newimg;
    String url, urlstr;
    Thread mytherd;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    citycircle.com.Utils.ImageUtils ImageUtils;
    ImageLoadingListener animateFirstListener;
    ImageView welimg;
    CountdownView countdownView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        welimg = (ImageView) findViewById(R.id.welimg);
        frist = PreferencesUtils.getInt(Loading.this, "frist");
        newimg = PreferencesUtils.getInt(Loading.this, "ornew");
        countdownView=(CountdownView)findViewById(R.id.cv_countdownViewTest1);
        url = GlobalVariables.urlstr + "News.getGuanggao&typeid=94";
        getImg();
        mytherd.start();
        try {
            GoApp();
            countdownView.start(3000);
        } catch (Exception e) {
            PreferencesUtils.putInt(Loading.this, "land", 0);
            Intent Intent = new Intent();
            Intent.setClass(Loading.this, NavigationActivity.class);
            Loading.this.startActivity(Intent);
            finish();
        }

    }

    public void GoApp() {
        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                super.run();
                Intent Intent = new Intent();
                if (frist == -1) {
//                    PreferencesUtils.putInt(Loading.this, "frist", 0);
                    PreferencesUtils.putInt(Loading.this, "land", 0);
                    PreferencesUtils.putInt(Loading.this, "photo", 1);
                    Intent.setClass(Loading.this, NavigationActivity.class);
                    Loading.this.startActivity(Intent);
                    finish();
                } else if (newimg == 1) {
                    Intent.setClass(Loading.this, NavigationActivity.class);
                    Loading.this.startActivity(Intent);
                    finish();
                } else {
                    Intent.setClass(Loading.this, MainActivity.class);
                    Loading.this.startActivity(Intent);
                    finish();
                }

            }
        }, 3000);
    }

    private void getImg() {
        mytherd = new Thread() {
            @Override
            public void run() {
                super.run();
                HttpRequest httpRequest = new HttpRequest();
                urlstr = httpRequest.doGet(url);
                if (urlstr.equals("网络超时")) {

                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        };
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    imageLoader = ImageLoader.getInstance();
                    imageLoader.init(ImageLoaderConfiguration.createDefault(Loading.this));
                    animateFirstListener = new ImageUtils.AnimateFirstDisplayListener();
                    ImageUtils=new ImageUtils();
//                    Toast.makeText(Loading.this, urlstr, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = JSON.parseObject(urlstr);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    String url = null;
                    if (jsonObject1.getIntValue("code") == 0) {
                        JSONArray jsonArray = jsonObject1.getJSONArray("info");
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            url = jsonObject2.getString("picurl");
                        }
                    }
                    options = ImageUtils.setcenterOptions();
                    imageLoader.displayImage(url,welimg,options,
                            animateFirstListener);
                    break;
                case 2:
                    break;
            }
        }
    };

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
