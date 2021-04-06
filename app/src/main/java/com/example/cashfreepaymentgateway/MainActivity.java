package com.example.cashfreepaymentgateway;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cashfree.pg.CFPaymentService;
import com.example.cashfreepaymentgateway.request.RequestCashFree;
import com.example.cashfreepaymentgateway.responce.ResponseCashFree;
import com.example.cashfreepaymentgateway.retrofit.ApiServices;
import com.example.cashfreepaymentgateway.retrofit.LoggerUtil;
import com.example.cashfreepaymentgateway.retrofit.ServiceGenerator;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "POPO";
    public ApiServices apiServices, createServiceUtilityV2;
    @BindView(R.id.orderId)
    EditText orderIdan;
    @BindView(R.id.orderAmount)
    EditText orderAmountcf;
    @BindView(R.id.orderCurrency)
    EditText orderCurrency;
    @BindView(R.id.startPaymentGateway)
    Button startPaymentGateway;
    CFPaymentService cfPaymentService;
    boolean doubleBackToExitPressedOnce = false;

    public static String randomString(int len) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        apiServices = ServiceGenerator.createService(ApiServices.class);
        startPaymentGateway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!orderIdan.getText().toString().isEmpty()) {
                    if (!orderAmountcf.getText().toString().isEmpty()) {
                        if (!orderCurrency.getText().toString().isEmpty()) {

                            RequestCashFree requestCashFree = new RequestCashFree();
                            requestCashFree.setOrderId(orderIdan.getText().toString());
                            requestCashFree.setOrderAmount(orderAmountcf.getText().toString());
                            requestCashFree.setOrderCurrency(orderCurrency.getText().toString());
                            Call<ResponseCashFree> call = apiServices.cashFreeResponse(getResources().getString(R.string.test_app_id), getResources().getString(R.string.test_secret_key), requestCashFree);
                            LoggerUtil.logItem(requestCashFree);
                            call.enqueue(new Callback<ResponseCashFree>() {
                                @Override
                                public void onResponse(Call<ResponseCashFree> call, Response<ResponseCashFree> response) {
                                    LoggerUtil.logItem(response.body());
                                    String stage = "TEST";
                                    assert response.body() != null;
                                    if (response.body().getStatus().equals("OK")) {
                                        String token = response.body().getCftoken();
                                       // cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
                                       // cfPaymentService.setOrientation(0);
                                       // cfPaymentService.setConfirmOnExit(true);

                                        // cfPaymentService.doPayment(MainActivity.this, getInputParams(), token, stage, "#784BD2", "#FFFFFF", false);
                                        CFPaymentService.getCFPaymentServiceInstance().doPayment(MainActivity.this, getInputParams(), token, stage, "#784BD2", "#FFFFFF", false);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Token not generated!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<ResponseCashFree> call, Throwable t) {
                                    LoggerUtil.logItem(t.getMessage());
                                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            orderCurrency.setError("Enter Order Currency!");
                        }
                    } else {
                        orderAmountcf.setError("Enter Order Amount!");
                    }
                } else {
                    orderIdan.setError("Enter Order Id!");
                }
            }
        });
    }

    private Map<String, String> getInputParams() {

        /*
         * appId will be available to you at CashFree Dashboard. This is a unique
         * identifier for your app. Please replace this appId with your appId.
         * Also, as explained below you will need to change your appId to prod
         * credentials before publishing your app.
         */
        String appId = getResources().getString(R.string.test_app_id);
        String orderId = orderIdan.getText().toString();
        String orderAmount = orderAmountcf.getText().toString();
        String orderNote = "Test Order";
        String customerName = "John Doe";
        String customerPhone = "9900012345";
        String customerEmail = "test@gmail.com";

        Map<String, String> params = new HashMap<>();

        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_ORDER_CURRENCY, "INR");
        return params;
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderIdan.setText(randomString(6));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                        Log.d(TAG, "Bundel" + " : " + bundle.toString());
                    }
                }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // cfPaymentService.onBackPressed(this);
        //cfPaymentService.getConfirmOnExit();
    }
}