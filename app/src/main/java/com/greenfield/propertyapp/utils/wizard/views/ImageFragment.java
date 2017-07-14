package com.greenfield.propertyapp.utils.wizard.views;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.greenfield.propertyapp.R;
import com.greenfield.propertyapp.utils.GenUtils;
import com.greenfield.propertyapp.utils.wizard.pages.Page;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by root on 7/4/17.
 */

public class ImageFragment extends Fragment {
    public static final String TAG = ImageFragment.class.getSimpleName();

    protected static final String ARG_KEY = "key";
    private static final String NEW_IMAGE_URI = "new_image_uri";
    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int REQUEST_CAMERA = 101;
    private static final int PIC_CROP = 102;
    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private Page mPage;
    private Uri mPhotoURI, mCroppedURI;
    private String data;
    private byte[] b;

    private ImageView imageView;
    private String encodedImage = "";
    private File compressedImage;

    private Uri mNewImageUri;

    public static ImageFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ImageFragment f = new ImageFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(mKey);

        if (savedInstanceState != null) {
            String uriString = savedInstanceState.getString(NEW_IMAGE_URI);
            if (!TextUtils.isEmpty(uriString)) {
                mNewImageUri = Uri.parse(uriString);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNewImageUri != null) {
            outState.putString(NEW_IMAGE_URI, mNewImageUri.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_image,
                container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage
                .getTitle());

        imageView = (ImageView) rootView.findViewById(R.id.imageView);

        String imageData = mPage.getData().getString(Page.SIMPLE_DATA_KEY);
        if (!TextUtils.isEmpty(imageData)) {
            Uri imageUri = Uri.parse(imageData);
            imageView.setImageURI(imageUri);
        } else {
            imageView.setImageResource(R.drawable.ic_person);
        }

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title(R.string.seller_pic_label)
                        .items(R.array.image_photo_sources)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                Toast.makeText(getContext(), " " + which, Toast.LENGTH_LONG).show();

                                switch (which) {
                                    case 0:
                                        Intent photoPickerIntent = new Intent(
                                                Intent.ACTION_GET_CONTENT);
                                        photoPickerIntent
                                                .setType("image/*");
                                        startActivityForResult(
                                                photoPickerIntent,
                                                GALLERY_REQUEST_CODE);

                                        break;

                                    default:
                                        // Camera
                                        cameraIntent();
//                                        mNewImageUri = getActivity()
//                                                .getContentResolver()
//                                                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                                        new ContentValues());
//                                        Intent photoFromCamera = new Intent(
//                                                MediaStore.ACTION_IMAGE_CAPTURE);
//                                        photoFromCamera.putExtra(
//                                                MediaStore.EXTRA_OUTPUT,
//                                                mNewImageUri);
//                                        photoFromCamera
//                                                .putExtra(
//                                                        MediaStore.EXTRA_SIZE_LIMIT,
//                                                        0);
//                                        startActivityForResult(
//                                                photoFromCamera,
//                                                CAMERA_REQUEST_CODE);
//                                        break;
                                }
                                return true;
                            }
                        })

                        .show();

            }
        });

//        new DialogFragment() {
//                    @Override
//                    public Dialog onCreateDialog(Bundle savedInstanceState) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(
//                                getActivity());
//                        builder.setItems(R.array.image_photo_sources,
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        switch (which) {
//                                            case 0:
//                                                // Gallery
//                                                Intent photoPickerIntent = new Intent(
//                                                        Intent.ACTION_GET_CONTENT);
//                                                photoPickerIntent
//                                                        .setType("image/*");
//                                                startActivityForResult(
//                                                        photoPickerIntent,
//                                                        GALLERY_REQUEST_CODE);
//                                                break;
//
//                                            default:
//                                                // Camera
//                                                mNewImageUri = getActivity()
//                                                        .getContentResolver()
//                                                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                                                new ContentValues());
//                                                Intent photoFromCamera = new Intent(
//                                                        MediaStore.ACTION_IMAGE_CAPTURE);
//                                                photoFromCamera.putExtra(
//                                                        MediaStore.EXTRA_OUTPUT,
//                                                        mNewImageUri);
//                                                photoFromCamera
//                                                        .putExtra(
//                                                                MediaStore.EXTRA_VIDEO_QUALITY,
//                                                                0);
//                                                startActivityForResult(
//                                                        photoFromCamera,
//                                                        CAMERA_REQUEST_CODE);
//                                                break;
//                                        }
//
//                                    }
//                                });
//                        return builder.create();
//                    }
//                };
//
//                pickPhotoSourceDialog.show(getFragmentManager(),
//                        "pickPhotoSourceDialog");
//            }
//        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException(
                    "Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
//            case CAMERA_REQUEST_CODE:
//                if (resultCode == Activity.RESULT_OK) {
//                    imageView.setImageURI(mNewImageUri);
//                    writeResult();
//                }
//                break;
            case GALLERY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    mNewImageUri = data.getData();
//                    performCrop(mNewImageUri);
                    imageView.setImageURI(mNewImageUri);
                    mPage.getData().putString(Page.SIMPLE_DATA_KEY,
                            (mNewImageUri != null) ? mNewImageUri.toString() : null);
                    mPage.notifyDataChanged();


                    writeResult();
                }
            case REQUEST_CAMERA:
                performCrop(mPhotoURI);
                break;
            case PIC_CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap selectedBitmap = extras.getParcelable("data");
                    onCroppedFinished(selectedBitmap);
                    writeResult();
                }
                // get the cropped bitmap

                break;
        }
    }


    private void writeResult() {
        mPage.getData().putString(Page.SIMPLE_DATA_KEY,
                (mCroppedURI != null) ? mCroppedURI.toString() : null);
        mPage.notifyDataChanged();

    }

    private void cameraIntent() {

        Intent intent = new Intent();

        File photoFile = null;

        try {
            photoFile = createImageFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Photo File " + photoFile);

        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
//        compressImage(image);

        mPhotoURI = Uri.fromFile(image);


        return image;
    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);

        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            GenUtils.getToastMessage(getContext(), errorMessage);
        }
    }

    private void onCroppedFinished(Bitmap bitmap) {

//  Code smell
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

            b = baos.toByteArray();

            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


            imageView.setImageBitmap(bitmap);

            Log.d(TAG, "Ecoded image: " + encodedImage);
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/saved_images");
            myDir.mkdirs();
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = "Image-" + n + ".jpg";
            compressedImage = new File(myDir, fname);
            try {
                FileOutputStream out = new FileOutputStream(compressedImage);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                mCroppedURI = Uri.fromFile(compressedImage);
                writeResult();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}