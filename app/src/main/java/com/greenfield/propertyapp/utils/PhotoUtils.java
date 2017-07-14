package com.greenfield.propertyapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by root on 7/9/17.
 */

public class PhotoUtils {

    public static Bitmap getBitmapFromUri(Uri uri, Context context) throws IOException {
        Bitmap image = null;

        if (uri != null){
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
        }
       ;
       
        return image;
    }

    public static String convertBitmap2Base64(Bitmap bitmap) {
        byte[] b;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap != null){
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }


        b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);

    }


}
