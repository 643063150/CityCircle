package citycircle.com.Property.PropertyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.R;
import citycircle.com.Utils.DateUtils;

/**
 * Created by admins on 2016/2/24.
 */
public class PaysAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    Context context;
    DateUtils dateUtils;
    public PaysAdapter(ArrayList<HashMap<String, String>> arrayList ,Context context){
        this.arrayList=arrayList;
        this.context=context;
        dateUtils=new DateUtils();
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
        getItemView getItemView=new getItemView();
        convertView= LayoutInflater.from(context).inflate(R.layout.paysitem,null);
        getItemView.title=(TextView)convertView.findViewById(R.id.title);
        getItemView.money=(TextView)convertView.findViewById(R.id.money);
        getItemView.time=(TextView)convertView.findViewById(R.id.time);
        String time=dateUtils.getDateToStrings(Long.parseLong(arrayList.get(position).get("create_time")));
        getItemView.title.setText(arrayList.get(position).get("bak"));
        getItemView.money.setText(arrayList.get(position).get("money")+"元");
        getItemView.time.setText(time);
        return convertView;
    }
    private class getItemView{
        TextView title,money,time;
    }
}
