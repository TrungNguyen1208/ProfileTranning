package ptit.nttrung.profiletranning.data.database;

/**
 * Created by TrungNguyen on 9/7/2017.
 */

public class DatabaseInjection {
    public static DatabaseSource provideDatabaseSource() {
        return FakeDatabaseService.getInstance();
    }
}
