package com.zh.mongodb.common;

import com.bugull.mongo.annotations.EnsureIndex;
import com.bugull.mongo.annotations.Entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@EnsureIndex("{deviceName:1},{batchCode:1}")
public class UpgradeCommand extends UpgradeData {
    public static final int DOWNLOAD_SUCCESS = 0;
    public static final int DOWNLOAD_SERIAL_COM_ERROR = 1;
    public static final int DOWNLOAD_CHECK_RESULT_ERROR = 2;

    public static final byte TRANS_SUCCESS = 0;
    public static final byte TRANS_ERROR = 1;

    public static final byte CANCEL_SUCCESS = 0;
    public static final byte CANCEL_ERROR = 1;

    public static final byte UPGRADE_SUCCESS = 0;
    public static final byte UPGRADE_ERROR = 1;
    //按照顺序走
    //public resources final int NORMAL = -1;
    //下载中:
    public static final int WAIT = 0;//等待下载
    public static final int REQ_UPGRADE = 1; //定时器修改  请求下载
    public static final int START_DOWNLOAD = 2;//等待下载

    public static final int DOWNLOAD_END = 3;//下载完成
    public static final int TRANSING = 4;//开始传输 BMU
    public static final int UPGRADING = 5;//升级中 RDB
    public static final int UPGRADE_END = 6;//升级结束

    public static final int STATUS_CANCEL_SUCCESS = 7;//升级取消成功
    public static final int STATUS_CANCEL_ERROR = 8;//升级取消失败
    public static final int UP_SUCCESS = 9;//升级成功
    public static final int DOWNLOAD_ERROR = 10;//下载错误
    public static final int UP_ERROR = 11;//升级失败:升级失败
    public static final int TRANSF_ERROR = 12;
    public static final int URL_ERROR = 13;//URL错误
    public static final int MATCH_ERROR = 14;//校验错误
    public static final int VERIFY_ERROR = 15;//验证码
    public static final int OFFLINE_ERROR = 16;//传输失败
    public static final int REPEAT_ERROR = 17;//重复升级
    public static final int NO_UPGRADE_ACK = 18;//升级指令响应超时
    public static final Map<Integer,String> msgs = new HashMap<>();
    static {
        /*0:等待下载，1：请求下载，2：开始下载，3：下载成功，4：传输中(BMU升级才存在)，5：升级中"（RDB才存在）
        "，7：取消成功，9：升级成功，10：下载错误，11：升级失败，12：传输失败(BMU升级才存在)，13：url错误，" +
                "14：匹配信息错误，15：包验证失败，16：设备离线，17：重复升级,18:升级指令响应超时*/
        msgs.put(WAIT,"等待下载");
        msgs.put(REQ_UPGRADE,"请求下载");
        msgs.put(START_DOWNLOAD,"开始下载");
        msgs.put(DOWNLOAD_END,"下载成功");
        msgs.put(TRANSING,"传输中");
        msgs.put(UPGRADING,"升级中");
        msgs.put(UPGRADE_END,"升级结束");
        msgs.put(STATUS_CANCEL_SUCCESS,"取消升级");
        msgs.put(UP_SUCCESS,"升级成功");
        msgs.put(DOWNLOAD_ERROR,"下载失败");
        msgs.put(UP_ERROR,"升级失败");
        msgs.put(TRANSF_ERROR,"传输失败");
        msgs.put(URL_ERROR,"URL错误");
        msgs.put(MATCH_ERROR,"匹配信息错误");
        msgs.put(VERIFY_ERROR,"包验证失败");
        msgs.put(OFFLINE_ERROR,"设备离线");
        msgs.put(REPEAT_ERROR,"重复升级");
        msgs.put(NO_UPGRADE_ACK,"升级指令响应超时");
    }
    public static String getMsg( int i ){
        return msgs.get( i );
    }

    private String deviceName;
    private String version;
    private byte[] md5;
    private String url;
    private String type;
    private String batchCode;

    private int status = WAIT;

    private Date lastScanTime = new Date();
    private Date lastCancelTime = new Date();
    private boolean waitForCancleAcg = false;
    private String cancleId = null;
    private boolean isReupgrade;

    private Date reqTime; // 定时器启动任务时候开始
    private Date downloadStartTime; //上报开始下载的时候开始
    private Date downloadEndTime; //上报下载结果的时候记录
    private Date upgradeStartTime; // RDB: 上报开始升级时候记录， BMU： 上报传输结果成功的时候记录
    private Date upgradeEndTime; //上报升级结果的时候记录
    private Date transStartTime; //BMU 上报开始传输的时候记录
    private Date transEndTime;  //BMU 上报传输结果的时候记录

    private Integer downloadCode;
    private Byte upgradeCode;
    private byte[] upgradeMsg;
    private byte[] matchVersion;
    private Integer transCode;
   // private List<TransLog> transMsg;
    private Date upgradeAckTimeout;
    private String rdbsn;

    public void setRdbsn(String rdbsn) {
        this.rdbsn = rdbsn;
    }

    public String getRdbsn() {
        return rdbsn;
    }

    public void setUpgradeAckTimeout(Date upgradeAckTimeout) {
        this.upgradeAckTimeout = upgradeAckTimeout;
    }

    public Date getUpgradeAckTimeout() {
        return upgradeAckTimeout;
    }

    public void setLastCancelTime(Date lastCancelTime) {
        this.lastCancelTime = lastCancelTime;
    }

    public Date getLastCancelTime() {
        return lastCancelTime;
    }

    public void setWaitForCancleAcg(boolean waitForCancleAcg) {
        this.waitForCancleAcg = waitForCancleAcg;
    }

    public void setMatchVersion(byte[] matchVersion) {
        this.matchVersion = matchVersion;
    }

    public byte[] getMatchVersion() {
        return matchVersion;
    }

    public void setCancleId(String cancleId) {
        this.cancleId = cancleId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setReupgrade(boolean reupgrade) {
        isReupgrade = reupgrade;
    }

    public boolean isWaitForCancleAcg() {
        return waitForCancleAcg;
    }

    public String getCancleId() {
        return cancleId;
    }

    public boolean isReupgrade() {
        return isReupgrade;
    }

    public void setLastScanTime(Date lastScanTime) {
        this.lastScanTime = lastScanTime;
    }

    public Date getLastScanTime() {
        return lastScanTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getBatchCode() {
        return batchCode;
    }


    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMd5(byte[] md5) {
        this.md5 = md5;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public void setDownloadStartTime(Date downloadStartTime) {
        this.downloadStartTime = downloadStartTime;
    }

    public void setDownloadEndTime(Date downloadEndTime) {
        this.downloadEndTime = downloadEndTime;
    }

    public void setUpgradeStartTime(Date upgradeStartTime) {
        this.upgradeStartTime = upgradeStartTime;
    }

    public void setUpgradeEndTime(Date upgradeEndTime) {
        this.upgradeEndTime = upgradeEndTime;
    }

    public void setTransStartTime(Date transStartTime) {
        this.transStartTime = transStartTime;
    }

    public void setTransEndTime(Date transEndTime) {
        this.transEndTime = transEndTime;
    }

    public void setDownloadCode(int downloadCode) {
        this.downloadCode = downloadCode;
    }

    public void setUpgradeCode(byte upgradeCode) {
        this.upgradeCode = upgradeCode;
    }

    public void setUpgradeMsg(byte[] upgradeMsg) {
        this.upgradeMsg = upgradeMsg;
    }

    public void setTransCode(int transCode) {
        this.transCode = transCode;
    }

//    public void setTransMsg(List<TransLog> transMsg) {
//        this.transMsg = transMsg;
//    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getVersion() {
        return version;
    }

    public byte[] getMd5() {
        return md5;
    }

    public String getUrl() {
        return url;
    }


    public Date getReqTime() {
        return reqTime;
    }

    public Date getDownloadStartTime() {
        return downloadStartTime;
    }

    public Date getDownloadEndTime() {
        return downloadEndTime;
    }

    public Date getUpgradeStartTime() {
        return upgradeStartTime;
    }

    public Date getUpgradeEndTime() {
        return upgradeEndTime;
    }

    public Date getTransStartTime() {
        return transStartTime;
    }

    public Date getTransEndTime() {
        return transEndTime;
    }

    public int getDownloadCode() {
        return downloadCode;
    }

    public byte getUpgradeCode() {
        return upgradeCode;
    }

    public byte[] getUpgradeMsg() {
        return upgradeMsg;
    }

    public int getTransCode() {
        return transCode;
    }

//   // public List<TransLog> getTransMsg() {
//        return transMsg;
//    }

    //等待取消升级
//    public UpgradeLog cancelUpgradeWaitLog() {
//        UpgradeLog upgradeLog = new UpgradeLog();
//        upgradeLog.setId( this.getId() );
//        upgradeLog.setStatus( UpgradeCommand.STATUS_CANCEL_SUCCESS );
//        upgradeLog.setDeviceName( this.getDeviceName() );
//        upgradeLog.setUrl( this.getUrl() );
//        upgradeLog.setMd5( this.getMd5() );
//        upgradeLog.setType( this.getType() );
//        upgradeLog.setVersion( this.getVersion() );
//        upgradeLog.setReupgrade( this.isReupgrade() );
//        upgradeLog.setBatchCode( this.getBatchCode() );
//        upgradeLog.setRecordTime( new Date() );
//        upgradeLog.setRdbsn(this.rdbsn);
//        return upgradeLog;
//    }
//
//    //获取升级日志 设备离线日志
//    public UpgradeLog getOfflineLog() {
//        UpgradeLog upgradeLog = new UpgradeLog();
//        upgradeLog.setStatus( UpgradeCommand.OFFLINE_ERROR );
//        upgradeLog.setDeviceName( this.getDeviceName() );
//        upgradeLog.setUrl( this.getUrl() );
//        upgradeLog.setMd5( this.getMd5() );
//        upgradeLog.setType( this.getType() );
//        upgradeLog.setVersion( this.getVersion() );
//        upgradeLog.setReupgrade( this.isReupgrade() );
//        upgradeLog.setBatchCode( this.getBatchCode() );
//        upgradeLog.setRecordTime( new Date() );
//        upgradeLog.setRdbsn(this.rdbsn);
//        return upgradeLog;
//    }
//
//    public UpgradeLog getRepeatLog() {
//        UpgradeLog upgradeLog = new UpgradeLog();
//        upgradeLog.setStatus( UpgradeCommand.REPEAT_ERROR );
//        upgradeLog.setDeviceName( this.getDeviceName() );
//        upgradeLog.setUrl( this.getUrl() );
//        upgradeLog.setMd5( this.getMd5() );
//        upgradeLog.setType( this.getType() );
//        upgradeLog.setVersion( this.getVersion() );
//        upgradeLog.setReupgrade( this.isReupgrade() );
//        upgradeLog.setBatchCode( this.getBatchCode() );
//        upgradeLog.setRecordTime( new Date() );
//        upgradeLog.setRdbsn(this.rdbsn);
//        return upgradeLog;
//    }
//
//    public UpgradeLog getNoUpgradeAckLog() {
//        UpgradeLog upgradeLog = new UpgradeLog();
//        upgradeLog.setStatus( UpgradeCommand.NO_UPGRADE_ACK );
//        upgradeLog.setId( this.getId() );
//        upgradeLog.setDeviceName( this.getDeviceName() );
//        upgradeLog.setUrl( this.getUrl() );
//        upgradeLog.setMd5( this.getMd5() );
//        upgradeLog.setType( this.getType() );
//        upgradeLog.setVersion( this.getVersion() );
//        upgradeLog.setReupgrade( this.isReupgrade() );
//        upgradeLog.setBatchCode( this.getBatchCode() );
//        upgradeLog.setRecordTime( new Date() );
//        upgradeLog.setReqTime( this.getReqTime() );
//        upgradeLog.setRdbsn(this.rdbsn);
//        return upgradeLog;
//    }
}
