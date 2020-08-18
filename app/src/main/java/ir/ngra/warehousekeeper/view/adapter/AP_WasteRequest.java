package ir.ngra.warehousekeeper.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.AdapterItemOrdersWasteBinding;
import ir.ngra.warehousekeeper.model.MD_WasteRequest;


public class AP_WasteRequest extends RecyclerView.Adapter<AP_WasteRequest.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_WasteRequest> requestList;
    private ItemOrderWaste itemOrderWaste;

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_orders_waste, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(requestList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }


    public interface ItemOrderWaste {//_____________________________________________________________ ItemBoothClick
        void itemOrderDeliver(Integer position);
        void itemOrderNotDeliver(Integer position);
/*        void itemOrderMap(Integer position);*/
        void itemOrderClick(Integer position);
    }//_____________________________________________________________________________________________ ItemBoothClick


    public AP_WasteRequest(List<MD_WasteRequest> requestList, ItemOrderWaste itemOrderWaste) {
        this.requestList = requestList;
        this.itemOrderWaste  =itemOrderWaste;
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemOrdersWasteBinding binding;

        @BindView(R.id.LinearLayoutDeliver)
        LinearLayout LinearLayoutDeliver;

        @BindView(R.id.LinearLayoutNotDeliver)
        LinearLayout LinearLayoutNotDeliver;

        @BindView(R.id.ImageViewMap)
        ImageView ImageViewMap;

        @BindView(R.id.gifDeliver)
        GifView gifDeliver;

        @BindView(R.id.gifNotDeliver)
        GifView gifNotDeliver;

        @BindView(R.id.ImageViewDeliver)
        ImageView ImageViewDeliver;

        @BindView(R.id.ImageViewNotDeliver)
        ImageView ImageViewNotDeliver;

        @BindView(R.id.ImageViewCall)
        ImageView ImageViewCall;

        @BindView(R.id.LinearLayoutDetail)
        LinearLayout LinearLayoutDetail;

        @BindView(R.id.ImageViewDetail)
        ImageView ImageViewDetail;

        @BindView(R.id.gifDetail)
        GifView gifDetail;

        public CustomHolder(AdapterItemOrdersWasteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_WasteRequest item, final int position) {
            binding.setWastRequest(item);

            LinearLayoutDeliver.setOnClickListener(v -> {
                itemOrderWaste.itemOrderDeliver(position);
                gifDeliver.setVisibility(View.VISIBLE);
                ImageViewDeliver.setVisibility(View.GONE);
            });

            LinearLayoutNotDeliver.setOnClickListener(v -> {
                itemOrderWaste.itemOrderNotDeliver(position);
                gifNotDeliver.setVisibility(View.VISIBLE);
                ImageViewNotDeliver.setVisibility(View.GONE);
            });

            ImageViewCall.setOnClickListener(v -> {
                CallPerson(ImageViewCall.getContext(), item.getPhoneNumber());
            });


            LinearLayoutDetail.setOnClickListener(v -> {
//                ImageViewDetail.setVisibility(View.GONE);
//                gifDetail.setVisibility(View.VISIBLE);
                itemOrderWaste.itemOrderClick(position);
            });

/*
            ImageViewMap.setOnClickListener(v -> itemOrderWaste.itemOrderMap(position));*/

            binding.executePendingBindings();
        }

        private void CallPerson(Context context, String PhoneNumber) {//____________________________ CallPerson
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            call.setData(Uri.parse("tel:" + PhoneNumber));
            context.startActivity(call);
        }//_____________________________________________________________________________________________ CallPerson



    }

}
