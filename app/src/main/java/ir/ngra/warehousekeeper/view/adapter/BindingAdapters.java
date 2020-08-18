package ir.ngra.warehousekeeper.view.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import ir.ngra.warehousekeeper.R;
import ir.ngra.warehousekeeper.utility.ApplicationUtility;
import ir.ngra.warehousekeeper.utility.StaticValues;
import ir.ngra.warehousekeeper.view.application.WarehouseKeeper;


public class BindingAdapters {


    @BindingAdapter(value = {"SetImageItemOfWast"})
    public static void SetImageItemOfWast(ImageView imageView, String url) {//______________________ SetImageItemOfWast

        ImageLoader imageLoader = WarehouseKeeper
                .getWarehouseKeeper(imageView.getContext())
                .getImageLoaderComponent()
                .getImageLoader();


        imageLoader.displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                imageView.setImageResource(R.drawable.wmslogo);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                imageView.setImageResource(R.drawable.wmslogo);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage == null)
                    imageView.setImageResource(R.drawable.wmslogo);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                imageView.setImageResource(R.drawable.wmslogo);
            }
        });

    }//_____________________________________________________________________________________________ SetImageItemOfWast


    @BindingAdapter(value = {"SetJalaliDate"})
    public static void SetJalaliDate(TextView textView, Date date) {//_______________________________ SetOrderDate

        ApplicationUtility component = WarehouseKeeper
                .getWarehouseKeeper(textView.getContext())
                .getUtilityComponent()
                .getApplicationUtility();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(component.MiladiToJalali(date, "FullJalaliString"));
        stringBuilder.append(" ساعت ");
        stringBuilder.append(simpleDateFormat.format(date));


        textView.setText(stringBuilder.toString());

    }//_____________________________________________________________________________________________ SetOrderDate




    @BindingAdapter(value = {"SetTicketDate"})
    public static void SetTicketDate(TextView textView, Date date) {//_______________________________ SetOrderDate

        ApplicationUtility component = WarehouseKeeper
                .getWarehouseKeeper(textView.getContext())
                .getUtilityComponent()
                .getApplicationUtility();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(component.MiladiToJalali(date, "FullJalaliNumber"));
        stringBuilder.append(" - ");
        stringBuilder.append(simpleDateFormat.format(date));


        textView.setText(stringBuilder.toString());

    }//_____________________________________________________________________________________________ SetOrderDate




/*
    @BindingAdapter(value = {"SetTicketStatus"})
    public static void SetTicketStatus(TextView textView, Byte deliveryType) {//____________________ SetTicketStatus

        Resources resources = textView.getContext().getResources();

        if (deliveryType.equals(StaticValues.TicketStatusNew)) {
            textView.setText(resources.getString(R.string.TicketStatusNew));
            textView.setTextColor(resources.getColor(R.color.Links));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusPending)) {
            textView.setText(resources.getString(R.string.TicketStatusPending));
            textView.setTextColor(resources.getColor(R.color.colorPrimaryDark));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusWaiting)) {
            textView.setText(resources.getString(R.string.TicketStatusWaiting));
            textView.setTextColor(resources.getColor(R.color.colorPrimaryVeryDark));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusAnswered)) {
            textView.setText(resources.getString(R.string.TicketStatusAnswered));
            textView.setTextColor(resources.getColor(R.color.mlCollectRight1));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusReferred)) {
            textView.setText(resources.getString(R.string.TicketStatusReferred));
            textView.setTextColor(resources.getColor(R.color.TicketStatusReferred));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusSolved)) {
            textView.setText(resources.getString(R.string.TicketStatusSolved));
            textView.setTextColor(resources.getColor(R.color.mlCollectLeft1));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusClosed)) {
            textView.setText(resources.getString(R.string.TicketStatusClosed));
            textView.setTextColor(resources.getColor(R.color.mlHeader));
            return;
        }

        if (deliveryType.equals(StaticValues.TicketStatusArchived)) {
            textView.setText(resources.getString(R.string.TicketStatusArchived));
            textView.setTextColor(resources.getColor(R.color.mlEdit));
        }


    }//_____________________________________________________________________________________________ SetTicketStatus
*/



    @BindingAdapter(value = {"SetCountItemsWasteList"})
    public static void SetCountItemsWasteList(TextView textView, Integer count) {//_________________ SetCountItemsWasteList
        StringBuilder builder = new StringBuilder();
        builder.append(count);
        builder.append(" ");
        builder.append(textView.getContext().getResources().getString(R.string.KGr));
        textView.setText(builder.toString());
    }//_____________________________________________________________________________________________ SetCountItemsWasteLis




    @BindingAdapter(value = {"SetOrderTotalAmount"})
    public static void SetOrderTotalAmount(TextView textView, float Amount) {//________________ SetOrderTotalAmount

        Integer am = Math.round(Amount);
        textView.setText(am.toString());

    }//_____________________________________________________________________________________________ SetOrderTotalAmount




    @BindingAdapter(value = {"SetOrderTime"})
    public static void SetOrderTime(TextView textView, Date date) {//_______________________________ SetOrderTime

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        String d = simpleDateFormat.format(date);
        textView.setText(d);

    }//_____________________________________________________________________________________________ SetOrderTime




    @BindingAdapter(value = {"SetAmountItemsWasteList"})
    public static void SetAmountItemsWasteList(TextView textView, float count) {//_________________ SetCountItemsWasteList
        StringBuilder builder = new StringBuilder();
        builder.append(Math.round(count));
        builder.append(" ");
        builder.append(textView.getContext().getResources().getString(R.string.KGr));
        textView.setText(builder.toString());
    }//_____________________________________________________________________________________________ SetCountItemsWasteLis



    @BindingAdapter(value = {"SetLayoutActionOrder"})
    public static void SetLayoutActionOrder(LinearLayout linearLayout, Integer State ){
        if (State.byteValue() == StaticValues.WasteCollectionStateOnProgress)
            linearLayout.setVisibility(View.VISIBLE);
        else if (State.byteValue() == StaticValues.WasteCollectionStateLading)
            linearLayout.setVisibility(View.VISIBLE);
        else
            linearLayout.setVisibility(View.GONE);
    }//_____________________________________________________________________________________________ SetLayoutActionOrder


    @BindingAdapter(value = {"SetStateOrder"})
    public static void SetStateOrder(TextView textView, Integer State ){
        if (State.byteValue() == StaticValues.WasteCollectionStateOnProgress)
            textView.setVisibility(View.GONE);
        else if (State.byteValue() == StaticValues.WasteCollectionStateLading)
            textView.setVisibility(View.GONE);
        else {
            textView.setVisibility(View.VISIBLE);
            if (State.byteValue() == StaticValues.WasteCollectionStateNoDelivery) {
                textView.setText(textView.getContext().getResources().getString(R.string.FailureDeliver));
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorBlack));
            } else if (State.byteValue() == StaticValues.WasteCollectionStateDelivered){
                textView.setText(textView.getContext().getResources().getString(R.string.Delivered));
                textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorWhite));
            }
        }


    }//_____________________________________________________________________________________________ SetStateOrder

}
