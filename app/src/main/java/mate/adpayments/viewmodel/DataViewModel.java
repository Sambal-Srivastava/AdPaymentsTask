package mate.adpayments.viewmodel;
import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import mate.adpayments.repository.MyRepository;

public class DataViewModel extends AndroidViewModel {

    private MyRepository repository;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public Cursor getAllData() {
        return repository.getAllData();
    }

    public long insertData(String alias, String name, String privacyUrl) {
        return repository.insertData(alias, name, privacyUrl);
    }

    public boolean updateData(int id, String alias, String name, String privacyUrl) {
        return repository.updateData(id, alias, name, privacyUrl);
    }

    public boolean deleteData(int id) {
        return repository.deleteData(id);
    }

    public Cursor getDataById(int itemId) {
        return repository.getDataById(itemId);
    }

}
