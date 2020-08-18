package ir.ngra.warehousekeeper.view.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cunoraz.gifview.library.GifView;


import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.FrLoginBinding;
import ir.ngra.warehousekeeper.utility.StaticFunctions;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.viewmodel.VM_Login;

/**
 * A simple {@link Fragment} subclass.
 */
public class FR_Login extends FR_Primary implements FR_Primary.getActionFromObservable {

    private VM_Login vm_Login;
    private NavController navController;
    private boolean passVisible;


    @BindView(R.id.LoginClick)
    RelativeLayout LoginClick;

    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;

    @BindView(R.id.EditPassword)
    EditText EditPassword;

    @BindView(R.id.ImgPassVisible)
    ImageView ImgPassVisible;

    @BindView(R.id.imgLock)
    ImageView imgLock;

    @BindView(R.id.gif)
    GifView gif;


    //______________________________________________________________________________________________ FR_Login
    public FR_Login() {

    }
    //______________________________________________________________________________________________ FR_Login


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_Login = new VM_Login(getContext());
            FrLoginBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_login, container, false
            );
            binding.setLogin(vm_Login);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        if (getView() != null)
            navController = Navigation.findNavController(getView());
        setPublishSubjectFromObservable(
                FR_Login.this,
                vm_Login.getPublishSubject(),
                vm_Login);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        passVisible = false;
        dismissProgress();
        setTextWatcher();
        setClick();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        dismissProgress();

        if (action.equals(StaticValues.ML_GoToHome)) {
            getContext().onBackPressed();
        }


    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setClick
    private void setClick() {

        LoginClick.setOnClickListener(v -> {

            if (checkEmpty()) {
                saveLogin();
            } else {
                dismissProgress();
            }
        });


        ImgPassVisible.setOnClickListener(v -> {
            if (!passVisible) {
                EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ImgPassVisible.setImageResource(R.drawable.svg_hide_password);
                passVisible = true;

            } else {
                EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ImgPassVisible.setImageResource(R.drawable.svg_password_visible);
                passVisible = false;
            }
            EditPassword.setSelection(EditPassword.getText().length());
        });


    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        boolean phone;
        boolean pass;


        if (EditPassword.getText().length() < 6) {
            EditPassword.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditPassword.setError(getResources().getString(R.string.emptyPassword));
            EditPassword.requestFocus();
            pass = false;
        } else
            pass = true;


        if (EditPhoneNumber.getText().length() != 11) {
            EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditPhoneNumber.setError(getResources().getString(R.string.emptyPhoneNumber));
            EditPhoneNumber.requestFocus();
            phone = false;
        } else {
            String ZeroNine = EditPhoneNumber.getText().subSequence(0, 2).toString();
            if (ZeroNine.equalsIgnoreCase("09"))
                phone = true;
            else {
                EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
                EditPhoneNumber.setError(getResources().getString(R.string.emptyPhoneNumber));
                EditPhoneNumber.requestFocus();
                phone = false;
            }
        }


        return phone && pass;

    }
    //______________________________________________________________________________________________ checkEmpty


    //______________________________________________________________________________________________ saveLogin
    private void saveLogin() {
        showProgress();
        vm_Login.GetLoginToken(EditPhoneNumber.getText().toString(), EditPassword.getText().toString());
    }
    //______________________________________________________________________________________________ saveLogin


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {
        EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_back);
        EditPhoneNumber.addTextChangedListener(StaticFunctions.textChangeForChangeBack(EditPhoneNumber));
        EditPassword.setBackgroundResource(R.drawable.dw_edit_back);
        EditPassword.addTextChangedListener(StaticFunctions.textChangeForChangeBack(EditPassword));
    }
    //______________________________________________________________________________________________ setTextWatcher


    //______________________________________________________________________________________________ dismissProgress
    private void dismissProgress() {
        gif.setVisibility(View.INVISIBLE);
        imgLock.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ dismissProgress


    //______________________________________________________________________________________________ showProgress
    private void showProgress() {
        gif.setVisibility(View.VISIBLE);
        imgLock.setVisibility(View.INVISIBLE);
    }
    //______________________________________________________________________________________________ showProgress


}
