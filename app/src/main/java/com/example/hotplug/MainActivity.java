package com.example.hotplug;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hotplug.retrofit.IMyService;
import com.example.hotplug.util.LogUtils;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        rxjava();
//        okhttp();
        retrofit();
    }

    private void retrofit() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IMyService iMyService = retrofit.create(IMyService.class);

        retrofit2.Call call = iMyService.getService("lisi");

        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(retrofit2.Call call, retrofit2.Response response) {

            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {

            }
        });
    }

    private void okhttp() {
        //1.新建OKHttpClient客户端
        OkHttpClient client = new OkHttpClient();
        //新建一个Request对象
        Request request = new Request.Builder()
                .url("https://github.com/square/okhttp")
                .build();
        //2.Response为OKHttp中的响应
//        Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void rxjava() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.e("onSubscribe 主线程?" + isMainThread());
            }

            @Override
            public void onNext(String data) {
                LogUtils.e("onNext 主线程?" + isMainThread());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        ObservableOnSubscribe<String> source = new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LogUtils.e("create 主线程?" + isMainThread());
                emitter.onNext("你妹的");
                Thread.sleep(20000);
                emitter.onComplete();
            }
        };
        Observable<String> observableCreate = Observable.create(source);//new ObservableCreate<T>(source)对象
        Observable<String> observableSubscribeOn = observableCreate.subscribeOn(Schedulers.newThread());//new ObservableSubscribeOn<T>(observable, scheduler)变成的这个对象
        Observable<String> observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread());//new ObservableObserveOn<T>(observableSubscribeOn, scheduler。。)
        observableObserveOn.subscribe(observer);//new ObservableSubscribeOn<T>(observable, scheduler)变成的这个对象

        //AndroidSchedulers.mainThread()：里面Schedulers封装了handler，looper，message异步消息处理机制，observer最终在主线程调用而已
        //Schedulers.newThread()；里面Schedulers封装子线程，observableCreate在该线程执行
        //链式调用：observableObserveOn--》observableSubscribeOn--》observableCreate
    }

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
