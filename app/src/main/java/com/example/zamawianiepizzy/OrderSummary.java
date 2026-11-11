package com.example.zamawianiepizzy;

import android.app.AlertDialog;
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

    private String result;
    private TextView tvResult;
    private Button btnShare, btnConfirm, btnEdit;
    private double cena;
    public static final String EXTRA_ADRESS = "com.example.zamawianiepizzy.ADRESS";
    public static final String EXTRA_PHONE = "com.example.zamawianiepizzy.PHONE";
    public static final String EXTRA_TYPE = "com.example.zamawianiepizzy.TYPE";
    public static final String EXTRA_CUSTOM = "com.example.zamawianiepizzy.CUSTOM";
    public static final String EXTRA_SIZE = "com.example.zamawianiepizzy.SIZE";
    public static final String EXTRA_AMOUNT = "com.example.zamawianiepizzy.AMOUNT";
    public static final String EXTRA_PRICE = "com.example.zamawianiepizzy.PRICE";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_summary);

        Intent intent = getIntent();
        String adressString = intent.getStringExtra(EXTRA_ADRESS);
        int amount = intent.getIntExtra(EXTRA_AMOUNT, 0);
        String customString = intent.getStringExtra(EXTRA_CUSTOM);
        String phoneString = intent.getStringExtra(EXTRA_PHONE);
        String typeString = intent.getStringExtra(EXTRA_TYPE);
        String sizeString = intent.getStringExtra(EXTRA_SIZE);

        tvResult = findViewById(R.id.tvResult);
        btnShare = findViewById(R.id.btnShare);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnEdit = findViewById(R.id.btnEdit);

        if(sizeString.equals(getString(R.string.rb_medium))){
            cena = amount * 15.0;
        } else if (sizeString.equals(getString(R.string.rb_large))) {
            cena = amount * 20.0;
        }else{
            cena = amount * 25.0;
        }

        btnEdit.setOnClickListener(v -> finish());
        btnConfirm.setOnClickListener(v -> askForOrderConfirmation());
        btnShare.setOnClickListener(v -> share());

        result = getString(R.string.tv_result_format, adressString, phoneString, typeString, customString, sizeString, String.valueOf(amount), String.valueOf(cena))
        tvResult.setText(result);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.summary), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void askForOrderConfirmation(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_title)
                .setMessage(R.string.confirm_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> confirm())
                .setNegativeButton(R.string.no, null)
                .show();
    }


    private void confirm(){
        Intent result = new Intent();
        result.putExtra(EXTRA_PRICE, cena);
        setResult(RESULT_OK, result);
        finish();
    }

    private void share(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, result);
        startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
    }

}