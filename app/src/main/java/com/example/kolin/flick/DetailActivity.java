package com.example.kolin.flick;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.kolin.flick.data.Photo_;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = (ImageView) findViewById(R.id.image_image);

        Photo_ p = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            p = (Photo_) bundle.get("photoObj");
        }

        Picasso.with(getApplicationContext()).load(p.getUrlS())
                .placeholder(R.drawable.icc_plus)
                .into(imageView);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(p.getTitle());
    }


}
