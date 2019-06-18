package ncku.aad.popochord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class ListRecordAdapter extends RecyclerView.Adapter<ListRecordAdapter.MyViewHolder> {


    interface OnItemClickHandler{
        void onItemClick(String text);
    }

    private LinkedList<String> mWordList;
    private LayoutInflater mInflater;
    private String SelectedFileName = null;
    private OnItemClickHandler onItemClickHandler;


    public class MyViewHolder extends RecyclerView.ViewHolder
                                        implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView textView;
        final ListRecordAdapter mListRecordAdapter;
        public MyViewHolder(View v, ListRecordAdapter mListRecordAdapter) {
            super(v);
            textView = v.findViewById(R.id.word);
            this.mListRecordAdapter = mListRecordAdapter;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();


            // TODO: file choosed action
            SelectedFileName = mWordList.get(mPosition);
            mListRecordAdapter.notifyDataSetChanged();
            onItemClickHandler.onItemClick(SelectedFileName);
        }

    }

    public ListRecordAdapter(Context context, LinkedList<String> mWordList, OnItemClickHandler handler) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = mWordList;
        onItemClickHandler = handler;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mViewItem = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new MyViewHolder(mViewItem, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.textView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

}
