package com.yqbd.yqbdapp.bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class TaskBean implements Parcelable,Serializable {
    private Integer taskId;

    private Integer companyId;

    private Integer userId;

    private String classification;

    private String taskTitle;

    private Integer taskStatus;

    private Double pay;

    private Long publishTime;

    private Long deadline;

    private Long startTime;

    private Long completeTime;

    private Integer signUpPeopleNumber;

    private Integer currentPeopleNumber;

    private Integer maxPeopleNumber;

    private String simpleDrawingAddress;

    private Integer groupId;

    private String province;

    private String city;

    private String district;

    private String taskAddress;

    private String taskDescription;

    private String primaryWork;

    private String otherCompany;

    private String primaryContact;

    private String remark;

    public TaskBean(Parcel in) {
        taskId = in.readInt();
        companyId = in.readInt();
        userId = in.readInt();
        classification = in.readString();
        taskTitle = in.readString();
        taskStatus = in.readInt();
        pay = in.readDouble();
        publishTime = in.readLong();
        deadline = in.readLong();
        startTime = in.readLong();
        completeTime = in.readLong();
        signUpPeopleNumber = in.readInt();
        currentPeopleNumber = in.readInt();
        maxPeopleNumber = in.readInt();
        simpleDrawingAddress = in.readString();
        groupId = in.readInt();
        province= in.readString();
        city= in.readString();
        district= in.readString();
        taskAddress= in.readString();
        taskDescription= in.readString();
        primaryWork= in.readString();
        otherCompany= in.readString();
        primaryContact= in.readString();
        remark= in.readString();
    }

    public static final Creator<TaskBean> CREATOR = new Creator<TaskBean>() {
        @Override
        public TaskBean createFromParcel(Parcel in) {
            return new TaskBean(in);
        }

        @Override
        public TaskBean[] newArray(int size) {
            return new TaskBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeInt(taskId);
        dest.writeInt(companyId);
        dest.writeInt(userId);
        dest.writeString(classification);
        dest.writeString(taskTitle);
        dest.writeInt(taskStatus);
        dest.writeDouble(pay);
        dest.writeLong(publishTime);
        dest.writeLong(deadline);
        dest.writeLong(startTime);
        dest.writeLong(completeTime);
        dest.writeInt(signUpPeopleNumber);
        dest.writeInt(currentPeopleNumber);
        dest.writeInt(maxPeopleNumber);
        dest.writeString(simpleDrawingAddress);
        dest.writeInt(groupId);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeString(taskAddress);
        dest.writeString(taskDescription);
        dest.writeString(primaryWork);
        dest.writeString(otherCompany);
        dest.writeString(primaryContact);
        dest.writeString(remark);
    }
    
//    private String formatDateToStr(Date date){
//        if (date == null) return "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return sdf.format(date);
//    }
//
//    private Date formatStrToDate(String str){
//        if (str == null || str.isEmpty()) return null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = null;
//        try {
//            date = sdf.parse(str);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
//    }
}
