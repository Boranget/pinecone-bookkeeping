package com.blackorangejuice.songguojizhang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.activity.home.edit.account.UpdateEditAccountPageActivity;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.Tag;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.globle.SongGuoUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class AccountItemRecycleViewAdapter extends RecyclerView.Adapter<AccountItemRecycleViewAdapter.AccountItemViewHolder> {
    private List<AccountItem> accountItems;

    static class AccountItemViewHolder extends RecyclerView.ViewHolder {
        View accountItemView;
        ImageView tagImageView;
        TextView tagNameTextView;
        TextView remarkTextView;
        TextView sumTextView;
        TextView timeTextView;


        public AccountItemViewHolder(@NonNull View itemView) {
            super(itemView);
            accountItemView = itemView;
            tagImageView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_img);
            tagNameTextView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_name);
            remarkTextView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_remark);
            sumTextView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_sum);
            timeTextView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_time);
        }

    }

    public AccountItemRecycleViewAdapter(List<AccountItem> accountItems) {
        this.accountItems = accountItems;
    }

    @NonNull
    @Override
    public AccountItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_item_in_the_recycle_view, parent, false);
        AccountItemViewHolder accountItemViewHolder = new AccountItemViewHolder(view);
        accountItemViewHolder.accountItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = accountItemViewHolder.getAdapterPosition();
                AccountItem accountItem = accountItems.get(adapterPosition);
                // 将该记账项存入全局
                GlobalInfo.lastAddAccount = accountItem;
                UpdateEditAccountPageActivity.startThisActivity(parent.getContext());

            }
        });
        return  accountItemViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull AccountItemViewHolder holder, int position) {

        AccountItem accountItem = accountItems.get(position);

        String tagImgName = accountItem.getTag().getTagImgName();
        String fileName = "tag/" + tagImgName;
        holder.tagImageView.setImageBitmap(SongGuoUtils.getBitmapByFileName(holder.itemView.getContext(), fileName));
        holder.tagNameTextView.setText(accountItem.getTag().getTagName());
        holder.remarkTextView.setText(accountItem.getRemark());

        // 计算金额
        StringBuilder sum = new StringBuilder("");
        if(AccountItem.INCOME.equals(accountItem.getIncomeOrExpenditure())){
            sum.append("+");
        }else{
            sum.append("-");
        }

        holder.sumTextView.setText(sum.append(accountItem.getSum()).toString());
        holder.timeTextView.setText(new SimpleDateFormat("yyyy/MM/dd").format(accountItem.getAccountTime()));

    }

    @Override
    public int getItemCount() {
        return accountItems.size();
    }


}
