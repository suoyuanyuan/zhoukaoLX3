package test.bawei.zhoukaolx3.util;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import test.bawei.zhoukaolx3.bean.News;

/**
 * Created by 索园 on 2017/11/18.
 */

public interface ApiService {
    /**
     * http://result.eolinker.com/iYXEPGn4e9c6dafce6e5cdd23287d2bb136ee7e9194d3e9?uri=vedio
     */
    @GET("/iYXEPGn4e9c6dafce6e5cdd23287d2bb136ee7e9194d3e9?uri=vedio")
    Observable<News> getNews(@Query("uri")String vedio);
}
