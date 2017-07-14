package com.greenfield.propertyapp.models;

import android.content.Context;

import com.greenfield.propertyapp.utils.Constants;
import com.greenfield.propertyapp.utils.wizard.model.AbstractWizardModel;
import com.greenfield.propertyapp.utils.wizard.pages.BranchPage;
import com.greenfield.propertyapp.utils.wizard.pages.ContactInfoPage;
import com.greenfield.propertyapp.utils.wizard.pages.CustomerInfoPage;
import com.greenfield.propertyapp.utils.wizard.pages.DatePickerPage;
import com.greenfield.propertyapp.utils.wizard.pages.GeoPage;
import com.greenfield.propertyapp.utils.wizard.pages.ImagePage;
import com.greenfield.propertyapp.utils.wizard.pages.NumberPage;
import com.greenfield.propertyapp.utils.wizard.pages.PageList;
import com.greenfield.propertyapp.utils.wizard.pages.SingleFixedChoicePage;
import com.greenfield.propertyapp.utils.wizard.pages.TextPage;


/**
 * Created by root on 7/10/17.
 */

public class OwnerWizardModel extends AbstractWizardModel {

    public OwnerWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new CustomerInfoPage(this, Constants.NAME_LABEL),
                new SingleFixedChoicePage(this, Constants.MARITAL_STATUS_LABEL)
                        .setChoices(Constants.SINGLE_CHOICE,
                                Constants.MARRIED_CHOICE,
                                Constants.DIVORCED_CHOICE,
                                Constants.WIDOW_CHOICE,
                                Constants.WIDOWER_CHOICE),
                new TextPage(this, Constants.DISABILITIES_STATUS_LABEL),
                new ImagePage(this , Constants.PICTURE_LABEL),
                new ImagePage(this , Constants.ID_PICTURE_LABEL),
                new SingleFixedChoicePage(this, Constants.ID_TYPE_LABEL)
                        .setChoices(Constants.VOTER_ID_CHOICE,
                                Constants.DRIVER_LICENCSE_CHOICE,
                                Constants.SSNIT_NUMBER_CHOICE,
                                Constants.NHIS_CHOICE,
                                Constants.NATIONAL_ID_CHOICE,
                                Constants.PASSPORT_CHOICE),
                new TextPage(this, Constants.ID_NUMBER_LABEL),
                new ContactInfoPage(this,Constants.PHONE_LABEL),
                new TextPage(this, Constants.EMAIL_LABEL),
                new TextPage(this, Constants.ADDRESS_LABEL),
                new SingleFixedChoicePage(this, Constants.LANGUAGE_WRITTEN_LABEL)
                        .setChoices(Constants.ENGLISH_CHOICE,
                                Constants.FRENCH_CHOICE),
                new TextPage(this, Constants.LANGUAGE_SPOKEN_LABEL),
                new TextPage(this, Constants.ETHNICITY_LABEL),
                new DatePickerPage(this,  Constants.DATE_PICKER_LABEL)



//
//                new SingleFixedChoicePage(this, Constants.CLASSIFICATION_TYPE_LABEL)
//                        .setChoices(Constants.RESIDENTIAL_CHOICE,
//                                Constants.COMMERCIAL_CHOICE),
//                new SingleFixedChoicePage(this, Constants.OWNERSHIP_STATUS_LABEL)
//                        .setChoices(Constants.LEGAL_CHOICE, Constants.ILLEGAL_CHOICE),
//                new SingleFixedChoicePage(this, Constants.REGISTRATION_STATUS_LABEL)
//                        .setChoices(Constants.YES_CHOICE, Constants.NO_CHOICE),
//                new SingleFixedChoicePage(this, Constants.ELECTRICITY_SOURCE_LABEL)
//                        .setChoices(Constants.MAIN_CHOICE, Constants.PRIVATE_GENERATOR_CHOICE, Constants.NONE_CHOICE),
//                new NumberPage(this, Constants.NUMBER_OF_UNITS_LABEL)
//                ,
//                new TextPage(this, Constants.ADDRESS_LABEL),
//
//                new GeoPage(this, "Location")
//                ,
//                new TextPage(this, Constants.PROPERTY_IDENTIFICATION_NUMBER_LABEL)
//                ,
//                new TextPage(this, Constants.IDENTURE_NUMBER_LABEL)
//                        .setRequired(true)
//
        );
    }
}
