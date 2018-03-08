package com.yqbd.yqbdapp.user.request;

import com.yqbd.yqbdapp.base.YQBDBaseRequest;
import com.yqbd.yqbdapp.bean.TaskBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskRequest extends YQBDBaseRequest {
    TaskBean taskBean;
}
