package com.yqbd.yqbdapp.bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoBean implements Parcelable {
    private Integer userId;

    private String accountNumber;

    private String password;

    private String sex;

    private String realName;

    private String nickName;

    private String headPortrait;

    private Integer professionalLevel;

    private Integer creditLevel;

    private String telephone;

    private String school;

    private String occupation;

    private String companyName;

    protected UserInfoBean(Parcel in) {
        accountNumber = in.readString();
        password = in.readString();
        sex = in.readString();
        realName = in.readString();
        nickName = in.readString();
        headPortrait = in.readString();
        telephone = in.readString();
        school = in.readString();
        occupation = in.readString();
        companyName = in.readString();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accountNumber);
        dest.writeString(password);
        dest.writeString(sex);
        dest.writeString(realName);
        dest.writeString(nickName);
        dest.writeString(headPortrait);
        dest.writeString(telephone);
        dest.writeString(school);
        dest.writeString(occupation);
        dest.writeString(companyName);
    }
}