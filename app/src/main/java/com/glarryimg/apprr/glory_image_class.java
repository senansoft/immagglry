package com.glarryimg.apprr;

public class glory_image_class
{
    String saleh_namecell;
    boolean saleh_fav;

    public String getName() {
        return saleh_namecell;
    }

    public glory_image_class(String name, boolean fav) {
        this.saleh_namecell = name;
        this.saleh_fav = fav;
    }

    public void setName(String name) {
        this.saleh_namecell = name;
    }

    public boolean issaleh_fav() {
        return saleh_fav;
    }

    public void setsaleh_fav(boolean saleh_fav) {
        this.saleh_fav = saleh_fav;
    }
}
