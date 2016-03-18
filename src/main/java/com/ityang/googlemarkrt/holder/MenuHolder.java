package com.ityang.googlemarkrt.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.domain.UserInfo;
import com.ityang.googlemarkrt.http.HttpHelper;
import com.ityang.googlemarkrt.manager.ThreadPoolManger;
import com.ityang.googlemarkrt.protocol.UserProtocol;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2015/10/4.
 */
public class MenuHolder extends BaseHolder<UserInfo> implements View.OnClickListener {
    @ViewInject(R.id.photo_layout)
    private RelativeLayout photo_layout;

    @ViewInject(R.id.home_layout)
    private RelativeLayout home_layout;

    @ViewInject(R.id.setting_layout)
    private RelativeLayout setting_layout;

    @ViewInject(R.id.theme_layout)
    private RelativeLayout theme_layout;

    @ViewInject(R.id.scans_layout)
    private RelativeLayout scans_layout;

    @ViewInject(R.id.feedback_layout)
    private RelativeLayout feedback_layout;

    @ViewInject(R.id.updates_layout)
    private RelativeLayout updates_layout;

    @ViewInject(R.id.about_layout)
    private RelativeLayout about_layout;

    @ViewInject(R.id.exit_layout)
    private RelativeLayout exit_layout;

    @ViewInject(R.id.image_photo)
    private ImageView image_photo;
    @ViewInject(R.id.user_name)
    private TextView user_name;
    @ViewInject(R.id.user_email)
    private TextView user_email;

    @Override
    protected View initViews() {
        View view = View.inflate(UiUtils.getContext(), R.layout.menu_holder, null);
        ViewUtils.inject(this, view);
        photo_layout.setOnClickListener(this);
        home_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);
        theme_layout.setOnClickListener(this);
        scans_layout.setOnClickListener(this);
        feedback_layout.setOnClickListener(this);
        updates_layout.setOnClickListener(this);
        about_layout.setOnClickListener(this);
        exit_layout.setOnClickListener(this);

        return view;
    }

    @Override
    protected void refreshViews(UserInfo data) {
        //user_name.setText(data.getName());
        user_name.setText("杨杰");
        //user_email.setText(data.getEmail());
        user_email.setText("1147185600@qq.com");
        String url = data.getUrl();//image/user.png
        bitmapUtils.display(image_photo, HttpHelper.URL + "image?name=" + url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_layout:
                Toast.makeText(UiUtils.getContext(), "登录", Toast.LENGTH_SHORT).show();
                ThreadPoolManger.getInstance().creatLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        UserProtocol protocol = new UserProtocol();
                        final UserInfo load = protocol.load(0);
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showViews(load); // 当调用该方法的时候  就会调用refreshView
                            }
                        });
                    }
                });
                break;
            case R.id.home_layout:
                Toast.makeText(UiUtils.getContext(), "首页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_layout:
                Toast.makeText(UiUtils.getContext(), "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_layout:
                Toast.makeText(UiUtils.getContext(), "主题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.scans_layout:
                Toast.makeText(UiUtils.getContext(), "安装包管理", Toast.LENGTH_SHORT).show();
                break;
            case R.id.feedback_layout:
                Toast.makeText(UiUtils.getContext(), "反馈", Toast.LENGTH_SHORT).show();
                break;
            case R.id.updates_layout:
                Toast.makeText(UiUtils.getContext(), "检查更新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_layout:
                Toast.makeText(UiUtils.getContext(), "关于", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_layout:
                Toast.makeText(UiUtils.getContext(), "退出", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
