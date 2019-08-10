package co.carlosortiz.workqueue.workers.units.processors;

import java.util.Map;
import java.util.concurrent.Callable;

public interface JobProcessor<T> extends Callable<T> {

    void setParams(Map<String,String> params);

}
