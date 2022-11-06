package com.glarryimg.modulads.adsa.glory_app;

import android.view.animation.Interpolator;

public class glory_MyBounceInterpolator implements Interpolator {
     private double mAmplitude = 1;
     private double mFrequency = 4;

     public glory_MyBounceInterpolator(double amplitude, double frequency) {
         mAmplitude = amplitude;
         mFrequency = frequency;
     }

     public float getInterpolation(float time) {
         return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                 Math.cos(mFrequency * time) + 1);
     }
 }

