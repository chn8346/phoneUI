package com.example.phoneui;


import android.content.Context;

import android.os.Bundle;

import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.msc.util.FileUtil;
import com.iflytek.cloud.msc.util.log.DebugLog;


public class speaker {

    private SpeechSynthesizer Tts;
    private Context context_;
    private Toast_ toast = new Toast_();
    private int state = 0;
    private boolean after_listen = false;


    // 构造函数
    public speaker(Context context)
    {
        InitListener TtsInitListener = new InitListener() {
            @Override
            public void onInit(int i) {
                if (i != ErrorCode.SUCCESS) {
                    Log.d("TAG______TAG", "onInit: 初始化失败,错误码："+i);
                    toast.show(context_,"初始化失败,错误码："+i, 700);
                }
                else {
                    state = state + 1;
                    // 初始化成功，之后可以调用startSpeaking方法
                    // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                    // 正确的做法是将onCreate中的startSpeaking调用移至这里
                }
            }
        };

        context_ = context;
        Tts = SpeechSynthesizer.createSynthesizer(context, TtsInitListener);
        if(Tts != null)
        {
            state = state + 10;

            // 部分参数的预定
            String EngineType = SpeechConstant.TYPE_CLOUD;
            String voicer = "xiaoyan";
            String speed = "40";
            String pitch = "55";
            String volume = "50";

            //参数设置
            Tts.setParameter(SpeechConstant.PARAMS, null);
            // 根据合成引擎设置相应参数
            if(EngineType.equals(SpeechConstant.TYPE_CLOUD)) {
                Tts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
                //支持实时音频返回，仅在synthesizeToUri条件下支持
                Tts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
                //	mTts.setParameter(SpeechConstant.TTS_BUFFER_TIME,"1");

                // 设置在线合成发音人
                Tts.setParameter(SpeechConstant.VOICE_NAME, voicer);
                //设置合成语速
                Tts.setParameter(SpeechConstant.SPEED, speed);
                //设置合成音调
                Tts.setParameter(SpeechConstant.PITCH, pitch);
                //设置合成音量
                Tts.setParameter(SpeechConstant.VOLUME, volume);
            }else {
                Tts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
                Tts.setParameter(SpeechConstant.VOICE_NAME, "");

            }
        }
    }

    // 发音方法-- 完整
    public void doSpeech(String speech, boolean input_)
    {
        this.after_listen = input_;
        if(state < 10)
        {
            toast.show(context_, "初始化失败"+state, 800);
        }
        else
        {
            if(speech.length() != 0)
                Tts.startSpeaking(speech,listener);
        }
    }

    // 发音方法-- 重载的简单版
    public void doSpeech(String speech)
    {
        doSpeech(speech, false);
    }

    private SynthesizerListener listener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

}
