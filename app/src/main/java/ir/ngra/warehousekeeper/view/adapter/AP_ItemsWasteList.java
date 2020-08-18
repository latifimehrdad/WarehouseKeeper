package ir.ngra.warehousekeeper.view.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.AdapterItemWasteListBinding;
import ir.ngra.warehousekeeper.model.MD_ItemsWasteList;
import ir.ngra.warehousekeeper.model.MD_Weight;


public class AP_ItemsWasteList extends RecyclerView.Adapter<AP_ItemsWasteList.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ItemsWasteList> wasteLists;
    private ItemWasteListAmount itemWasteListAmount;
    private List<MD_Weight> md_weights;


    public AP_ItemsWasteList(List<MD_ItemsWasteList> wasteLists, ItemWasteListAmount itemWasteListAmount, List<MD_Weight> md_weights) {
        this.wasteLists = wasteLists;
        this.itemWasteListAmount = itemWasteListAmount;
        this.md_weights = md_weights;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_waste_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(wasteLists.get(position), position);
    }

    @Override
    public int getItemCount() {
        return wasteLists.size();
    }


    public interface ItemWasteListAmount {//________________________________________________________ ItemWasteListAmount

        void itemWasteClickAction(Integer position, Integer WeightPosition, String Amount);
        void itemWasteDelete(Integer poInteger);

    }//_____________________________________________________________________________________________ ItemWasteListAmount



    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemWasteListBinding binding;

        @BindView(R.id.EditText1)
        EditText EditText1;

        @BindView(R.id.EditText2)
        EditText EditText2;

        @BindView(R.id.ImageViewDelete)
        ImageView ImageViewDelete;

        public CustomHolder(AdapterItemWasteListBinding binding) {
           super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ItemsWasteList item, final int position) {
            binding.setWasteList(item);
            binding.setWeight1(md_weights.get(0));
            binding.setWeight2(md_weights.get(1));

            EditText1.addTextChangedListener(TextChangeForChangeBack(position, 0));
            EditText2.addTextChangedListener(TextChangeForChangeBack(position, 1));

            ImageViewDelete.setOnClickListener(v -> itemWasteListAmount.itemWasteDelete(position));

            binding.executePendingBindings();
        }


        private TextWatcher TextChangeForChangeBack(Integer Position, Integer WeightPosition) {//__________ TextChangeForChangeBack

            return new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    itemWasteListAmount.itemWasteClickAction(Position, WeightPosition, s.toString());
                }
            };

        }//_________________________________________________________________________________________ TextChangeForChangeBack


    }
}
