package citycircle.com.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import citycircle.com.R;

/**
 * Created by admins on 2016/6/2.
 */
public class ShopInfo extends Activity implements View.OnClickListener {
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopinfo);
        intview();
    }
private void intview(){
    back=(ImageView)findViewById(R.id.back);
    back.setOnClickListener(this);
}
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
