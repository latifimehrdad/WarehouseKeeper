package ir.ngra.warehousekeeper.dagger.retrofit;



import ir.ngra.warehousekeeper.model.MD_ProfileInfo;
import ir.ngra.warehousekeeper.model.MD_Token;
import ir.ngra.warehousekeeper.model.MD_WasteAmountRequests;
import ir.ngra.warehousekeeper.model.MR_Hi;
import ir.ngra.warehousekeeper.model.MR_ItemsWast;
import ir.ngra.warehousekeeper.model.MR_Primary;
import ir.ngra.warehousekeeper.model.MR_Register;
import ir.ngra.warehousekeeper.model.MR_ResponseWaste;
import ir.ngra.warehousekeeper.model.MR_Weights;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitApiInterface {

    String Version = "/api/v1";

    @FormUrlEncoded
    @POST("/token")
    Call<MD_Token> getToken
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("grant_type") String grant_type,
                    @Field("app_token") String app_token
            );


    @FormUrlEncoded
    @POST("/token")
    Call<MD_Token> getRefreshToken
    (
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token,
            @Field("app_token") String app_token
    );



    @FormUrlEncoded
    @POST(Version + "/CitizenContact/Register")
    Call<MR_Register> SendPhoneNumber
            (
                    @Field("Mobile") String PhoneNumber,
                    @Header("Authorization") String Authorization
            );



    @FormUrlEncoded
    @POST(Version + "/CitizenContact/SendVerificationSms")
    Call<MR_Primary> SendVerificationSms
            (
                    @Field("Mobile") String PhoneNumber,
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST(Version + "/account/confirmmobile")
    Call<MR_Primary> SendVerifyCode
            (
                    @Field("Mobile") String PhoneNumber,
                    @Field("Code") String Password,
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST(Version + "/account/authrequest")
    Call<MR_Primary> LoginCode
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("mobile") String PhoneNumber,
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST("/token")
    Call<MD_Token> Login
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("grant_type") String grant_type,
                    @Field("username") String PhoneNumber,
                    @Field("Password") String Password,
                    @Field("app_token") String app_token,
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/citizen/settinginfo")
    Call<MD_ProfileInfo> getSettingInfo
            (
                    @Query("app_token") String app_token,
                    @Header("aToken") String aToken,
                    @Header("Authorization") String Authorization

            );


    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String urlString);


    @FormUrlEncoded
    @POST(Version + "/application/hi")
    Call<MR_Hi> getHi
            (
                    @Field("client_id") String client_id,
                    @Field("client_secret") String client_secret,
                    @Field("name") String name,
                    @Field("app_token") String app_token
            );


    @GET(Version + "/requestwarehousedelivery/getrequestwarehousedeliveries")
    Call<MR_ResponseWaste> getWasteCollection
            (
                    @Query("State") Byte State,
                    @Query("app_token") String app_token,
                    @Header("aToken") String aToken,
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST(Version + "/requestwarehouse/warehousedelivered")
    Call<MR_Primary> WasteCollectionDeliver
            (
                    @Field("RequestCode") String RequestCode,
                    @Field("app_token") String app_token,
                    @Header("aToken") String aToken,
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST(Version + "/requestwarehouse/warehousenotdelivered")
    Call<MR_Primary> WasteCollectionNotDeliver
            (
                    @Field("RequestCode") String RequestCode,
                    @Field("app_token") String app_token,
                    @Header("aToken") String aToken,
                    @Header("Authorization") String Authorization
            );

    @GET(Version + "/Waste/WasteList")
    Call<MR_ItemsWast> getWasteList
            (
                    @Query("app_token") String app_token,
                    @Header("aToken") String aToken,
                    @Header("Authorization") String Authorization

            );



    @GET(Version + "/utility/getWeights")
    Call<MR_Weights> getWeights
            (
                    @Query("app_token") String app_token,
                    @Header("aToken") String aToken,
                    @Header("Authorization") String Authorization

            );


    @POST(Version + "/Requestwarehouse/LadingRequestwarehouse")
    Call<MR_Primary> LadingRequestCollection
            (
                    @Body MD_WasteAmountRequests WasteAmountRequests,
                    @Header("aToken") String aToken,
                    @Header("Authorization") String Authorization

            );


    @FormUrlEncoded
    @POST(Version + "/Requestwarehouse/ConfirmRequest")
    Call<MR_Primary> ConfirmRequest
            (
                    @Field("RequestCode") String RequestCode,
                    @Field("Code") String Code,
                    @Field("app_token") String app_token,
                    @Header("aToken") String aToken,
                    @Header("Authorization") String Authorization
            );


    @FormUrlEncoded
    @POST(Version + "/Requestwarehouse/resendconfirmrequest")
    Call<MR_Primary> ReTryVerifyCode
            (
                    @Field("RequestCode") String RequestCode,
                    @Field("app_token") String app_token,
                    @Header("aToken") String aToken,
                    @Header("Authorization") String Authorization
            );


}
