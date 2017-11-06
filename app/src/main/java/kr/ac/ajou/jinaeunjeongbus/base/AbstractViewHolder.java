package kr.ac.ajou.jinaeunjeongbus.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class AbstractViewHolder<T> extends RecyclerView.ViewHolder {

    public AbstractViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void onBindView(@NonNull T item, int position);

}