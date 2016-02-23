package citycircle.com.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import citycircle.com.Activity.Logn;
import citycircle.com.Activity.SaleActivity;
import citycircle.com.Activity.TelYelloePage;
import citycircle.com.OA.HomePageActivity;
import citycircle.com.OA.LandActivity;
import citycircle.com.Property.AddHome;
import citycircle.com.Property.PropertyHome;
import citycircle.com.R;
import citycircle.com.Utils.PreferencesUtils;

/**
 * Created by admins on 2015/11/14.
 */
public class FoundFragment extends Fragment implements View.OnClickListener{
    View view;
    LinearLayout tYellowPages,sale,sales,Property;
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
        Property=(LinearLayout)view.findViewById(R.id.Property);
        Property.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        int oaland= PreferencesUtils.getInt(getActivity(),"oaland");
        int land=PreferencesUtils.getInt(getActivity(),"land");
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

                if (oaland==1){
                    intent.setClass(getActivity(), HomePageActivity.class);
                    getActivity().startActivity(intent);
                }else {
                    intent.putExtra("type",1);
                    intent.setClass(getActivity(), LandActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.Property:
                if (land==1){
                    String houseid = PreferencesUtils.getString(getActivity(), "houseid");
                    if (houseid==null||houseid.equals("0")){
                        intent.putExtra("types",1);
                        intent.setClass(getActivity(), AddHome.class);
                        getActivity().startActivity(intent);
                    }else {
                        intent.setClass(getActivity(), PropertyHome.class);
                        getActivity().startActivity(intent);
                    }

                }else {
                    intent.putExtra("type",1);
                    intent.setClass(getActivity(), Logn.class);
                    getActivity().startActivity(intent);
                }
                break;
        }
    }
}
