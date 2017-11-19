package test.bawei.zhoukaolx3.model;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.bawei.zhoukaolx3.bean.News;
import test.bawei.zhoukaolx3.View2.NewsSuccess;
import test.bawei.zhoukaolx3.View2.NewsView;
import test.bawei.zhoukaolx3.util.Api;
import test.bawei.zhoukaolx3.util.ApiService;

/**
 * Created by 索园 on 2017/11/18.
 */

public class NewsModel {
    public void getNewsNet(final NewsSuccess newsSuccess, String index){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient build=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Api.NEWS)
                .client(build)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService=retrofit.create(ApiService.class);
        Observable<News> observable=apiService.getNews(index);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(News news) {
                        if (newsSuccess!=null){
                            newsSuccess.newSuccess(news);
                        }
                    }
                });
    }

    /**
     * Created by 索园 on 2017/11/18.
     */

    public static class NewsPresenter implements NewsSuccess {
        private NewsView newsView;
        private final NewsModel model;

        public NewsPresenter(NewsView newsView) {
            this.newsView = newsView;
            model = new NewsModel();
        }
        public void relevance(String index){
            model.getNewsNet(this,index);
        }
        @Override
        public void newSuccess(News news) {
            newsView.newsVie(news);
        }
    }
}
