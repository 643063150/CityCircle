package citycircle.com.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import citycircle.com.R;

/**
 * Created by admins on 2016/8/8.
 */
public class Statement extends Activity {
    ImageView back;
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statement);
        back=(ImageView)findViewById(R.id.back);
        webview=(WebView)findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/statement.html");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
