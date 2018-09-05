package com.ifreecomm.imageloadsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ifreecomm.imageloadsample.adapter.ImageAdapter;

public class ListActivity extends AppCompatActivity {
    String url1 = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
    String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536579501&di=8ccda86a336fdc477c3264166d6c4b26&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0117e2571b8b246ac72538120dd8a4.jpg%401280w_1l_2o_100sh.jpg";
    String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535984824428&di=e1106da63d84c6fdfc7f066a4b84454e&imgtype=0&src=http%3A%2F%2Fimg1.juimg.com%2F170404%2F355859-1F40403355571.jpg";
    String url4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535984824607&di=6941c34123f6ec37fea340ca0b671520&imgtype=0&src=http%3A%2F%2Fpic31.photophoto.cn%2F20140525%2F0036036876026611_b.jpg";
    String url5 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535984824606&di=12b8ca14ad7e6c0c6f83f7242e5ce127&imgtype=0&src=http%3A%2F%2Fimg05.tooopen.com%2Fimages%2F20151227%2Ftooopen_sy_152882831976.jpg";
    String url6 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/%20u=2237129721,1649030900&fm=23&gp=0.jpg";
    String url7 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535984824599&di=106b88c1d7a8c6a7a28d6a5f38557e2c&imgtype=0&src=http%3A%2F%2Fpic.nipic.com%2F2008-03-13%2F200831311313566_2.jpg";
    String url8 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535984824598&di=3a3e2f08e8b49c457bcf6f20cca9dfc5&imgtype=0&src=http%3A%2F%2Ffile06.16sucai.com%2F2016%2F0424%2Fba7d81982d739c90bdb068cd55a84dd7.jpg";
    String url9 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535984824598&di=4c876ae55035ebdb0040f75b362e5e07&imgtype=0&src=http%3A%2F%2Fpic22.nipic.com%2F20120730%2F7582250_193402307000_2.jpg";
    String[] mDatas = {url1, url2, url3, url4, url5, url6, url7, url8, url9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
    }

    private void initView() {

        ListView listView = findViewById(R.id.listView);
        ImageAdapter adapter = new ImageAdapter(this,mDatas,R.layout.list_item_layout);
        listView.setAdapter(adapter);
    }
}
