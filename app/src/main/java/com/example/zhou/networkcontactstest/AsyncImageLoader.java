package com.example.zhou.networkcontactstest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jxr34 on 2017/6/25
 */

public class AsyncImageLoader extends AsyncTask<String, Void, Bitmap> {

    private String tag = "AsyncImageLoader";
    private OkHttpClient client;
    private LruCache<String, Bitmap> lruCache = new LruCache<>(15);
    private ImageView imageView;

    public AsyncImageLoader(ImageView imageView, OkHttpClient client) {
        this.imageView = imageView;
        this.client = client;
    }

    public boolean hasBitmapInCache(String url) {
        if (lruCache.get(url) == null) {
            Log.i(tag, "not has cache in :" + url);
            return false;
        } else {
            Log.i(tag, "has cache in :" + url);
            imageView.setImageBitmap(lruCache.get(url));
            return true;
        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
            Response response = client.newCall(request).execute();
            byte bytes[] = response.body().bytes();
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            if (lruCache.get(params[0]) == null) {
                lruCache.put(params[0], bitmap);
                Log.i(tag, "save cache int " + params[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
