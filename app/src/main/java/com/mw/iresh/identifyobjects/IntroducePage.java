package com.mw.iresh.identifyobjects;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IntroducePage extends AppCompatActivity implements TextToSpeech.OnInitListener{

    ImageButton fatherBtn, motherBtn, daughterBtn, sonBtn, nextPageBtn,replayBtn;
    Button skipBtn;
    ImageView baseImg;
    TextView memberText;
    Map<String, ImageButton> map;
    private TextToSpeech tts;
    boolean endingAnimations  = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intoduce_page);

        fatherBtn = (ImageButton) findViewById(R.id.fatherBtn);
        sonBtn = (ImageButton) findViewById(R.id.sonBtn);
        daughterBtn = (ImageButton) findViewById(R.id.daughterBtn);
        motherBtn = (ImageButton) findViewById(R.id.motherBtn);
        memberText = (TextView) findViewById(R.id.memberText);
        nextPageBtn = (ImageButton) findViewById(R.id.nextPageBtn);
        replayBtn = (ImageButton) findViewById(R.id.replayBtn);
        baseImg = (ImageView) findViewById(R.id.baseImg);
        skipBtn = (Button) findViewById(R.id.skipBtn);
        replayBtn.setVisibility(View.INVISIBLE);
        nextPageBtn.setVisibility(View.INVISIBLE);


        tts = new TextToSpeech(this, this);
        tts.setLanguage(Locale.US);

        map = new HashMap<>();
        map.put("Father", fatherBtn);
        map.put("Mother", motherBtn);
        map.put("Daughter", daughterBtn);
        map.put("Son", sonBtn);

        btnMapLooper();


        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnMapLooper();
                memberText.setText("");

                nextPageBtn.setVisibility(View.INVISIBLE);
                replayBtn.setVisibility(View.INVISIBLE);
                fatherBtn.setVisibility(View.VISIBLE);
                motherBtn.setVisibility(View.VISIBLE);
                daughterBtn.setVisibility(View.VISIBLE);
                sonBtn.setVisibility(View.VISIBLE);
            }
        });

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameFace = new Intent (IntroducePage.this, GameFace.class);
                startActivity(gameFace);
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameFace = new Intent (IntroducePage.this, GameFace.class);
                startActivity(gameFace);
            }
        });


    }

    public void btnMapLooper(){

        int i = 1;
        if (i<map.size()){

            for (Map.Entry<String, ImageButton> entry : map.entrySet()) {
                imageBtnAnimate(entry.getKey(), entry.getValue(), i);
                System.out.println("btnmaperloop .........." + entry.getKey()+".........."+ entry.getValue());

                i++;
            }

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
                            nextPageBtn.setVisibility(View.VISIBLE);
                            replayBtn.setVisibility(View.VISIBLE);
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

    @Override
    public void onInit(int i) {
        say("Identify the family members!");
    }

    public void say(String text2say) {
        System.out.println("inside the say() ....name......" + text2say);
        tts.speak(text2say, TextToSpeech.QUEUE_FLUSH, null);
    }


}

