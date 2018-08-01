package com.keyeonacole.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;

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
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        //Intent intent = new Intent(context, MainActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        //views.setOnClickPendingIntent(R.id.image, pendingIntent);

        Intent intent1 = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.widgetListview, intent1);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,0,intent1,0);
        views.setPendingIntentTemplate(R.id.widgetListview, pendingIntent1);
        views.setOnClickPendingIntent(R.id.image, pendingIntent1);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

