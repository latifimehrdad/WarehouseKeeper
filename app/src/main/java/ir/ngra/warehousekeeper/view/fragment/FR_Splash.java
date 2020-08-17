package ir.ngra.warehousekeeper.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.FrSplashBinding;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.viewmodel.VM_Splash;

public class FR_Splash extends FR_Primary implements FR_Primary.getActionFromObservable{


    private VM_Splash vm_splash;
    private NavController navController;

    @BindView(R.id.linearLayoutRefresh)
    LinearLayout linearLayoutRefresh;

    @BindView(R.id.gifViewLoading)
    GifView gifViewLoading;


    //______________________________________________________________________________________________ FR_Splash
    public FR_Splash() {
    }
    //______________________________________________________________________________________________ FR_Splash


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FrSplashBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_splash, container, false);
            vm_splash = new VM_Splash(getContext());
            binding.setSplash(vm_splash);
            setView(binding.getRoot());
            setOnclick();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                FR_Splash.this,
                vm_splash.getPublishSubject(),
                vm_splash);
        if (getView() != null)
            navController = Navigation.findNavController(getView());

        checkToken();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifViewLoading.setVisibility(View.GONE);
/*
        if (action.equals(StaticValues.ML_GoToHome)) {
            navController.navigate(R.id.action_splash_to_home);
            return;
        }*/

        if (action.equals(StaticValues.ML_GoToUpdate)) {
            if (getContext() != null) {
                Bundle bundle = new Bundle();
                bundle.putString(getContext().getResources().getString(R.string.ML_UpdateUrl), vm_splash.getMd_hi().getApplicationUrl());
                bundle.putString(getContext().getResources().getString(R.string.ML_UpdateFile), vm_splash.getMd_hi().getFileName());
                navController.navigate(R.id.action_FR_Splash_to_FR_AppUpdate, bundle);
            }
            return;
        }

        if (action.equals(StaticValues.ML_GotoLogin)) {
            navController.navigate(R.id.action_FR_Splash_to_FR_Login);
            return;
        }

        if (action.equals(StaticValues.ML_ResponseFailure)
                || action.equals(StaticValues.ML_ResponseError)
                || action.equals(StaticValues.ML_RequestCancel)) {
            linearLayoutRefresh.setVisibility(View.VISIBLE);
        }

    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ checkToken
    private void checkToken() {
        gifViewLoading.setVisibility(View.VISIBLE);
        linearLayoutRefresh.setVisibility(View.GONE);
        vm_splash.callHI();
    }
    //______________________________________________________________________________________________ checkToken



    //______________________________________________________________________________________________ setOnclick
    private void setOnclick() {
        linearLayoutRefresh.setOnClickListener(v -> checkToken());
    }
    //______________________________________________________________________________________________ setOnclick



}
