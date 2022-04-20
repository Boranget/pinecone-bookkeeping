package com.blackorangejuice.songguojizhang.transaction.home.list.in.event.choose;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.utils.SongGuoUtils;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChooseAccountItemRecycleViewAdapter extends RecyclerView.Adapter<ChooseAccountItemRecycleViewAdapter.AccountItemViewHolder> {
    private List<AccountItem> accountItems;
    List<AccountItem> willAccountItemList ;

    static class AccountItemViewHolder extends RecyclerView.ViewHolder {
        View accountItemView;
        ImageView tagImageView;
        TextView tagNameTextView;
        TextView remarkTextView;
        TextView sumTextView;
        TextView timeTextView;
        CheckBox checkBox;


        public AccountItemViewHolder(@NonNull View itemView) {
            super(itemView);
            accountItemView = itemView;
            tagImageView = itemView.findViewById(R.id.item_choose_account_list_img);
            tagNameTextView = itemView.findViewById(R.id.item_choose_account_list_name);
            remarkTextView = itemView.findViewById(R.id.item_choose_account_list_remark);
            sumTextView = itemView.findViewById(R.id.item_choose_account_list_sum);
            timeTextView = itemView.findViewById(R.id.item_choose_account_list_time);
            checkBox = itemView.findViewById(R.id.item_choose_account_list_checkbox);
        }

    }

    public ChooseAccountItemRecycleViewAdapter(ChooseBlockRecycleViewAdapter blockAdapter, int postation, ChooseShowAccountListPageActivity fragment, List<AccountItem> accountItems) {
        this.accountItems = accountItems;
    }

    @NonNull
    @Override
    public AccountItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choose_account_list, parent, false);
        AccountItemViewHolder accountItemViewHolder = new AccountItemViewHolder(view);
        accountItemViewHolder.accountItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checkbox 反选
                accountItemViewHolder.checkBox.setChecked(!accountItemViewHolder.checkBox.isChecked());

            }
        });
        accountItemViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AccountItemMapper accountItemMapper = new AccountItemMapper(SongGuoDatabaseHelper.getSongGuoDatabaseHelper(accountItemViewHolder.itemView.getContext()));
                int adapterPosition = accountItemViewHolder.getAdapterPosition();
                AccountItem accountItem = accountItems.get(adapterPosition);
                if(willAccountItemList == null){
                    willAccountItemList = new ArrayList<>();
                    GlobalInfo.lastAddEvent.setWillAccountItemList(willAccountItemList);
                }
                if (isChecked) {
                    // 将当前账单添加到当前事件的list中
                    // 这里使用list主要是因为在新建的时候事件还没有id，无法绑定，所以暂存
                    willAccountItemList.add(accountItem);
                } else {
                    // 移除
                    willAccountItemList.remove(accountItem);
                    // 同时eid设为0
                    accountItem.setEid(0);
                }
            }
        });


        return accountItemViewHolder;
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
        if (AccountItem.INCOME.equals(accountItem.getIncomeOrExpenditure())) {
            sum.append("+");
        } else {
            sum.append("-");
        }

        holder.sumTextView.setText(sum.append(accountItem.getSum()).toString());
        holder.timeTextView.setText(new SimpleDateFormat("yyyy/MM/dd").format(accountItem.getAccountTime()));
        // 获取account的eid
        Integer eid = accountItem.getEid();
        // 若eid不为0
        if (eid != 0) {
            // 如果eid为当前事件的id,则设为选中状态
            if (eid == GlobalInfo.lastAddEvent.getEid()) {
                holder.checkBox.setChecked(true);
            } else {
                // 否则隐藏
                holder.checkBox.setEnabled(false);
                holder.itemView.setClickable(false);
                holder.checkBox.setVisibility(View.INVISIBLE);
            }

        }


    }

    @Override
    public int getItemCount() {
        return accountItems.size();
    }


}
