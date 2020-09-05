package ir.ngra.warehousekeeper.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.dinuscxj.refresh.RecyclerRefreshLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.FrHomeBinding;

import ir.ngra.warehousekeeper.model.MD_WarehouseAmounts;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.view.adapter.AP_WasteRequest;
import ir.ngra.warehousekeeper.viewmodel.VM_Home;

public class FR_Home extends FR_Primary implements
        FR_Primary.getActionFromObservable,
        AP_WasteRequest.ItemOrderWaste{


    private VM_Home vm_home;
    private NavController navController;
    private Byte SortType;
    public static List<MD_WarehouseAmounts> wareHouseAmount;


    @BindView(R.id.LinearLayoutWasteCollectionStateOnProgress)
    LinearLayout LinearLayoutWasteCollectionStateOnProgress;

    @BindView(R.id.LinearLayoutWasteCollectionStateNoDelivery)
    LinearLayout LinearLayoutWasteCollectionStateNoDelivery;

    @BindView(R.id.LinearLayoutWasteCollectionStateDelivered)
    LinearLayout LinearLayoutWasteCollectionStateDelivered;

    @BindView(R.id.LinearLayoutWasteCollectionStateLading)
    LinearLayout LinearLayoutWasteCollectionStateLading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.ExpandableLayoutItemSort)
    ExpandableLayout ExpandableLayoutItemSort;

    @BindView(R.id.RecyclerViewRequest)
    RecyclerView RecyclerViewRequest;

    @BindView(R.id.LinearLayoutExpandClick)
    LinearLayout LinearLayoutExpandClick;

    @BindView(R.id.recyclerRefreshLayout)
    RecyclerRefreshLayout recyclerRefreshLayout;


    //______________________________________________________________________________________________ FR_Home
    public FR_Home() {
    }
    //______________________________________________________________________________________________ FR_Home


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FrHomeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_home, container, false);
            vm_home = new VM_Home(getContext());
            binding.setHome(vm_home);
            setView(binding.getRoot());
            setOnclick();
            SortType = StaticValues.WasteCollectionStateAll;
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                FR_Home.this,
                vm_home.getPublishSubject(),
                vm_home);
        if (getView() != null)
            navController = Navigation.findNavController(getView());

        getListOfRequest();

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifLoading.setVisibility(View.GONE);
        recyclerRefreshLayout.setRefreshing(false);
        if (action.equals(StaticValues.ML_GetWasteCollection)) {
            setAdapterWaste();
            return;
        }

    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ setOnclick
    private void setOnclick() {


        recyclerRefreshLayout.setOnRefreshListener(() -> {
            getListOfRequest();
        });


        LinearLayoutExpandClick.setOnClickListener(v -> {
            if (ExpandableLayoutItemSort.isExpanded())
                ExpandableLayoutItemSort.collapse();
            else
                ExpandableLayoutItemSort.expand();
        });

        LinearLayoutWasteCollectionStateOnProgress.setOnClickListener(v -> {
            SortType = StaticValues.WasteCollectionStateOnProgress;
            getListOfRequest();
        });

        LinearLayoutWasteCollectionStateNoDelivery.setOnClickListener(v -> {
            SortType = StaticValues.WasteCollectionStateNoDelivery;
            getListOfRequest();
        });

        LinearLayoutWasteCollectionStateDelivered.setOnClickListener(v -> {
            SortType = StaticValues.WasteCollectionStateDelivered;
            getListOfRequest();
        });

        LinearLayoutWasteCollectionStateLading.setOnClickListener(v -> {
            SortType = StaticValues.WasteCollectionStateLading;
            getListOfRequest();
        });

    }
    //______________________________________________________________________________________________ setOnclick


    //______________________________________________________________________________________________ getListOfRequest
    private void getListOfRequest() {

        recyclerRefreshLayout.setRefreshing(true);
        gifLoading.setVisibility(View.VISIBLE);
        ExpandableLayoutItemSort.collapse();
        vm_home.getWasteCollection(SortType);

    }
    //______________________________________________________________________________________________ getListOfRequest



    //______________________________________________________________________________________________ setAdapterWaste
    private void setAdapterWaste() {
        AP_WasteRequest ap_wasteRequest = new AP_WasteRequest(vm_home.getMd_wasteRequests(), FR_Home.this);
        RecyclerViewRequest.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewRequest.setAdapter(ap_wasteRequest);
    }
    //______________________________________________________________________________________________ setAdapterWaste



    //______________________________________________________________________________________________ itemOrderDeliver
    @Override
    public void itemOrderDeliver(Integer position) {
        vm_home.wasteDeliver(vm_home.getMd_wasteRequests().get(position).getRequestCode());
    }
    //______________________________________________________________________________________________ itemOrderDeliver


    //______________________________________________________________________________________________ itemOrderNotDeliver
    @Override
    public void itemOrderNotDeliver(Integer position) {
        vm_home.wasteNotDeliver(vm_home.getMd_wasteRequests().get(position).getRequestCode());
    }
    //______________________________________________________________________________________________ itemOrderNotDeliver


    @Override
    public void itemOrderClick(Integer position) {//________________________________________________ itemOrderClick

        wareHouseAmount = vm_home.getMd_wasteRequests().get(position).getWarehouseAmounts();
        Bundle bundle = new Bundle();
        bundle.putString(getContext().getResources().getString(R.string.ML_RequestCode), vm_home.getMd_wasteRequests().get(position).getRequestCode());
        navController.navigate(R.id.action_FR_Home_to_FR_RequestDetail, bundle);

    }//_____________________________________________________________________________________________ itemOrderClick


}
