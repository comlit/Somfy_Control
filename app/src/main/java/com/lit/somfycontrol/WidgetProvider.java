package com.lit.somfycontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class WidgetProvider extends AppWidgetProvider {

    private static final String MyOnClick = "myOnClickTag";
    private static final String MyOnClick2 = "myOnClickTag2";
    private static final String MyOnClick3 = "myOnClickTag3";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
        remoteViews.setOnClickPendingIntent(R.id.imageButton, getPendingSelfIntent(context, MyOnClick));
        remoteViews.setOnClickPendingIntent(R.id.imageButton2, getPendingSelfIntent(context, MyOnClick2));
        remoteViews.setOnClickPendingIntent(R.id.imageButton3, getPendingSelfIntent(context, MyOnClick3));
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (MyOnClick.equals(intent.getAction())){
            Sender.senden('U');
        }
        if (MyOnClick2.equals(intent.getAction())) {
            Sender.senden('M');
        }
        if (MyOnClick3.equals(intent.getAction())) {
            Sender.senden('D');
        }
    };
}
