package com.coocaa.ie;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.coocaa.ie.games.wc2018.WC2018GameController;
import com.coocaa.ie.games.wc2018.launcher.WC2018LauncherActivity;

public class AndroidLauncher extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            LinearLayout root = new LinearLayout(this);
            root.setOrientation(LinearLayout.VERTICAL);
            root.addView(demoGame());
            root.addView(penaltyGame());
            root.addView(answerGame());
            setContentView(root);
        }
    }

    private Button demoGame() {
        return button("别摸我", WC2018GameController.GAME_DEMO);
    }

    private Button penaltyGame() {
        return button("点球", WC2018GameController.GAME_PENALTY);
    }

    private Button answerGame() {
        return button("答题", WC2018GameController.GAME_ANSWER);
    }

    private Button button(String name, final String value) {
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AndroidLauncher.this, WC2018LauncherActivity.class);
                intent.putExtra(WC2018LauncherActivity.EXTRA_GAME, value);
                startActivity(intent);
                finish();
            }
        });
        button.setText(name);
        return button;
    }
}
