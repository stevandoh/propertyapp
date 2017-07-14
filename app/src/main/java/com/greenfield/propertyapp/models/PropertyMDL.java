//package com.greenfield.propertyapp.models;
//
//import com.google.gson.annotations.SerializedName;
//
//import io.realm.RealmList;
//import io.realm.RealmObject;
//import io.realm.annotations.PrimaryKey;
//
///**
// * Created by SOVAVY on 5/7/2017.
// */
//
////
////pins 			: String,
////        propertyType	: String,
////        classification 	: String,
////        ownershipType 	: {
////        status 		: { type: String, enum: ['Legal', 'Illegal'] },
////        registered  : { type: Boolean },
////        titleNumber : String,
////        indentureNumber: String
////        },
////        location		: {
////        gps 		: { lat: Number, lng: Number },
////        region 		: { type: Schema.Types.ObjectId, ref: 'Region' },
////        town 		: String,
////        district	: { type: Schema.Types.ObjectId },
////        what3words	: String
////        },
////        address 		: String,
////        familyUnits     : Number,
////        electricitySource: String,
////        partnership		:[{
////        name 		: String,
////        percentage	: Number
////        }],
////        partnershipType : String,
////
////        emergency		: {
////        name 		: String,
////        address		: String,
////        city		: String,
////        phone		: String,
////        email		: String
////        },
////        agent           : {
////        name 		: String,
////        address		: String,
////        city		: String,
////        phone		: String,
////        email		: String
////        },
//
//public class PropertyMDL extends RealmObject {
//    @SerializedName("owners")
//    public RealmList<OwnerMDL> ownerList;
//    @PrimaryKey
//    private String id;
//    private String pins;
//    private String propertyType;
//    private String classification;
//    private String ownershipType;
//    private boolean status;
//    private boolean registered;
//    private String titleNumber;
//    private String identureNumber;
//    //        location		: {
////        gps 		: { lat: Number, lng: Number },
////        region 		: { type: Schema.Types.ObjectId, ref: 'Region' },
////        town 		: String,
////        district	: { type: Schema.Types.ObjectId },
////        what3words	: String
////        },
//    @SerializedName("location")
//    private LocationMDL locationMDL;
//    private String address;
//    private String familyUnit;
//    private String electricitySource;
//    private String partnershipName;
//    private float partnershipPercentage;
//    private String partnershipType;
////    private UserMDL createdBy;
//    @SerializedName("propertyManager")
////    private PropertyManagerMDL propertyManagerMDL;
//    private String createdDate;
//
////    public void setCreatedBy(UserMDL createdBy) {
////        this.createdBy = createdBy;
////    }
//
//    public RealmList<OwnerMDL> getOwnerList() {
//        return ownerList;
//    }
//
//    public void setOwnerList(RealmList<OwnerMDL> ownerList) {
//        this.ownerList = ownerList;
//    }
//
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getPins() {
//        return pins;
//    }
//
//    public void setPins(String pins) {
//        this.pins = pins;
//    }
//
//    public String getPropertyType() {
//        return propertyType;
//    }
//
//    public void setPropertyType(String propertyType) {
//        this.propertyType = propertyType;
//    }
//
//    public String getClassification() {
//        return classification;
//    }
//
//    public void setClassification(String classification) {
//        this.classification = classification;
//    }
//
//    public String getOwnershipType() {
//        return ownershipType;
//    }
//
//    public void setOwnershipType(String ownershipType) {
//        this.ownershipType = ownershipType;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
//    public boolean isRegistered() {
//        return registered;
//    }
//
//    public void setRegistered(boolean registered) {
//        this.registered = registered;
//    }
//
//    public String getTitleNumber() {
//        return titleNumber;
//    }
//
//    public void setTitleNumber(String titleNumber) {
//        this.titleNumber = titleNumber;
//    }
//
//    public String getIdentureNumber() {
//        return identureNumber;
//    }
//
//    public void setIdentureNumber(String identureNumber) {
//        this.identureNumber = identureNumber;
//    }
//
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getFamilyUnit() {
//        return familyUnit;
//    }
//
//    public void setFamilyUnit(String familyUnit) {
//        this.familyUnit = familyUnit;
//    }
//
//    public String getElectricitySource() {
//        return electricitySource;
//    }
//
//    public void setElectricitySource(String electricitySource) {
//        this.electricitySource = electricitySource;
//    }
//
//    public String getPartnershipName() {
//        return partnershipName;
//    }
//
//    public void setPartnershipName(String partnershipName) {
//        this.partnershipName = partnershipName;
//    }
//
//    public float getPartnershipPercentage() {
//        return partnershipPercentage;
//    }
//
//    public void setPartnershipPercentage(float partnershipPercentage) {
//        this.partnershipPercentage = partnershipPercentage;
//    }
//
//    public String getPartnershipType() {
//        return partnershipType;
//    }
//
//    public void setPartnershipType(String partnershipType) {
//        this.partnershipType = partnershipType;
//    }
//
//
////    public boolean isLandTrust() {
////        return landTrust;
////    }
////
////    public void setLandTrust(boolean landTrust) {
////        this.landTrust = landTrust;
////    }
////
////    public RealmList<TrustyModel> getTrusties() {
////        return trusties;
////    }
////
////    public void setTrusties(RealmList<TrustyModel> trusties) {
////        this.trusties = trusties;
////    }
//
//
//    public LocationMDL getLocationMDL() {
//        return locationMDL;
//    }
//
////    public void setLocationMDL(LocationMDL locationMDL) {
////        this.locationMDL = locationMDL;
////    }
//
//
//}
