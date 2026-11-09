package com.example.zamawianiepizzy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderSummary extends AppCompatActivity {

    private TextView tvResult;
    private Button btnShare, btnConfirm, btnEdit;

    public static final String EXTRA_ADRESS = "com.example.zamawianiepizzy.ADRESS";
    public static final String EXTRA_PHONE = "com.example.zamawianiepizzy.PHONE";
    public static final String EXTRA_TYPE = "com.example.zamawianiepizzy.TYPE";
    public static final String EXTRA_CUSTOM = "com.example.zamawianiepizzy.CUSTOM";
    public static final String EXTRA_SIZE = "com.example.zamawianiepizzy.SIZE";
    public static final String EXTRA_AMOUNT = "com.example.zamawianiepizzy.AMOUNT";

    Intent intent = getIntent();
    private String adressString = intent.getStringExtra(EXTRA_ADRESS);
    private int amount = intent.getIntExtra(EXTRA_AMOUNT, 0);
    private String customString = intent.getStringExtra(EXTRA_CUSTOM);
    private String phoneString = intent.getStringExtra(EXTRA_PHONE);
    private String typeString = intent.getStringExtra(EXTRA_TYPE);
    private String sizeString = intent.getStringExtra(EXTRA_SIZE);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_summary);

        tvResult = findViewById(R.id.tvResult);
        btnShare = findViewById(R.id.btnShare);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnEdit = findViewById(R.id.btnEdit);






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.summary), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}