package citycircle.com.Property;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import citycircle.com.R;

/**
 * Created by admins on 2016/2/19.
 */
public class HouseAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, String>> array;
    Context context;
   Handler handler=new Handler();
    private Animation animation;
    private Map<Integer, Boolean> isFrist;
    public HouseAdapter(ArrayList<HashMap<String, String>> array, Context context,Handler handler) {
        this.array = array;
        this.context = context;
        this.handler=handler;
        animation = AnimationUtils.loadAnimation(context, R.anim.woniu_list_item);
        isFrist = new HashMap<Integer, Boolean>();
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final getItemView getItemView=new getItemView();
        convertView = LayoutInflater.from(context).inflate(R.layout.houseitem, null);
        if (isFrist.get(position) == null || isFrist.get(position)) {
            convertView.startAnimation(animation);
            isFrist.put(position, false);
        }
        getItemView.adress=(TextView)convertView.findViewById(R.id.adress);
        getItemView.title=(TextView)convertView.findViewById(R.id.title);
        getItemView.yanzheng=(TextView)convertView.findViewById(R.id.yanzheng);
        getItemView.del=(TextView)convertView.findViewById(R.id.del);
        getItemView.defaults=(TextView)convertView.findViewById(R.id.defaults);
        getItemView.adress.setText(array.get(position).get("xiaoqu")+array.get(position).get("louhao")+array.get(position).get("fanghao"));
        getItemView.title.setText(array.get(position).get("xiaoqu"));
        if (array.get(position).get("houseid").equals("80")){
            Drawable drawable=context.getDrawable(R.mipmap.checkbox_blue_selected);
            drawable.setBounds(0, 0,drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            getItemView.defaults.setCompoundDrawables(drawable,null,null,null);
        }
        getItemView.defaults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(5);
            }
        });
        getItemView.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(5);
            }
        });
        return convertView;
    }

    private class getItemView {
        TextView title,adress,yanzheng,del;
        TextView defaults;
    }
}
