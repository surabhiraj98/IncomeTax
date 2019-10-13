package com.example.incometax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //taking two variable
    int taxableIncome;
    boolean display = false;
//get the input through onClick
    public void onClick(View v){
        EditText income = (EditText)findViewById(R.id.income);
        EditText investments = (EditText)findViewById(R.id.investments);
        EditText infraInvestments = (EditText)findViewById(R.id.infraInvestment);
        EditText housingInterest = (EditText)findViewById(R.id.housingInterest);
        EditText medicalPremium = (EditText)findViewById(R.id.medicalPremium);
//reset button
        Button reset =(Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText income = (EditText)findViewById(R.id.income);
                EditText investments = (EditText)findViewById(R.id.investments);
                EditText infraInvestments = (EditText)findViewById(R.id.infraInvestment);
                EditText housingInterest = (EditText)findViewById(R.id.housingInterest);
                EditText medicalPremium = (EditText)findViewById(R.id.medicalPremium);

                income.setText("");
                investments.setText("");
                infraInvestments.setText("");
                housingInterest.setText("");
                medicalPremium.setText("");
            }
        });

//string input
        String in = income.getText().toString();
        String invest = investments.getText().toString();
        String infra = infraInvestments.getText().toString();
        String house = housingInterest.getText().toString();
        String medical = medicalPremium.getText().toString();

        if (TextUtils.isEmpty(in)) {
            income.setError("Enter Income");
            income.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(invest)) {
            investments.setError("This field can't be empty");
            investments.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(infra)) {
            infraInvestments.setError("This field can't be empty");
            infraInvestments.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(house)) {
            housingInterest.setError("Enter Interest Rate");
            housingInterest.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(medical)) {
            medicalPremium.setError("This field can't be empty");
            medicalPremium.requestFocus();
            return;
        }

        int income_i, investments_i, infraInvestments_i;
        int housingInterest_i, medicalPremium_i;
        try{
            income_i = Integer.parseInt(income.getText().toString());
        }
        catch (NumberFormatException e) {
            income_i = 0;
        }
        try {
            investments_i = Integer.parseInt(investments.getText().toString());
        } catch (NumberFormatException e) {
            investments_i = 0;
        }
        try {
            infraInvestments_i = Integer.parseInt(infraInvestments.getText().toString());
        } catch (NumberFormatException e) {
            infraInvestments_i = 0;
        }
        try {
            housingInterest_i = Integer.parseInt(housingInterest.getText().toString());
        } catch (NumberFormatException e) {
            housingInterest_i = 0;
        }
        try {
            medicalPremium_i =  Integer.parseInt(medicalPremium.getText().toString());
        } catch (NumberFormatException e) {
            medicalPremium_i = 0;
        }


        //Checking for Zero values----------
        if (income_i == 0 || investments_i ==0 || infraInvestments_i == 0 || housingInterest_i == 0 || medicalPremium_i == 0) {
            Toast.makeText(this, "Zero's are not allowed", Toast.LENGTH_LONG).show();
        } else {

            double totaltax = calculateTax(income_i, investments_i, infraInvestments_i, housingInterest_i, medicalPremium_i);
            Intent intent = new Intent(this, Main2Activity.class);
            if(display) {
                intent.putExtra("totaltax", totaltax + " ");
                intent.putExtra("taxable", taxableIncome + " ");
                this.startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"YOU'RE NOT A TAX PAYER YET!",Toast.LENGTH_LONG).show();
            }
        }
    }

    public double calculateTax(int income, int investments, int infraInvestments, int housingInterest, int medicalPremium){
        investments = Math.max(0, Math.min(investments, 100000));
        infraInvestments = Math.max(0, Math.min(infraInvestments, 20000));
        housingInterest =  Math.max(0, Math.min(housingInterest, 15000));
        medicalPremium =   Math.max(0, Math.min(medicalPremium, 35000));
        taxableIncome = income - (investments+infraInvestments+housingInterest+medicalPremium);
        double taxOnThisSlab;
        if (taxableIncome < 0){
            display = false;
            return 0;
        }
        else if (taxableIncome < 160000) {
            display = false;
            return 0;
        }
        else if (taxableIncome < 500000){
            taxOnThisSlab = 0.1 * (taxableIncome - 160000);
            display = true;
            return taxOnThisSlab;
        }
        else if (taxableIncome < 800000){
            taxOnThisSlab = 0.2 * (taxableIncome - 500000);
            display = true;
            return taxOnThisSlab + 34000;
        }
        else {
            taxOnThisSlab = 0.3 * (taxableIncome - 800000);
            display = true;
            return taxOnThisSlab + 94000;
        }
    }
}

