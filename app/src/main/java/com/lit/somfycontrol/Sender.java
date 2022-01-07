package com.lit.somfycontrol;

import com.github.kevinsawicki.http.HttpRequest;

public class Sender
{
    public static void senden(final char con)
    {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    String ip = "http://192.168.178.44/";
                    String url = ip + con;
                    int resp = HttpRequest.get(url).code();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
