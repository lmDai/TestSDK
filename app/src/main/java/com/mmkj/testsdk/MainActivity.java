package com.mmkj.testsdk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.game.sdk.MeisdkManager;
import com.game.sdk.domain.CustomPayParam;
import com.game.sdk.domain.LoginErrorMsg;
import com.game.sdk.domain.LogincallBack;
import com.game.sdk.domain.PaymentCallbackInfo;
import com.game.sdk.domain.PaymentErrorMsg;
import com.game.sdk.domain.RealNameCallBack;
import com.game.sdk.domain.RoleInfo;
import com.game.sdk.domain.SubmitRoleInfoCallBack;
import com.game.sdk.listener.OnExitListener;
import com.game.sdk.listener.OnInitSdkListener;
import com.game.sdk.listener.OnLoginListener;
import com.game.sdk.listener.OnLogoutListener;
import com.game.sdk.listener.OnPaymentListener;
import com.game.sdk.listener.OnRealNameAuthListener;
import com.game.sdk.listener.OnSwitchAccountListener;
import com.game.sdk.pay.CommonJsForWeb;
import com.game.sdk.pay.IPayListener;
import com.game.sdk.util.WebViewUtils;


public class MainActivity extends Activity implements IPayListener {
    private Button btn_login;
    private WebView webView;

    public MainActivity() {
    }
    private void initView() {
        webView = findViewById(R.id.iv_web_back);
        CommonJsForWeb commonJsForWeb = new CommonJsForWeb(this, "", this);
        webView.loadUrl("https://www.androidweekly.io/");
        this.findViewById(R.id.btn_params).setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                MeisdkManager.getInstance().switchAccount();
            }
        });
        this.btn_login = this.findViewById(R.id.btn_login);
        this.btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MeisdkManager.getInstance().showLogin();
            }
        });
        this.findViewById(R.id.btn_real_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeisdkManager.getInstance().showRealNameAuth();
            }
        });
        this.findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                String thirdId = "1231jhkaskd23" + Math.random();//第三方订单id（易接订单id）
                String gameName = "梦幻西游";//游戏名称
                String payment = "0.01";//实付金额
                CustomPayParam localCustomPayParam = new CustomPayParam();
                localCustomPayParam.setCp_order_id(thirdId);
                localCustomPayParam.setProduct_price(Float.valueOf(payment));
                localCustomPayParam.setProduct_count(Double.valueOf(payment));
                localCustomPayParam.setProduct_id(thirdId);
                localCustomPayParam.setProduct_name(gameName);
                localCustomPayParam.setProduct_desc(gameName);
                localCustomPayParam.setExchange_rate(Integer.valueOf(1));
                localCustomPayParam.setCurrency_name("砖石");
                localCustomPayParam.setExt(thirdId);
                RoleInfo roleInfo = new RoleInfo();
                roleInfo.setFight_value("1");
                roleInfo.setParty_name("1");
                roleInfo.setRole_balence(1f);
                roleInfo.setRole_id("1");
                roleInfo.setRole_level(10);
                roleInfo.setRole_name("1");
                roleInfo.setRole_type(1);
                roleInfo.setRole_vip(1);
                roleInfo.setRolelevel_ctime("2020-09-10");
                roleInfo.setRolelevel_mtime("2020-09-10");
                roleInfo.setServer_id("1");
                roleInfo.setServer_name("sssss");
                localCustomPayParam.setRoleinfo(roleInfo);
                MeisdkManager.getInstance().showPay(localCustomPayParam);
            }
        });
        this.findViewById(R.id.btn_login_out).setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                MeisdkManager.getInstance().logout();

            }
        });
        this.findViewById(R.id.btn_recycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeisdkManager.getInstance().exit();
            }
        });
        this.findViewById(R.id.btn_uprole).setOnClickListener(new View.OnClickListener() {
            public void onClick(View var1) {
                RoleInfo roleInfo = new RoleInfo();
                MeisdkManager.getInstance().setRoleInfo(roleInfo, new SubmitRoleInfoCallBack() {
                    @Override
                    public void submitFail(String msg) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void submitSuccess() {
                        Toast.makeText(MainActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void sdkInit() {
        //初始化通知
        MeisdkManager.getInstance().addInitSdkListener(new OnInitSdkListener() {
            @Override
            public void initError(int code, String msg) {
                //初始化失败
                showToast("initError" + code + msg);
            }

            @Override
            public void initSuccess(int code, String msg) {
                //初始化成功
                showToast("initSuccess" + code + msg);
                webView.loadUrl("https://www.androidweekly.io/android-dev-weekly-issue-295/");
            }
        });
        //登录通知
        MeisdkManager.getInstance().addLoginListener(new OnLoginListener() {
            @Override
            public void loginError(LoginErrorMsg call) {
                showToast("loginError" + call.code + call.msg);
            }

            @Override
            public void loginSuccess(LogincallBack logincallBack) {
                showToast("loginSuccess" + logincallBack.user_token + "mem_id:" + logincallBack.mem_id);

                webView.loadUrl("https://www.androidweekly.io/android-dev-weekly-issue-296/");
            }
        });
        //注销账号通知
        MeisdkManager.getInstance().addLogoutListener(new OnLogoutListener() {
            @Override
            public void logoutError(int code, String msg) {
                showToast("logoutError" + code + msg);
            }

            @Override
            public void logoutSuccess(int code, String msg) {
                showToast("logoutSuccess" + code + msg);
                webView.loadUrl("https://www.androidweekly.io/android-dev-weekly-issue-298/");
            }
        });
        //切换账号通知
        MeisdkManager.getInstance().addSwitchAccountListener(new OnSwitchAccountListener() {

            @Override
            public void onFailed(int code, String msg) {
                showToast("SwitchAccountonFailed" + code + msg);
            }

            @Override
            public void onSuccess(int code, String msg) {
                showToast("SwitchAccountonSuccess" + code + msg);
                webView.loadUrl("https://www.androidweekly.io/android-dev-weekly-issue-297/");
            }
        });
        //退出通知
        MeisdkManager.getInstance().addExitListener(new OnExitListener() {
            @Override
            public void exitError(int code, String msg) {
                showToast("exitError" + code + msg);
            }

            @Override
            public void exitSuccess(int code, String msg) {
                showToast("exitSuccess" + code + msg);
                webView.loadUrl("https://www.androidweekly.io/");
            }
        });

        MeisdkManager.getInstance().addPayListener(new OnPaymentListener() {
            @Override
            public void paymentError(PaymentErrorMsg errorMsg) {

            }

            @Override
            public void paymentSuccess(PaymentCallbackInfo callbackInfo) {

            }
        });
        MeisdkManager.getInstance().addRealNameAuthListener(new OnRealNameAuthListener() {
            @Override
            public void authError(int code, String msg) {
                showToast("authError" + code + msg);
            }

            @Override
            public void authSuccess(int code, RealNameCallBack callBack) {
                showToast("authSuccess" + code + callBack.msg + callBack.authStatus);
                webView.loadUrl("https://www.androidweekly.io/android-dev-weekly-issue-294/");
            }
        });
        MeisdkManager.getInstance().initSdk(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            this.setContentView(R.layout.activity_main);

        this.initView();
        this.sdkInit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MeisdkManager.getInstance().recycle();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void payFail(String var1, float var2, boolean var3, String var4) {

    }

    @Override
    public void paySuccess(String var1, float var2) {

    }

    public boolean onKeyUp(int var1, KeyEvent var2) {
        boolean var3;
        if (var1 == 4) {
            MeisdkManager.getInstance().exit();
            var3 = true;
        } else {
            var3 = super.onKeyUp(var1, var2);
        }
        return var3;
    }
}
