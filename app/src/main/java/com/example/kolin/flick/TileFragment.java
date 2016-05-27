package com.example.kolin.flick;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TileFragment extends Fragment {


    private List<Photo_> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private TileAdapter adapter;

    public TileFragment() {
        // Required empty public constructor
    }

    public static TileFragment newInstance(List<Photo_> list){
        Bundle args = new Bundle();
        args.putParcelableArrayList("list", new ArrayList<Photo_>(list));
        TileFragment fragment = new TileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Photo_> list = getArguments().getParcelableArrayList("list");
        adapter = new TileAdapter(list);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        recyclerView. setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setPadding(16, 16, 16, 16);
        recyclerView.setAdapter(adapter);
        return recyclerView;

    }



    public class TileAdapter extends RecyclerView.Adapter<ViewHolder>{

        private List<Photo_> galleryItems;

        public TileAdapter(List<Photo_> list) {
            galleryItems = new ArrayList<>();

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_tile, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Photo_ item = galleryItems.get(position);
            holder.textView.setText(item.getTitle());
            Picasso.with(getActivity()).load(item.getUrlS())
                    .placeholder(R.drawable.icc_plus)
                    .into(holder.imageView);
        }

        public void clear(){
            galleryItems.clear();
            notifyDataSetChanged();
        }

        public void add(List<Photo_> list){
            galleryItems.addAll(list);
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return 18;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.tile_picture);
            textView = (TextView) itemView.findViewById(R.id.tile_title);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetailActivity.class);
            context.startActivity(intent);
        }

    }

}
