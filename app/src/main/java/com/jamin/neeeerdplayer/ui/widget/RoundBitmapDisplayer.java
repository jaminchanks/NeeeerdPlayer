package com.jamin.neeeerdplayer.ui.widget;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer.CircleDrawable;
/**
 * Created With Android Studio
 * User @47
 * Date 2014-07-27
 * Time 20:55
 * 显示原型图片的ImageLoader使用的显示器
 *
 */
public class RoundBitmapDisplayer implements BitmapDisplayer {

    protected  final int margin ;

    public RoundBitmapDisplayer() {
        this(0);
    }

    public RoundBitmapDisplayer(int margin) {
        this.margin = margin;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }

        imageAware.setImageDrawable(new CircleDrawable(bitmap, null, margin));
    }


}