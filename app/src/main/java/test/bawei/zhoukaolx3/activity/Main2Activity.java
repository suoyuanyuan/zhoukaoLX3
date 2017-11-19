package test.bawei.zhoukaolx3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.bawei.zhoukaolx3.bean.News;
import test.bawei.zhoukaolx3.R;
import test.bawei.zhoukaolx3.View2.NewsView;
import test.bawei.zhoukaolx3.adapter.Adapter;
import test.bawei.zhoukaolx3.model.NewsModel;
import test.bawei.zhoukaolx3.util.MyImage;
import zlc.season.rxdownload.DownloadStatus;
import zlc.season.rxdownload.RxDownload;

public class Main2Activity extends AppCompatActivity implements NewsView {

    private Banner banner;
    private RecyclerView rlv;
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       /* banner = (Banner) findViewById(R.id.banner);
        List<String> list=new ArrayList<>();
        list.add("http://120.27.23.105//images//ad//0.jpg");
        list.add("http://120.27.23.105//images//ad//1.jpg");
        list.add("http://120.27.23.105//images//ad//2.jpg");
        list.add("http://120.27.23.105//images//ad//3.jpg");
        banner.setImageLoader(new MyImage());
        banner.setImages(list);
        banner.setDelayTime(3000);
        banner.start();*/
        rlv = (RecyclerView) findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        NewsModel.NewsPresenter presenter=new NewsModel.NewsPresenter(this);
        presenter.relevance("vedio");
    }


    @Override
    public void newsVie(final News news) {
        List<News.DataBean> data = news.getData();
        List<String> images=new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String s = data.get(0).getImage_url();
            images.add(s);
        }
        banner = (Banner) findViewById(R.id.banner);
        banner.setImageLoader(new MyImage());
        banner.setImages(images);
        banner.start();
        adapter = new Adapter(this,data);
        rlv.setAdapter(adapter);
        adapter.setOnItemListener(new Adapter.OnItemListener() {
            @Override
            public void onItemClick(News.DataBean newsBean) {
                Subscription subscription = RxDownload.getInstance()
                        .maxThread(3)
                        .download(newsBean.getVedio_url(), "video.mp4", null)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<DownloadStatus>() {
                            @Override
                            public void onCompleted() {
                                Toast.makeText(Main2Activity.this,"下载完成",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(DownloadStatus downloadStatus) {

                            }
                        });
            }
        });
    }
}
