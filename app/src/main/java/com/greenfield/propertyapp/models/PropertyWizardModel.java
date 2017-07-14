package com.greenfield.propertyapp.models;

import android.content.Context;

import com.greenfield.propertyapp.utils.Constants;
import com.greenfield.propertyapp.utils.wizard.model.AbstractWizardModel;
import com.greenfield.propertyapp.utils.wizard.pages.GeoPage;
import com.greenfield.propertyapp.utils.wizard.pages.NumberPage;
import com.greenfield.propertyapp.utils.wizard.pages.PageList;
import com.greenfield.propertyapp.utils.wizard.pages.SingleFixedChoicePage;
import com.greenfield.propertyapp.utils.wizard.pages.TextPage;

import static com.greenfield.propertyapp.utils.Constants.COMPOUND_CHOICE;
import static com.greenfield.propertyapp.utils.Constants.CONTAINER_CHOICE;
import static com.greenfield.propertyapp.utils.Constants.FLAT_APPARTMENT_CHOICE;
import static com.greenfield.propertyapp.utils.Constants.HUTS_CHOICE;
import static com.greenfield.propertyapp.utils.Constants.LIVING_QUARTERS_ATTACHED_TO_OFFICE_CHOICE;
import static com.greenfield.propertyapp.utils.Constants.OTHER_CHOICE;
import static com.greenfield.propertyapp.utils.Constants.SEMI_DETACHED_CHOICE;
import static com.greenfield.propertyapp.utils.Constants.UNCOMPLETED_BUILDING_CHOICE;


/**
 * Created by root on 7/2/17.
 */

public class PropertyWizardModel extends AbstractWizardModel {
    public PropertyWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(

                new SingleFixedChoicePage(this, Constants.PROPERTY_TYPE_LABEL)
                        .setChoices(Constants.LAND_CHOICE,
                                Constants.HOUSE_CHOICE,
                                Constants.KIOSK_CHOICE,
                                CONTAINER_CHOICE,
                                Constants.SEPERATE_HOUSE_UNIT_CHOICE,
                                SEMI_DETACHED_CHOICE,
                                FLAT_APPARTMENT_CHOICE,
                                COMPOUND_CHOICE,
                                HUTS_CHOICE,
                                LIVING_QUARTERS_ATTACHED_TO_OFFICE_CHOICE,
                                UNCOMPLETED_BUILDING_CHOICE,
                                OTHER_CHOICE)
                        .setRequired(true),
                new SingleFixedChoicePage(this, Constants.CLASSIFICATION_TYPE_LABEL)
                        .setChoices(Constants.RESIDENTIAL_CHOICE,
                                Constants.COMMERCIAL_CHOICE),
                new SingleFixedChoicePage(this, Constants.OWNERSHIP_STATUS_LABEL)
                        .setChoices(Constants.LEGAL_CHOICE, Constants.ILLEGAL_CHOICE),
                new SingleFixedChoicePage(this, Constants.REGISTRATION_STATUS_LABEL)
                        .setChoices(Constants.YES_CHOICE, Constants.NO_CHOICE),
                new SingleFixedChoicePage(this, Constants.ELECTRICITY_SOURCE_LABEL)
                        .setChoices(Constants.MAIN_CHOICE, Constants.PRIVATE_GENERATOR_CHOICE, Constants.NONE_CHOICE),
                new NumberPage(this, Constants.NUMBER_OF_UNITS_LABEL)
                ,
                new TextPage(this, Constants.ADDRESS_LABEL),

                new GeoPage(this, "Location")
                ,
                new TextPage(this, Constants.PROPERTY_IDENTIFICATION_NUMBER_LABEL)
                ,
                new TextPage(this, Constants.IDENTURE_NUMBER_LABEL)
                        .setRequired(true)

        );
    }
}
