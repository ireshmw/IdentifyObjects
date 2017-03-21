package com.mw.iresh.identifyobjects;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.mw.iresh.identifyobjects.customRecognitionListener.CustomRecognitionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class GameFace extends AppCompatActivity implements TextToSpeech.OnInitListener {

    ImageButton fatherBtn, motherBtn, daughterBtn, sonBtn;
    ImageView baseImg;
    TextView memberText;
    Map<String, ImageButton> map;
    boolean endingAnimations  = false;
    String[] member = {"Father","Mother","Daughter","Son"};
    ArrayList<Integer> objectCount = new ArrayList<>();
    private TextToSpeech tts;

    TextView spText;
    Switch aSwitch;
    SpeechRecognizer mySp;
    CustomRecognitionListener recognitionListener;
    Intent recognizeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_face);

        fatherBtn = (ImageButton) findViewById(R.id.fatherBtn);
        sonBtn = (ImageButton) findViewById(R.id.sonBtn);
        daughterBtn = (ImageButton) findViewById(R.id.daughterBtn);
        motherBtn = (ImageButton) findViewById(R.id.motherBtn);
        memberText = (TextView) findViewById(R.id.memberText);
        baseImg = (ImageView) findViewById(R.id.baseImg);


        mySp = SpeechRecognizer.createSpeechRecognizer(this);
        recognitionListener = new CustomRecognitionListener();
        mySp.setRecognitionListener(recognitionListener);
        recognizeIntent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        recognizeIntent.putExtra("android.speech.extra.DICTATION_MODE", true);
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);




        tts = new TextToSpeech(this,this);
        tts.setLanguage(Locale.US);

        map = new HashMap<>();
        map.put("Father", fatherBtn);
        map.put("Mother", motherBtn);
        map.put("Daughter", daughterBtn);
        map.put("Son", sonBtn);

        randomObjectGen(4);
        btnMapLooper();


    }


    public void randomObjectGen(int numbersOfObjects){
        Random rand = new Random();
        int randNum = rand.nextInt(4);
        boolean state = false;

            while (!state){
                if (objectCount.size() == 0) {
                    objectCount.add(randNum);
                }
                for (int i=0;i<objectCount.size();i++){
                    if (objectCount.get(i)== randNum) {
                        randNum = rand.nextInt(4);
                        break;
                    }
                    if (i+1 == objectCount.size()) {
                        if (objectCount.size() == numbersOfObjects-1) {
                            state = true;
                        }
                        objectCount.add(randNum);

                    }
                }

            }

        System.out.println("object counts........."+objectCount);
    }



    public void btnMapLooper(){
//
//        int i = 1;
//        if (i<map.size()){
//
//            for (Map.Entry<String, ImageButton> entry : map.entrySet()) {
//                imageBtnAnimate(entry.getKey(), entry.getValue(), i);
//                System.out.println("btnmaperloop .........." + entry.getKey()+".........."+ entry.getValue());
//
//                i++;
//            }
//
//        }
        int x=1;
        for (int i:objectCount){
            String key =  member[i];
            String nextKey="";
            if (i!=member.length){
                 nextKey = member[i];
            }
            //imageBtnAnimate(key, map.get(key),x);
            objectAnimating(key, map.get(key),map.get(nextKey),x);
            x++;
        }
    }


    private void imageBtnAnimate(final String name, final ImageButton member, final int delay) {

        final ViewPropertyAnimator anim = member.animate();
        System.out.println("inside the imageBtnAnimate ....name......" + name +".....delay....."+ delay);
        anim.scaleX(1.7f).scaleY(1.7f).setDuration(2000).translationX(0).translationY(400).setStartDelay(delay * 3000).withStartAction(new Runnable() {
            @Override
            public void run() {
                say("this is " + name);
                memberText.setText(name);
            }
        })
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {


                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        anim.setStartDelay(2000);
                        anim.scaleX(1f).scaleY(1f).translationX(0).translationY(0).setDuration(1000).setStartDelay(1000);
                        if (delay==map.size()){
                            endingAnimations = true;

                            fatherBtn.setVisibility(View.INVISIBLE);
                            motherBtn.setVisibility(View.INVISIBLE);
                            daughterBtn.setVisibility(View.INVISIBLE);
                            sonBtn.setVisibility(View.INVISIBLE);
                            baseImg.setVisibility(View.INVISIBLE);


                        }

                        //state[0] = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

    }


    public boolean objectAnimating(final String name, ImageButton currantObject, ImageButton netObject,int delay){
        ViewPropertyAnimator animator = currantObject.animate();
        animator.scaleX(1.7f).scaleY(1.7f).setDuration(2000).translationX(0).translationY(400).setStartDelay(delay*3000).withStartAction(new Runnable() {
            @Override
            public void run() {
                //say("this is " + name);
                say("who is this?");
                //memberText.setText(name);
            }
        })
                .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {



            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mySp.startListening(recognizeIntent);
                memberText.setText(recognitionListener.getReturnedText());
                mySp.stopListening();
                System.out.println("end of the animation...");
                System.out.println("ending words....."+recognitionListener.getReturnedText());
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        return true;
    }

    @Override
    public void onInit(int i) {
        say("Identify the family members!");
    }

    public void say(String text2say) {
        System.out.println("inside the say() ....name......" + text2say);
        tts.speak(text2say, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.shutdown();
        mySp.destroy();
        //Log.i(LOG_TAG, "destroy");
    }



}
