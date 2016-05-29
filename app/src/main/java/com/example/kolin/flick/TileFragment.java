package com.example.kolin.flick;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kolin.flick.data.Photo_;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TileFragment extends Fragment {


    private RecyclerView recyclerView;
    private TileAdapter adapter;

    private ArrayList<Photo_> photos;

    public OnClickTileItem listenerTile;

    public TileFragment() {
        // Required empty public constructor
    }


    public interface OnClickTileItem{
        void onSelectedTileItem(Photo_ photo);
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);

        try{
            listenerTile = (OnClickTileItem) a;
        } catch (ClassCastException e){
            throw new ClassCastException(a.toString() + "must implement listener");
        }
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
        photos = new ArrayList<>();
        photos.addAll(list);
        adapter = new TileAdapter(list);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        recyclerView.setAdapter(adapter);

        recyclerView.setPadding(16, 16, 16, 16);
        recyclerView. setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return recyclerView;

    }



    public class TileAdapter extends RecyclerView.Adapter<ViewHolder>{

        private List<Photo_> galleryItems;

        public TileAdapter(List<Photo_> list) {
            galleryItems = new ArrayList<>(list);
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
            return galleryItems.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            Photo_ p = photos.get(getAdapterPosition());
            listenerTile.onSelectedTileItem(p);
        }

    }

}
