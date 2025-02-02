package com.ed.payment.infrastructure.out.pg.toss.retrofit.interceptor;

import static com.ed.payment.libs.common.constant.HttpHeaders.IDEMPOTENCY_KEY;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.springframework.stereotype.Component;

@Component
public class IdempotencyKeyInterceptor implements AutoCloseable, Interceptor {

    private static final ThreadLocal<String> idempotencyKey = new ThreadLocal<>();

    public void setIdempotencyKey(String key) {
        idempotencyKey.set(key);
    }

    @Override
    public void close() {
        idempotencyKey.remove();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
      return chain.proceed(chain.request().newBuilder()
          .addHeader(IDEMPOTENCY_KEY, idempotencyKey.get())
          .build());
    }
}
