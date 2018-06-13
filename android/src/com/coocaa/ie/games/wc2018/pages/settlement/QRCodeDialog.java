package com.coocaa.ie.games.wc2018.pages.settlement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coocaa.ie.R;
import com.coocaa.ie.core.android.UI;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by lu on 2018/id_wc2018_penalty_settlement_title_number_2/id_wc2018_penalty_settlement_title_number_8.
 */

public class QRCodeDialog extends Dialog {
    private String qrValue;
    private Bitmap qrBitmap;
    private Context mContext;

    public QRCodeDialog(@NonNull Context context, String qrValue) {
        super(context, R.style.dialog_translucent_notitlebar_fullscreen);
        this.mContext = context;
        setQrValue(qrValue);

    }

    public void setQrValue(String qrValue) {
        this.qrValue = qrValue;
    }

    @Override
    protected void onStart() {

        super.onStart();
        initUI();
    }

    private void initUI() {
        clear();
        FrameLayout content = new FrameLayout(mContext);
        content.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        content.setBackgroundColor(mContext.getResources().getColor(R.color.wc_qr_dialog_bg));

        LinearLayout main = new LinearLayout(mContext);
        FrameLayout.LayoutParams mParams = new FrameLayout.LayoutParams(UI.div(555), UI.div(624));
        mParams.setMargins(UI.div(682), UI.div(227), 0, 0);
        main.setLayoutParams(mParams);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setBackgroundColor(Color.WHITE);


        RelativeLayout tipsLayout = new RelativeLayout(mContext);
        LinearLayout.LayoutParams tlParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tipsLayout.setLayoutParams(tlParams);
        tipsLayout.setBackgroundColor(Color.WHITE);

        TextView tips = new TextView(mContext);
        RelativeLayout.LayoutParams tParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tParams.setMargins(0, UI.div(40), 0, 0);
        tParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        tips.setTextSize(UI.dpi(42));
        tips.setText(mContext.getResources().getText(R.string.wc2018_qr_dialog_phone_qr_title));
        tips.setTextColor(Color.BLACK);
        tips.setBackgroundColor(Color.WHITE);
        tipsLayout.addView(tips, tParams);

        main.addView(tipsLayout);

//        qrBitmap = createBarcode(qrValue, UI.div(454), UI.div(454));
        qrBitmap = createBarcode(qrValue, UI.div(555), UI.div(517));
        ImageView view = new ImageView(mContext);
        view.setImageBitmap(qrBitmap);
        LinearLayout.LayoutParams vParams = new LinearLayout.LayoutParams(UI.div(555), UI.div(517));
        vParams.topMargin = 0;
//        vParams.setMargins(UI.div(50), UI.div(40), 0, 0);
        main.addView(view, vParams);

        content.addView(main);
        setContentView(content);
    }

    @Override
    protected void onStop() {
        super.onStop();
        clear();
    }

    private synchronized void clear() {
        if (qrBitmap != null && !qrBitmap.isRecycled()) {
            qrBitmap.recycle();
            qrBitmap = null;
        }
    }

    private static Bitmap createBarcode(String str, int width, int height) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();

            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, width, height, hints);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
