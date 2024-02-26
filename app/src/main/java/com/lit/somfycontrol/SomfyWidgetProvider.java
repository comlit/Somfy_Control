package com.lit.somfycontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;


public class SomfyWidgetProvider extends AppWidgetProvider {

    private static final String SOMFY_UP = "upTag";
    private static final String SOMFY_STOP = "stopTag";
    private static final String SOMFY_DOWN = "downTag";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.somfy_widget);
        ComponentName thisWidget = new ComponentName(context, SomfyWidgetProvider.class);
        remoteViews.setOnClickPendingIntent(R.id.somfy_up, getPendingSelfIntent(context, SOMFY_UP));
        remoteViews.setOnClickPendingIntent(R.id.somfy_stop, getPendingSelfIntent(context, SOMFY_STOP));
        remoteViews.setOnClickPendingIntent(R.id.somfy_down, getPendingSelfIntent(context, SOMFY_DOWN));
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    public void changeColor(int code, int button, Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int color = 0;
        switch(code){
            case(200):
                color = Color.GREEN;
                break;
            case(500):
                color = Color.YELLOW;
                break;
            default:
                color = Color.RED;
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.somfy_widget);
        ComponentName thisWidget = new ComponentName(context, SomfyWidgetProvider.class);

        int buttonRef = 0;
        switch(button){
            case 0:
                buttonRef = R.id.somfy_up;
                break;
            case 1:
                buttonRef = R.id.somfy_stop;
                break;
            case 2:
                buttonRef = R.id.somfy_down;
                break;
        }
        remoteViews.setInt(buttonRef, "setColorFilter", color);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    public void resetColor(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.somfy_widget);
        ComponentName thisWidget = new ComponentName(context, SomfyWidgetProvider.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        remoteViews.setInt(R.id.somfy_up, "setColorFilter", Color.WHITE);
        remoteViews.setInt(R.id.somfy_stop, "setColorFilter", Color.WHITE);
        remoteViews.setInt(R.id.somfy_down, "setColorFilter", Color.WHITE);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (SOMFY_UP.equals(intent.getAction())){
            changeColor(500, 0, context);
            int resp = Sender.senden('U');
            changeColor(resp, 0, context);
        }
        if (SOMFY_STOP.equals(intent.getAction())) {
            changeColor(500, 1, context);
            int resp = Sender.senden('M');
            changeColor(resp, 1, context);
        }
        if (SOMFY_DOWN.equals(intent.getAction())) {
            changeColor(500, 2, context);
            int resp = Sender.senden('D');
            changeColor(resp, 2, context);
        }
        try
        {
            Thread.sleep(1000);
            resetColor(context);
        } catch(Exception e)
        {}
    };
}
