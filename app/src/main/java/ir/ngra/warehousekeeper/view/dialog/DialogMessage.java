package ir.ngra.warehousekeeper.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;
import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.databinding.DialogMessageBinding;
import ir.ngra.warehousekeeper.utility.StaticValues;

public class DialogMessage extends DialogFragment {

    private Context context;
    private String Title;
    private int color;
    private Drawable icon;
    private int tintColor;
    private PublishSubject<Byte> subject;

    @BindView(R.id.buttonDialogIgnore)
    Button buttonDialogIgnore;

    @BindView(R.id.textViewTitle)
    TextView textViewTitle;

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @BindView(R.id.imageViewIcon)
    ImageView imageViewIcon;


    public DialogMessage(
            Context context,
            String title,
            int color,
            Drawable icon,
            int tintColor,
            PublishSubject<Byte> subject) {//______________________________________________________________________ Start DialogMessage
        this.context = context;
        Title = title;
        this.color = color;
        this.icon = icon;
        this.tintColor = tintColor;
        this.subject = subject;

    }//_____________________________________________________________________________________________ End DialogMessage


    public Dialog onCreateDialog(Bundle savedInstanceState) {//_____________________________________ Start onCreateDialog
        View view = null;
        DialogMessageBinding binding = DataBindingUtil
                .inflate(LayoutInflater
                                .from(this.context),
                        R.layout.dialog_message,
                        null,
                        false);
        binding.setTitle("");
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        linearLayout.setBackgroundColor(color);
        textViewTitle.setText(Title);
        imageViewIcon.setImageDrawable(icon);
        imageViewIcon.setColorFilter(tintColor);
        buttonDialogIgnore.setOnClickListener(view1 -> {
            subject.onNext(StaticValues.ML_DialogClose);
            DialogMessage.this.dismiss();
            subject = null;
        });
        Dialog dialog = new AlertDialog.Builder(context).setView(view).create();
        Animation bottom = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        view.setAnimation(bottom);
        return dialog;
    }//_____________________________________________________________________________________________ End onCreateDialog

}
