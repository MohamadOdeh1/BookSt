package com.example.bookst;

import android.graphics.Bitmap;

public class VipBook {
     boolean isVip;
     Bitmap bt;

    public VipBook(boolean isVip) {
        this.isVip = isVip;
    }
    public VipBook(Bitmap bt) {
        this.bt = bt;
    }

    public VipBook() {
    }

    public Bitmap getBt() {
        return bt;
    }

    public void setBt(Bitmap bt) {
        this.bt = bt;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }
}
