package mate.adpayments.adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import mate.adpayments.R;
import mate.adpayments.model.DatabaseHelper;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Cursor cursor;
    private OnItemClickListener listener;

    public MyAdapter(Cursor cursor, OnItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (cursor.moveToPosition(position)) {
            int aliasIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ALIAS);


            if (aliasIndex != -1) {
                String alias = cursor.getString(aliasIndex);
                holder.tvAlias.setText(alias);
            }
//            int idColumnIndex = cursor.getColumnIndex(String.valueOf(position));
            int idColumnIndex = position;
            holder.ivEdit.setOnClickListener(v -> listener.onEditClick(idColumnIndex));
            holder.ivDelete.setOnClickListener(v -> listener.onDeleteClick(idColumnIndex));

            // Set initial visibility
            holder.ivEdit.setVisibility(View.GONE);
            holder.ivDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    @Override
    public long getItemId(int position) {
        // Return a unique identifier for the item at the given position
        // For example, you can use the database ID if available
        cursor.moveToPosition(position);
        return cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAlias;
        public ImageView ivEdit;
        public ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAlias = itemView.findViewById(R.id.tvAlias);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            // Set up the click listener for the entire item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle the visibility of edit and delete buttons
                    ivEdit.setVisibility((ivEdit.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
                    ivDelete.setVisibility((ivDelete.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
                }
            });
        }
    }




public interface OnItemClickListener {
    void onEditClick(int itemId);

    void onDeleteClick(int itemId);
}}

