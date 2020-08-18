package ir.ngra.warehousekeeper.view.application;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.memory.BaseMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.lang.ref.Reference;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.dagger.imageloader.DaggerImageLoaderComponent;
import ir.ngra.warehousekeeper.dagger.imageloader.ImageLoaderComponent;
import ir.ngra.warehousekeeper.dagger.imageloader.ImageLoaderModule;
import ir.ngra.warehousekeeper.dagger.retrofit.DaggerRetrofitComponent;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitComponent;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitModule;
import ir.ngra.warehousekeeper.dagger.utility.DaggerUtilityComponent;
import ir.ngra.warehousekeeper.dagger.utility.UtilityComponent;
import ir.ngra.warehousekeeper.dagger.utility.UtilityModule;

public class WarehouseKeeper extends MultiDexApplication {


    private Context context;
    private RetrofitComponent retrofitComponent;
    private ImageLoaderComponent imageLoaderComponent;
    private UtilityComponent utilityComponent;


    //______________________________________________________________________________________________ onCreate
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        configurationCalligraphy();
        configurationRetrofitComponent();
        configurationImageLoader();
        configurationUtilityComponent();
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ configurationUtilityComponent
    private void configurationUtilityComponent() {
        utilityComponent = DaggerUtilityComponent
                .builder()
                .utilityModule(new UtilityModule())
                .build();
    }
    //______________________________________________________________________________________________ configurationUtilityComponent




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



    //______________________________________________________________________________________________ configurationImageLoader
    private void configurationImageLoader() {

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new BaseMemoryCache() {
                    @Override
                    protected Reference<Bitmap> createReference(Bitmap value) {
                        return null;
                    }
                })
                .threadPoolSize(3)
                .diskCacheSize(100 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);

        imageLoaderComponent = DaggerImageLoaderComponent.builder().imageLoaderModule(new ImageLoaderModule()).build();
    }
    //______________________________________________________________________________________________ configurationImageLoader




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



    //______________________________________________________________________________________________ getImageLoaderComponent
    public ImageLoaderComponent getImageLoaderComponent() {
        return imageLoaderComponent;
    }
    //______________________________________________________________________________________________ getImageLoaderComponent



    //______________________________________________________________________________________________ getUtilityComponent
    public UtilityComponent getUtilityComponent() {
        return utilityComponent;
    }
    //______________________________________________________________________________________________ getUtilityComponent



}
