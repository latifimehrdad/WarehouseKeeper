package ir.ngra.warehousekeeper.viewmodel;

import android.app.Activity;

import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitApis;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitComponent;
import ir.ngra.warehousekeeper.model.MD_ProfileInfo;
import ir.ngra.warehousekeeper.model.MD_Token;
import ir.ngra.warehousekeeper.utility.StaticFunctions;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.view.application.WarehouseKeeper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_Login extends VM_Primary {

    private MD_Token md_token;

    //______________________________________________________________________________________________ VM_FragmentLogin
    public VM_Login(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_FragmentLogin


    //______________________________________________________________________________________________ GetLoginToken
    public void GetLoginToken(String PhoneNumber, String Password) {

        RetrofitComponent retrofitComponent = WarehouseKeeper
                .getWarehouseKeeper(getContext())
                .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .Login(RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_password,
                        PhoneNumber,
                        Password,
                        authorization));

        getPrimaryCall().enqueue(new Callback<MD_Token>() {
            @Override
            public void onResponse(Call<MD_Token> call, Response<MD_Token> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    md_token = response.body();
                    if (StaticFunctions.saveToken(getContext(), md_token))
                        getLoginInformation();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MD_Token> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ GetLoginToken


    //______________________________________________________________________________________________ getLoginInformation
    public void getLoginInformation() {

        RetrofitComponent retrofitComponent =
                WarehouseKeeper
                        .getWarehouseKeeper(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getSettingInfo(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MD_ProfileInfo>() {
            @Override
            public void onResponse(Call<MD_ProfileInfo> call, Response<MD_ProfileInfo> response) {
                setResponseMessage(checkResponse(response, true));
                if (getResponseMessage() == null) {
                    if (StaticFunctions.saveProfile(getContext(), response.body().getResult()))
                        sendActionToObservable(StaticValues.ML_GoToHome);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MD_ProfileInfo> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getLoginInformation

}
