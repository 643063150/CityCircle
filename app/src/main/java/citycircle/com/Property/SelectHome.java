package citycircle.com.Property;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import citycircle.com.R;

/**
 * Created by admins on 2016/1/30.
 */
public class SelectHome extends Fragment implements View.OnClickListener {
    private View view;
    Button Property, door, homenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selecthome, container, false);
        intview();
        return view;
    }

    private void intview() {
        Property = (Button) view.findViewById(R.id.Property);
        Property.setOnClickListener(this);
        door = (Button) view.findViewById(R.id.door);
        door.setOnClickListener(this);
        homenu = (Button) view.findViewById(R.id.homenu);
        homenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.Property:
                intent.putExtra("type",1);
                intent.setClass(getActivity(),SelectMessage.class);
                getActivity().startActivity(intent);
                break;
            case R.id.door:
                intent.putExtra("type",2);
                intent.setClass(getActivity(),SelectMessage.class);
                getActivity().startActivity(intent);
                break;
            case R.id.homenu:
                intent.putExtra("type",3);
                intent.setClass(getActivity(),SelectMessage.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
