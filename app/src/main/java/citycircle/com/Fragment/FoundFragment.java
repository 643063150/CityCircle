package citycircle.com.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import citycircle.com.Activity.SaleActivity;
import citycircle.com.Activity.TelYelloePage;
import citycircle.com.OA.HomePageActivity;
import citycircle.com.OA.LandActivity;
import citycircle.com.R;
import citycircle.com.Utils.PreferencesUtils;

/**
 * Created by admins on 2015/11/14.
 */
public class FoundFragment extends Fragment implements View.OnClickListener{
    View view;
    LinearLayout tYellowPages,sale,sales;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.found_layout, container, false);
        intview();
        return view;
    }
    public void intview(){
        tYellowPages=(LinearLayout)view.findViewById(R.id.YellowPages);
        tYellowPages.setOnClickListener(this);
        sale=(LinearLayout)view.findViewById(R.id.sale);
        sale.setOnClickListener(this);
        sales=(LinearLayout)view.findViewById(R.id.sales);
        sales.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.YellowPages:
                intent.setClass(getActivity(), TelYelloePage.class);
                getActivity().startActivity(intent);
                break;
            case R.id.sale:
                intent.setClass(getActivity(), SaleActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.sales:
//                try {
//                    Intent i = new Intent();
//                    i.setClassName("com.example.hkoa", "com.hkoa.activity.StartoverActivity");
//                    getActivity(). startActivity(i);
//                }catch (Exception e){
//                    Toast.makeText(getActivity(),"应用位下载",Toast.LENGTH_SHORT).show();
//                }
                int oaland= PreferencesUtils.getInt(getActivity(),"oaland");
                if (oaland==1){
                    intent.setClass(getActivity(), HomePageActivity.class);
                    getActivity().startActivity(intent);
                }else {
                    intent.putExtra("type",1);
                    intent.setClass(getActivity(), LandActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
        }
    }
}
