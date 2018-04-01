package com.yqbd.yqbdapp.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyInfoBean {
    private Integer companyId;

    private String companyName;

    private String companyAccount;

    private String password;

    private String classification;

    private String summary;

    private String headPortraitAddress;
}
