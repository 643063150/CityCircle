package citycircle.com.Property;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;

/**
 * Created by admins on 2016/1/30.
 */
public class SelectHome extends Fragment implements View.OnClickListener {
    private View view;
    Button Property, door, homenu;
    Animation translateAnimation;
    int type=0;
    Button addhouse;
    String url,urlstr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selecthome, container, false);
        intview();
        return view;
    }

    private void intview() {
        translateAnimation  = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(5));
        translateAnimation.setDuration(1000);
        Property = (Button) view.findViewById(R.id.Property);
        Property.setOnClickListener(this);
        door = (Button) view.findViewById(R.id.door);
        door.setOnClickListener(this);
        homenu = (Button) view.findViewById(R.id.homenu);
        homenu.setOnClickListener(this);
        addhouse=(Button)view.findViewById(R.id.addhouse);
        addhouse.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!GlobalVariables.Propertyid.equals("")) {
            Property.setText(GlobalVariables.Property);
            if (!GlobalVariables.doorid.equals("")){
                door.setText(GlobalVariables.door);
                if (!GlobalVariables.homenuid.equals("")){
                    homenu.setText(GlobalVariables.homenu);
                    type=1;
                    addhouse.setBackgroundResource(R.color.butbg);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.Property:
                intent.putExtra("type", 1);
                intent.setClass(getActivity(), SelectMessage.class);
                getActivity().startActivity(intent);
                break;
            case R.id.door:
                if (GlobalVariables.Propertyid.equals("")) {
                    Property.startAnimation(translateAnimation);
                } else {
                    intent.putExtra("type", 2);
                    intent.setClass(getActivity(), SelectMessage.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.homenu:
                if (GlobalVariables.Propertyid.equals("")){
                    Property.startAnimation(translateAnimation);
                }else if (GlobalVariables.doorid.equals("")){
                    door.startAnimation(translateAnimation);
                }else {
                    intent.putExtra("type", 3);
                    intent.setClass(getActivity(), SelectMessage.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.addhouse:
                if (type==1){

                }
                break;
        }
    }
}
