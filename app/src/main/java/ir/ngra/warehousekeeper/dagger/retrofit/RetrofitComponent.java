package ir.ngra.warehousekeeper.dagger.retrofit;


import dagger.Component;
import ir.ngra.warehousekeeper.dagger.DaggerScope;

@DaggerScope
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    RetrofitApiInterface getRetrofitApiInterface();
}
