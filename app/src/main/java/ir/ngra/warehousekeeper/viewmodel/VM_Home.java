package ir.ngra.warehousekeeper.viewmodel;

import android.app.Activity;

import java.util.List;

import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitApis;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitComponent;
import ir.ngra.warehousekeeper.model.MD_WasteRequest;
import ir.ngra.warehousekeeper.model.MR_Primary;
import ir.ngra.warehousekeeper.model.MR_ResponseWaste;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.view.application.WarehouseKeeper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Home extends VM_Primary {


    private List<MD_WasteRequest> md_wasteRequests;


    //______________________________________________________________________________________________ VM_Home
    public VM_Home(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Home


    //______________________________________________________________________________________________ getWasteCollection
    public void getWasteCollection(Byte state) {

        RetrofitComponent retrofitComponent = WarehouseKeeper
                .getWarehouseKeeper(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getWasteCollection(
                        state,
                        RetrofitApis.app_token,
                        aToken,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_ResponseWaste>() {
            @Override
            public void onResponse(Call<MR_ResponseWaste> call, Response<MR_ResponseWaste> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_wasteRequests = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetWasteCollection);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_ResponseWaste> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getWasteCollection



    //______________________________________________________________________________________________ wasteDeliver
    public void wasteDeliver(String RequestCode) {

        RetrofitComponent retrofitComponent = WarehouseKeeper
                .getWarehouseKeeper(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .WasteCollectionDeliver(
                        RequestCode,
                        RetrofitApis.app_token,
                        aToken,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    sendActionToObservable(StaticValues.WasteCollectionStateDelivered);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ wasteDeliver



    //______________________________________________________________________________________________ wasteNotDeliver
    public void wasteNotDeliver(String RequestCode) {

        RetrofitComponent retrofitComponent = WarehouseKeeper
                .getWarehouseKeeper(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .WasteCollectionNotDeliver(
                        RequestCode,
                        RetrofitApis.app_token,
                        aToken,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
                    sendActionToObservable(StaticValues.WasteCollectionStateNoDelivery);
                } else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ wasteNotDeliver



    //______________________________________________________________________________________________ getMd_wasteRequests
    public List<MD_WasteRequest> getMd_wasteRequests() {
        return md_wasteRequests;
    }
    //______________________________________________________________________________________________ getMd_wasteRequests


}
