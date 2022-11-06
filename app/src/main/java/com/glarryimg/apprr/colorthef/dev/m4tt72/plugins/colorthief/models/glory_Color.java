package com.glarryimg.apprr.colorthef.dev.m4tt72.plugins.colorthief.models;

import android.content.Context;

import com.glarryimg.apprr.colorthef.dev.m4tt72.plugins.colorthief.ntc.glory_NameThatColor;
import com.glarryimg.apprr.colorthef.dev.m4tt72.plugins.colorthief.utils.Utils;


public class glory_Color {

    private String hex;
    private String name;
    private int[] rgb;
    private int[] hsl;

    public glory_Color(Context context, String hex) {
        super();
        glory_NameThatColor ntc = glory_NameThatColor.getInstance(context);
        this.name = ntc.getName(hex);
        this.hex = hex;
        rgb = Utils.hexToRgb(hex);
        hsl = Utils.hexToHsl(hex);
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }

    public int[] getHsl() {
        return hsl;
    }

    public void setHsl(int[] hsl) {
        this.hsl = hsl;
    }

}
