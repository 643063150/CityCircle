package citycircle.com.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;

import citycircle.com.Activity.AttaFragment;
import citycircle.com.Activity.CamFragment;
import citycircle.com.Activity.LocalFragment;
import citycircle.com.Activity.RecommFragment;
import citycircle.com.Adapter.SectionsPagerAdapter;
import citycircle.com.R;

/**
 * Created by admins on 2015/11/14.
 */
public class HomeFragment extends Fragment {
    View view;
    ArrayList<Fragment> arrayList=new ArrayList<>();
    ViewPager viewPager;
    SectionsPagerAdapter adapter;
    TabLayout tabLayout;
    AttaFragment attaFragment;
    LocalFragment localFragment;
    RecommFragment recommFragment;
    CamFragment camFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);
        UmengUpdateAgent.update(getActivity());
        PushAgent mPushAgent = PushAgent.getInstance(getActivity());
        mPushAgent.enable();
        setView();
        return view;
    }
    private void setView(){
        viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        attaFragment=new AttaFragment();
        arrayList.add(attaFragment);
        localFragment=new LocalFragment();
        arrayList.add(localFragment);
        recommFragment=new RecommFragment();
        arrayList.add(recommFragment);
        camFragment=new CamFragment();
        arrayList.add(camFragment);
        adapter=new SectionsPagerAdapter(getChildFragmentManager(),arrayList);
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout)view. findViewById(R.id.mytabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
