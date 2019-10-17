package com.example.hotplug.retrofit;

import android.support.annotation.Nullable;

import com.example.hotplug.util.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.internal.platform.Platform;

/**
 * Description:    <br>
 * Author: cxh <br>
 * Date: 2019/10/16
 */
public class RetrofitTest {
    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
                new InvocationHandler() {
                    private final Platform platform = Platform.get();
                    private final Object[] emptyArgs = new Object[0];

                    @Override public @Nullable
                    Object invoke(Object proxy, Method method,
                                  @Nullable Object[] args) throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        return null;
                    }
                });
    }

}
