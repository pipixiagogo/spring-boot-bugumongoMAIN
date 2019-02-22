package com.zh.mongodb.common;

public class DoMain{
    public static void main(String[] args) {
//        for(UpgradeOptionTypes types:UpgradeOptionTypes.values()){
//            //一共7种状态
//            System.out.println(types+"   "+types.ordinal());
//            //System.out.println(types.name());
//        }

        UpgradeOptionTypes val = UpgradeOptionTypes.getVal(1);
        boolean equals = val.equals(UpgradeOptionTypes.UPGREATE_OPTION_STATE_TRANSING);
        System.out.println(equals);
        System.out.println(val);
        boolean match = val.match(17);
        System.out.println(match);


    }
}
