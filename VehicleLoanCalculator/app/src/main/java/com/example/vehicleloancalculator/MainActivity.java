package com.example.vehicleloancalculator; // <-- replace with your package

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etVehiclePrice, etDownPayment, etLoanPeriod, etInterestRate;
    Button btnCalculate;
    TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar setup (important)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find views
        etVehiclePrice = findViewById(R.id.etVehiclePrice);
        etDownPayment = findViewById(R.id.etDownPayment);
        etLoanPeriod = findViewById(R.id.etLoanPeriod);
        etInterestRate = findViewById(R.id.etInterestRate);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResults = findViewById(R.id.tvResults);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateLoan();
            }
        });
    }

    private void calculateLoan() {
        String sPrice = etVehiclePrice.getText().toString().trim();
        String sDown = etDownPayment.getText().toString().trim();
        String sYears = etLoanPeriod.getText().toString().trim();
        String sRate = etInterestRate.getText().toString().trim();

        if (sPrice.isEmpty() || sDown.isEmpty() || sYears.isEmpty() || sRate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double vehiclePrice = Double.parseDouble(sPrice);
            double downPayment = Double.parseDouble(sDown);
            int years = Integer.parseInt(sYears);
            double interestRate = Double.parseDouble(sRate);

            if (vehiclePrice < 0 || downPayment < 0 || years <= 0 || interestRate < 0) {
                Toast.makeText(this, "Please enter valid positive numbers", Toast.LENGTH_SHORT).show();
                return;
            }
            if (downPayment > vehiclePrice) {
                Toast.makeText(this, "Down payment cannot exceed vehicle price", Toast.LENGTH_SHORT).show();
                return;
            }

            double loanAmount = vehiclePrice - downPayment;
            double totalInterest = loanAmount * (interestRate / 100.0) * years;
            double totalPayment = loanAmount + totalInterest;
            double monthlyPayment = totalPayment / (years * 12.0);

            String res = "";
            res += String.format(Locale.getDefault(), "Loan Amount: RM %.2f\n", loanAmount);
            res += String.format(Locale.getDefault(), "Total Interest: RM %.2f\n", totalInterest);
            res += String.format(Locale.getDefault(), "Total Payment: RM %.2f\n", totalPayment);
            res += String.format(Locale.getDefault(), "Monthly Payment: RM %.2f\n", monthlyPayment);

            tvResults.setText(res);

        } catch (NumberFormatException ex) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
        }
    }

    // Inflate the menu (main_menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Handle menu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            etVehiclePrice.setText("");
            etDownPayment.setText("");
            etLoanPeriod.setText("");
            etInterestRate.setText("");
            tvResults.setText("");
            return true;
        } else if (id == R.id.action_about) {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
