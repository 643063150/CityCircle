package citycircle.com.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by admins on 2016/5/10.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> arrayList=new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm, ArrayList<Fragment> arrayList) {
        super(fm);
        this.arrayList=arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return arrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "推荐";
            case 1:
                return "本地";
            case 2:
                return "关注";
            case 3:
                return "活动";
        }
        return null;
    }
}
