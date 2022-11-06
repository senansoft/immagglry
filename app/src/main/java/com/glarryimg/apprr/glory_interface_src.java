package com.glarryimg.apprr;

import android.widget.FrameLayout;

public interface glory_interface_src
{
    void saleh_set_src(int pos);
    void saleh_finsh_activity();
    void saleh_show_ads_full();
    void saleh_save_image();
    void saleh_set_wall_clic();
    void saleh_shareing_img();
    void saleh_add_fav_to_db(int pos);
    void saleh_remove_from_db(int pos);

    void saleh_load_again_nativ_ads(int pos);
    void saleh_change_background(int imgsrc);

    void onsuccess(String toJson, FrameLayout framcolor);
}
