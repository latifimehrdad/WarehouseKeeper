package ir.ngra.warehousekeeper.dagger.utility;

import dagger.Component;
import ir.ngra.warehousekeeper.dagger.DaggerScope;
import ir.ngra.warehousekeeper.utility.ApplicationUtility;

@DaggerScope
@Component(modules = UtilityModule.class)
public interface UtilityComponent {

    ApplicationUtility getApplicationUtility();
}
