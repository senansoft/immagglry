package com.glarryimg.apprr;

import androidx.annotation.Keep;

import com.google.firebase.firestore.PropertyName;

@Keep
public class glory_updatemodel {
    @PropertyName("comname")
    String comname;
    @PropertyName("new_com")
    String new_com;

    @Keep
    public String getComname() {
        return comname;
    }

    @Keep
    public void setComname(String comname) {
        this.comname = comname;
    }

    @Keep
    public String getNew_com() {
        return new_com;
    }

    @Keep
    public void setNew_com(String new_com) {
        this.new_com = new_com;
    }
}
