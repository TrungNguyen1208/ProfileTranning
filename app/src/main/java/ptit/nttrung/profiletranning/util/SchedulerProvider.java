package ptit.nttrung.profiletranning.util;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * This one for use in production/instrumented tests.
 * Created by TrungNguyen on 8/29/2017.
 */

public class SchedulerProvider implements BaseSchedulerProvider{

    private static SchedulerProvider INSTANCE;

    private SchedulerProvider(){}

    public static synchronized SchedulerProvider getInstance(){
        if (INSTANCE == null){
            INSTANCE = new SchedulerProvider();
        }
        return INSTANCE;
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
