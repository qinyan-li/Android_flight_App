package com.glasses.flightapp.flightapp.Adapter;

import android.view.View;

/**
 * listener interface to enable users of recycler view adapters to plug in their
 * custom click event actions on list item view
 *
 * @param <T> class of displayed list items
 */
public interface OnItemClickListener<T> {
    /**
     * called whenever the associated list item is clicked
     *
     * @param view      view object of list item (e.g. to find item UI elements)
     * @param item      model object associated with the list item
     */
    void onItemClick(View view, T item);
}
