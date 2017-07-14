package com.greenfield.propertyapp.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SOVAVY on 5/8/2017.
 */

//
//firstname		: String,
//        surname			: String,
//        othernames 		: String,
//        dob 			: Date,
//        birthPlace 		: String,
//        nationality 	: String,
//        nationalityType : String,
//        dualCitizenship	: [String],
//        ethnicity 		: String,
//        maritalStatus 	: String,
//        languages 		: {
//        spoken 		: [String],
//        written 	: [String]
//        },
//        phones			: [String],
//        email 			: String,
//        address 		: {
//        residential : String,
//        postal 		: String,
//        work 		: String
//        },
//        region 			: String,
//        districtType 	: String,
//        homeGps			: {
//        lat		: String,
//        lng		: String
//        },
//        what3words 		: {
//        home		: String,
//        work 		: String
//        },
//        photo 			: String,
//        employmentStatus: String,
//        employer 		: String,
//        occupation 		: String,
//        commencementDate: Date,
//        position 		: String,
//        employmentSector: String,
//        disability		: [String],
//        identification	: {
//        type  		: String,
//        number		: String,
//        picture		: String
//        },
//
//        createdBy 		: { type: Schema.Types.ObjectId, ref: 'Agent' },
//        createdDate 	: { type: Date, default: Date.now },
public class OwnerMDL extends RealmObject {

    //personal data
    @PrimaryKey
    private String id;
    private String firstname;
    private String lastname;
    private String othername;
    private String maritalStatus;
    private String disability;
    private String ownerPhoto;


    //    private String dob;
//    private String profession;
//    private String commencementDate;

    //    id data


    @SerializedName("identification")
//    private IdentificationMDL identificationMDL;

    //    timestamp
    private String createdDate;

    //contact detail
    private String PhoneNumber;
    private String PostalAddress;
    private String email;
    private String houseAddress;
    private String AdditionalPhoneNumber;
    private String district;

    //    language data
    private String languageSpoken;
    private String languageWritten;
    private String getLanguageSpokenWritten;

//    Citizenship data

    private String nationalityType;
    private String nationality;
    private String dualCityzenship;
    private String ethnicity;
    private String birthPlace;
    private String dateOfBirth;
    // Employment Status
    private String employer;
    private String employmentStatus;
    private String employmentSector;
    private String position;
    private String profession;
    private String workplaceLocation;
    private String tin_number;

    public String getTin_number() {
        return tin_number;
    }

    public void setTin_number(String tin_number) {
        this.tin_number = tin_number;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }


    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getEmploymentSector() {
        return employmentSector;
    }

    public void setEmploymentSector(String employmentSector) {
        this.employmentSector = employmentSector;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWorkplaceLocation() {
        return workplaceLocation;
    }

    public void setWorkplaceLocation(String workplaceLocation) {
        this.workplaceLocation = workplaceLocation;
    }


    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public String getNationalityType() {
        return nationalityType;
    }

    public void setNationalityType(String nationalityType) {
        this.nationalityType = nationalityType;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDualCityzenship() {
        return dualCityzenship;
    }

    public void setDualCityzenship(String dualCityzenship) {
        this.dualCityzenship = dualCityzenship;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }


    public String getLanguageSpoken() {
        return languageSpoken;
    }

    public void setLanguageSpoken(String languageSpoken) {
        this.languageSpoken = languageSpoken;
    }

    public String getLanguageWritten() {
        return languageWritten;
    }

    public void setLanguageWritten(String languageWritten) {
        this.languageWritten = languageWritten;
    }

    public String getGetLanguageSpokenWritten() {
        return getLanguageSpokenWritten;
    }

    public void setGetLanguageSpokenWritten(String getLanguageSpokenWritten) {
        this.getLanguageSpokenWritten = getLanguageSpokenWritten;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getOwnerPhoto() {
        return ownerPhoto;
    }

    public void setOwnerPhoto(String ownerPhoto) {
        this.ownerPhoto = ownerPhoto;
    }


    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAdditionalPhoneNumber() {
        return AdditionalPhoneNumber;
    }

    public void setAdditionalPhoneNumber(String additionalPhoneNumber) {
        AdditionalPhoneNumber = additionalPhoneNumber;
    }


    public String getPostalAddress() {
        return PostalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        PostalAddress = postalAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

//    public IdentificationMDL getIdentificationMDL() {
//        return identificationMDL;
//    }
//
//    public void setIdentificationMDL(IdentificationMDL identificationMDL) {
//        this.identificationMDL = identificationMDL;
//    }

}
