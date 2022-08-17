package com.example.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.IntDef;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AudioUtil {

    private String TAG = "AudioUtil";

    private static AudioUtil audioUtil;
    private Context context;
    private AudioManager audioManager;

    private static int minVolume = 0;
    private static int maxVolume = 15;
    private static int beforeAudioOffValue = 0;// 开启静音前的音量值/非静音下设置后的音量值（百分占比）
    private String silence = ""; // 保存静音状态 "false":非静音，"true":静音

    private int VOLUME_UI_FLAG = 1 << 0;// 默认 FLAG_SHOW_UI

    /**
     * 封装可选 STREAM TYPE
     * AudioManager.STREAM_MUSIC - 音乐回放即媒体音量 - 默认支持
     * AudioManager.STREAM_ALARM - 警报
     * AudioManager.STREAM_DTMF - 双音多频（基本没用过）
     * AudioManager.STREAM_NOTIFICATION - 窗口顶部状态栏通知
     * AudioManager.STREAM_RING - 铃声
     * AudioManager.STREAM_SYSTEM - 系统
     * AudioManager.STREAM_VOICE_CALL - 通话
     *
     * 【当前已支持 AudioManager.STREAM_MUSIC】
     * 【 ... 】
     */
    public static final int Type_STREAM_MUSIC = AudioManager.STREAM_MUSIC;
    public static final int Type_STREAM_ALARM = AudioManager.STREAM_ALARM;
    public static final int Type_STREAM_DTMF = AudioManager.STREAM_DTMF;
    public static final int Type_STREAM_NOTIFICATION = AudioManager.STREAM_NOTIFICATION;
    public static final int Type_STREAM_RING = AudioManager.STREAM_RING;
    public static final int Type_STREAM_SYSTEM = AudioManager.STREAM_SYSTEM;
    public static final int Type_STREAM_VOICE_CALL = AudioManager.STREAM_VOICE_CALL;
    @IntDef({Type_STREAM_MUSIC, Type_STREAM_ALARM, Type_STREAM_DTMF,
            Type_STREAM_NOTIFICATION, Type_STREAM_RING, Type_STREAM_SYSTEM,
            Type_STREAM_VOICE_CALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE{}

    private int AUDIO_STREAM_TYPE = AudioManager.STREAM_MUSIC;// 默认流媒体类型

    // 构造
    public AudioUtil(Context context) {
        this.context = context.getApplicationContext();
        if (audioManager == null){
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            initMinVolume();
            initMaxVolume();
        }
    }

    /**
     * 单例
     * @param context
     * @return
     */
    public static AudioUtil newInstance(Context context){
        if (audioUtil == null){
            synchronized (AudioUtil.class){
                audioUtil = new AudioUtil(context);
            }
        }
        return audioUtil;
    }

    /**
     * 控制音量时，控制音量 UI 是否显示
     * @param flag
     */
    public void setUiFlag(int flag){
        this.VOLUME_UI_FLAG = flag;
    }

    /**
     * 记录当前音量
     */
    private void recordVolume(){
        beforeAudioOffValue = audioManager.getStreamVolume(AUDIO_STREAM_TYPE);
    }

    /**
     * 调小音量
     * @return 返回调整后的音量值 0-100
     */
    public String audioDown() {
        JSONObject objDown = new JSONObject();
        if (!isAudioMute()){
            audioManager.adjustStreamVolume(AUDIO_STREAM_TYPE, AudioManager.ADJUST_LOWER, VOLUME_UI_FLAG);
        }else {
//            showToast("已静音");
            // 静音时调小音量
            if (beforeAudioOffValue > 0){
                audioManager.setStreamVolume(Type_STREAM_MUSIC, beforeAudioOffValue, VOLUME_UI_FLAG);
                audioManager.adjustStreamVolume(AUDIO_STREAM_TYPE, AudioManager.ADJUST_LOWER, VOLUME_UI_FLAG);
            }
        }
        // 记录调小后音量值
        recordVolume();

        int volume = get100CurrentVolume();// 获取的是百分比音量值
        if (volume <= 0){
            silence = "true";

        }else {
            silence = "false";
        }
        try {
            objDown.put("silence", silence);
            objDown.put("value", String.valueOf(volume));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strDown = objDown.toString();
        return strDown;
    }

    /**
     * 调大音量
     * @return 返回调整后的音量值 0-100
     */
    public String audioUp() {
        JSONObject objUp = new JSONObject();

        if (isAudioMute()){
            // 静音时调大，需要先设置静音前的音量值
            if (beforeAudioOffValue > 0){
                audioManager.setStreamVolume(Type_STREAM_MUSIC, beforeAudioOffValue, VOLUME_UI_FLAG);
            }
        }

        if (!isAudioMax()){
            audioManager.adjustStreamVolume(AUDIO_STREAM_TYPE, AudioManager.ADJUST_RAISE, VOLUME_UI_FLAG);
        }else {
//            showToast("音量已最大");
        }
        // 记录调大后音量值
        recordVolume();

        int volume = get100CurrentVolume();// 获取的是百分比音量值
        silence = "false";
        try {
            objUp.put("silence", silence);
            objUp.put("value", String.valueOf(volume));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String strUp = objUp.toString();
        return strUp;
    }

    /**
     * 设置音量大小
     * @param relativeVolume 0 ~ 100 之间的某个整数值，包括 0 和 100
     * @return 设置后音量值的大小
     */
    public String audioSet(int relativeVolume){
        JSONObject objSet = new JSONObject();
        try {
            if (relativeVolume <= 0) {
//            initMinVolume();
                audioManager.setStreamVolume(Type_STREAM_MUSIC, minVolume, VOLUME_UI_FLAG);
                recordVolume();
                objSet.put("silence", "true");
                objSet.put("value", "0");
                return objSet.toString();
            }
            // 获取音量最大值
//        initMaxVolume();
            if (relativeVolume >= 100){
                audioManager.setStreamVolume(Type_STREAM_MUSIC, maxVolume, VOLUME_UI_FLAG);
                recordVolume();
                objSet.put("silence", "false");
                objSet.put("value", "100");
                return objSet.toString();
            }
            int volumeIndex = (int) (relativeVolume * maxVolume / 100 + 0.5f);
            audioManager.setStreamVolume(Type_STREAM_MUSIC, volumeIndex, VOLUME_UI_FLAG);
            recordVolume();// 记录设置后音量的当前值
            objSet.put("silence", "false");
            objSet.put("value", String.valueOf(relativeVolume));

            int volume_before = audioManager.getStreamVolume(AUDIO_STREAM_TYPE);
            // 设为静音
            audioManager.adjustStreamVolume(AUDIO_STREAM_TYPE, AudioManager.ADJUST_MUTE, 0);
            int volume_after = audioManager.getStreamVolume(AUDIO_STREAM_TYPE);
            Log.e("imxiaoqi", "> volume_before:" + volume_before + ", volume_after:"
            + volume_after);

        }catch (Exception e){
            e.printStackTrace();
        }
        return objSet.toString();
    }

    /**
     * 开启静音
     */
    public String audioOff() {
        JSONObject objAudioOff = new JSONObject();
        try {
            if (isAudioMute()){
//            showToast("已静音");
                if (beforeAudioOffValue <= 0){
                    objAudioOff.put("value", "0");
                }else {
                    objAudioOff.put("value", 100 * beforeAudioOffValue / maxVolume + "");
                }

            }else {
                // 记录开启静音前的音量值
                recordVolume();
                // 开启静音后，返回静音前的音量值
                objAudioOff.put("value", 100 * beforeAudioOffValue / maxVolume + "");
                audioManager.setStreamVolume(AUDIO_STREAM_TYPE, minVolume, VOLUME_UI_FLAG);
            }

            silence = "true";
            objAudioOff.put("silence", silence);

            audioManager.setStreamVolume(AUDIO_STREAM_TYPE, beforeAudioOffValue, VOLUME_UI_FLAG);
            audioManager.setStreamMute(AUDIO_STREAM_TYPE, true);
        }catch (Exception e){
            e.printStackTrace();
        }

        return objAudioOff.toString();
    }


    /**
     * 取消静音
     * @return 返回开启静音之前的音量值 0-100
     */
    public String audioOn() {
        JSONObject objAudioOn = new JSONObject();
//        if (isAudioMute()){
//            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, beforeAudioOffValue, VOLUME_UI_FLAG);
//        }else {
//            showToast("已取消静音");
//        }

        try {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, beforeAudioOffValue, VOLUME_UI_FLAG);
            int volume = get100CurrentVolume();
            if (volume <= 0){
                silence = "true";
            }else {
                silence = "false";
            }
            recordVolume();
            objAudioOn.put("silence", silence);
            objAudioOn.put("value", String.valueOf(volume));
            audioManager.setStreamMute(AUDIO_STREAM_TYPE, false);
        }catch (Exception e){
            e.printStackTrace();
        }

        return objAudioOn.toString();
    }

    private void showToast(String message) {
        if (TextUtils.isEmpty(message)){
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 音量是否已经最大
     * @return true-是 | false-否
     */
    public boolean isAudioMax() {
//        initMaxVolume();
        boolean isMax = false;
        int currentVolume = audioManager.getStreamVolume(AUDIO_STREAM_TYPE);
        if (currentVolume == maxVolume){
            isMax = true;
        }else {
            isMax = false;
        }
        return isMax;
    }

    /**
     * 以0-100为范围，获取当前的音量值
     * @return  返回当前的音量值
     */
    public int get100CurrentVolume() {
        Log.e(TAG, "> StreamVolume:" + audioManager.getStreamVolume(AUDIO_STREAM_TYPE));
        return 100 * audioManager.getStreamVolume(AUDIO_STREAM_TYPE) / maxVolume;
    }

    public String getCurrentVolume(){
        JSONObject objCurrent = new JSONObject();

        try {
            if (TextUtils.isEmpty(silence)){
                int volume = get100CurrentVolume();
                if (volume <= 0){
                    silence = "true";
                }else {
                    silence = "false";
                }
                if (isAudioMute()){
                    silence = "true";
                }else {
                    silence = "false";
                }
                objCurrent.put("value", String.valueOf(volume));
                recordVolume();// 记录一下当前音量值
            }else {
                objCurrent.put("value", 100 * beforeAudioOffValue / maxVolume + "");
            }

            objCurrent.put("silence", silence);
        }catch (Exception e){
            e.printStackTrace();
        }

        return objCurrent.toString();
    }

    /**
     * 获取音量最小值
     */
    private void initMinVolume() {
        // 获取音量最小值，需要考虑stream_type，设置静音用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            minVolume = audioManager.getStreamMinVolume(AUDIO_STREAM_TYPE);
        }else {
            // sdk 28以下 不同的Stream Type，最小值可能不一样，需要另做支持
            if (AUDIO_STREAM_TYPE == AudioManager.STREAM_MUSIC){
                minVolume = 0;
            }
        }
    }

    /**
     * 获取音量最大值
     */
    private void initMaxVolume() {
        // 获取音量最大值
        maxVolume = audioManager.getStreamMaxVolume(AUDIO_STREAM_TYPE);
    }

    /**
     * 判断-音乐回放即媒体音量-是否静音
     * @return true - 是静音 | false - 不是静音
     */
    public boolean isAudioMute() {
//        initMinVolume();
        boolean isAudioMute = false;
//        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        if (volume == minVolume){
//            isAudioMute = true;
//        }else {
//            isAudioMute = false;
//        }

        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
            isAudioMute = true;
        }else {
            isAudioMute = false;
        }
        Log.e(TAG, "> isAudioMute:" + isAudioMute);
        return isAudioMute;

    }

    /**
     * 【当前已支持 AudioManager.STREAM_MUSIC】
     * 注意：当前默认并已支持AudioManager.STREAM_MUSIC类型，其他类型需要另做支持，
     * 如果选用其他类型请自行扩展，同时需将该方法 【public】
     * @param type
     * @return
     */
    private AudioUtil setStreamType(@TYPE int type){
        AUDIO_STREAM_TYPE = type;
        return this;
    }

}
