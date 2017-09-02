package ptit.nttrung.profiletranning.data.scheduler;


import ptit.nttrung.profiletranning.util.BaseSchedulerProvider;
import ptit.nttrung.profiletranning.util.ImmediateSchedulerProvider;
/**
 * Get the Immediate Scheduler for tests
 * Created by TrungNguyen on 8/29/2017.
 */

public class SchedulerInjection {
    public static BaseSchedulerProvider provideSchedulerProvider(){
        return ImmediateSchedulerProvider.getInstance();
    }
}
