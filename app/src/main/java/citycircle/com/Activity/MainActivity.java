package citycircle.com.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import citycircle.com.Fragment.CityCircleFragment;
import citycircle.com.Fragment.FoundFragment;
import citycircle.com.Fragment.HomeFragment;
import citycircle.com.Fragment.MineFragment;
import citycircle.com.Fragment.VipCardFragment;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.MyEventBus;
import citycircle.com.Utils.PreferencesUtils;

public class MainActivity extends FragmentActivity implements CompoundButton.OnCheckedChangeListener {
    private RadioButton home, rb_lehui, rb_subscribe, rb_mall,rb_vipcard;
    public static FragmentTransaction transaction;
    String activityStyle = null;
    public HomeFragment HomeFragment;
    public CityCircleFragment LehuiFragment;
    public FoundFragment MallFragment;
    public MineFragment MemberFragment;
    public VipCardFragment vipCardFragment;
    TextView badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        EventBus.getDefault().register(this);
        if (activityStyle == null) {

            if (HomeFragment == null) {
                HomeFragment = new HomeFragment();
                transaction.add(R.id.all_content, HomeFragment);
                transaction.commit();
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void initComponents() {
        badge=(TextView)findViewById(R.id.bdage);
        badge.setVisibility(View.GONE);
        home = (RadioButton) findViewById(R.id.rb_home);
        rb_lehui = (RadioButton) findViewById(R.id.rb_lehui);
        rb_vipcard = (RadioButton) findViewById(R.id.rb_vipcard);
        rb_subscribe = (RadioButton) findViewById(R.id.rb_subscribe);
        rb_mall = (RadioButton) findViewById(R.id.rb_mall);
        home.setOnCheckedChangeListener(this);
        rb_lehui.setOnCheckedChangeListener(this);
        rb_subscribe.setOnCheckedChangeListener(this);
        rb_mall.setOnCheckedChangeListener(this);
        rb_vipcard.setOnCheckedChangeListener(this);
        transaction = getSupportFragmentManager().beginTransaction();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            transaction = getSupportFragmentManager().beginTransaction();
            switch (buttonView.getId()) {
                case R.id.rb_home:

                    if (HomeFragment == null) {
                        HomeFragment = new HomeFragment();
                        transaction.add(R.id.all_content, HomeFragment);
                    }
                    if (LehuiFragment != null) {
                        transaction.hide(LehuiFragment);
                    }

                    if (MallFragment != null) {
                        transaction.hide(MallFragment);
                    }
                    if (MemberFragment != null) {
                        transaction.hide(MemberFragment);
                    }
                    if (vipCardFragment != null) {
                        transaction.hide(vipCardFragment);
                    }
                    transaction.show(HomeFragment);
                    break;
                case R.id.rb_lehui:
                    if (LehuiFragment == null) {
                        LehuiFragment = new CityCircleFragment();
                        transaction.add(R.id.all_content, LehuiFragment);
                    }
                    if (HomeFragment != null) {
                        transaction.hide(HomeFragment);
                    }

                    if (MallFragment != null) {
                        transaction.hide(MallFragment);
                    }
                    if (MemberFragment != null) {
                        transaction.hide(MemberFragment);
                    }
                    if (vipCardFragment != null) {
                        transaction.hide(vipCardFragment);
                    }
                    transaction.show(LehuiFragment);
                    break;
                case R.id.rb_subscribe:
                    if (MallFragment == null) {
                        MallFragment = new FoundFragment();
                        transaction.add(R.id.all_content, MallFragment);
                    }
                    if (HomeFragment != null) {
                        transaction.hide(HomeFragment);
                    }
                    if (MemberFragment != null) {
                        transaction.hide(MemberFragment);
                    }
                    if (LehuiFragment != null) {
                        transaction.hide(LehuiFragment);
                    }
                    if (vipCardFragment != null) {
                        transaction.hide(vipCardFragment);
                    }
                    transaction.show(MallFragment);
                    break;
                case R.id.rb_mall:
                    if (MemberFragment == null) {
                        MemberFragment = new MineFragment();
                        transaction.add(R.id.all_content, MemberFragment);
                    }
                    if (HomeFragment != null) {
                        transaction.hide(HomeFragment);
                    }

                    if (MallFragment != null) {
                        transaction.hide(MallFragment);
                    }
                    if (LehuiFragment != null) {
                        transaction.hide(LehuiFragment);
                    }
                    if (vipCardFragment != null) {
                        transaction.hide(vipCardFragment);
                    }
                    transaction.show(MemberFragment);
                    break;
                case R.id.rb_vipcard:
                    if (vipCardFragment == null) {
                        vipCardFragment = new VipCardFragment();
                        transaction.add(R.id.all_content, vipCardFragment);
                    }
                    if (HomeFragment != null) {
                        transaction.hide(HomeFragment);
                    }

                    if (MallFragment != null) {
                        transaction.hide(MallFragment);
                    }
                    if (LehuiFragment != null) {
                        transaction.hide(LehuiFragment);
                    }
                    if (MemberFragment != null) {
                        transaction.hide(MemberFragment);
                    }
                    transaction.show(vipCardFragment);
                    break;
            }
            transaction.commitAllowingStateLoss();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private static Boolean isExit = false;
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            // 提示放屏幕中间
            // Toast toast;
            // toast = Toast.makeText(getApplicationContext(), "再按一次退出程序",
            // Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER, 0, 0);
            // toast.show();

            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            for (int i=0;i< GlobalVariables.TITLE.length;i++){
                PreferencesUtils.putString(MainActivity.this,GlobalVariables.TITLE[i],null);
                PreferencesUtils.putString(MainActivity.this,GlobalVariables.TITLE[i]+ "img",null);
            }
            finish();
            System.exit(0);
        }
    }
    @Subscribe
    public void getEventmsg(MyEventBus myEventBus){
        if (myEventBus.getMsg().equals("show")){
            badge.setVisibility(View.VISIBLE);
        }else {
            badge.setVisibility(View.GONE);
        }


//        Toast.makeText(getActivity(),myEventBus.getMsg(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
