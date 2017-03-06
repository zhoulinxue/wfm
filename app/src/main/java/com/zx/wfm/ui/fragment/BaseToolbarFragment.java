package com.zx.wfm.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.zx.wfm.R;
import com.zx.wfm.utils.UKutils;

/**
 * Created by aspsine on 15/9/8.
 */
public class BaseToolbarFragment extends BaseFragment {
    private SearchView searchView=null;

    public interface ToggleDrawerCallBack {
        void openDrawer();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            final ActionBar actionbar = getSupportActionBar();
            actionbar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((ToggleDrawerCallBack) getActivity()).openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ActionBar getSupportActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    protected void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        //用MenuItem的`getActionView()`方法获取`android:actionViewClass`对应的实例,这里是`android.widget.SearchView`
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSubmitButtonEnabled(true);//是否显示确认搜索按钮
//        searchView.setIconifiedByDefault(true);//设置展开后图标的样式,这里只有两种,一种图标在搜索框外,一种在搜索框内
//        searchView.setIconified(false);//设置
//        searchView.clearFocus();//清除焦点
        /*
        这里是重点,SearchView并没有提供样式的修改方法,所以只能
        1.用获取到的实例调用getContext()方法,返回当前view的上下文
        2.调用getResources()方法,获取该view的资源实例(Return a Resources instance)
        3.调用getIdentifier()方法,获取相同名字的ID,(Return a resource identifier for the given resource name)
        4.通过findViewById()获取该ID的实例,然后就可以做相应的操作了
        */
//        int search_mag_icon_id = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
//        ImageView search_mag_icon = (ImageView)searchView.findViewById(search_mag_icon_id);//获取搜索图标
//        search_mag_icon.setImageResource(R.mipmap.ic_action_search);//图标都是用src的、

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                //当点击搜索按钮,输入法搜索按钮,会触发这个方法.在这里做相应的搜索事件,query为用户输入的值
                //当输入框为空或者""时,此方法没有被调用
                Log.e("wzj", "搜索文本-->"+s);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UKutils.getSearchWords(s);
                    }
                }).start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //当输入的文字发生变化的时候,会触发这个方法.在这里做匹配提示的操作等
                Log.e("wzj", "当输入的文字发生变化的时候,会触发这个方法-->"+s);
                return false;
            }
        });
//        String str=searchView.getQuery().toString();
    }

    /**
     * 该方法是用来加载菜单布局的
     * @param menu
     * @return
     */


}
