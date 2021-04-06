package com.example.cashfreepaymentgateway.retrofit;


import com.example.cashfreepaymentgateway.request.RequestCashFree;
import com.example.cashfreepaymentgateway.responce.ResponseCashFree;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Padmakar on 29/6/18.
 */

public interface ApiServices {
   @Headers({
            "Content-Type: application/json"
//            "x-client-id: <YOUR_APP_ID",
//            "x-client-secret: <YOUR_SECRET_KEY>"
    })
    @POST("order")
    Call<ResponseCashFree> cashFreeResponse(@Header("x-client-id") String x_client_id,
                                            @Header("x-client-secret") String x_client_secret,
                                            @Body RequestCashFree obj);


}

