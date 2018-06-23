package app.androiddevelopmenttest.net;

/**
 * Created by DELL on 6/22/2018.
 */

public interface CommunicationManager<T> {
    void onResponse(String response);

    void onProcessNext(T response);

    void onError();
}
