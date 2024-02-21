package mate.adpayments.views;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import mate.adpayments.R;
import mate.adpayments.adapter.MyAdapter;
import mate.adpayments.model.DatabaseHelper;
import mate.adpayments.viewmodel.DataViewModel;

public class AdNetworks extends AppCompatActivity {
    private RecyclerView rvAdNetworks;
    private RecyclerView.Adapter adapter;
    private DataViewModel viewModel;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_networks);

        viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        rvAdNetworks = findViewById(R.id.rvAdNetworks);
        rvAdNetworks.setLayoutManager(new LinearLayoutManager(this));
        rvAdNetworks.addItemDecoration(new DividerItemDecoration(rvAdNetworks.getContext(), DividerItemDecoration.VERTICAL));

        setAdapter();

    }

    private void setAdapter() {
        cursor = viewModel.getAllData();
        adapter = new MyAdapter(cursor, new MyAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int itemId) {
                showEditDialog(itemId);
            }

            @Override
            public void onDeleteClick(int itemId) {
                // Assuming the position directly corresponds to the item's position in the list
                cursor.moveToPosition(itemId);
                int cursorInt = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                viewModel.deleteData(cursorInt);
                setAdapter();
                Toast.makeText(AdNetworks.this, "record deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        rvAdNetworks.setAdapter(adapter);
    }

    private void showEditDialog(int itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.edit_dialog_title));

        View view = LayoutInflater.from(this).inflate(R.layout.add_ad_network_dialog, null);
        builder.setView(view);

        EditText editAlias = view.findViewById(R.id.editAlias);
        EditText editName = view.findViewById(R.id.editName);
        EditText editPrivacyUrl = view.findViewById(R.id.editPrivacyUrl);

        // Retrieve existing data and set it to the EditTexts
        cursor.moveToPosition(itemId);
        int cursorInt = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
        editAlias.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ALIAS)));
            editName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
            editPrivacyUrl.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRIVACY_URL)));
//        }

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newAlias = editAlias.getText().toString();
            String newName = editName.getText().toString();
            String newPrivacyUrl = editPrivacyUrl.getText().toString();

            if ((newName != null && !newName.trim().isEmpty()) &&
                    (newAlias != null && !newAlias.trim().isEmpty()) &&
                    (newPrivacyUrl != null && !newPrivacyUrl.trim().isEmpty())) {
                //updating to the records to DB
            boolean updateResult = viewModel.updateData(cursorInt, newAlias, newName, newPrivacyUrl);
            if (updateResult) {
                Toast.makeText(AdNetworks.this, "Item updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdNetworks.this, "Failed to update item", Toast.LENGTH_SHORT).show();
            }
            setAdapter();
            }
            else{
                Toast.makeText(this, "Please fill all the fields. Data not updated", Toast.LENGTH_SHORT).show();
            }


        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Do nothing or add any other desired behavior
            dialog.dismiss();
        });

        builder.show();
    }

}