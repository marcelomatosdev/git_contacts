package com.example.gitcontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactCardAdapter extends RecyclerView.Adapter<ContactCardAdapter.ContactViewHolder> {

    private Context mContext;
    private ArrayList<ContactItem> mContactList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public ContactCardAdapter(Context context, ArrayList<ContactItem> contactList){
        mContext = context;
        mContactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactItem currentItem = mContactList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String username = currentItem.getUsername();
        String name = currentItem.getName();
        String bio = currentItem.getBio();
        int repositories = currentItem.getRepositories();
        int followers = currentItem.getFollowers();

        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        holder.mTextViewUsername.setText(username);
        holder.mTextViewName.setText("Name: "+name);
        holder.mTextViewRepositories.setText("Repositories: "+repositories);
        holder.mTextViewFollowers.setText("Followers: "+followers);


    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewUsername;
        public  TextView mTextViewName;
        public  TextView mTextViewBio;
        public  TextView mTextViewRepositories;
        public  TextView mTextViewFollowers;

        public ContactViewHolder(final View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewUsername = itemView.findViewById(R.id.text_view_username);
            mTextViewName = itemView.findViewById(R.id.text_view_name);
            mTextViewRepositories = itemView.findViewById(R.id.text_view_public_repos);
            mTextViewFollowers = itemView.findViewById(R.id.text_view_followers);
            mTextViewBio = itemView.findViewById(R.id.text_view_bio_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
