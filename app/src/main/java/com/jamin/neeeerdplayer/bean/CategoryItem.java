package com.jamin.neeeerdplayer.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jamin.neeeerdplayer.ui.base.Category;

/**
 * Created by jamin on 16-3-10.
 */
public class CategoryItem {
    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public void setIcon(Context context, int resId) {
        this.icon = BitmapFactory.decodeResource(context.getResources(), resId);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public CategoryItem(Context context, Category category) {
        setIcon(context, category.getCategoryIconResId());
        setTitle(category.toString(context));
        setCategory(category);
    }


    private Bitmap icon;
    private String title;
    private Category category;

}
