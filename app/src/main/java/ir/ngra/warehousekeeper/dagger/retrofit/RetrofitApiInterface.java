package ir.ngra.warehousekeeper.dagger.retrofit;



import ir.ngra.warehousekeeper.model.MD_ProfileInfo;
import ir.ngra.warehousekeeper.model.MD_Token;
import ir.ngra.warehousekeeper.model.MR_Hi;
import ir.ngra.warehousekeeper.model.MR_Primary;
import ir.ngra.warehousekeeper.model.MR_Register;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
                    @Field("grant_type") String grant_type
            );


    @FormUrlEncoded
    @POST("/token")
    Call<MD_Token> getRefreshToken
    (
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token
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
                    @Field("code") String code,
                    @Header("Authorization") String Authorization

            );


    @GET(Version + "/citizen/settinginfo")
    Call<MD_ProfileInfo> getSettingInfo
            (
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
                    @Field("name") String name
            );



}
