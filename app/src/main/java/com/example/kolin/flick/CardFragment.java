package com.example.kolin.flick;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kolin.flick.data.Photo_;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CardFragment extends Fragment {


    private CardAdapter adapter;

    public CardFragment() {
        // Required empty public constructor
    }


    public static CardFragment newInstace(List<Photo_> list){
        Bundle args = new Bundle();
        args.putParcelableArrayList("list", new ArrayList<Photo_>(list));
        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Photo_> list = getArguments().getParcelableArrayList("list");
        adapter = new CardAdapter(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.setPadding(16, 16, 16, 16);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        return recyclerView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public class CardAdapter extends RecyclerView.Adapter<ViewHolder>{

        private List<Photo_> galleryItems;

        public CardAdapter(ArrayList<Photo_> list) {
            galleryItems = new ArrayList<>(list);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_card, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Photo_ item = galleryItems.get(position);
            holder.texView.setText(item.getTitle());
            Picasso.with(getActivity())
                    .load(item.getUrlS())
                    .placeholder(R.drawable.icc_plus)
                    .into(holder.imageView);
        }


        @Override
        public int getItemCount() {
            return 18;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private Button button;
        private TextView texView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.card_image);
            button = (Button) itemView.findViewById(R.id.action_button);
            texView = (TextView) itemView.findViewById(R.id.card_title);
            button.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Snackbar.make(v, "Holla!", Snackbar.LENGTH_LONG).show();
        }
    }
}