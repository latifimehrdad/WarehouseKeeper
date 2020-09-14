package ir.ngra.warehousekeeper.viewmodel;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.subjects.PublishSubject;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitApis;
import ir.ngra.warehousekeeper.dagger.retrofit.RetrofitComponent;
import ir.ngra.warehousekeeper.model.MD_Message;
import ir.ngra.warehousekeeper.model.MD_Token;
import ir.ngra.warehousekeeper.model.MR_Primary;
import ir.ngra.warehousekeeper.utility.StaticFunctions;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.view.application.WarehouseKeeper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Primary {

    private PublishSubject<Byte> publishSubject;
    private String responseMessage;
    private Call primaryCall;
    private Activity context;
    private int responseCode;

    //______________________________________________________________________________________________ VM_Primary
    public VM_Primary() {
        publishSubject = PublishSubject.create();
    }
    //______________________________________________________________________________________________ VM_Primary


    //______________________________________________________________________________________________ getAuthorizationTokenFromSharedPreferences
    public String getAuthorizationTokenFromSharedPreferences() {
        String authorization = "Bearer ";
        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            String access_token = prefs.getString(context.getString(R.string.ML_AccessToken), null);
            if (access_token != null)
                authorization = authorization + access_token;
        }
        return authorization;
    }
    //______________________________________________________________________________________________ getAuthorizationTokenFromSharedPreferences


    //______________________________________________________________________________________________ getRefreshTokenFromSharedPreferences
    public String getRefreshTokenFromSharedPreferences() {
        String authorization = "";
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            String access_token = prefs.getString(context.getString(R.string.ML_RefreshToken), null);
            if (access_token != null)
                authorization = authorization + access_token;
        }
        return authorization;
    }
    //______________________________________________________________________________________________ getRefreshTokenFromSharedPreferences


    //______________________________________________________________________________________________ checkResponse
    public String checkResponse(Response response, Boolean authorization) {
        if (response.body() != null)
            return null;
        else {
            if (authorization)
                return checkResponseIfThatIsAuthorization(response);
            else
                return checkResponseIfThatIsGeneral(response);
        }
    }
    //______________________________________________________________________________________________ checkResponse


    //______________________________________________________________________________________________ checkResponseIfThatIsAuthorization
    private String checkResponseIfThatIsAuthorization(Response response) {

        JSONObject jObjError = null;
        try {
            setResponseCode(response.code());
            jObjError = new JSONObject(response.errorBody().string());
            return jObjError.getString("error_description");
        } catch (Exception ex) {
            try {
                if (jObjError != null) {
                    return jObjError.getString("message");
                } else
                    return getContext().getResources().getString(R.string.NetworkError);
            } catch (JSONException e) {
                e.printStackTrace();
                return getContext().getResources().getString(R.string.NetworkError);
            }
        }

    }
    //______________________________________________________________________________________________ checkResponseIfThatIsAuthorization


    //______________________________________________________________________________________________ checkResponseIfThatIsGeneral
    public String checkResponseIfThatIsGeneral(Response response) {
        try {
            setResponseCode(response.code());
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            String jobErrorString = jObjError.toString();
            StringBuilder message = new StringBuilder();
            if (jobErrorString.contains("messages")) {
                JSONArray jsonArray = jObjError.getJSONArray("messages");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = new JSONObject(jsonArray.get(i).toString());
                    message.append(temp.getString("message"));
                    message.append("\n");
                }
            } else {
                message.append(jObjError.getString("message"));
            }
            return message.toString();
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    //______________________________________________________________________________________________ checkResponseIfThatIsGeneral


    //______________________________________________________________________________________________ getResponseMessage
    public String getResponseMessage(MR_Primary mrPrimary) {
        try {
            ArrayList<MD_Message> modelMessages = mrPrimary.getMessages();
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < modelMessages.size(); i++) {
                message.append(modelMessages.get(i).getMessage());
                message.append("\n");
            }
            return message.toString();
        } catch (Exception ex) {
            return getContext().getResources().getString(R.string.NetworkError);
        }
    }
    //______________________________________________________________________________________________ getResponseMessage


    //______________________________________________________________________________________________ reactionToIncorrectResponse
    public void reactionToIncorrectResponse(Byte action) {
        if (getResponseCode() == 401) {
            refreshToken();
        } else {
            publishSubject.onNext(action);
        }
    }
    //______________________________________________________________________________________________ reactionToIncorrectResponse


    //______________________________________________________________________________________________ refreshToken
    private void refreshToken() {


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
                    MD_Token md_token = response.body();
                    if (StaticFunctions.saveToken(getContext(), md_token)) {
                        setResponseMessage(getContext().getResources().getString(R.string.PleaseTryAgain));
                        publishSubject.onNext(StaticValues.ML_ResponseError);
                    }
                } else {
                    StaticFunctions.logOut(getContext());
                    System.exit(0);
                }
            }

            @Override
            public void onFailure(Call<MD_Token> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ refreshToken


    //______________________________________________________________________________________________ onFailureRequest
    public void onFailureRequest() {
        if (getPrimaryCall().isCanceled()) {
            setResponseMessage("");
            getPublishSubject().onNext(StaticValues.ML_RequestCancel);
        } else {
            setResponseMessage(getContext().getResources().getString(R.string.NetworkError));
            getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
        }
    }
    //______________________________________________________________________________________________ onFailureRequest


    //______________________________________________________________________________________________ cancelRequest
    public void cancelRequest() {
        if (primaryCall != null) {
            primaryCall.cancel();
            primaryCall = null;
        }
    }
    //______________________________________________________________________________________________ cancelRequest


    //______________________________________________________________________________________________ getPrimaryCall
    public Call getPrimaryCall() {
        return primaryCall;
    }
    //______________________________________________________________________________________________ getPrimaryCall


    //______________________________________________________________________________________________ setPrimaryCall
    public void setPrimaryCall(Call primaryCall) {
        hideKeyboard();
        cancelRequest();
        this.primaryCall = primaryCall;
    }
    //______________________________________________________________________________________________ setPrimaryCall


    //______________________________________________________________________________________________ getPublishSubject
    public PublishSubject<Byte> getPublishSubject() {
        return publishSubject;
    }
    //______________________________________________________________________________________________ getPublishSubject


    //______________________________________________________________________________________________ getResponseMessage
    public String getResponseMessage() {
        return responseMessage;
    }
    //______________________________________________________________________________________________ getResponseMessage


    //______________________________________________________________________________________________ setResponseMessage
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    //______________________________________________________________________________________________ setResponseMessage


    //______________________________________________________________________________________________ getContext
    public Activity getContext() {
        return context;
    }
    //______________________________________________________________________________________________ getContext


    //______________________________________________________________________________________________ setContext
    public void setContext(Activity context) {
        this.context = context;
    }
    //______________________________________________________________________________________________ setContext


    //______________________________________________________________________________________________ sendActionToObservable
    public void sendActionToObservable(Byte action) {
        Handler handler = new Handler();
        if (action.equals(StaticValues.ML_ResponseError))
            handler.postDelayed(() -> reactionToIncorrectResponse(action), 200);
        else
            handler.postDelayed(() -> publishSubject.onNext(action), 200);

    }
    //______________________________________________________________________________________________ sendActionToObservable


    //______________________________________________________________________________________________ getResponseCode
    public int getResponseCode() {
        return responseCode;
    }
    //______________________________________________________________________________________________ getResponseCode


    //______________________________________________________________________________________________ setResponseCode
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    //_____________________________________________________________________________________________ setResponseCode



    //______________________________________________________________________________________________ hideKeyboard
    public void hideKeyboard() {
        if (getContext() != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = getContext().getCurrentFocus();
            if (view == null) {
                view = new View(getContext());
            }
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //______________________________________________________________________________________________ hideKeyboard



}
