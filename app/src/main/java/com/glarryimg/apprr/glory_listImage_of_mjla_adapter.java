package com.glarryimg.apprr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ablanco.zoomy.DoubleTapListener;
import com.ablanco.zoomy.LongPressListener;
import com.ablanco.zoomy.TapListener;
import com.ablanco.zoomy.Zoomy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.VideoController;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.glarryimg.modulads.adsa.glory_app.glory_class_settings.func_check_ads;
import static com.glarryimg.apprr.glory_public_class.anim_view;


public class glory_listImage_of_mjla_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<glory_image_class> saleh_mImagesList;
    private Context mContext;
    private int saleh_pos_ads =10000;
    glory_myinterface saleh_myinterface;
    private SparseBooleanArray saleh_mSparseBooleanArray;
    CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            saleh_mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
        }
    };
    private boolean from_local;

    Activity saleh_myactivty;
    private final static int saleh_one_column = 1;
    private final static int saleh_two_column = 2;

    public glory_listImage_of_mjla_adapter(Context context, ArrayList<glory_image_class> imageList, Boolean _from_local, Activity actv, glory_myinterface _myinterface) {
        mContext = context;
        saleh_mSparseBooleanArray = new SparseBooleanArray();
        this.saleh_mImagesList = imageList;
        this.from_local = _from_local;
        saleh_myactivty =actv;
        saleh_myinterface =_myinterface;

    }

    @Override
    public int getItemViewType(int position)
    {
        if(position>0 && position% saleh_pos_ads ==0)
        return saleh_one_column;
        else
           return saleh_two_column;
    }

    public static boolean saleh_isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void saleh_setFadeAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.glory_pop_out);
       // animation.setDuration(1000);
        view.startAnimation(animation);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType== saleh_one_column)
        {
            View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.glory_ads,parent, false);

            return new ItemRowHolder_one(v);
        }
        else
        if(viewType== saleh_two_column) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.glory_layout_single_mjla, parent, false);

            return new ItemRowHolder_two(itemView);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        saleh_setFadeAnimation(holder.itemView);

        if(position>0 &&position% saleh_pos_ads ==0) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
           layoutParams.setFullSpan(true);
        }
        switch (holder.getItemViewType()) {
            case saleh_one_column:
                saleh_initLayoutmyone((ItemRowHolder_one) holder, position);
                break;
            case saleh_two_column:
                saleh_initLayouttwo((ItemRowHolder_two) holder, position);
                break;
        }
    }

    private void saleh_initLayouttwo(final ItemRowHolder_two holder, final int position) {
        if (from_local) {
            try {
                int imageKey = mContext.getResources().getIdentifier("drawable/"+ saleh_mImagesList.get(position).getName(), "drawable", mContext.getPackageName());

                if(saleh_isValidContextForGlide(mContext)) {
                    Picasso.get()
                            .load(imageKey)

                            .resize(400, 0)
                            //.centerCrop()
                            // .fit()
                            //.centerCrop()
                            .into(holder.img_singl_image_majlah, new Callback() {
                                @Override
                                public void onSuccess() {
                                    holder.progressBar3.setVisibility(View.GONE);
                                }
                                @Override
                                public void onError(Exception e) {
                                    Log.e("error_dpr=",e.getMessage());
                                }
                            });
                }




            }
            catch (IndexOutOfBoundsException wx){}
            catch(Exception ex) {
                return;
            }

            holder.img_singl_image_majlah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animat(view);
                    Log.e("current_pos=",position+"");
                    //Intent intent=new Intent(mContext,second_activity.class);
                    Intent intent=new Intent(mContext, glory_second_activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   // intent.putExtra("src",mImagesList.get(position));
                    intent.putExtra("recived_mg_name", saleh_mImagesList.get(position).getName());

                    mContext.startActivity(intent);
                }
            });

        }
        Zoomy.Builder builder = new Zoomy.Builder(saleh_myactivty)
                .target(holder.img_singl_image_majlah)
                .interpolator(new OvershootInterpolator())
                .tapListener(new TapListener() {
                    @Override
                    public void onTap(View v) {
                        if(position>=0) {

                            Log.e("current_pos=",position+"");
                            Intent intent = new Intent(mContext, glory_second_activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            // intent.putExtra("src",mImagesList.get(position));


                            intent.putExtra("recived_mg_name", saleh_mImagesList.get(position).getName());
                            //intent.putExtra("position",position);
                            mContext.startActivity(intent);
                        }
                    }
                })
                .longPressListener(new LongPressListener() {
                    @Override
                    public void onLongPress(View v) {
                        Toast.makeText(mContext, "Long press on " + v.getTag(), Toast.LENGTH_SHORT).show();
                    }
                }).doubleTapListener(new DoubleTapListener() {
                    @Override
                    public void onDoubleTap(View v) {
                        Toast.makeText(mContext, "Double tap on " + v.getTag(), Toast.LENGTH_SHORT).show();
                    }
                });

        builder.register();
    }

    void saleh_load_banner(AdView ads) {
        AdRequest adRequest = null;

        adRequest = new AdRequest.Builder()
                .build();
        //}
        if(func_check_ads(ads.getAdUnitId()))
            ads.loadAd(adRequest);
    }
    private void saleh_initLayoutmyone(final ItemRowHolder_one holder, int position) {


      saleh_load_banner(holder.mAdView);
        //holder.mAdView.loadAd(new AdRequest.Builder().build());
    }


    public static int saleh_calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

                      while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    void animat(View view)
    {
        anim_view(mContext).setInterpolator(glory_public_class.saleh_interpolator);
        view.startAnimation(anim_view(mContext));
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = saleh_calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
       return BitmapFactory.decodeResource(res, resId, options);
        //return BitmapFactory.decodeResource(res, resId);
    }
    @Override
    public int getItemCount() {
        return saleh_mImagesList.size();
    }



    private class ItemRowHolder_one extends RecyclerView.ViewHolder{
        AdView mAdView;
        VideoController mVideoController;
        public ItemRowHolder_one(@NonNull View itemView) {
            super(itemView);
            mAdView = itemView.findViewById(R.id.ads_in_adapter);

        }
    }
    private class ItemRowHolder_two extends RecyclerView.ViewHolder {
        public ImageView img_singl_image_majlah;
        ProgressBar progressBar3;
       // MaterialFavoriteButton favbutton;
        public ItemRowHolder_two(@NonNull View itemView) {
            super(itemView);
            img_singl_image_majlah = (ImageView) itemView.findViewById(R.id.img_singl_image_majlah);
            progressBar3= itemView.findViewById(R.id.progressBar3);
        }
    }
}
