package com.example.hotplug.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Description:    <br>
 * Author: cxh <br>
 * Date: 2019/10/15
 */
public interface IMyService {
    @GET("www.baidu.com/{user}/first")
    Call getService(@Path("user")String  user);
}
