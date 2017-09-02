package ptit.nttrung.profiletranning.util;

import io.reactivex.Scheduler;

/**
 * Created by TrungNguyen on 8/29/2017.
 */

public interface BaseSchedulerProvider {
    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
