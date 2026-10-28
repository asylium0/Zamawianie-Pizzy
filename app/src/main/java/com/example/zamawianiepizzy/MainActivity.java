package com.example.zamawianiepizzy;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private TextView tvLabelAmount, tvResult;
    private EditText etAdress, etPhone;
    private CheckBox cbSalami, cbMushrooms, cbCrustCheese;
    private RadioButton rbMedium, rbLarge, rbXXL;
    private SeekBar sbAmount;
    private Spinner spType;

    private String sbAmountProgress;

    private LinearLayout layoutType;
    private Button btnSubmit;
    private RadioGroup rgSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etAdress = findViewById(R.id.etAdress);
        etPhone = findViewById(R.id.etPhone);
        cbSalami = findViewById(R.id.cbSalami);
        cbMushrooms = findViewById(R.id.cbMushrooms);
        cbCrustCheese = findViewById(R.id.cbCrustCheese);
        rbMedium = findViewById(R.id.rbMedium);
        rbLarge = findViewById(R.id.rbLarge);
        rbXXL = findViewById(R.id.rbXXL);
        sbAmount = findViewById(R.id.sbAmount);
        spType = findViewById(R.id.spType);
        tvLabelAmount = findViewById(R.id.tvLabelAmount);
        tvResult = findViewById(R.id.tvResult);
        layoutType = findViewById(R.id.layoutType);
        btnSubmit = findViewById(R.id.btnSubmit);
        rgSize = findViewById(R.id.rgSize);


        setUpTypeSpinner();
        setUpListeners();
        sbAmountProgress = String.valueOf(sbAmount.getProgress());
        tvLabelAmount.setText(getString(R.string.tv_label_amount, sbAmountProgress));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void setUpTypeSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);
    }


    private void setUpListeners(){
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == spType.getCount()-1){
                    layoutType.setVisibility(View.VISIBLE);
                }else{
                    layoutType.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {   }
        });

        sbAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sbAmountProgress = String.valueOf(sbAmount.getProgress());
                tvLabelAmount.setText(getString(R.string.tv_label_amount, sbAmountProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {   }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {    }
        });

        btnSubmit.setOnClickListener(v -> askForConfirmation());
    }
    

    private void askForConfirmation(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_title)
                .setMessage(R.string.confirm_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> validate())
                .setNegativeButton(R.string.no, null)
                .show();


    }

    private void validate(){
        String adressString = etAdress.getText().toString();
        if(adressString.isEmpty()){
            etAdress.setError(getString(R.string.error_empty));
            etAdress.requestFocus();
            return;
        }
        
        String phoneString = etPhone.getText().toString();
        if (!Patterns.PHONE.matcher(phoneString).matches()){
            etPhone.setError(getString(R.string.error_wrong_phone));
            etPhone.requestFocus();
            return;
        }
        
        if (spType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.error_type, Toast.LENGTH_SHORT).show();
            return;
        }
        
        if(rgSize.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, R.string.error_size, Toast.LENGTH_SHORT).show();
            return;
        }

        if(layoutType.getVisibility() == View.VISIBLE && !cbSalami.isChecked() && !cbMushrooms.isChecked() && !cbCrustCheese.isChecked()){
            Toast.makeText(this, R.string.error_custom, Toast.LENGTH_SHORT).show();
            return;
        }

    submit();
    }
    private void submit(){
        Snackbar.make(findViewById(R.id.main), R.string.sent, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> tvResult.setText(""))
                .show();


        createResult();
    }
    
    private void createResult(){

        RadioButton chosenSize = findViewById(rgSize.getCheckedRadioButtonId());
        String sizeString = chosenSize.getText().toString();

        String customOrderString = "";
        if(layoutType.getVisibility() == View.VISIBLE){
            customOrderString = getCustomOrder();
        }

        String typeString = spType.getSelectedItem().toString();

        String adressString = etAdress.getText().toString();

        String phoneString = etPhone.getText().toString();



        tvResult.setText(getString(R.string.tv_result_format, adressString, phoneString, typeString, customOrderString, sizeString, sbAmountProgress));
    }


    private String getCustomOrder() {
        StringBuilder checkboxString = new StringBuilder();
        CheckBox[] checkBoxes = {cbCrustCheese, cbMushrooms, cbSalami};
        for (CheckBox i : checkBoxes){
            if(i.isChecked()){
                checkboxString.append(", ").append(i.getText().toString());
            }
        }
        return getString(R.string.tv_toppings_format, checkboxString.toString());
    }

    


}