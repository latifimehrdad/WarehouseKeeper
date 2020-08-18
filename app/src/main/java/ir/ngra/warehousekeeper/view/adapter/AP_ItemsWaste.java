package ir.ngra.warehousekeeper.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.AdapterItemWasteBinding;
import ir.ngra.warehousekeeper.model.MD_ItemWaste;


public class AP_ItemsWaste extends RecyclerView.Adapter<AP_ItemsWaste.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ItemWaste> md_itemWastes;
    private ItemWastClick itemWastClick;


    public interface ItemWastClick {//______________________________________________________________ ItemWastClick
        void itemWastClick(Integer position);
    }//_____________________________________________________________________________________________ ItemWastClick


    public AP_ItemsWaste(List<MD_ItemWaste> md_itemWastes,
                         ItemWastClick itemWastClick) {
        this.md_itemWastes = md_itemWastes;
        this.itemWastClick = itemWastClick;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_waste, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_itemWastes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_itemWastes.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemWasteBinding binding;


        public CustomHolder(AdapterItemWasteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
        }

        public void bind(MD_ItemWaste item, final int position) {
            binding.setWaste(item);
            binding.getRoot().setOnClickListener(v -> {
                itemWastClick.itemWastClick(position);
            });
            binding.executePendingBindings();
        }
    }

}
