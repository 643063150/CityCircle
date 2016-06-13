package citycircle.com.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.R;

/**
 * Created by admins on 2016/6/13.
 */
public class RecomAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> arrayList;
    Context context;

    public RecomAdapter(ArrayList<HashMap<String, String>> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        getitem getitem=new getitem();
        if (Integer.parseInt(arrayList.get(position).get("category_id"))==98){
            convertView= LayoutInflater.from(context).inflate(R.layout.renews_item,null);
            getitem.name=(TextView)convertView.findViewById(R.id.name);
            getitem.title=(TextView)convertView.findViewById(R.id.title);
            getitem.views=(TextView)convertView.findViewById(R.id.views);
            getitem.shopimg=(ImageView) convertView.findViewById(R.id.shopimg);
        }else {

        }
        return convertView;
    }
    private class getitem{
        TextView title,name,views;
        ImageView shopimg;
    }
}
