package com.testdevelop.mysdk;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.testdevelop.mysdk.imageloader.ImageLoaderManager;

/**
 * Created by zyx on 2017/4/15.
 */

public class ImageLoaderTest {
    private void testApi() {
        /**
         * 为我们的Imageloader去配置参数
         */
        //  ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context).build();

        /**
         * 我们先来获取到Imageloader的一个实例
         */
//        ImageLoader imageLoader = ImageLoader.getInstance();
//
//        /**
//         *
//         */
//        DisplayImageOptions options = new DisplayImageOptions.Builder().build();
//
//        /**
//         * 使用displayImage 去加载图片
//         */
//        imageLoader.displayImage("url",imageview,options,new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                super.onLoadingStarted(imageUri, view);
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                super.onLoadingFailed(imageUri, view, failReason);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                super.onLoadingCancelled(imageUri, view);
//            }
//        });
//
//        ImageLoaderManager.getInstance(context).displayImage();
//    }
    }
}
