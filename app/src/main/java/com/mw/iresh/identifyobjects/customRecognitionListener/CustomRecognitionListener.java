package com.mw.iresh.identifyobjects.customRecognitionListener;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

import java.util.ArrayList;

/**
 * Created by iresh on 3/23/2017.
 */

public class CustomRecognitionListener implements RecognitionListener {
    private String returnedText = "default";


    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {
        //Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = bundle
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
//        for (String result : matches) {
//            text += result + "\n";
//            System.out.println("OnResults......................");
//
//            returnedText = text;
//        }
        returnedText = matches.get(0);

        System.out.println("match array results..............." + returnedText);
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        //Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = bundle
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";

//        for (String result : matches) {
//            text += result + "\n";
//            System.out.println("OnPartial......................");
//
//            returnedText = text;
//        }
        returnedText = matches.get(0);
        System.out.println("match array partial..............." + returnedText);
    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    public String getReturnedText() {
        System.out.println("inside the custom Rcognizer getText method........." + returnedText);
        return returnedText;
    }
}
