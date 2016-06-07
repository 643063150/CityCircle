package citycircle.com.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import citycircle.com.R;

/**
 * Created by admins on 2016/5/31.
 */
public class VipcardInfo extends Activity implements View.OnClickListener{
    CardView cardView;
    TextView shopinfo;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vipcardinfo);
        intview();
    }
    private void intview(){
        cardView=(CardView)findViewById(R.id.cardview);
        shopinfo=(TextView)findViewById(R.id.shopinfo);
        back=(ImageView)findViewById(R.id.back);
        shopinfo.setOnClickListener(this);
        back.setOnClickListener(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.app);
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getLightVibrantSwatch();
                if (vibrant!=null){
                    cardView.setCardBackgroundColor(vibrant.getRgb());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shopinfo:
                Intent intent=new Intent();
                intent.setClass(VipcardInfo.this,ShopInfo.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
