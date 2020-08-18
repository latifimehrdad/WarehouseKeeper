package ir.ngra.warehousekeeper.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.DialogProgressBinding;

public class DialogProgress extends DialogFragment {

    private Context context;
    private String Title;


    @BindView(R.id.DialogProgressIgnor)
    Button DialogProgressIgnor;

    @BindView(R.id.DialogProgressTitle)
    TextView DialogProgressTitle;


    public DialogProgress(Context context, String title) {//________________________________________ Start DialogProgress
        this.context = context;
        Title = title;
    }//_____________________________________________________________________________________________ End DialogProgress


    public Dialog onCreateDialog(Bundle savedInstanceState) {//_____________________________________ Start onCreateDialog
        View view = null;
        DialogProgressBinding binding = DataBindingUtil
                .inflate(LayoutInflater
                                .from(this.context),
                        R.layout.dialog_progress,
                        null,
                        false);
        binding.setString("null");
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        DialogProgressTitle.setText(Title);
        DialogProgressIgnor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogProgress.this.dismiss();
            }
        });
        return new AlertDialog.Builder(context).setView(view).create();
    }//_____________________________________________________________________________________________ End onCreateDialog

}
