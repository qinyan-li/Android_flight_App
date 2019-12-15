package com.glasses.flightapp.flightapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * listener interface to enable users of recycler view adapters to plug in their
 * custom click event actions on buttons inside the list item view
 *
 * @param <T>   class of displayed list items
 * @param <A>   class of actually used adapter
 */
public interface OnItemButtonClickListener<T, A extends RecyclerView.Adapter> {
    /**
     * called whenever the associated button in one of the list items is clicked
     *
     * @param view      view object of list item (e.g. to find item UI elements)
     * @param item      model object associated with the list item
     * @param adapter   handling adapter (e.g. to refresh data set after modification)
     */
    void onItemClick(View view, T item, A adapter);
}
