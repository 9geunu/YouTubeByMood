package com.example.youtubebymood.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubebymood.R;
import com.example.youtubebymood.core.model.HashTagModel;

import java.util.ArrayList;

public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.HashTagAdapterViewHolder> {

    private Context mContext;
    final private HashTagAdapterOnClickHandler hashTagAdapterOnClickHandler;
    private ArrayList<HashTagModel> hashTagModelArrayList;


    public interface HashTagAdapterOnClickHandler {
        void onClick(String query);
    }

    public HashTagAdapter(Context context, HashTagAdapterOnClickHandler clickHandler, ArrayList<HashTagModel> hashTagModelArrayList){
        mContext = context;
        hashTagAdapterOnClickHandler = clickHandler;
        this.hashTagModelArrayList = hashTagModelArrayList;
    }

    @NonNull
    @Override
    public HashTagAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutId = R.layout.tag_list_item;

        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);

        view.setFocusable(true);

        return new HashTagAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HashTagAdapterViewHolder holder, int position) {
        final HashTagModel hashTagModel = hashTagModelArrayList.get(position);

        holder.hashTag.setText(hashTagModel.getTag());
    }

    @Override
    public int getItemCount() {
        return hashTagModelArrayList != null ? hashTagModelArrayList.size() : 0;
    }

    class HashTagAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView hashTag;

        HashTagAdapterViewHolder(View view) {
            super(view);

            hashTag = (TextView) view.findViewById(R.id.tv_hashtag);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String query = hashTagModelArrayList.get(position).getQuery();
            hashTagAdapterOnClickHandler.onClick(query);
        }
    }
}
