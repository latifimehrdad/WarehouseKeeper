package ir.ngra.warehousekeeper.dagger.utility;


import dagger.Module;
import dagger.Provides;
import ir.ngra.warehousekeeper.dagger.DaggerScope;
import ir.ngra.warehousekeeper.utility.ApplicationUtility;

@Module
public class UtilityModule {

    @Provides
    @DaggerScope
    public ApplicationUtility getApplicationUtility() {
        return new ApplicationUtility();
    }
}
