package ir.ngra.warehousekeeper.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.FrRequestDetailBinding;
import ir.ngra.warehousekeeper.model.MD_Collect;
import ir.ngra.warehousekeeper.model.MD_ItemWaste;
import ir.ngra.warehousekeeper.model.MD_ItemsWasteList;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.view.adapter.AP_ItemsWaste;
import ir.ngra.warehousekeeper.view.adapter.AP_ItemsWasteList;
import ir.ngra.warehousekeeper.view.dialog.DialogProgress;
import ir.ngra.warehousekeeper.viewmodel.VM_RequestDetail;


public class FR_RequestDetail extends FR_Primary implements
        FR_Primary.getActionFromObservable,
        AP_ItemsWaste.ItemWastClick,
        AP_ItemsWasteList.ItemWasteListAmount {


    private VM_RequestDetail vm_requestDetail;
    private NavController navController;
    private List<MD_ItemsWasteList> wasteLists;
    private AP_ItemsWasteList ap_itemsWasteList;
    private String RequestCode;
    private boolean ReTryGetSMSClick = false;
    private Handler timer;
    private Runnable runnable;
    private DialogProgress progress;


    @BindView(R.id.RecyclerViewItemsWaste)
    RecyclerView RecyclerViewItemsWaste;

    @BindView(R.id.RecyclerViewWasteList)
    RecyclerView RecyclerViewWasteList;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.RelativeLayoutSendSms)
    RelativeLayout RelativeLayoutSendSms;

    @BindView(R.id.LinearLayoutVerify)
    LinearLayout LinearLayoutVerify;


    @BindView(R.id.imgLoadingSms)
    ImageView imgLoadingSms;

    @BindView(R.id.gifLoadingSms)
    GifView gifLoadingSms;

    @BindView(R.id.RelativeLayoutSendVerify)
    RelativeLayout RelativeLayoutSendVerify;

    @BindView(R.id.imgLoadingVerify)
    ImageView imgLoadingVerify;

    @BindView(R.id.gifLoadingVerify)
    GifView gifLoadingVerify;

    @BindView(R.id.EditTextVerify)
    EditText EditTextVerify;

    @BindView(R.id.ScrollViewParent)
    ScrollView ScrollViewParent;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.TimeElapsed)
    TextView TimeElapsed;

    @BindView(R.id.message)
    TextView message;


    public FR_RequestDetail() {//______________________________________________________________________ RequestDetail
    }//_____________________________________________________________________________________________ RequestDetail

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_requestDetail = new VM_RequestDetail(getContext());
            FrRequestDetailBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_request_detail, container, false
            );
            binding.setDetail(vm_requestDetail);
            setView(binding.getRoot());
            SetClicks();
            GetItemsOfWast();
            RelativeLayoutSendSms.setVisibility(View.GONE);
            LinearLayoutVerify.setVisibility(View.GONE);

        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                FR_RequestDetail.this,
                vm_requestDetail.getPublishSubject(),
                vm_requestDetail);
        RequestCode = getArguments().getString(getContext().getString(R.string.ML_RequestCode), "");
        if (getView() != null)
            navController = Navigation.findNavController(getView());

    }//_____________________________________________________________________________________________ onStart



    private void initList() {//_____________________________________________________________________ initList


        for (MD_Collect collect: FR_Home.estimateAmount){
            Integer intAmount = Math.round(Float.valueOf(collect.getAmount()));
            Integer kg = intAmount / 1000;
            Integer gr = intAmount % 1000;
            wasteLists.add(new MD_ItemsWasteList(
                    collect.getWaste().getId(),
                    collect.getWaste().getTitle(),
                    vm_requestDetail.getMd_weights().get(1).getId(),
                    vm_requestDetail.getMd_weights().get(0).getId(),
                    "0",
                    gr.toString()));

            wasteLists.add(new MD_ItemsWasteList(
                    collect.getWaste().getId(),
                    collect.getWaste().getTitle(),
                    vm_requestDetail.getMd_weights().get(1).getId(),
                    vm_requestDetail.getMd_weights().get(0).getId(),
                    kg.toString(),
                    "0"));
/*
            if (collect.getWeight().getId() == vm_requestDetail.getMd_weights().get(0).getId()) {
                Integer intAmount = Math.round(Float.valueOf(collect.getAmount()));
                wasteLists.add(new MD_ItemsWasteList(
                        collect.getWaste().getId(),
                        collect.getWaste().getTitle(),
                        collect.getWeight().getId(),
                        vm_requestDetail.getMd_weights().get(1).getId(),
                        intAmount.toString(),
                        ""));
            } else {
                Integer intAmount = Math.round(Float.valueOf(collect.getAmount()));
                wasteLists.add(new MD_ItemsWasteList(
                        collect.getWaste().getId(),
                        collect.getWaste().getTitle(),
                        vm_requestDetail.getMd_weights().get(0).getId(),
                        collect.getWeight().getId(),
                        "",
                        intAmount.toString()));
            }*/
        }

        SetItemsWasteListAdapter();
    }//_____________________________________________________________________________________________ initList


    @Override
    public void getActionFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        DismissProgress();
        gifLoading.setVisibility(View.GONE);
        imgLoadingSms.setVisibility(View.VISIBLE);
        gifLoadingSms.setVisibility(View.GONE);
        imgLoadingVerify.setVisibility(View.VISIBLE);
        gifLoadingVerify.setVisibility(View.GONE);


        if (action.equals(StaticValues.ML_GetItemsOfWasteIsSuccess)) {
            SetItemsWasteAdapter();
            return;
        }

        if (action.equals(StaticValues.ML_GotoVerify)) {
            RelativeLayoutSendSms.setVisibility(View.GONE);
            LinearLayoutVerify.setVisibility(View.VISIBLE);
            ScrollViewParent.scrollTo(0, ScrollViewParent.getBottom());
            StartTimer(120);
            return;
        }

        if (action.equals(StaticValues.WasteCollectionStateDelivered))
            getContext().onBackPressed();


    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void GetItemsOfWast() {//_______________________________________________________________ GetItemsOfWast
        gifLoading.setVisibility(View.VISIBLE);
        RecyclerViewItemsWaste.setVisibility(View.GONE);
        RecyclerViewWasteList.setVisibility(View.GONE);
        vm_requestDetail.getItemsOfWast();
    }//_____________________________________________________________________________________________ GetItemsOfWast


    private void SetClicks() {//____________________________________________________________________ SetClicks

        RelativeLayoutSendSms.setOnClickListener(v -> {
            if (wasteLists.size() > 0) {
                imgLoadingSms.setVisibility(View.INVISIBLE);
                gifLoadingSms.setVisibility(View.VISIBLE);
                hideKeyboard();
                vm_requestDetail.sendVerifyCode(RequestCode, wasteLists);
            }

        });

        RelativeLayoutSendVerify.setOnClickListener(v -> {
            if (EditTextVerify.getText().toString().length() != 0) {
                imgLoadingVerify.setVisibility(View.GONE);
                gifLoadingVerify.setVisibility(View.VISIBLE);
                hideKeyboard();
                vm_requestDetail.verifyCode(RequestCode, EditTextVerify.getText().toString());
            }
        });

        message.setOnClickListener(v -> {
            if (ReTryGetSMSClick) {
                vm_requestDetail.reTryVerifyCode(RequestCode);
            }
        });


    }//_____________________________________________________________________________________________ SetClicks


    private void SetItemsWasteAdapter() {//_________________________________________________________ SetItemsWasteAdapter

        RecyclerViewItemsWaste.setVisibility(View.VISIBLE);
        RecyclerViewWasteList.setVisibility(View.VISIBLE);
        AP_ItemsWaste ap_itemsWaste = new AP_ItemsWaste(vm_requestDetail.getMd_itemWastes(), FR_RequestDetail.this);
        RecyclerViewItemsWaste.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        RecyclerViewItemsWaste.setAdapter(ap_itemsWaste);
        wasteLists = new ArrayList<>();
        initList();

    }//_____________________________________________________________________________________________ SetItemsWasteAdapter



    private void SetItemsWasteListAdapter() {//_____________________________________________________ SetItemsWasteListAdapter
        ap_itemsWasteList = new AP_ItemsWasteList(wasteLists, FR_RequestDetail.this, vm_requestDetail.getMd_weights());
        RecyclerViewWasteList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewWasteList.setAdapter(ap_itemsWasteList);
        if (wasteLists.size() > 0) {
            RelativeLayoutSendSms.setVisibility(View.VISIBLE);
            LinearLayoutVerify.setVisibility(View.GONE);
        }

    }//_____________________________________________________________________________________________ SetItemsWasteListAdapter


    @Override
    public void itemWastClick(Integer position) {//_________________________________________________ itemWastClick

        if (getContext() != null) {
            MD_ItemWaste waste = vm_requestDetail.getMd_itemWastes().get(position);
            if (wasteLists.size() == 0) {
                wasteLists.add(new MD_ItemsWasteList(
                        waste.getId(),
                        waste.getTitle(),
                        vm_requestDetail.getMd_weights().get(0).getId(),
                        vm_requestDetail.getMd_weights().get(1).getId(),
                        "0",
                        "0"));
            } else {
                boolean duplicate = false;
                for (MD_ItemsWasteList item : wasteLists) {
                    if (item.getId() == waste.getId()) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    wasteLists.add(new MD_ItemsWasteList(
                            waste.getId(),
                            waste.getTitle(),
                            vm_requestDetail.getMd_weights().get(0).getId(),
                            vm_requestDetail.getMd_weights().get(1).getId(),
                            "0",
                            "0"));
                }
            }
            SetItemsWasteListAdapter();
            RelativeLayoutSendSms.setVisibility(View.VISIBLE);
            LinearLayoutVerify.setVisibility(View.GONE);
        }

    }//_____________________________________________________________________________________________ itemWastClick


    @Override
    public void itemWasteClickAction(Integer position, Integer WeightPosition, String Amount) {//___ itemWasteClickAction
        if (WeightPosition == 0)
            wasteLists.get(position).setAmount1(Amount);
        else
            wasteLists.get(position).setAmount2(Amount);
    }//_____________________________________________________________________________________________ itemWasteClickAction



    @Override
    public void itemWasteDelete(Integer poInteger) {//______________________________________________ itemWasteDelete

        wasteLists.remove(wasteLists.get(poInteger));
        ap_itemsWasteList.notifyDataSetChanged();

    }//_____________________________________________________________________________________________ itemWasteDelete




    private void StartTimer(int Elapse) {//_________________________________________________________ Start StartTimer

        ReTryGetSMSClick = false;
        TimeElapsed.setVisibility(View.VISIBLE);
        message.setText(getResources().getString(R.string.ElapsedTimeGetSMS));

        Elapse = Elapse * 10;
        progressBar.setMax(Elapse * 2);
        progressBar.setProgress(Elapse);
        TimeElapsed.setVisibility(View.VISIBLE);
        timer = new Handler();
        runnable = new Runnable() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void run() {
                progressBar.setProgress(progressBar.getProgress() - 1);
                int mili = progressBar.getProgress() + 10;
                int seconds = (mili / 10) % 60;
                int minutes = (mili / (10 * 60)) % 60;
                TimeElapsed.setText(String.format("%02d", minutes) + " : " + String.format("%02d", seconds));

                if (progressBar.getProgress() > 0)
                    timer.postDelayed(this, 100);
                else
                    ReTryGetSMS();
            }
        };
        timer.postDelayed(runnable, 100);

    }//_____________________________________________________________________________________________ StartTimer



    private void ReTryGetSMS() {//__________________________________________________________________ ReTryGetSMS
        if (progress != null)
            progress.dismiss();
        TimeElapsed.setVisibility(View.GONE);
        ReTryGetSMSClick = true;
        message.setText(getResources().getString(R.string.ReTryGetSMS));
    }//_____________________________________________________________________________________________ ReTryGetSMS



    private void DismissProgress() {//______________________________________________________________ DismissProgress

        progressBar.setProgress(0);
        if (timer != null && runnable != null) {
            timer.removeCallbacks(runnable);
            timer = null;
            runnable = null;
        }

    }//_____________________________________________________________________________________________ DismissProgress




}
