package ir.ngra.warehousekeeper.view.application;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.dagger.retrofit.DaggerRetrofitComponent;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitComponent;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitModule;

public class WarehouseKeeper extends MultiDexApplication {


    private Context context;
    private RetrofitComponent retrofitComponent;


    //______________________________________________________________________________________________ onCreate
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        configurationCalligraphy();
        configurationRetrofitComponent();
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ configurationCalligraphy
    private void configurationCalligraphy() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/iransanslight.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }
    //______________________________________________________________________________________________ configurationCalligraphy


    //______________________________________________________________________________________________ configurationRetrofitComponent
    private void configurationRetrofitComponent() {
        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule(context))
                .build();
    }
    //______________________________________________________________________________________________ configurationRetrofitComponent


    //______________________________________________________________________________________________ getWarehouseKeeper
    public static WarehouseKeeper getWarehouseKeeper(Context context) {
        return (WarehouseKeeper) context.getApplicationContext();
    }
    //______________________________________________________________________________________________ getWarehouseKeeper


    //______________________________________________________________________________________________ getRetrofitComponent
    public RetrofitComponent getRetrofitComponent() {
        return retrofitComponent;
    }
    //______________________________________________________________________________________________ getRetrofitComponent



}
