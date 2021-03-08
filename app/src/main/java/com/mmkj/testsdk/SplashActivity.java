package com.mmkj.testsdk;

import android.content.Intent;

import com.game.sdk.ui.activity.MeiyxSplashActivity;

/**
 * @package: com.mmkj.testsdk
 * @user:xhkj
 * @date:3/5/21
 * @description:
 **/
public class SplashActivity extends MeiyxSplashActivity {
    @Override
    public void onSplashEnd() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
