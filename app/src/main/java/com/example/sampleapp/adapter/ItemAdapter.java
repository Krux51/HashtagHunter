package com.example.sampleapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sampleapp.R;
import com.example.sampleapp.Utility.DateUtils;
import com.example.sampleapp.models.Item;


import java.util.Date;

public class ItemAdapter extends PagedListAdapter<Item, ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    private int mItemSelected= -1;

    // Create a final private ItemAdapterOnClickHandler called mClickHandler
    private ItemAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface ItemAdapterOnClickHandler {
        void onClick(String videoId);
    }

    public ItemAdapter(Context mCtx, ItemAdapterOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Item item = getItem(position);

        if (item != null) {
            holder.videoTitle.setText(item.getSnippet().getTitle());
            // Load the video image into ImageView
            Glide.with(mCtx)
                    .load(item.getSnippet().getThumbnail().getImageInfo().getUrl())
                    .into(holder.videoImage);

            holder.channelTitle.setText(item.getSnippet().getChannelTitle());

            Date convertedDate = DateUtils.convertStringToDate(item.getSnippet().getPublishedAt());
            String timeAgo = DateUtils.getTimeAgo(convertedDate,mCtx);
            holder.publicationDate.setText(timeAgo);
        } else {
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public PagedList<Item> getCurrentList() {
        return super.getCurrentList();
    }

    // It determine if two list objects are the same or not
    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Item>() {
                @Override
                public boolean areItemsTheSame(Item oldItem, Item newItem) {
                    return oldItem.getVideoId().getId().equals(newItem.getVideoId().getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Item oldItem, Item newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        TextView videoTitle;
        ImageView videoImage;
        TextView channelTitle;
        TextView publicationDate;

        private ItemViewHolder(View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.titleOfVideo);
            videoImage = itemView.findViewById(R.id.imageOfVideo);
            channelTitle = itemView.findViewById(R.id.titleOfChannel);
            publicationDate = itemView.findViewById(R.id.dataOfPublication);
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the view in the adapter
            mItemSelected = getAdapterPosition();
            notifyDataSetChanged();
            // Get the id of the video
            String videoId = getCurrentList().get(mItemSelected).getVideoId().getId();
            if(videoId == null){
                return;
            }
            // Send videoId through click
            clickHandler.onClick(videoId);
        }
    }
}
