package ir.ngra.warehousekeeper.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.ngra.warehousekeeper.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private boolean preLogin = false;
    boolean doubleBackToExitPressedOnce = false;

    //______________________________________________________________________________________________ onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermission();
        setListener();
    }
    //______________________________________________________________________________________________ onCreate



    //______________________________________________________________________________________________ setPermission
    public void setPermission() {

        int permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionInstall = ContextCompat.checkSelfPermission(this, Manifest.permission.REQUEST_INSTALL_PACKAGES);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionRead != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionInstall != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.REQUEST_INSTALL_PACKAGES);


        if (permissionWrite != PackageManager.PERMISSION_GRANTED)
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    0);
        }

    }
    //______________________________________________________________________________________________ setPermission




    //______________________________________________________________________________________________ attachBaseContext
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    //______________________________________________________________________________________________ attachBaseContext




    //______________________________________________________________________________________________ setListener
    @SuppressLint("RtlHardcoded")
    private void setListener() {

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

/*        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            String fragment = "";
            if (destination.getLabel() != null)
                fragment = destination.getLabel().toString();

            if ((fragment.equalsIgnoreCase("FR_Splash")) ||
                    (fragment.equalsIgnoreCase("FR_Login")) ||
                    (fragment.equalsIgnoreCase("FR_AppUpdate"))) {
                if (!preLogin) {
                    preLogin = true;
                    NavInflater navInflater = navController.getNavInflater();
                    NavGraph graph = navInflater.inflate(R.navigation.nav_host);
                    graph.setStartDestination(R.id.FR_Splash);
                    navController.setGraph(graph);
                }

            } else {
                if (preLogin) {
                    preLogin = false;
                    NavInflater navInflater = navController.getNavInflater();
                    NavGraph graph = navInflater.inflate(R.navigation.nav_host);
                    graph.setStartDestination(R.id.FR_Home);
                    navController.setGraph(graph);
                }
            }

        });*/
    }
    //______________________________________________________________________________________________ setListener



    @Override
    public void onBackPressed() {//_________________________________________________________________ Start onBackPressed

        NavDestination navDestination = navController.getCurrentDestination();
        String fragment = navDestination.getLabel().toString();
        if ((!fragment.equalsIgnoreCase("FR_Home"))) {
            super.onBackPressed();
            return;
        }


        if (doubleBackToExitPressedOnce) {
            System.exit(1);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج 2 بار کلیک کنید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }//_____________________________________________________________________________________________ End onBackPressed


}