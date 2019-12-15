package com.glasses.flightapp.flightapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * a generic view holder for usage in RecyclerViews extended to bind
 * two listeners to items
 *
 * @param <E>
 * @param <ELA>
 */
abstract class EntityViewHolder<E, ELA extends RecyclerView.Adapter> extends RecyclerView.ViewHolder {
    EntityViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * map element's data to layout represented by itemView using
     * the two listeners to enable the user to interact with this list item
     *
     * @param element       model to be bound to this holder
     * @param listener      listener specifying the behaviour when item is clicked
     * @param btnListener   listener specifying the behaviour when action button is clicked
     */
    public abstract void bind(E element,
                              OnItemClickListener<E> listener,
                              OnItemButtonClickListener<E, ELA> btnListener);
}
