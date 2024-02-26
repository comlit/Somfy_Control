package com.lit.somfycontrol;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.Random;

public class Sender
{
    public static class SendThread implements Runnable {
        private volatile int res;
        private char con;

        public SendThread(char c)
        {
            con = c;
        }

        public int getRes() {
            return res;
        }

        @Override
        public void run()
        {
            try
            {
                String name = "?name=fabi";
                String ip = "http://pi:5000/";
                String method = "";
                switch(con)
                {
                    case 'U':
                        method = "rise";
                        break;
                    case 'M':
                        method = "stop";
                        break;
                    case 'D':
                        method = "lower";
                        break;
                }
                String url = ip + method + name;
                res = HttpRequest.get(url).code();
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static int senden(final char con)
    {
        try
        {
            SendThread sendThread = new SendThread(con);
            Thread thread = new Thread(sendThread);
            thread.start();
            thread.join();

            return sendThread.getRes();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
