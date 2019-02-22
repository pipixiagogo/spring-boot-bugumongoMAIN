package com.zh.mongodb.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//升级选项类型
public enum UpgradeOptionTypes {

    //下载中:   等待下载 请求下载 开始下载
    UPGREATE_OPTION_STATE_DOWNLOADING(1, UpgradeCommand.WAIT,UpgradeCommand.REQ_UPGRADE,UpgradeCommand.START_DOWNLOAD),
    //下载完成:  下载成功 下载失败
    UPGREATE_OPTION_STATE_DOWNLOAD_FIN(2,UpgradeCommand.DOWNLOAD_END,UpgradeCommand.DOWNLOAD_ERROR),
    //传输:  开始传输
    UPGREATE_OPTION_STATE_TRANSING(3,UpgradeCommand.TRANSING),
    //传输完成: 传输成功 传输失败
    UPGREATE_OPTION_STATE_TRANSING_FIN(4,UpgradeCommand.TRANSF_ERROR,UpgradeCommand.UPGRADING),
    //开始升级:  升级中
    UPGREATE_OPTION_STATE_UPGRADEING(5,UpgradeCommand.UPGRADING),
    //升级成功
    UPGREATE_OPTION_STATE_UP_SUCCESS(6,UpgradeCommand.UP_SUCCESS),
    //升级失败:升级失败:设备离线、升级失败:重复升级、升级失败:URL错误、升级失败:匹配信息错误、升级失败:包验证失败、升级失败:升级指令响应超时、
    //升级失败:下载失败、升级失败:传输失败、升级失败:更新程序包失败、升级失败:取消升级
    UPGREATE_OPTION_STATE_UP_FAIL(7,UpgradeCommand.UP_ERROR,
            UpgradeCommand.URL_ERROR,UpgradeCommand.MATCH_ERROR,
            UpgradeCommand.VERIFY_ERROR,UpgradeCommand.OFFLINE_ERROR,
            UpgradeCommand.REPEAT_ERROR,UpgradeCommand.NO_UPGRADE_ACK,
            UpgradeCommand.STATUS_CANCEL_SUCCESS,UpgradeCommand.TRANSF_ERROR,
            UpgradeCommand.DOWNLOAD_ERROR);
    private Integer status;
    private List<Integer> types;
    private static Map<Integer,UpgradeOptionTypes> map;
    UpgradeOptionTypes(int status, Integer... types) {
        this.status = status;
        this.types = Arrays.asList(types);
        init();
    }

    private void init() {
        if( map == null ){
            map = new HashMap<>();
        }
        map.put(this.status,this);
    }

    //验证状态
    public boolean match(Integer status){
        if(status == null){
            return false;
        }
        return types.contains(status);
    }

    public static UpgradeOptionTypes getVal(Integer i){
        if(i == null){
            return null;
        }
        return map.get(i);
    }

}
