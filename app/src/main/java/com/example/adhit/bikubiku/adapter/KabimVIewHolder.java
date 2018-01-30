package com.example.adhit.bikubiku.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.Kabim;
import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by roy on 12/10/2017.
 */

public class KabimVIewHolder extends GroupViewHolder{

    private final TextView txtSee;
    private ImageView expandButton;
    private ImageView icon;
    protected ImageView chat;
    private Context context;
    private TextView namaProduct, harga;

    public KabimVIewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        namaProduct = (TextView) itemView.findViewById(R.id.name);
        icon = (ImageView) itemView.findViewById(R.id.iconImage);
        chat = itemView.findViewById(R.id.img_chat);
        harga = (TextView) itemView.findViewById(R.id.price);
        expandButton = (ImageView) itemView.findViewById(R.id.expand_button);
        txtSee = itemView.findViewById(R.id.txt_expandable);
    }

    public void setProduct (ExpandableGroup kabim){
        if (kabim instanceof Kabim){
            namaProduct.setText(((Kabim) kabim).getTitle());
            String imageKabim = ((Kabim) kabim).getAvatarUrlKabim();
            if (imageKabim.length() > 0){
                System.out.println("url image : " + imageKabim);
                Picasso.with(context).load(imageKabim).into(icon);
            }
            else {
                Drawable drawable = context.getResources().getDrawable(R.drawable.no_image);
                icon.setImageDrawable(drawable);
            }
        }

    }

    @Override
    public void expand() {
       animateExpand();
    }

    @Override
    public void collapse() {
       animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        expandButton.setAnimation(rotate);
        txtSee.setText("See Less");
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        expandButton.setAnimation(rotate);
        txtSee.setText("See More");
    }
}
