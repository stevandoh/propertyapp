package com.greenfield.propertyapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.greenfield.propertyapp.models.OwnerWizardModel;
import com.greenfield.propertyapp.utils.Constants;
import com.greenfield.propertyapp.utils.GenUtils;
import com.greenfield.propertyapp.utils.PhotoUtils;
import com.greenfield.propertyapp.utils.VolleyRequests;
import com.greenfield.propertyapp.utils.wizard.model.AbstractWizardModel;
import com.greenfield.propertyapp.utils.wizard.model.ModelCallbacks;
import com.greenfield.propertyapp.utils.wizard.pages.Page;
import com.greenfield.propertyapp.utils.wizard.views.PageFragmentCallbacks;
import com.greenfield.propertyapp.utils.wizard.views.ReviewFragment;
import com.greenfield.propertyapp.utils.wizard.views.StepPagerStrip;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

import static com.greenfield.propertyapp.utils.Constants.NULL;
import static com.greenfield.propertyapp.utils.Constants.SHOP_LABEL_AND_SHOP_TYPE_LABEL;

public class OwnerFormActivity extends FragmentActivity implements PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks {
    public static final String TAG = PropertyFormActivity.class.getSimpleName();
    double lon, lat;
    VolleyRequests mVolleyRequest;
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    private boolean mEditingAfterReview;
    private AbstractWizardModel mWizardModel = new OwnerWizardModel(this);
    private boolean mConsumePageSelectedEvent;
    private Button mNextButton;
    private Button mPrevButton;
    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;
    private Realm mRealm;
    private RealmAsyncTask realmAsyncTask;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);


        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {


                    //getData();


//                   dg = new DialogFragment() {
//                        @Override
//                        public Dialog onCreateDialog(Bundle savedInstanceState) {
//                            return new AlertDialog.Builder(getActivity())
//                                    .setMessage(R.string.submit_confirm_message)
//                                    .setPositiveButton(R.string.submit_confirm_button, null)
//                                    .setNegativeButton(android.R.string.cancel, null)
//                                    .create();
//                        }
//                    };
//                    dg.show(getSupportFragmentManager(), "place_order_dia
// ");
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });


        onPageTreeChanged();
        updateBottomBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getData() {
        final String firstname = mWizardModel.findByKey(Constants.NAME_LABEL).getData().getString(Constants.FIRSTNAME_DATA_KEY);
        final String surname = mWizardModel.findByKey(Constants.NAME_LABEL).getData().getString(Constants.SURNAME_DATA_KEY);
        final String sellerType = mWizardModel.findByKey(Constants.SELLLER_TYPE_LABEL).getData().getString(Constants.SIMPLE_DATA_KEY);
        final String shop = mWizardModel.findByKey(SHOP_LABEL_AND_SHOP_TYPE_LABEL).getData().getString(Constants.SIMPLE_DATA_KEY, NULL);
        final String hawkerType = mWizardModel.findByKey(Constants.HAWKER_LABEL_AND_HAWKER_TYPE_LABEL).getData().getString(Constants.SIMPLE_DATA_KEY, NULL);
        final String hawkerStationaryType = mWizardModel.findByKey(Constants.HAWKER_STATIONARY_LABEL_AND_SELLING_SPOT_LABEL).getData().getString(Constants.SIMPLE_DATA_KEY, NULL);
        final String phone = mWizardModel.findByKey(Constants.PHONE_LABEL).getData().getString(Constants.PHONE_DATA_KEY);
        final String network = mWizardModel.findByKey(Constants.PHONE_LABEL).getData().getString(Constants.NETWORK_DATA_KEY);
        final String location = mWizardModel.findByKey(Constants.LOCATION_LABEL).getData().getString(Constants.SIMPLE_DATA_KEY, NULL).trim();
        final String photoURI = mWizardModel.findByKey(Constants.PICTURE_LABEL).getData().getString(Constants.SIMPLE_DATA_KEY, NULL);
        Uri photo;
        Bitmap photoToBitmap = null;
        final String photoToBase;
        if (photoURI != "(None)") {
            photo = Uri.parse(photoURI);

            try {
                photoToBitmap = PhotoUtils.getBitmapFromUri(photo, getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoToBase = PhotoUtils.convertBitmap2Base64(photoToBitmap);
        } else {
            photoToBase = NULL;
        }


//                    Log.d(TAG,location);

        if (!Objects.equals(location, NULL)) {
            String[] strings = location.split(Constants.COMMA);
            lat = Double.parseDouble(strings[0]);
            lon = Double.parseDouble(strings[1]);
        }

        GenUtils.getToastMessage(OwnerFormActivity.this, photoToBase);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date();
        final String id = UUID.randomUUID().toString();
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
////
//                SellerMDL sellerMDL = realm.createObject(SellerMDL.class, id);
//                sellerMDL.setFirstname(firstname);
//                sellerMDL.setSurname(surname);
//                sellerMDL.setType(sellerType);
//                sellerMDL.setShopType(shop);
//                sellerMDL.setHawkerType(hawkerType);
//                sellerMDL.setSellingSpot(hawkerStationaryType);
//                sellerMDL.setPhone(phone);
//                sellerMDL.setMobileNetwork(network);
//                sellerMDL.setLongitude(lon);
//                sellerMDL.setLatitude(lat);
//                sellerMDL.setPhoto(photoToBase);
//                sellerMDL.setPhotoUri(photoURI);
//                if (sellerType.equals(Constants.SHOP_LABEL)) {
//                    sellerMDL.setMonthlyPayment("50");
//                } else {
//                    if (sellerType.equals(Constants.HAWKER_LABEL)) {
//                        if (hawkerType.equals(Constants.HAWKER_TRUCK_PUSHER_LABEL)) {
//                            sellerMDL.setMonthlyPayment("5");
//                            sellerMDL.setMonthlyPayment("1");
//                        } else {
//                            sellerMDL.setMonthlyPayment("1");
//                            if (hawkerStationaryType.equals(Constants.HAWKER_STATIONARY_FLOOR_LABEL)) {
//                                sellerMDL.setDailyPayment("0.5");
//                            } else {
//                                sellerMDL.setDailyPayment("1.0");
//                            }
//                        }
//
//                    }
//                }
//
//
//                sellerMDL.setCreatedDate(dateFormat.format(date));
//            }
//
//        });
//
//
//        SellerMDL sellerMDL = mRealm.where(SellerMDL.class).findAllSorted("createdDate").last();
//
//
//        if (!sellerMDL.isHasSynced()) {
//
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("firstname", firstname);
//            params.put("surname", surname);
//            params.put("type", sellerType);
//            params.put("phone", phone);
//            params.put("sellingSpot", hawkerStationaryType);
//            params.put("mmNetwork", network);
//            params.put("uuid", id);
//            params.put("photo", photoToBase);
//            mVolleyRequest.postData(Constants.SELLERS_URL, params, new VolleyRequests.VolleyPostCallBack() {
//
//                @RequiresApi(api = Build.VERSION_CODES.M)
//                @Override
//                public void onSuccess(final JSONObject result) {
//                    Log.d(TAG, result.toString());
//                    try {
//
//                        final String message = result.getString("message");
//                        final String success = result.getString("success");
//
//                        Log.d(TAG, message);
//                        Log.d(TAG, success);
//                        final JSONObject seller = result.getJSONObject("seller");
////
//                        mRealm.executeTransaction(new Realm.Transaction() {
//                            @Override
//                            public void execute(Realm realm) {
//                                SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();
//                                try {
//                                    sellerMDL.setWebguid(seller.getString("_id"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                                sellerMDL.setHasSynced(true);
//                            }
//                        });
//
//                        progressDialog.dismiss();
//                        GenUtils.getToastMessage(PropertyFormActivity.this, message);
//                        GenUtils.getToastMessage(PropertyFormActivity.this, success);
//                        startActivity(new Intent(PropertyFormActivity.this, MenuActivity.class));
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @RequiresApi(api = Build.VERSION_CODES.M)
//                @Override
//                public void onError(VolleyError error) {
//
//                    Log.e("Shit", error.toString());
//                    String message = null;
//                    if (error instanceof NetworkError) {
//                        message = "Cannot connect to Internet...Please check your connection!";
//                    } else if (error instanceof ServerError) {
//                        message = "The server could not be found. Please try again after some time!!";
//                    } else if (error instanceof AuthFailureError) {
//                        message = "Cannot connect to Internet...Please check your connection!";
//                    } else if (error instanceof ParseError) {
//                        message = "Parsing error! Please try again after some time!!";
//                    } else if (error instanceof NoConnectionError) {
//                        message = "Cannot connect to Internet...Please check your connection!";
//                    } else if (error instanceof TimeoutError) {
//                        message = "Connection TimeOut! Please check your internet connection.";
//
//                    }
////                    progressBar.setVisibility(View.INVISIBLE);
//                    progressDialog.dismiss();
//                }
//
//                @Override
//                public void onStart() {
////                    progressBar.setVisibility(View.VISIBLE);
//                    progressDialog.setMessage("Please wait...");
////                    progressDialog.setIndeterminate(false);
//                    progressDialog.setCancelable(false);
//                    progressDialog.show();
//                }
//
//                @Override
//                public void onFinish() {
////                    progressBar.setVisibility(View.INVISIBLE);
//                    progressDialog.dismiss();
//                }
//            });
//
//        } else {
//            GenUtils.getToastMessage(PropertyFormActivity.this, "already synced");
//        }

    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }

        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO: be smarter about this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }


        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }


        @Override
        public int getCount() {
            if (mCurrentPageSequence == null) {
                return 0;
            }
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

    }

}
