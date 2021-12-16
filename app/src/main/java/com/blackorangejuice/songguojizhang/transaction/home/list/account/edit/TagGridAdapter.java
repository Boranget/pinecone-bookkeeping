package com.blackorangejuice.songguojizhang.transaction.home.list.account.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.Tag;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;

import java.util.List;

public class TagGridAdapter extends RecyclerView.Adapter<TagGridAdapter.TagGridViewHolder> {
    EditAccountActivity addEditAccountPageActivity;

    private List<Tag> tagGridItems;

    private Context mContext ;

    static class TagGridViewHolder extends RecyclerView.ViewHolder {

        private View tagItem;
        ImageView tagImageView;
        TextView tagTextView;

        public TagGridViewHolder(@NonNull View itemView) {
            super(itemView);
            tagItem = itemView;
            tagImageView = itemView.findViewById(R.id.tag_grid_item_image_view);
            tagTextView = itemView.findViewById(R.id.tag_grid_item_text_view);
        }
    }

    public TagGridAdapter(List<Tag> tags, EditAccountActivity activity) {
        tagGridItems = tags;
        addEditAccountPageActivity = activity;
    }

    @NonNull
    @Override
    public TagGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag_grid, parent, false);
        TagGridViewHolder tagGridViewHolder = new TagGridViewHolder(view);

        tagGridViewHolder.tagItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = tagGridViewHolder.getAdapterPosition();
                Tag tag = tagGridItems.get(adapterPosition);
                addEditAccountPageActivity.setTagNameAndImg(tag);

            }
        });
        return tagGridViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagGridViewHolder holder, int position) {
        Tag tag = tagGridItems.get(position);
        // 绑定标签名
        holder.tagTextView.setText(tag.getTagName());
        // 绑定图片
        String tagImgName = tag.getTagImgName();
        Bitmap bitmap = SongGuoUtils.getBitmapByFileName(mContext,"tag/"+tagImgName);
        holder.tagImageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return tagGridItems.size();
    }


}
