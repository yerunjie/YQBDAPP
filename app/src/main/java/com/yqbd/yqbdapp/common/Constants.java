package com.yqbd.yqbdapp.common;

import android.graphics.Color;
import com.yqbd.yqbdapp.R;

public class Constants {
    public enum TASK_STATUS{
        RECRUITING(0,"未开始", R.drawable.border_blue, R.color.accessible_blue),
        ONGOING(1, "进行中", R.drawable.border_green, R.color.accessible_green),
        FINISH(2,"已结束", R.drawable.border_grey, R.color.text_disable);

        private int value;
        private String name;
        private int bgId;
        private int colorId;
        Color color = new Color();

        private TASK_STATUS(int value, String name, int bgId, int colorId) {
            this.value = value;
            this.name = name;
            this.bgId = bgId;
            this.colorId = colorId;
        }

        public static TASK_STATUS getConfig(int value){
            TASK_STATUS result = null;
            for (TASK_STATUS status : TASK_STATUS.values()) {
                if (value == status.value){
                    result = status;
                    break;
                }
            }
            return result;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public int getBgId() {
            return bgId;
        }

        public int getColorId() {
            return colorId;
        }
    }

}
