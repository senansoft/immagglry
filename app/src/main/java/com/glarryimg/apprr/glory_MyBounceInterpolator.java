package com.glarryimg.apprr;

import android.view.animation.Interpolator;



public class glory_MyBounceInterpolator implements Interpolator

{

    private double saleh_mAmplitude = 1;
    private double saleh_mFrequency = 4;

    public glory_MyBounceInterpolator(double amplitude, double frequency) {
        saleh_mAmplitude = amplitude;
        saleh_mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ saleh_mAmplitude) *
                Math.cos(saleh_mFrequency * time) + 1);
    }
}
