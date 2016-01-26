package citycircle.com.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;
import com.viewpagerindicator.TabPageIndicator;

import citycircle.com.Activity.ToplineFragment;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;
import citycircle.com.Utils.PreferencesUtils;

/**
 * Created by admins on 2015/11/14.
 */
public class HomeFragment extends Fragment {
    View view;
    private static String[] TITLE = new String[]{};
    private static String[] TITLE2 = new String[]{};
    TabPageIndicator indicator;
    private ViewPager mPager;
    TabPageIndicatorAdapter adapter;
    String url, urlinfo, appurl, appstr;
    LinearLayout wifierror;
    Button update;
//    private ArrayList<Fragment> fragmentsList;
//    Fragment home1;
//    Fragment home2;
//    Fragment home3;
//    Fragment home4;
//    private TextView tv1;
//    private TextView tv2;
//    private TextView tv3;
//    private TextView tv4;
//    private ImageView iv1;
//    private ImageView iv2;
//    private ImageView iv3;
//    private ImageView iv4;
//    private ImageView iv01;
//    private ImageView iv02;
//    private ImageView iv03;
//    private ImageView iv04;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Context ctxWithTheme = new ContextThemeWrapper(getActivity().getApplicationContext(), R.style.StyledIndicators);
        LayoutInflater localLayoutInflater = inflater.cloneInContext(ctxWithTheme);
        view = localLayoutInflater.inflate(R.layout.home_layout, container, false);
        mPager = (ViewPager) view.findViewById(R.id.vPager);
        wifierror = (LinearLayout) view.findViewById(R.id.wifierror);
        url = GlobalVariables.urlstr + "News.getCategory";
        appurl = GlobalVariables.urlstr + "News.getQidong";
        adapter = new TabPageIndicatorAdapter(getChildFragmentManager());
        mPager.setAdapter(adapter);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        try {
            cleaner();
        }catch (Exception e){
        }
        getTabtitle();
        UmengUpdateAgent.update(getActivity());
        update=(Button)view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTabtitle();
            }
        });
//        intview();
        PushAgent mPushAgent = PushAgent.getInstance(getActivity());
        mPushAgent.enable();
        return view;

    }

    public void getTabtitle() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpRequest httprequest = new HttpRequest();
                urlinfo = httprequest.doGet(url);
                appstr = httprequest.doGet(appurl);
                if (urlinfo.equals("网络超时")) {
                    handler.sendEmptyMessage(2);
                } else {
                    handler.sendEmptyMessage(1);
                }
                if (appstr.equals("网络超时")) {

                } else {
                    String string = PreferencesUtils.getString(getActivity(), "picimg");
                    if (string == null) {
                        PreferencesUtils.putString(getActivity(), "picimg", appstr);
                        PreferencesUtils.putInt(getActivity(), "ornew", 1);
                    } else {
                        if (string.equals(appstr)) {
                            PreferencesUtils.putInt(getActivity(),"ornew",0);
                        } else {
                            PreferencesUtils.putString(getActivity(), "picimg", string);
                            PreferencesUtils.putInt(getActivity(), "ornew", 1);
                        }
                    }
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
                    mPager.setVisibility(View.VISIBLE);
                    settitle(urlinfo);
                    adapter.notifyDataSetChanged();
                    indicator.notifyDataSetChanged();
                    GlobalVariables.TITLE = TITLE;
                    wifierror.setVisibility(View.GONE);
                    break;
                case 2:
                    mPager.setVisibility(View.GONE);
                    wifierror.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "网络似乎有问题了", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getActivity(), "暂时没有内容", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void settitle(String str) {
        JSONObject object = JSON.parseObject(str);
        PreferencesUtils.putString(getActivity(),"title",str);
        JSONObject jsobject = object.getJSONObject("data");
        int a = jsobject.getIntValue("code");
        if (a == 0) {
            JSONArray array = jsobject.getJSONArray("info");
            TITLE = new String[array.size()];
            TITLE2 = new String[array.size()];
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsob = array.getJSONObject(i);
                TITLE[i] = jsob.getString("title");
                TITLE2[i] = jsob.getString("id");
            }
        } else {
            handler.sendEmptyMessage(3);
        }
    }

    /**
     * 定义ViewPager的适配器
     */
    class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {
        private int mChildCount = 0;

        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = new ToplineFragment();
            Bundle args = new Bundle();
            args.putString("arg", TITLE[position]);
            args.putString("classID", TITLE2[position]);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }
private void cleaner(){
    String str=PreferencesUtils.getString(getActivity(), "title");
    JSONObject object = JSON.parseObject(str);
    JSONObject jsobject = object.getJSONObject("data");
    String[] TITLE = new String[]{};
    int a = jsobject.getIntValue("code");
    if (a == 0) {
        JSONArray array = jsobject.getJSONArray("info");
        TITLE = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsob = array.getJSONObject(i);
            TITLE[i] = jsob.getString("title");
        }
        for (int i=0;i< TITLE.length;i++){
            PreferencesUtils.putString(getActivity(),TITLE[i],null);
            PreferencesUtils.putString(getActivity(),TITLE[i]+ "img",null);
        }
    }else {

    }

}
//    public void intview() {
//        mPager = (ViewPager) view.findViewById(R.id.vPager);
//        fragmentsList = new ArrayList<Fragment>();
//        home1 = new ToplineFragment();
//        home2 = new CityStoryFragment();
//        home3 = new RecommendFragment();
//        home4 = new MyReport();
//        fragmentsList.add(home1);
//        fragmentsList.add(home2);
//        fragmentsList.add(home3);
//        fragmentsList.add(home4);
//        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(),
//                fragmentsList));
//        mPager.setOffscreenPageLimit(4);
//        tv1 = (TextView) view.findViewById(R.id.tv1);
//        tv1.setOnClickListener(this);
//        tv2 = (TextView) view.findViewById(R.id.tv2);
//        tv2.setOnClickListener(this);
//        tv3 = (TextView) view.findViewById(R.id.tv3);
//        tv3.setOnClickListener(this);
//        tv4 = (TextView) view.findViewById(R.id.tv4);
//        tv4.setOnClickListener(this);
//        iv1 = (ImageView) view.findViewById(R.id.iv1);
//        iv2 = (ImageView) view.findViewById(R.id.iv2);
//        iv3 = (ImageView) view.findViewById(R.id.iv3);
//        iv4 = (ImageView) view.findViewById(R.id.iv4);
//        iv01 = (ImageView) view.findViewById(R.id.iv01);
//        iv02 = (ImageView) view.findViewById(R.id.iv02);
//        iv03 = (ImageView) view.findViewById(R.id.iv03);
//        iv04 = (ImageView) view.findViewById(R.id.iv04);
//        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                switch (position) {
//                    case 0:
//
//                        ViewHelper.setAlpha(iv1, 1 - positionOffset);
//                        ViewHelper.setAlpha(iv01, positionOffset);
//                        ViewHelper.setAlpha(iv2, 1 - positionOffset);
//                        ViewHelper.setAlpha(iv02, positionOffset);
//
//                        iv01.setVisibility(View.VISIBLE);
//                        iv02.setVisibility(View.VISIBLE);
//                        break;
//                    case 1:
//
//                        ViewHelper.setAlpha(iv2, positionOffset);
//                        ViewHelper.setAlpha(iv02, 1 - positionOffset);
//                        ViewHelper.setAlpha(iv3, 1 - positionOffset);
//                        ViewHelper.setAlpha(iv03, positionOffset);
//                        iv02.setVisibility(View.VISIBLE);
//                        iv03.setVisibility(View.VISIBLE);
//                        break;
//                    case 2:
//                        ViewHelper.setAlpha(iv3, positionOffset);
//                        ViewHelper.setAlpha(iv03, 1 - positionOffset);
//                        ViewHelper.setAlpha(iv4, 1 - positionOffset);
//                        ViewHelper.setAlpha(iv04, positionOffset);
//                        iv03.setVisibility(View.VISIBLE);
//                        iv04.setVisibility(View.VISIBLE);
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                int color = getResources().getColor(R.color.buttonbule);
//                switch (position) {
//                    case 0:
//                        tv1.setTextColor(color);
//                        tv2.setTextColor(Color.BLACK);
//                        tv3.setTextColor(Color.BLACK);
//                        tv4.setTextColor(Color.BLACK);
//
//                        iv1.setVisibility(View.VISIBLE);
//                        iv01.setVisibility(View.GONE);
//
//                        iv2.setVisibility(View.VISIBLE);
//                        iv02.setVisibility(View.GONE);
//
//                        iv3.setVisibility(View.VISIBLE);
//                        iv03.setVisibility(View.GONE);
//                        iv4.setVisibility(View.VISIBLE);
//                        iv04.setVisibility(View.GONE);
//                        break;
//                    case 1:
//                        tv2.setTextColor(color);
//                        tv1.setTextColor(Color.BLACK);
//                        tv3.setTextColor(Color.BLACK);
//                        tv4.setTextColor(Color.BLACK);
//                        iv1.setVisibility(View.GONE);
//                        iv01.setVisibility(View.VISIBLE);
//
//                        iv2.setVisibility(View.GONE);
//                        iv02.setVisibility(View.VISIBLE);
//
//                        iv3.setVisibility(View.VISIBLE);
//                        iv03.setVisibility(View.GONE);
//                        iv4.setVisibility(View.VISIBLE);
//                        iv04.setVisibility(View.GONE);
//                        break;
//                    case 2:
//                        tv3.setTextColor(color);
//                        tv2.setTextColor(Color.BLACK);
//                        tv1.setTextColor(Color.BLACK);
//                        tv4.setTextColor(Color.BLACK);
//                        iv1.setVisibility(View.GONE);
//                        iv01.setVisibility(View.VISIBLE);
//
//                        iv2.setVisibility(View.VISIBLE);
//                        iv02.setVisibility(View.GONE);
//
//                        iv3.setVisibility(View.GONE);
//                        iv03.setVisibility(View.VISIBLE);
//                        iv4.setVisibility(View.VISIBLE);
//                        iv04.setVisibility(View.GONE);
//                        break;
//                    case 3:
//                        tv4.setTextColor(color);
//                        tv2.setTextColor(Color.BLACK);
//                        tv1.setTextColor(Color.BLACK);
//                        tv3.setTextColor(Color.BLACK);
//                        iv1.setVisibility(View.GONE);
//                        iv01.setVisibility(View.VISIBLE);
//
//                        iv2.setVisibility(View.VISIBLE);
//                        iv02.setVisibility(View.GONE);
//
//                        iv3.setVisibility(View.VISIBLE);
//                        iv03.setVisibility(View.GONE);
//                        iv4.setVisibility(View.GONE);
//                        iv04.setVisibility(View.VISIBLE);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv1:
//                mPager.setCurrentItem(0);
//                break;
//            case R.id.tv2:
//                mPager.setCurrentItem(1);
//                break;
//            case R.id.tv3:
//                mPager.setCurrentItem(2);
//                break;
//            case R.id.tv4:
//                mPager.setCurrentItem(3);
//                break;
//        }
//    }
}
