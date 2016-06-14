package citycircle.com.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.R;
import citycircle.com.Utils.ImageUtils;

/**
 * Created by admins on 2016/6/14.
 */
public class Camadapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> arrayList;
    Context context;
    com.nostra13.universalimageloader.core.ImageLoader ImageLoader;
    DisplayImageOptions options;
    citycircle.com.Utils.ImageUtils ImageUtils;
    ImageLoadingListener animateFirstListener;
    public Camadapter(ArrayList<HashMap<String, String>> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        ImageUtils = new ImageUtils();
        ImageLoader = ImageLoader.getInstance();
        ImageLoader.init(ImageLoaderConfiguration.createDefault(context));
        animateFirstListener = new ImageUtils.AnimateFirstDisplayListener();
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
        convertView= LayoutInflater.from(context).inflate(R.layout.cam_item,null);
        getitem.title=(TextView)convertView.findViewById(R.id.title);
        getitem.banner=(ImageView) convertView.findViewById(R.id.banner);
        getitem.title.setText(arrayList.get(position).get("title"));
        options=ImageUtils.setcenterOptions();
        String url=arrayList.get(position).get("url");
        ImageLoader.displayImage(url,getitem.banner,options,animateFirstListener);
        return convertView;
    }
    private class getitem{
        TextView title;
        ImageView banner;
    }
}
