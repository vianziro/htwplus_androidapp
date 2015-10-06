package htw_berlin.de.htwplus.androidapp;

/**
 * Von dieser Klasse wird bei Start der App nur ein Objekt instanziiert,
 * auf welches dann global zugegriffen werden kann (Singleton). Diese
 * Klasse ist in der AndroidManifext.xml unter <application> eingetragen.
 *
 *
 *
 * 0.0.2.1	    Router/gateway address
 * 10.0.2.2	    Special alias to your host loopback interface (i.e., 127.0.0.1 on your development machine)
 * 10.0.2.3	    First DNS server
 * 10.0.2.15	The emulated device's own network/ethernet interface
 * 127.0.0.1	The emulated device's own loopback interface
 *
 * Created by tino on 23.06.15.
 */
public class Application extends android.app.Application {

    private static Application mInstance;
    private static VolleyNetworkController vncInstance;
    private static SharedPreferencesController spcInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        vncInstance = VolleyNetworkController.getInstance(getApplicationContext());
        spcInstance = SharedPreferencesController.getInstance(getApplicationContext(),
                                                                "AppPreferences",
                                                                MODE_PRIVATE);
    }

    public static synchronized Application getInstance() {
        return mInstance;
    }

    public static VolleyNetworkController getVolleyController() {
        return vncInstance;
    }

    public static SharedPreferencesController preferences() {
        return spcInstance;
    }

    public static boolean isWorkingState() {
        boolean isWorking = (preferences().apiRoute().hasApiUrl() &&
                             !preferences().oAuth2().isAccessTokenExpired());
        return isWorking;
    }
}
