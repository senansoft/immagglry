package com.glarryimg.apprr.colorthef.dev.m4tt72.plugins.colorthief.Woltmann_colorthief;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.FrameLayout;

import com.glarryimg.apprr.colorthef.de.androidpit.colorthief.glory_ColorThief;
import com.glarryimg.apprr.colorthef.dev.m4tt72.plugins.colorthief.models.glory_Color;
import com.glarryimg.apprr.glory_interface_src;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class glory_ColorThiefTask extends AsyncTask<Integer, Void, List<glory_Color>> {

    private Context context;
    private Bitmap image;
    private glory_interface_src result;
    FrameLayout framcolor;

    public glory_ColorThiefTask(Context context, Bitmap image, glory_interface_src result, FrameLayout myfram) {
        this.context = context;
        this.image = image;
        this.result = result;
        this.framcolor=myfram;
    }

    @Override
    protected List<glory_Color> doInBackground(Integer... params) {
        Integer size = Objects.requireNonNull(params[0]);
        List<glory_Color> palette = new ArrayList<>();
        int[][] colors = glory_ColorThief.getPalette(image, size);
        for(int[] color: Objects.requireNonNull(colors)) {
            palette.add(new glory_Color(context, String.format("#%02x%02x%02x", color[0], color[1], color[2])));
        }
        return palette;
    }

    @Override
    protected void onPostExecute(List<glory_Color> palette) {
        Gson gson = new Gson();
        result.onsuccess(gson.toJson(palette),framcolor);
    }
}
