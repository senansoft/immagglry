package com.glarryimg.apprr;

import static com.glarryimg.apprr.glory_public_class.saleh_current_index_loaded;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.glarryimg.apprr.colorthef.dev.m4tt72.plugins.colorthief.glory_glory_JsonHelper;
import com.glarryimg.apprr.colorthef.dev.m4tt72.plugins.colorthief.Woltmann_colorthief.glory_ColorThiefTask;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class glory_ViewPagerAdapter extends PagerAdapter implements View.OnClickListener, glory_interface_src {
    boolean saleh_is_clicked;
    private glory_interface_src saleh_myinterface;
    private Context context;
    private ArrayList<Object> saleh_imageUrls;
    private LayoutInflater saleh_inflater;

    glory_ViewPagerAdapter(Context context, ArrayList<Object> imageUrls, glory_interface_src handlr_interface) {
        this.context = context;
        this.saleh_imageUrls = imageUrls;
        saleh_myinterface = handlr_interface;
        saleh_inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return saleh_imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private void populateNativeAdView(NativeAd nativeAd,
                                      NativeAdView adView) {
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());


        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd);
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {


        if ((saleh_imageUrls.get(position) instanceof NativeAd)) {
            // View unifiedNativeLayoutView1 = LayoutInflater.from(view.getContext()).inflate(R.layout.ad_unified, view, false);
            final View unifiedNativeLayoutView = saleh_inflater.inflate(R.layout.ad_unified, view, false);

            NativeAd nativeAd = (NativeAd) saleh_imageUrls.get(position);
            NativeAdView adView;

            adView = unifiedNativeLayoutView.findViewById(R.id.ad_view);


            adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

            adView.setHeadlineView(unifiedNativeLayoutView.findViewById(R.id.ad_headline));
            adView.setBodyView(unifiedNativeLayoutView.findViewById(R.id.ad_body));
            adView.setCallToActionView(unifiedNativeLayoutView.findViewById(R.id.ad_call_to_action));
            adView.setIconView(unifiedNativeLayoutView.findViewById(R.id.ad_icon));
            adView.setPriceView(unifiedNativeLayoutView.findViewById(R.id.ad_price));
            adView.setStarRatingView(unifiedNativeLayoutView.findViewById(R.id.ad_stars));
            adView.setStoreView(unifiedNativeLayoutView.findViewById(R.id.ad_store));
            adView.setAdvertiserView(unifiedNativeLayoutView.findViewById(R.id.ad_advertiser));

            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView); // <- fix

            }
            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

            NativeAd.Image icon = nativeAd.getIcon();

            if (icon == null) {
                adView.getIconView().setVisibility(View.INVISIBLE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }

            if (nativeAd.getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
            }

            if (nativeAd.getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
            }

            if (nativeAd.getStarRating() == null) {
                adView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                ((RatingBar) adView.getStarRatingView())
                        .setRating(nativeAd.getStarRating().floatValue());
                adView.getStarRatingView().setVisibility(View.VISIBLE);
            }

            if (nativeAd.getAdvertiser() == null) {
                adView.getAdvertiserView().setVisibility(View.INVISIBLE);
            } else {
                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }

            // Assign native ad object to the native view.
            adView.setNativeAd(nativeAd);
            //populateNativeAdView(nativeAd, adView);
            saleh_myinterface.saleh_change_background(999);
            view.addView(adView);

            return adView;

        } else if ((saleh_imageUrls.get(position) instanceof glory_image_class)) {

            View v = LayoutInflater.from(view.getContext()).inflate(R.layout.glory_card_row_view, null, false);
            // return  viewType;
            if (v.getParent() != null) {
                ((ViewGroup) v.getParent()).removeView(v); // <- fix
            }
            //view.addView(myframe);
            view.addView(v);
            initLayoutOne(new SingleItemRowHolderone(v), position);
            return v;
        } else
            throw new RuntimeException("The type has to be ONE or TWO");
    }


    void get_plate(int path, FrameLayout myfram_card) {
        ByteArrayOutputStream bytearrayoutputstream;
        bytearrayoutputstream = new ByteArrayOutputStream();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), path, options);
        image.compress(Bitmap.CompressFormat.JPEG, 50, bytearrayoutputstream);

        glory_ColorThiefTask colorThief = new glory_ColorThiefTask(context, image, this,myfram_card);
        colorThief.execute(2);
    }

    private View initLayoutOne(SingleItemRowHolderone holder, int position) {
        if (saleh_current_index_loaded < position) {
            saleh_current_index_loaded = position;
        }

        if (position % Integer.parseInt(context.getString(R.string.ads_index)) == 0) {
            saleh_myinterface.saleh_show_ads_full();
        }


        holder.ama_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saleh_is_clicked) {
                    saleh_is_clicked = false;
                    // myinterface.hide_show_frame(true);
                } else {
                    // myinterface.hide_show_frame(false);
                    saleh_is_clicked = true;
                }
            }
        });

        int imageKey = context.getResources().getIdentifier("drawable/" + ((glory_image_class) saleh_imageUrls.get(position)).getName(), "drawable", context.getPackageName());

        try {
            Picasso.get()
                    .load(imageKey)
                    //.fit()
                    //.centerInside()

                    //.fit()
                    // .centerCrop()
                    .into(holder.ama_imageView);
        } catch (IllegalArgumentException ex) {
        }

        try {
            convtobitmap(imageKey, holder.img_back, holder.myfram_card);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       /* if ((saleh_imageUrls.get(position) instanceof ver_image_class)) {
            // try
            {
                saleh_myinterface.saleh_change_background(1);

            }


        }*/
        return holder.saleh_mycardsss;

    }


    void convtobitmap(final int imageViewpath, ImageView imgback, FrameLayout myfram_card) throws IllegalArgumentException, FileNotFoundException {


        get_plate(imageViewpath,myfram_card);

        /*Picasso.get()
                .load(imageViewpath)
                .resize(50, 0)
                // .fit()
                //.transform(new RoundedTransformation(15, 0))
                .centerCrop(Gravity.CENTER)
                .placeholder( R.drawable.gradient_list )
                .into(imgback, new Callback() {
                    @Override
                    public void onSuccess() {
                        // myinterface.set_src(pos);
                        imgback.setAlpha(0.2f);
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                });*/
        /*if (imageViewpath > 0) {
            return;
        }*/
        // Log.e("imggg_it=",imageViewpath);
       /* Bitmap Icon = BitmapFactory.decodeResource(context.getResources(), imageViewpath);

        Palette.generateAsync(Icon, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette paletteImageView) {
                // Do something with colors...
                Palette.Swatch vibrant = paletteImageView.getDarkVibrantSwatch();


                int vibranta = paletteImageView.getDarkVibrantColor(2);//1
                int vibrantDark = paletteImageView.getDarkMutedColor(3);//1
                int vibrantLight = paletteImageView.getDarkVibrantColor(3);
                int muted = paletteImageView.getDarkVibrantColor(3);
                int mutedDark = paletteImageView.getDarkVibrantColor(4);
                int mutedLight = paletteImageView.getDarkVibrantColor(4);

                final GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{vibranta, vibrantDark, mutedDark, mutedLight, muted, vibrantLight});//      gd.setCornerRadius(0f);
                gd.setCornerRadius(50f);

                final GradientDrawable gd2 = new GradientDrawable(
                        GradientDrawable.Orientation.BOTTOM_TOP,
                        new int[]{vibranta, vibrantDark, mutedDark, mutedLight, muted, vibrantLight});//      gd.setCornerRadius(0f);
                gd2.setCornerRadius(0f);
                imgback.setBackground(gd);//groundResource(imageView);

                myfram_card.setBackground(gd);//groundResource(imageView);
                try {
                    // imgback.setAlpha(0.9f);
                  *//*  Picasso.get()
                            .load(imageViewpath)
                            .resize(50, 0)
                            // .fit()
                            //.transform(new RoundedTransformation(15, 0))
                            .centerCrop(Gravity.CENTER)
                            .placeholder( R.drawable.gradient_list )
                            .into(imgback, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // myinterface.set_src(pos);
                                    imgback.setAlpha(0.2f);
                                }
                                @Override
                                public void onError(Exception e) {
                                }
                            });*//*

                } catch (IllegalArgumentException ex) {

                }
            }
        });*/
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container,position,object);
        container.removeView((View) object);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void saleh_set_src(int pos) {

    }

    @Override
    public void saleh_finsh_activity() {

    }

    @Override
    public void saleh_show_ads_full() {

    }

    @Override
    public void saleh_save_image() {

    }

    @Override
    public void saleh_set_wall_clic() {

    }

    @Override
    public void saleh_shareing_img() {

    }

    @Override
    public void saleh_add_fav_to_db(int pos) {

    }

    @Override
    public void saleh_remove_from_db(int pos) {

    }

    @Override
    public void saleh_load_again_nativ_ads(int pos) {

    }

    @Override
    public void saleh_change_background(int imgsrc) {

    }

    @Override
    public void onsuccess(String toJson, FrameLayout framcolor) {

        Log.e("from_dprcolor_thif=", toJson);


        List<Integer>colr=new ArrayList<>();

        try {
            List<HashMap> imgcolors = new ArrayList<>(glory_glory_JsonHelper.toList(new JSONArray(toJson)));
            Log.e("size_color=", imgcolors.size() + "");

            for (int i=0;i<imgcolors.size();i++) {
                Log.e("colr_hex=", String.valueOf(imgcolors.get(i).get("hex")));
                colr.add(android.graphics.Color.parseColor(String.valueOf(imgcolors.get(i).get("hex"))));
            }
            Log.e("size_color=", imgcolors.size() + "");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("errror", "errrr");
        }

       // int dominantColor = Color.rgb(rgb[0], rgb[1], rgb[2]);
        final GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{colr.get(0),colr.get(1)});//      gd.setCornerRadius(0f);
        //gd.setCornerRadius(50f);
//                new int[]{colr.get(0),colr.get(1),colr.get(2),colr.get(3),colr.get(4),colr.get(5),colr.get(6),colr.get(7),colr.get(8),colr.get(9)});//      gd.setCornerRadius(0f);
        framcolor.setBackgroundColor(colr.get(0));
        //img_back.setBackground(gd);


    }

    public class SingleItemRowHolderone {

        CardView saleh_mycardsss;
        ImageView ama_imageView, img_back;
        ImageButton saleh_img_obtion;
        FrameLayout myfram_card;


        public SingleItemRowHolderone(View v) {
            saleh_mycardsss = v.findViewById(R.id.mycardsss);
            ama_imageView = v.findViewById(R.id.photo_view);
            img_back = v.findViewById(R.id.img_back);
            saleh_img_obtion = v.findViewById(R.id.img_obtion);
            myfram_card = v.findViewById(R.id.myfram_card);

        }
    }
}
