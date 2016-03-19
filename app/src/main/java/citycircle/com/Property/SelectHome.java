package citycircle.com.Property;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;

/**
 * Created by admins on 2016/1/30.
 */
public class SelectHome extends Fragment implements OnItemClickListener, View.OnClickListener {
    private View view;
    Animation translateAnimation;
    int types;
    Button calm, village, building, unit, floor, room, addhouse;
    String url,urlstr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selecthome, container, false);
        intview();
        types = getActivity().getIntent().getIntExtra("types", 0);
        return view;
    }

    private void intview() {
        translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(5));
        translateAnimation.setDuration(1000);
        calm = (Button) view.findViewById(R.id.calm);
        village = (Button) view.findViewById(R.id.village);
        building = (Button) view.findViewById(R.id.building);
        unit = (Button) view.findViewById(R.id.unit);
        floor = (Button) view.findViewById(R.id.floor);
        room = (Button) view.findViewById(R.id.room);
        addhouse = (Button) view.findViewById(R.id.addhouse);
        calm.setOnClickListener(this);
        village.setOnClickListener(this);
        building.setOnClickListener(this);
        unit.setOnClickListener(this);
        floor.setOnClickListener(this);
        room.setOnClickListener(this);
        addhouse.setOnClickListener(this);
    }

    private void Alertshow(String str[]) {
        new AlertView(null, null, null, null,
                str, getActivity(), AlertView.Style.Alert, this).show();
    }

    //弹出框点击
    @Override
    public void onItemClick(Object o, int i) {
        Toast.makeText(getActivity(), i + "", Toast.LENGTH_SHORT).show();
    }

    //按钮点击
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calm:
//                Alertshow(new String[]{"其他按钮1", "其他按钮2", "其他按钮3", "其他按钮4", "其他按钮5", "其他按钮6",
//                "其他按钮7", "其他按钮8", "其他按钮9", "其他按钮10", "其他按钮11", "其他按钮12"});
                url= GlobalVariables.urlstr+"Common.getHouseList&pid=0";
                break;
            case R.id.village:
                break;
            case R.id.building:
                break;
            case R.id.unit:
                break;
            case R.id.floor:
                break;
            case R.id.room:
                break;
            case R.id.addhouse:
                break;
        }
    }
}
