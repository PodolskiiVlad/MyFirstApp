package com.example.listviewdemo;

import android.os.Parcel;
import android.os.Parcelable;

class MyListItem implements Parcelable {

    private String elementName;
    private String elementImagePath;
    private boolean elementSwitchState;

    MyListItem(){
        elementName = ContentMaker.generateName();
        elementImagePath = ContentMaker.generateImagePath();
    }

    private MyListItem(Parcel in) {
        elementName = in.readString();
        elementImagePath = in.readString();
        elementSwitchState = in.readByte() != 0;
    }

    public static final Creator<MyListItem> CREATOR = new Creator<MyListItem>() {
        @Override
        public MyListItem createFromParcel(Parcel in) {
            return new MyListItem(in);
        }

        @Override
        public MyListItem[] newArray(int size) {
            return new MyListItem[size];
        }
    };

    public String getElementName() {
        return elementName;
    }

    public String getImagePath() {

        return elementImagePath;
    }

    public boolean getSwitchState() {
        return elementSwitchState;
    }

    public void setSwitchState(boolean state) {
        this.elementSwitchState = state;
    }

    public void setImagePath(String path){
        elementImagePath = path;
    }

    public void setElementName(String elementName){
        this.elementName = elementName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(elementName);
        parcel.writeString(elementImagePath);
        parcel.writeByte((byte) (elementSwitchState ? 1 : 0));
    }
}
