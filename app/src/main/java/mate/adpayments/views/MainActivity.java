package mate.adpayments.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import mate.adpayments.R;
import mate.adpayments.viewmodel.DataViewModel;

public class MainActivity extends AppCompatActivity {

    private TextView tvAdd, tvView;
    private DataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
        setViews();
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AdNetworks.class));
            }
        });
    }

    private void setViews() {
        tvAdd = findViewById(R.id.tvAdd);
        tvView = findViewById(R.id.tvView);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.add_dialog_title));

        View view = LayoutInflater.from(this).inflate(R.layout.add_ad_network_dialog, null);
        builder.setView(view);

        EditText etAlias = view.findViewById(R.id.editAlias);
        EditText etName = view.findViewById(R.id.editName);
        EditText etPrivacyUrl = view.findViewById(R.id.editPrivacyUrl);

        builder.setPositiveButton(this.getResources().getText(R.string.add), (dialog, which) -> {

            String inpuName = etName.getText().toString();
            String inputAlias = etAlias.getText().toString();
            String inputPrivacyURL = etPrivacyUrl.getText().toString();

            if ((inpuName != null && !inpuName.trim().isEmpty()) &&
                    (inputAlias != null && !inputAlias.trim().isEmpty()) &&
                    (inputPrivacyURL != null && !inputPrivacyURL.trim().isEmpty())) {
                //adding to the records to DB
                viewModel.insertData(etAlias.getText().toString(), etName.getText().toString(), etPrivacyUrl.getText().toString());
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill all the fields. Data not inserted", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Do nothing or add any other desired behavior
            dialog.dismiss();
        });

        builder.show();
    }
}