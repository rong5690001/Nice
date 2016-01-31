package com.nice.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.nice.R;
import com.nice.model.Event.SqIdEvent;
import com.nice.model.NicetSheet;
import com.nice.util.FileUtil;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class QuestionSignActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.back_icon)
    NiceImageView backIcon;
    @Bind(R.id.back_layout)
    RelativeLayout backLayout;
    @Bind(R.id.title)
    NiceTextView title;
    @Bind(R.id.right_icon)
    NiceImageView rightIcon;
    @Bind(R.id.right_text)
    NiceTextView rightText;
    @Bind(R.id.right_btn_layout)
    RelativeLayout rightBtnLayout;
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.quest_sign_takephoto)
    NiceImageView questSignTakephoto;
    @Bind(R.id.quest_sign_retakephoto)
    NiceImageView questSignRetakephoto;
    @Bind(R.id.rephoto_btn)
    LinearLayout rephotoBtn;
    @Bind(R.id.sign_commit_btn)
    LinearLayout signCommitBtn;
    @Bind(R.id.sign_bottom_tab)
    LinearLayout signBottomTab;
    private NicetSheet entity;
    private AMap aMap;
    private LocationManager locationManager;
    private double lng;
    private double lat;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    lng = amapLocation.getLongitude();
                    lat = amapLocation.getLatitude();
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    System.out.print(amapLocation + "11111111111111");
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    private String sqId;//上传图片
    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_sign);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        map.onCreate(savedInstanceState);
        System.out.print("ffffffffffffffff");
        initLayout();
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听

        mLocationClient.setLocationListener(mLocationListener);
        System.out.print("dd"+lng+"---"+lat);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        entity = (NicetSheet) getIntent().getSerializableExtra("entity");
//        if (null != entity)
//            initLayout();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 8, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updatePosition(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission(QuestionSignActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(QuestionSignActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                updatePosition(locationManager.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        LatLng pos = new LatLng(lat,lng);
        CameraUpdate cu = CameraUpdateFactory.changeLatLng(pos);
        aMap.moveCamera(cu);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(pos);
        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        markerOption.draggable(true);
        Marker marker = aMap.addMarker(markerOption);

//        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
//        aMap.setMyLocationEnabled(true);
    }

    private void initLayout() {
        title.setText("签到");
        rightBtnLayout.setVisibility(View.GONE);
        if(aMap == null){
            aMap = map.getMap();
        }
    }

    private void updatePosition(Location location){
        LatLng pos = new LatLng(location.getLatitude(),location.getLongitude());
        CameraUpdate cu = CameraUpdateFactory.changeLatLng(pos);
        aMap.moveCamera(cu);
        aMap.clear();
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(pos);
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_blue));
        markerOption.draggable(true);
        Marker marker = aMap.addMarker(markerOption);
    }


    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            FileUtil.savePhoto(data, sqId);
        }
    }

    public void onEventMainThread(SqIdEvent event){
        sqId = event.sqId;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_commit_btn:
                Intent intent1 = new Intent(QuestionSignActivity.this, QuestionContextActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("entity", entity);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.quest_sign_takephoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                break;
            case R.id.quest_sign_retakephoto:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, 1);
                break;
            case R.id.back_layout:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
