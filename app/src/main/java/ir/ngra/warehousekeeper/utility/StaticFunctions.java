package ir.ngra.warehousekeeper.utility;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.model.MD_ProfileInfo;
import ir.ngra.warehousekeeper.model.MD_Token;

public class StaticFunctions {


    //______________________________________________________________________________________________ saveToken
    public static boolean saveToken(Context context, MD_Token MDToken) {

        SharedPreferences.Editor token = context
                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
                .edit();

        token.putString(context.getString(R.string.ML_AccessToken), MDToken.getAccess_token());
        token.putString(context.getString(R.string.ML_TokenType), MDToken.getToken_type());
        token.putInt(context.getString(R.string.ML_ExpireSin), MDToken.getExpires_in());
        token.putString(context.getString(R.string.ML_PhoneNumber), MDToken.getPhoneNumber());
        token.putString(context.getString(R.string.ML_ClientId), MDToken.getClient_id());
        token.putString(context.getString(R.string.ML_Issued), MDToken.getIssued());
        token.putString(context.getString(R.string.ML_Expires), MDToken.getExpires());
        token.putString(context.getString(R.string.ML_RefreshToken), MDToken.getRefresh_token());
        token.apply();
        return true;

    }
    //______________________________________________________________________________________________ saveToken


    //______________________________________________________________________________________________ logOut
    public static boolean logOut(Context context) {
        SharedPreferences.Editor token =
                context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0).edit();

        token.putBoolean(context.getString(R.string.ML_CompleteProfile), false);
        token.putString(context.getString(R.string.ML_AccessToken), null);
        token.putString(context.getString(R.string.ML_TokenType), null);
        token.putInt(context.getString(R.string.ML_ExpireSin), 0);
        token.putString(context.getString(R.string.ML_PhoneNumber), null);
        token.putString(context.getString(R.string.ML_ClientId), null);
        token.putString(context.getString(R.string.ML_Issued), null);
        token.putString(context.getString(R.string.ML_Expires), null);
        token.apply();
        return true;
    }
    //______________________________________________________________________________________________ logOut


    //______________________________________________________________________________________________ saveProfile
    public static boolean saveProfile(
            Context context,
            MD_ProfileInfo.MD_Profile profile) {
        SharedPreferences.Editor token = context
                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
                .edit();
        token.putString(context.getString(R.string.ML_Name), profile.getName());
        token.putString(context.getString(R.string.ML_lastName), profile.getLastName());
        token.putInt(context.getString(R.string.ML_Gender), profile.getGender());
        token.putBoolean(context.getString(R.string.ML_CompleteProfile), profile.getProfileCompleted());
        token.putBoolean(context.getString(R.string.ML_AddressCompleted), profile.getAddressCompleted());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        token.apply();
        return true;
    }
    //______________________________________________________________________________________________ saveProfile



    //______________________________________________________________________________________________ textChangeForChangeBack
    public static TextWatcher textChangeForChangeBack(EditText editText) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setBackgroundResource(R.drawable.dw_edit_back);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

    }
    //______________________________________________________________________________________________ textChangeForChangeBack




}
