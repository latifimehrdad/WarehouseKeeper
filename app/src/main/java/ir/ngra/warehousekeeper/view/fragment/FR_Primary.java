package ir.ngra.warehousekeeper.view.fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.view.dialog.DialogMessage;
import ir.ngra.warehousekeeper.viewmodel.VM_Primary;

public class FR_Primary extends Fragment {

    private DisposableObserver<Byte> disposableObserver;
    private Activity context;
    private View view;
    private getActionFromObservable getActionFromObservable;
    private VM_Primary vm_primary;


    //______________________________________________________________________________________________ getActionFromObservable
    public interface getActionFromObservable {
        void getActionFromObservable(Byte action);
    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ FragmentPrimary
    public FR_Primary() { }
    //______________________________________________________________________________________________ FragmentPrimary


    //______________________________________________________________________________________________ onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ onDestroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }
    //______________________________________________________________________________________________ onDestroy


    //______________________________________________________________________________________________ onStop
    @Override
    public void onStop() {
        super.onStop();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }
    //______________________________________________________________________________________________ onStop


    //______________________________________________________________________________________________ getContext
    @Override
    public Activity getContext() {
        return context;
    }
    //______________________________________________________________________________________________ getContext


    //______________________________________________________________________________________________ getView
    @Override
    public View getView() {
        return view;
    }
    //______________________________________________________________________________________________ getView


    //______________________________________________________________________________________________ setView
    public void setView(View view) {
        this.view = view;
        ButterKnife.bind(this, view);
    }
    //______________________________________________________________________________________________ setView


    //______________________________________________________________________________________________ setPublishSubjectFromObservable
    public void setPublishSubjectFromObservable(
            getActionFromObservable getActionFromObservable,
            PublishSubject<Byte> publishSubject,
            VM_Primary vm_primary) {
        this.getActionFromObservable = getActionFromObservable;
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
        this.vm_primary = vm_primary;
        setObserverToObservable(publishSubject);
    }
    //______________________________________________________________________________________________ setPublishSubjectFromObservable


    //______________________________________________________________________________________________ setObserverToObservable
    public void setObserverToObservable(PublishSubject<Byte> publishSubject) {

        disposableObserver = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte aByte) {
                actionHandler(aByte);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        publishSubject
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }
    //______________________________________________________________________________________________ setObserverToObservable


    //______________________________________________________________________________________________ actionHandler
    private void actionHandler(Byte action) {
        if (getContext() != null) {
            getContext().runOnUiThread(() -> {
                getActionFromObservable.getActionFromObservable(action);

                if (action.equals(StaticValues.ML_DialogClose))
                    return;

                if (vm_primary.getResponseMessage() == null)
                    return;

                if (vm_primary.getResponseMessage().equalsIgnoreCase(""))
                    return;

                if ((action.equals(StaticValues.ML_RequestCancel))
                        || (action.equals(StaticValues.ML_ResponseError))
                        || (action.equals(StaticValues.ML_ResponseFailure))) {
                    showMessageDialog(vm_primary.getResponseMessage(),
                            context.getResources().getColor(R.color.colorWhite),
                            context.getResources().getDrawable(R.drawable.svg_warning),
                            context.getResources().getColor(R.color.colorWarning));
                    return;
                } else
                    showMessageDialog(vm_primary.getResponseMessage(),
                            context.getResources().getColor(R.color.colorWhite),
                            context.getResources().getDrawable(R.drawable.svg_checked),
                            context.getResources().getColor(R.color.colorSuccess));
            });
        }
    }
    //______________________________________________________________________________________________ actionHandler


    //______________________________________________________________________________________________ showMessageDialog
    public void showMessageDialog(String message, int color, Drawable icon, int tintColor) {

        DialogMessage dialogMessage = new DialogMessage(getContext(), message, color, icon, tintColor, vm_primary.getPublishSubject());
        dialogMessage.setCancelable(false);
        dialogMessage.show(getParentFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }
    //______________________________________________________________________________________________ showMessageDialog


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