package ir.ngra.warehousekeeper.dagger.imageloader;


import com.nostra13.universalimageloader.core.ImageLoader;

import dagger.Component;

@Component(modules = ImageLoaderModule.class)
public interface ImageLoaderComponent {
    ImageLoader getImageLoader();
}