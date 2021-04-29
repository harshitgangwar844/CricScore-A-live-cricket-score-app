package com.sumit.myapplication444;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }


    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        String likeCount = currentItem.getLikeCount();
        holder.mImageView.setText(imageUrl);
        holder.mImageView.setVisibility(View.INVISIBLE);
        holder.mTextViewCreator.setText("MATCH STATUS: "+creatorName);
        holder.mTextViewLikes.setText("MATCH ID"+likeCount);
        holder.mbtnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ut = new Intent(mContext,DetailActivity.class);
                String matchid = currentItem.getLikeCount();
                ut.putExtra("uid",matchid);
                mContext.startActivity(ut);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;
        Button mbtnshow;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.text_view_likes);
            mbtnshow = itemView.findViewById(R.id.btnshow);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        Log.d("CLICKED ","CLICKED");
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                            Log.d("CLICKED ","CLICKED");

                        }
                    }
                }
            });


        }
    }
}