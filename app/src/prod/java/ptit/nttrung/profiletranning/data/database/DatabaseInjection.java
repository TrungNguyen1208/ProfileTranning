package ptit.nttrung.profiletranning.data.database;

public class DatabaseInjection {
    public static DatabaseSource provideDatabaseSource() {
        return FirebaseDatabaseService.getInstance();
    }
}
