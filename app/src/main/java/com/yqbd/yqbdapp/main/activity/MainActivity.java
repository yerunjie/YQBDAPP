package com.yqbd.yqbdapp.main.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lemon.support.util.AsyncBitmapLoader;
import com.yqbd.yqbdapp.R;
import com.yqbd.yqbdapp.base.YQBDBaseActivity;
import com.yqbd.yqbdapp.base.YQBDBaseCallBack;
import com.yqbd.yqbdapp.base.YQBDBaseResponse;
import com.yqbd.yqbdapp.main.fragments.UserCenterFragment;
import com.yqbd.yqbdapp.bean.UserInfoBean;
import com.yqbd.yqbdapp.main.api.MainApi;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

public class MainActivity extends YQBDBaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private ArrayList<Fragment> fragments;
    private CircleImageView civ_title_bar_head_portrait, civ_nav_head_portrait;
    private TextView tv_title, tv_nick_name;
    private Toolbar tb_title_bar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private UserInfoBean userInfoBean;

    private static final int FRAGMENT_POSITION_INDEX = 2;
    private static final int FRAGMENT_POSITION_TASK = 1;
    private static final int FRAGMENT_POSITION_MYTASK = 0;

    private static final int FRAGMENT_SIZE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //setTitleString("123");
        //showBackIcon();
        //showTitleBar();
        super.hideTitleBar();

        addRequest(getService(MainApi.class).getUserInfo(), new YQBDBaseCallBack<YQBDBaseResponse<UserInfoBean>>() {
            @Override
            public void onSuccess200(YQBDBaseResponse<UserInfoBean> o) {
                userInfoBean = o.obj;
            }
        });
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tb_title_bar = (Toolbar) findViewById(R.id.tb_title_bar);
//       civ_title_bar_head_portrait = (CircleImageView) tb_title_bar.findViewById(R.id.title_bar_head_portrait);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                civ_nav_head_portrait = (CircleImageView) navigationView.findViewById(R.id.civ_nav_head_portrait);
                tv_nick_name = (TextView) navigationView.findViewById(R.id.tv_nick_name);
                if (userInfoBean != null) {
                    setNavigationViewValue();
                }
                //todo
            }
        });

//        civ_title_bar_head_portrait.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        bottomNavigationBar
                //.addItem(new BottomNavigationItem(R.drawable.location, "定位").setActiveColorResource(R.color.orange))
                //.addItem(new BottomNavigationItem(R.drawable.share, "发现").setActiveColorResource(R.color.blue))
                //.addItem(new BottomNavigationItem(R.drawable.search, "搜索").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.share, "我的").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.share, "test").setActiveColorResource(R.color.blue))
                .setFirstSelectedPosition(0)
                .initialise();
        fragments = getFragments();
        bottomNavigationBar.setTabSelectedListener(this);
        onTabSelected(0);
    }

    private void setNavigationViewValue() {
        tv_nick_name.setText(userInfoBean.getNickName());
        Bitmap bitmap = bitmapLoader.loadBitmap(civ_nav_head_portrait, userInfoBean.getHeadPortrait(), new AsyncBitmapLoader.ImageCallBack() {
            @Override
            public void imageLoad(ImageView imageView, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
                //item.picture = bitmap;
            }
        });
        if (bitmap != null) {
            civ_nav_head_portrait.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = null;
                switch (position) {
                    //case 0:
                        //fragment = MapFragment.newInstance();
                        //setTitleString("定位");
                        //break;
                    /*case 1:
                        fragment = DiscoverFragment.newInstance();
                        setTitle("发现");
                        break;
                    case 2:
                        fragment = SearchFragment.newInstance();
                        setTitle("搜索");
                        break;*/
                    case FRAGMENT_POSITION_MYTASK:
                        fragment = UserCenterFragment.newInstance();
                        break;
                    default:
                        break;
                }
                if (fragment != null){
                    refreshFragment(position,fragment);
                }
//                fragments.remove(position);
//                fragments.add(position, fragment);
//                if (fragment.isAdded()) {
//                    ft.replace(R.id.layFrame, fragment);
//                } else {
//                    ft.add(R.id.layFrame, fragment);
//                }
//                ft.commitAllowingStateLoss();
            }
        }

    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(UserCenterFragment.newInstance());
        return fragments;
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void setTitleString(String title) {
        tv_title.setText(title);
    }

    @Override
    protected void setTitleId(int id) {
        tv_title.setText(id);
    }

    @Override
    protected void showTitleBar() {
        tb_title_bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hideTitleBar() {
        tb_title_bar.setVisibility(View.GONE);
    }

    private <T extends Fragment> void refreshFragment(int position, T fragment){
        if (position < 0 || position >= FRAGMENT_SIZE) return;

        if (fragments.size() == FRAGMENT_SIZE){
            fragments.remove(position);
        }

        fragments.add(position, fragment);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fragment.isAdded()) {
            ft.replace(R.id.layFrame, fragment);
        } else {
            ft.add(R.id.layFrame, fragment);
        }
        ft.commitAllowingStateLoss();
    }
}
