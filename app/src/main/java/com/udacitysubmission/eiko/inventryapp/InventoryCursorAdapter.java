package com.udacitysubmission.eiko.inventryapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacitysubmission.eiko.inventryapp.data.InventoryContract;

/**
 * Created by eiko on 12/26/2016.
 */
public class InventoryCursorAdapter extends CursorAdapter {
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        TextView tvItemName = (TextView)
                view.findViewById(R.id.name);
        TextView tvPrice = (TextView)
                view.findViewById(R.id.price);
        TextView tvQuantity = (TextView)
                view.findViewById(R.id.quantity);
        Button saleButton = (Button)
                view.findViewById(R.id.saleButton);
        ImageView imageview = (ImageView)
                view.findViewById(R.id.image);

        int sImage = cursor.getColumnIndex(
                InventoryContract.InventoryEntry.COLUMN_IMAGE);
        int sItemName = cursor.getColumnIndex(
                InventoryContract.InventoryEntry.COLUMN_ITEM_NAME);
        int sPrice = cursor.getColumnIndex(
                InventoryContract.InventoryEntry.COLUMN_ITEM_PRICE);
        int sQuantity = cursor.getColumnIndex(
                InventoryContract.InventoryEntry.COLUMN_ITEM_QUANTITY);
        int sId = cursor.getColumnIndex(
                InventoryContract.InventoryEntry._ID);

        String itemImageString = cursor.getString(sImage);
        final String itemName = cursor.getString(sItemName);
        String itemPrice = cursor.getString(sPrice);
        String itemQuantity = cursor.getString(sQuantity);
        final int quantityUpdate = Integer.parseInt(itemQuantity);
        final long itemId = cursor.getLong(sId);

        imageview.setImageURI(Uri.parse(itemImageString));

        tvItemName.setText(itemName);
        tvPrice.setText(itemPrice);
        tvQuantity.setText(itemQuantity);

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = quantityUpdate;
                if (qty > 0) {
                    qty--;
                }
                ContentValues values = new ContentValues();
                values.put(InventoryContract.InventoryEntry.COLUMN_ITEM_QUANTITY,
                        qty);
                ContentResolver contentResolver = v.getContext().getContentResolver();
                Uri uri = ContentUris.withAppendedId(
                        InventoryContract.InventoryEntry.CONTENT_URI,
                        itemId);
                contentResolver.update(uri, values, null, null);
            }
        });
    }
}
