package com.example.wallsuper.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wallsuper.Entity.CategoryActivity.Category_Wise;
import com.example.wallsuper.Entity.CategoryActivity.EditorChoice;
import com.example.wallsuper.R;
import com.example.wallsuper.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class CategoryFragment extends Fragment {

    CardView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13, card14, card15, card16, card17, card18, card19, card20;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12, iv13, iv14, iv15, iv16, iv17, iv18, iv19, iv20;
    View view;
    String name; //category name
    Integer cid; //category id
    private Activity context;
    private AdView mAdView;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mContext = getContext();
        this.context = (Activity) context;
        if (!Utils.isConnected(getContext())) showDialog(getContext()).show();
        else {
            view = inflater.inflate(R.layout.fragment_category, container, false);
            Declaration();
            Initial();
            MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            mAdView = view.findViewById(R.id.adViewCate);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
        return view;
    }

    public AlertDialog.Builder showDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.check_internet_dialog, null);
        Button btnRetry = view.findViewById(R.id.btnRetry);
        builder.setView(view);

        Dialog dialog = new Dialog(getContext());

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
        return builder;
    }

    private void Declaration() {
        //card view
        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);
        card5 = view.findViewById(R.id.card5);
        card6 = view.findViewById(R.id.card6);
        card7 = view.findViewById(R.id.card7);
        card8 = view.findViewById(R.id.card8);
        card9 = view.findViewById(R.id.card9);
        card10 = view.findViewById(R.id.card10);
        card11 = view.findViewById(R.id.card11);
        card12 = view.findViewById(R.id.card12);
        card13 = view.findViewById(R.id.card13);
        card14 = view.findViewById(R.id.card14);
        card15 = view.findViewById(R.id.card15);
        card16 = view.findViewById(R.id.card16);
        card17 = view.findViewById(R.id.card17);
        card18 = view.findViewById(R.id.card18);
        card19 = view.findViewById(R.id.card19);
        card20 = view.findViewById(R.id.card20);
        //img view
        iv1 = view.findViewById(R.id.iv1);
        iv2 = view.findViewById(R.id.iv2);
        iv3 = view.findViewById(R.id.iv3);
        iv4 = view.findViewById(R.id.iv4);
        iv5 = view.findViewById(R.id.iv5);
        iv6 = view.findViewById(R.id.iv6);
        iv7 = view.findViewById(R.id.iv7);
        iv8 = view.findViewById(R.id.iv8);
        iv9 = view.findViewById(R.id.iv9);
        iv10 = view.findViewById(R.id.iv10);
        iv11 = view.findViewById(R.id.iv11);
        iv12 = view.findViewById(R.id.iv12);
        iv13 = view.findViewById(R.id.iv13);
        iv14 = view.findViewById(R.id.iv14);
        iv15 = view.findViewById(R.id.iv15);
        iv16 = view.findViewById(R.id.iv16);
        iv17 = view.findViewById(R.id.iv17);
        iv18 = view.findViewById(R.id.iv18);
        iv19 = view.findViewById(R.id.iv19);
        iv20 = view.findViewById(R.id.iv20);
    }

    private void Initial() {
        setImageView();
        HandleClick();
    }

    private void HandleClick() {
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "editors_choice";
                Intent intent = new Intent(getContext(), EditorChoice.class);
                intent.putExtra("cname", name);
                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "fashion";
                cid = 2;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "animals";
                cid = 3;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "food";
                cid = 4;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "travel";
                cid = 5;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "music";
                cid = 6;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "science";
                cid = 7;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "places";
                cid = 8;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "sports";
                cid = 9;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "backgrounds";
                cid = 10;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "nature";
                cid = 11;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "education";
                cid = 12;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "feelings";
                cid = 13;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "people";
                cid = 14;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "buildings";
                cid = 15;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "health";
                cid = 16;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "business";
                cid = 17;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "computer";
                cid = 18;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "religion";
                cid = 19;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
        card20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "transportation";
                cid = 20;
                Intent intent = new Intent(getContext(), Category_Wise.class);
                intent.putExtra("cname", name);
                intent.putExtra("cid", cid);
                startActivity(intent);
            }
        });
    }

    private void setImageView() {
        //editor choice
        String path_1 = "https://i.pinimg.com/originals/0a/4d/cb/0a4dcb92fa2d3c601b58d72720d6bec4.jpg";
        Glide.with(getContext()).load(path_1).into(iv1);

        //fashion
        String path_2 = "https://webneel.com/wallpaper/sites/default/files/images/05-2013/12%20fashion%20wallpaper.jpg";
        Glide.with(getContext()).load(path_2).into(iv2);

        //animal
        String path_3 = "https://media.wired.com/photos/593261cab8eb31692072f129/master/pass/85120553.jpg";
        Glide.with(getContext()).load(path_3).into(iv3);

        //food
        String path_4 = "https://cdn.vox-cdn.com/thumbor/9qN-DmdwZE__GqwuoJIinjUXzmk=/0x0:960x646/1200x900/filters:focal(404x247:556x399)/cdn.vox-cdn.com/uploads/chorus_image/image/63084260/foodlife_2.0.jpg";
        Glide.with(getContext()).load(path_4).into(iv4);

        //travel
        String path_5 = "https://wallpapercave.com/wp/wp5240523.jpg";
        Glide.with(getContext()).load(path_5).into(iv5);

        //music
        String path_6 = "https://wallpaperaccess.com/full/4549365.jpg";
        Glide.with(getContext()).load(path_6).into(iv6);

        //sciense
        String path_7 = "https://free4kwallpapers.com/uploads/originals/2015/11/11/from-the-comic-dark-science-wallpaper.jpg";
        Glide.with(getContext()).load(path_7).into(iv7);

        //place
        String path_8 = "https://images4.alphacoders.com/215/215948.jpg";
        Glide.with(getContext()).load(path_8).into(iv8);

        //sport
        String path_9 = "https://airwallpaper.com/wp-content/uploads/wall001/Softball-Wallpaper-HD.jpg";
        Glide.with(getContext()).load(path_9).into(iv9);

        //backgrounds
        String path_10 = "https://c4.wallpaperflare.com/wallpaper/448/174/357/neon-4k-hd-best-for-desktop-wallpaper-preview.jpg";
        Glide.with(getContext()).load(path_10).into(iv10);

        //nature
        String path_11 = "https://images7.alphacoders.com/411/411820.jpg";
        Glide.with(getContext()).load(path_11).into(iv11);

        //education
        String path_12 = "https://c0.wallpaperflare.com/preview/534/41/125/school-books-young-adult-education.jpg";
        Glide.with(getContext()).load(path_12).into(iv12);

        //feelings
        String path_13 = "https://wallpaperaccess.com/full/3043312.jpg";
        Glide.with(getContext()).load(path_13).into(iv13);

        //people
        String path_14 = "https://c0.wallpaperflare.com/preview/849/546/571/wallpaper-people-wallpapers-people-backgrounds-rock.jpg";
        Glide.with(getContext()).load(path_14).into(iv14);

        //buildings
        String path_15 = "https://images7.alphacoders.com/387/387651.jpg";
        Glide.with(getContext()).load(path_15).into(iv15);

        //health
        String path_16 = "https://cdn.pixabay.com/photo/2017/05/25/15/08/jogging-2343558__340.jpg";
        Glide.with(getContext()).load(path_16).into(iv16);

        //business
        String path_17 = "https://w0.peakpx.com/wallpaper/766/63/HD-wallpaper-business-digital-technology-blue-business-background-technology-background-business-concepts-technology-in-business.jpg";
        Glide.with(getContext()).load(path_17).into(iv17);

        //computer
        String path_18 = "https://wallpaper.dog/large/875747.jpg";
        Glide.with(getContext()).load(path_18).into(iv18);

        //religion
        String path_19 = "https://i.pinimg.com/736x/95/0b/8c/950b8c25331ae8defed0ec45d98a1a61.jpg";
        Glide.with(getContext()).load(path_19).into(iv19);
        //transportation
        String path_20 = "https://i.pinimg.com/736x/a2/35/b0/a235b01fc3d7bbf257f436a9e22911e3.jpg";
        Glide.with(getContext()).load(path_20).into(iv20);
    }
}