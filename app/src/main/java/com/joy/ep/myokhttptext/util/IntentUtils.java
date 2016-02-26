package com.joy.ep.myokhttptext.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * author   Joy
 * Date:  2016/2/26.
 * version:  V1.0
 * Description:
 */
public class IntentUtils {

    public static void call(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
    }

    public static void callDial(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    public static void sendSms(Context context, String phoneNumber,
                               String content) {
        Uri uri = Uri.parse("smsto:"
                + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    public static void intUrl(Context context, String url) {
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        context.startActivity(it);
    }

}
