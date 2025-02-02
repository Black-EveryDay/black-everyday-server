package com.ed.payment.infrastructure.out.pg.toss.retrofit;

import com.ed.payment.application.port.out.pg.dtos.CancelPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.ConfirmPaymentRequest;
import com.ed.payment.infrastructure.out.pg.toss.dtos.TossPaymentCanceledResponse;
import com.ed.payment.infrastructure.out.pg.toss.dtos.TossPaymentDoneResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TossPaymentClient {

    @POST("confirm")
    Call<TossPaymentDoneResponse> confirmPayment(@Body ConfirmPaymentRequest request);

    @POST("{paymentKey}/cancel")
    Call<TossPaymentCanceledResponse> cancelPayment(@Path("paymentKey") String paymentKey, @Body CancelPaymentRequest request);
}