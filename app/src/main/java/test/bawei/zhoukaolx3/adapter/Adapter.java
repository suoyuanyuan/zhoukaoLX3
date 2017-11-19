package test.bawei.zhoukaolx3.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import test.bawei.zhoukaolx3.bean.News;
import test.bawei.zhoukaolx3.R;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

/**
 * Created by 索园 on 2017/11/18.
 */

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<News.DataBean> list;
    private OnItemListener onItemListener;
    private News.DataBean dataBean;

    public Adapter(Context context, List<News.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    public interface OnItemListener{
        public void onItemClick(News.DataBean newsBean);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_item, parent, false);
        MyViewHold hold=new MyViewHold(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHold myViewHold= (MyViewHold) holder;
        dataBean = list.get(position);
        myViewHold.show_tv.setText(list.get(position).getContent());
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        AndroidMediaController controller=new AndroidMediaController(context,false);
        myViewHold.ijkVideoView.setMediaController(controller);
        String url = list.get(position).getVedio_url();
        myViewHold.ijkVideoView.setVideoURI(Uri.parse(url));
        myViewHold.ijkVideoView.start();
        myViewHold.show_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListener.onItemClick(dataBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHold extends RecyclerView.ViewHolder{

        private final TextView show_tv;
        private final IjkVideoView ijkVideoView;
        private final LinearLayout show_ll;

        public MyViewHold(View itemView) {
            super(itemView);
            show_tv = itemView.findViewById(R.id.show_tv);
            ijkVideoView = itemView.findViewById(R.id.show_ijkPlayer);
            show_ll = itemView.findViewById(R.id.show_ll);
        }
    }
}
