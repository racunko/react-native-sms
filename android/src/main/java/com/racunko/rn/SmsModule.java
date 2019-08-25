
package com.racunko.rn;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.database.Cursor;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SmsModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public SmsModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "Sms";
  }

  @ReactMethod
  public void list(String query, final Callback successCallback, final Callback errorCallback) {
    try{
      JSONObject filter = new JSONObject(query);
      // by default read inbox
      String uri_filter = filter.optString("box", "inbox");

      // optional filters
      // String address = filter.optString("address");
      // int id = filter.optInt("id", -1);
      // int indexFrom = filter.optInt("indexFrom", 0);
      // int maxCount = filter.optInt("maxCount", -1);
      Uri contentUri = Uri.parse("content://sms/" + uri_filter);

      Cursor cursor = getCurrentActivity().
        getContentResolver().
        query(contentUri, null, "", null, null);

      JSONArray smsList = new JSONArray();
      while (cursor.moveToNext()) smsList.put(getJsonFromCursor(cursor));
      cursor.close();

      try {
        successCallback.invoke(smsList.length(), smsList.toString());
      } catch (Exception e) {
        errorCallback.invoke(e.getMessage());
      }
    } catch (JSONException e) {
      errorCallback.invoke(e.getMessage());
      return;
    }
  }

  private JSONObject getJsonFromCursor(Cursor cur) {
    JSONObject json = new JSONObject();
    String[] keys = cur.getColumnNames();

    try {
      for (int j = 0; j < cur.getColumnCount(); j++) {
        switch (cur.getType(j)) {
          case Cursor.FIELD_TYPE_NULL:
            json.put(keys[j], null);
            break;
          case Cursor.FIELD_TYPE_INTEGER:
            json.put(keys[j], cur.getLong(j));
            break;
          case Cursor.FIELD_TYPE_FLOAT:
            json.put(keys[j], cur.getFloat(j));
            break;
          case Cursor.FIELD_TYPE_STRING:
            json.put(keys[j], cur.getString(j));
            break;
          case Cursor.FIELD_TYPE_BLOB:
            json.put(keys[j], cur.getBlob(j));
        }
      }
    } catch (Exception e) {
      return null;
    }

    return json;
  }
}