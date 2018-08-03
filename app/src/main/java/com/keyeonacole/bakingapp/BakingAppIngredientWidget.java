package com.keyeonacole.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppIngredientWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_ingredient_widget);

        //Intent intent = new Intent(context, MainActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context,appWidgetId + 1,intent,0);

        Intent intent1 = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.widgetListview, intent1);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,appWidgetId + 1,intent1,0);
        views.setPendingIntentTemplate(R.id.widgetListview, pendingIntent1);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_ingredient_widget);
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context,appWidgetManager, appWidgetIds[i]);
            Toast.makeText(context, "Widget has been updated! ", Toast.LENGTH_SHORT).show();
        }


    }

}

