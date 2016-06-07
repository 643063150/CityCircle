package citycircle.com.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import citycircle.com.Activity.VipcardInfo;
import citycircle.com.R;

/**
 * Created by admins on 2016/5/31.
 */
public class VipCardFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView allclass;
    ImageView back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.vipcard,null);
        intview();
        return view;
    }
    private void intview(){
        allclass=(TextView)view.findViewById(R.id.allclass);
        allclass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.allclass:
                Intent intent=new Intent();
                intent.setClass(getActivity(), VipcardInfo.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
