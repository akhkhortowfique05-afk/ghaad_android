package com.ghaad.shield;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#0b0f19"));
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(60, 60, 60, 60);

        TextView title = new TextView(this);
        title.setText("PROJECT GHAAD\nPermanent Mobile Shield");
        title.setTextColor(Color.WHITE);
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        Button btnAdmin = new Button(this);
        btnAdmin.setText("Step 1: Enable Anti-Uninstall Lock");
        btnAdmin.setOnClickListener(v -> {
            ComponentName comp = new ComponentName(this, GhaadAdminReceiver.class);
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, comp);
            startActivity(intent);
        });
        layout.addView(btnAdmin);

        Button btnGuard = new Button(this);
        btnGuard.setText("Step 2: Activate Permanent AI Guard");
        btnGuard.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        });
        layout.addView(btnGuard);

        setContentView(layout);
    }
}
