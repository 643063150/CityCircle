package citycircle.com.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.umeng.analytics.MobclickAgent;

import citycircle.com.R;
import citycircle.com.Utils.PreferencesUtils;

/**
 * Created by admins on 2015/11/24.
 */
public class Loading extends Activity {
    int frist,newimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        frist = PreferencesUtils.getInt(Loading.this, "frist");
        newimg= PreferencesUtils.getInt(Loading.this, "ornew");
        try {
            GoApp();
        }catch (Exception e){
            PreferencesUtils.putInt(Loading.this, "land", 0);
            Intent Intent=new Intent();
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
                Intent Intent=new Intent();
                if (frist==-1){
//                    PreferencesUtils.putInt(Loading.this, "frist", 0);
                    PreferencesUtils.putInt(Loading.this, "land", 0);
                    PreferencesUtils.putInt(Loading.this, "photo", 1);
                    Intent.setClass(Loading.this, NavigationActivity.class);
                    Loading.this.startActivity(Intent);
                    finish();
                }else if (newimg==1){
                    Intent.setClass(Loading.this,NavigationActivity.class);
                    Loading.this.startActivity(Intent);
                    finish();
                } else{
                    Intent.setClass(Loading.this,MainActivity.class);
                    Loading.this.startActivity(Intent);
                    finish();
                }

            }
        }, 3000);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
