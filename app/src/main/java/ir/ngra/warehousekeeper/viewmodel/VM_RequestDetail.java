package ir.ngra.warehousekeeper.viewmodel;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitComponent;
import ir.ngra.warehousekeeper.model.MD_Collect;
import ir.ngra.warehousekeeper.model.MD_ItemWaste;
import ir.ngra.warehousekeeper.model.MD_ItemsWasteList;
import ir.ngra.warehousekeeper.model.MD_WasteAmountRequests;
import ir.ngra.warehousekeeper.model.MD_Weight;
import ir.ngra.warehousekeeper.model.MR_ItemsWast;
import ir.ngra.warehousekeeper.model.MR_Primary;
import ir.ngra.warehousekeeper.model.MR_Weights;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.view.application.WarehouseKeeper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_RequestDetail extends VM_Primary {


    private List<MD_ItemWaste> md_itemWastes;
    private List<MD_Weight> md_weights;


    //______________________________________________________________________________________________ VM_RequestDetail
    public VM_RequestDetail(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_RequestDetail



    //______________________________________________________________________________________________ getItemsOfWast
    public void getItemsOfWast() {

        RetrofitComponent retrofitComponent =
                WarehouseKeeper
                        .getWarehouseKeeper(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getWasteList(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_ItemsWast>() {
            @Override
            public void onResponse(Call<MR_ItemsWast> call, Response<MR_ItemsWast> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_itemWastes = response.body().getItemsWast();
                    getWeights();
                }
                else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_ItemsWast> call, Throwable t) {
                onFailureRequest();
            }
        });



    }
    //______________________________________________________________________________________________ getItemsOfWast



    //______________________________________________________________________________________________ getWeights
    public void getWeights() {

        RetrofitComponent retrofitComponent =
                WarehouseKeeper
                        .getWarehouseKeeper(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getWeights(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_Weights>() {
            @Override
            public void onResponse(Call<MR_Weights> call, Response<MR_Weights> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_weights = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetItemsOfWasteIsSuccess);
                }
                else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Weights> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getWeights



    //______________________________________________________________________________________________ sendVerifyCode
    public void sendVerifyCode(String RequestCode, List<MD_ItemsWasteList> wasteLists) {

        List<MD_Collect> collects = new ArrayList<>();
        for (MD_ItemsWasteList wasteList: wasteLists) {
            MD_ItemWaste waste = new MD_ItemWaste(wasteList.getId(), "", "");
            MD_Weight weightKg = new MD_Weight(wasteList.getAmount1Id(),"",false);
            collects.add(new MD_Collect(waste,wasteList.getAmount1(), weightKg));
            MD_Weight weightGr = new MD_Weight(wasteList.getAmount2Id(),"",false);
            collects.add(new MD_Collect(waste,wasteList.getAmount2(), weightGr));
        }

        MD_WasteAmountRequests wasteAmountRequests = new MD_WasteAmountRequests(RequestCode, collects);

        RetrofitComponent retrofitComponent =
                WarehouseKeeper
                        .getWarehouseKeeper(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .LadingRequestCollection(
                        wasteAmountRequests,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    sendActionToObservable(StaticValues.ML_GotoVerify);
                }
                else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                onFailureRequest();
            }
        });


    }
    //______________________________________________________________________________________________ sendVerifyCode



    //______________________________________________________________________________________________ verifyCode
    public void verifyCode(String RequestCode, String VerifyCode) {

        RetrofitComponent retrofitComponent =
                WarehouseKeeper
                        .getWarehouseKeeper(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .ConfirmRequest(
                        RequestCode,
                        VerifyCode,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    wasteDeliver(RequestCode);
                }
                else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ verifyCode



    //______________________________________________________________________________________________ reTryVerifyCode
    public void reTryVerifyCode(String RequestCode) {

        RetrofitComponent retrofitComponent =
                WarehouseKeeper
                        .getWarehouseKeeper(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .ReTryVerifyCode(
                        RequestCode,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) { ;
                    sendActionToObservable(StaticValues.ML_GotoVerify);
                }
                else {
                    sendActionToObservable(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ reTryVerifyCode




    //______________________________________________________________________________________________ wasteDeliver
    public void wasteDeliver(String RequestCode) {

        RetrofitComponent retrofitComponent = WarehouseKeeper
                .getWarehouseKeeper(getContext())
                .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .WasteCollectionDeliver(
                        RequestCode,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessage(response.body()));
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





    public List<MD_ItemWaste> getMd_itemWastes() {//________________________________________________ getMd_itemWasts
        if (md_itemWastes == null)
            md_itemWastes = new ArrayList<>();

        return md_itemWastes;
    }//_____________________________________________________________________________________________ getMd_itemWasts




    public List<MD_Weight> getMd_weights() {//______________________________________________________ getMd_weights
        if (md_itemWastes == null)
            md_itemWastes = new ArrayList<>();

        return md_weights;
    }//_____________________________________________________________________________________________ getMd_weights
}
