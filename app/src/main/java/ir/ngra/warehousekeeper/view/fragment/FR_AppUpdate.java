package ir.ngra.warehousekeeper.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;


import com.yangp.ypwaveview.YPWaveView;

import java.io.File;

import butterknife.BindView;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.FrUpdateBinding;
import ir.ngra.warehousekeeper.utility.DownloadTask;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.viewmodel.VM_Update;

public class FR_AppUpdate extends FR_Primary implements FR_Primary.getActionFromObservable,
        VM_Update.progressDownload {


    private VM_Update vm_update;
    private String fileName;
    private Handler handlerDownload;

    @BindView(R.id.TextViewProgress)
    TextView TextViewProgress;

//    @BindView(R.id.progressBar)
//    ProgressBar progressBar;

    @BindView(R.id.ImageViewDownload)
    ImageView ImageViewDownload;

    @BindView(R.id.ButtonInstall)
    Button ButtonInstall;

    @BindView(R.id.yPWaveView)
    YPWaveView yPWaveView;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        if (getView() == null) {
            vm_update = new VM_Update(getContext(), FR_AppUpdate.this);
            FrUpdateBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_update, container, false);
            binding.setUpdate(vm_update);
            setView(binding.getRoot());
            if (getContext() != null)
                TextViewProgress.setText(getContext().getResources().getString(R.string.PleaseWait));
            yPWaveView.setProgress(0);
            ButtonInstall.setVisibility(View.GONE);
            setOnClick();
            init();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                FR_AppUpdate.this,
                vm_update.getPublishSubject(),
                vm_update);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        if (getContext() != null && getArguments() != null) {
            String url = getArguments().getString(getContext().getResources().getString(R.string.ML_UpdateUrl), "");
            fileName = getArguments().getString(getContext().getResources().getString(R.string.ML_UpdateFile), "");

            if (!url.equalsIgnoreCase(""))
                if (!fileName.equalsIgnoreCase("")) {
                    setProgress();
                    vm_update.downloadFile(url, fileName, yPWaveView);
                }
//                    vm_update.downloadFile(url, fileName);
        }

    }
    //______________________________________________________________________________________________ init


    private void setOnClick() {//___________________________________________________________________ setOnClick

        ButtonInstall.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = vm_update.getTempUri(fileName);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //dont forget add this line
                if (getContext() != null)
                    getContext().startActivity(intent);
            } else {
                File apkFile;
                apkFile = new File(Environment.getExternalStorageDirectory()
                        + "/pishtazan/", fileName);
                Uri apkUri = Uri.fromFile(apkFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }

        });

    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        handlerDownload = null;


        if (action.equals(StaticValues.ML_FileDownloading)) {
            yPWaveView.setProgress(0);
            if (getContext() != null)
                TextViewProgress.setText(getContext().getResources().getString(R.string.FileDownloaded));
            return;
        }

        if (action.equals(StaticValues.ML_FileDownloaded)) {
            yPWaveView.setProgress(0);
            ImageViewDownload.setAnimation(null);
            ButtonInstall.setVisibility(View.VISIBLE);
            ImageViewDownload.setVisibility(View.GONE);
            TextViewProgress.setVisibility(View.GONE);
            yPWaveView.setVisibility(View.GONE);
            if (getContext() != null)
                TextViewProgress.setText(getContext().getResources().getString(R.string.FileDownloaded));
            return;
        }

    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ onProgress
    @Override
    public void onProgress(int progress) {
        yPWaveView.setProgress(progress);
        TextViewProgress.setText(progress + " %");
    }
    //______________________________________________________________________________________________ onProgress


    //______________________________________________________________________________________________ setProgress
    private void setProgress() {
        handlerDownload = new Handler();
        handlerDownload.postDelayed(new Runnable() {
            @Override
            public void run() {
                yPWaveView.setProgress(DownloadTask.progressDownload);
                if (DownloadTask.progressDownload < 100)
                    handlerDownload.postDelayed(this, 500);
            }
        }, 500);
    }
    //______________________________________________________________________________________________ setProgress


}