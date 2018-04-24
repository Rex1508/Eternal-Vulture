package edu.coe.jlgarcia.eternalvulture;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Ian on 4/23/2018.
 */

public class TestConnection extends AsyncTask<String, Void, Boolean> {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(String... strings) {

        return hostAvailable("www.google.com",80);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean hostAvailable(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            // Either we have a timeout or unreachable host or failed DNS lookup
            System.out.println(e);
            return false;
        }
    }
}
