package citycircle.com.MyAppService;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class LocationApplication extends Application {
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    public double longitude, latitude, a, conlongitude, conlatitude;
    public Vibrator mVibrator;
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    private String tempcoor = "bd09ll";
    LocationClientOption locationClientOption;
    public String adress,getStreet;
    public String city,District;

    @Override
    public void onCreate() {
        super.onCreate();
        // System.out.println("getApplicationContext()"+getApplicationContext());
        SDKInitializer.initialize(this.getApplicationContext());
        mLocationClient = new LocationClient(this.getApplicationContext());
        //初始化imgload
        File diskCache = StorageUtils.getOwnCacheDirectory(this.getApplicationContext(),
                "citycircle/Cache");
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                this.getApplicationContext()).memoryCacheExtraOptions(400, 800).discCacheFileCount(200)
                .discCache(new UnlimitedDiskCache(diskCache)).build();
        ImageLoader.getInstance().init(configuration);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        locationClientOption = new LocationClientOption();
        locationClientOption.setLocationMode(tempMode);//设置定位模式
        locationClientOption.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
        locationClientOption.setIsNeedAddress(true);
        mLocationClient.setLocOption(locationClientOption);
        mVibrator = (Vibrator) getApplicationContext().getSystemService(
                Service.VIBRATOR_SERVICE);
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            adress = location.getAddrStr();
            city = location.getCity();
            District=location.getDistrict();
            getStreet=location.getStreet();
            System.out.println("longitude:" + longitude + "latitude:"
                    + latitude);
            System.out.println("adressa:" + adress);
        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.out.println("+++++++++++");
    }

}
