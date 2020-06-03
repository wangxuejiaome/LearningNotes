package com.wxj.interview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wxj.interview.activitychapter.AActivity;

import java.util.List;

public class ContentsAdapter extends RecyclerView.Adapter<ContentsAdapter.ContentsHolder> {

    private Context mContext;
    private List<String> mContentList;

    public ContentsAdapter(Context context, List<String> contentList) {
        mContext = context;
        mContentList = contentList;
    }

    @NonNull
    @Override
    public ContentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contents, parent, false);
        return new ContentsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentsHolder holder, final int position) {
        if (mContentList == null) return;
        holder.tvContent.setText(mContentList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        mContext.startActivity(new Intent(mContext, AActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContentList == null ? 0 : mContentList.size();
    }

    class ContentsHolder extends RecyclerView.ViewHolder {

        private TextView tvContent;

        public ContentsHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
