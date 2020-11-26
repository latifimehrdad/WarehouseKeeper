package ir.ngra.warehousekeeper.viewmodel;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitApis;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitComponent;
import ir.ngra.warehousekeeper.model.MD_Hi;
import ir.ngra.warehousekeeper.model.MD_ProfileInfo;
import ir.ngra.warehousekeeper.model.MD_Token;
import ir.ngra.warehousekeeper.model.MR_Hi;
import ir.ngra.warehousekeeper.utility.StaticFunctions;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.view.application.WarehouseKeeper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_Splash extends VM_Primary {

    private MD_Token md_Token;
    private MD_ProfileInfo.MD_Profile profile;
    private MD_Hi md_hi;

    //______________________________________________________________________________________________ VM_Splash
    public VM_Splash(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Splash


    //______________________________________________________________________________________________ callHI
    public void callHI() {
        callHiService();
    }
    //______________________________________________________________________________________________ callHI


    //______________________________________________________________________________________________ checkToken
    public void checkToken() {

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs == null) {
            getTokenFromServer();
        } else {
            String accessToken = prefs.getString(getContext().getString(R.string.ML_AccessToken), null);
            String expires = prefs.getString(getContext().getString(R.string.ML_Expires), null);
            String phoneNumber = prefs.getString(getContext().getString(R.string.ML_PhoneNumber), null);
            if ((accessToken == null) || (expires == null))
                getTokenFromServer();
            else {
                if (phoneNumber != null) {
                    getLoginInformation();
                } else
                    getTokenFromServer();
            }
        }
    }
    //______________________________________________________________________________________________ checkToken


    //______________________________________________________________________________________________ getTokenFromServer
    public void getTokenFromServer() {

        RetrofitComponent retrofitComponent = WarehouseKeeper
                .getWarehouseKeeper(getContext())
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getToken(
                        RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value));

        getPrimaryCall().enqueue(new Callback<MD_Token>() {
            @Override
            public void onResponse(Call<MD_Token> call, Response<MD_Token> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    md_Token = response.body();
                    if (StaticFunctions.saveToken(getContext(), md_Token))
                        sendActionToObservable(StaticValues.ML_GotoLogin);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MD_Token> call, Throwable t) {
                onFailureRequest();
            }
        });
    }
    //______________________________________________________________________________________________ getTokenFromServer


    //______________________________________________________________________________________________ getLoginInformation
    public void getLoginInformation() {

        RetrofitComponent retrofitComponent =
                WarehouseKeeper
                        .getWarehouseKeeper(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        authorization));

        getPrimaryCall().enqueue(new Callback<MD_ProfileInfo>() {
            @Override
            public void onResponse(Call<MD_ProfileInfo> call, Response<MD_ProfileInfo> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    profile = response.body().getResult();
                    if (profile != null) {
                        if (StaticFunctions.saveProfile(getContext(), profile))
                            sendActionToObservable(StaticValues.ML_GoToHome);
                    } else {
                        if (StaticFunctions.logOut(getContext()))
                            getTokenFromServer();
                    }
                } else
                    refreshToken();
            }

            @Override
            public void onFailure(Call<MD_ProfileInfo> call, Throwable t) {
                onFailureRequest();
            }
        });
    }
    //______________________________________________________________________________________________ getLoginInformation


    //______________________________________________________________________________________________ refreshToken
    public void refreshToken() {

        RetrofitComponent retrofitComponent =
                WarehouseKeeper
                        .getWarehouseKeeper(getContext())
                        .getRetrofitComponent();

        String refresh_token = getRefreshTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getRefreshToken(
                        RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value_Refresh_Token,
                        refresh_token));

        getPrimaryCall().enqueue(new Callback<MD_Token>() {
            @Override
            public void onResponse(Call<MD_Token> call, Response<MD_Token> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    md_Token = response.body();
                    if (StaticFunctions.saveToken(getContext(), md_Token))
                        sendActionToObservable(StaticValues.ML_GoToHome);
                } else {
                    StaticFunctions.logOut(getContext());
                    getTokenFromServer();
                }
            }

            @Override
            public void onFailure(Call<MD_Token> call, Throwable t) {
                onFailureRequest();
            }
        });
    }
    //______________________________________________________________________________________________ refreshToken


    //______________________________________________________________________________________________ callHiService
    public void callHiService() {

        RetrofitComponent retrofitComponent =
                WarehouseKeeper
                        .getWarehouseKeeper(getContext())
                        .getRetrofitComponent();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getHi(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        getContext().getResources().getString(R.string.UpdateAppName)));

        getPrimaryCall().enqueue(new Callback<MR_Hi>() {
            @Override
            public void onResponse(Call<MR_Hi> call, Response<MR_Hi> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    md_hi = response.body().getResult();
                    setResponseMessage("");
                    checkUpdate();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_Hi> call, Throwable t) {
                onFailureRequest();
            }
        });
    }
    //______________________________________________________________________________________________ callHiService


    //______________________________________________________________________________________________ checkUpdate
    private void checkUpdate() {
        PackageInfo pInfo;
        float versionName = 0;
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            versionName = Float.valueOf(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        String v = md_hi.getVersion();
        v = v.replaceAll("v", "");
        v = v.replaceAll("V", "");
        float lastVersion = Float.valueOf(v);


        if (versionName < lastVersion)
            sendActionToObservable(StaticValues.ML_GoToUpdate);
        else
            checkToken();
    }
    //______________________________________________________________________________________________ checkUpdate


    //______________________________________________________________________________________________ getMd_hi
    public MD_Hi getMd_hi() {
        return md_hi;
    }
    //______________________________________________________________________________________________ getMd_hi

}



