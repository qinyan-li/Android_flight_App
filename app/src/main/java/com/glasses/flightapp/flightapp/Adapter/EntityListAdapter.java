package com.glasses.flightapp.flightapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * adapter class handling display and behaviour of list items of
 * any kind of model class
 *
 * @param <E>   entity, i.e. model class of underlying list data
 * @param <EVH> entity view holder, i.e. a view holder class controlling the display of list items
 */
public abstract class EntityListAdapter<E, EVH extends EntityViewHolder> extends RecyclerView.Adapter<EVH> {
    private final LayoutInflater inflater;
    private final Context context;

    private List<E> elements;

    private final OnItemClickListener<E> listener;
    private final OnItemButtonClickListener<E, EntityListAdapter<E, EVH>> btnListener;

    EntityListAdapter(Context context,
                      OnItemClickListener<E> listener, OnItemButtonClickListener<E, EntityListAdapter<E, EVH>> btnListener) {
        inflater = LayoutInflater.from(context);
        this.context = context;

        this.listener = listener;
        this.btnListener = btnListener;
    }

    /**
     * get resource identifier (R.layout.*) representing the list item layout fragment
     *
     * @return resource identifier (R.layout.*)
     */
    protected abstract int getResourceId();

    /**
     * create a view holder to map model data to list item layout
     *
     * @param context   list context
     * @param itemView  view class representing an inflated list item layout
     *
     * @return a concrete view holder instance
     */
    protected abstract EVH createNewViewHolder(Context context, View itemView);

    @Override
    @NonNull
    public EVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(getResourceId(), parent, false);
        return createNewViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EVH holder, int position) {
        if (elements != null)
            holder.bind(elements.get(position), listener, btnListener);
    }

    public void setElements(List<E> flights){
        this.elements = flights;
        notifyDataSetChanged();
    }

    public List<E> getElements() {
        return elements;
    }

    @Override
    public int getItemCount() {
        if (elements != null)
            return elements.size();

        else return 0;
    }
}
