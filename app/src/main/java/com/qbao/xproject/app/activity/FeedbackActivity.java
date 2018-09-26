package com.qbao.xproject.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbao.xproject.app.R;
import com.qbao.xproject.app.base.BaseRxActivity;
import com.qbao.xproject.app.databinding.ActivityFeedbackBinding;
import com.qbao.xproject.app.interf.StatusBarContentColor;
import com.qbao.xproject.app.manager.AccountManager;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.RxSchedulers;
import com.qbao.xproject.app.utility.StatusBarUtils;
import com.qbao.xproject.app.viewmodel.FeedbackViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author Created by jackieyao on 2018/9/17 下午1:55
 */

public class FeedbackActivity extends BaseRxActivity<ActivityFeedbackBinding> {
    private String mPhotoPath;
    private FeedbackViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setToolBarTitle(getString(R.string.feedback));
        StatusBarUtils.setWindowStatusBarColor(activity,R.color.white, StatusBarContentColor.GRAY);
    }
    public static void go(Context context){
        Intent intent= new Intent(context,FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(bindingView.imageAddPhone).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        pickPhoto();
                    }
                });
        RxView.clicks(bindingView.imagePhone).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        picturePreview(mPhotoPath,true);
                    }
                });

        RxView.clicks(bindingView.imageClose).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        picturePreview(mPhotoPath,false);
                    }
                });

        RxView.clicks(bindingView.imageDelete).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mPhotoPath = null;
                        bindingView.relativePhone.setVisibility(View.GONE);
                    }
                });
        RxView.clicks(bindingView.buttonCommit).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        commitFeedback();
                    }
                });
    }

    private void commitFeedback() {
        if (TextUtils.isEmpty(bindingView.editContent.getText().toString())){
            Toast.makeText(activity, R.string.input_feedback_content,Toast.LENGTH_LONG).show();
            return;
        }

        if (viewModel == null){
            viewModel = new FeedbackViewModel(activity.getApplication(),TAG);
        }

        viewModel.upLoadImage(mPhotoPath)
                .flatMap(new Function<String, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(String attachment) throws Exception {
                        String attach = attachment.equals("null")?null:attachment;
                        return viewModel.commitFeedback(AccountManager.getInstance().getAccountEntity().getPhone(),bindingView.editContent.getText().toString(),attach);
                    }
                })
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                           Toast.makeText(activity,R.string.commit_success,Toast.LENGTH_LONG).show();
                           finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        try {
                            Toast.makeText(activity,throwable.getMessage(),Toast.LENGTH_LONG).show();
                        }catch (Exception e){e.printStackTrace();}
                    }
                });
    }

    private void pickPhoto() {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage(),new String[]{getString(R.string.camera_roll),getString(R.string.pic_take_picture),getString(R.string.pic_cancel),getString(R.string.pic_preview),getString(R.string.pic_choose),getString(R.string.pic_completed),getString(R.string.pic_crop)})
                .imageSpanCount(4)
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)
                .isCamera(true)
                .sizeMultiplier(0.5f)
                .enableCrop(false)
                .compress(true)
//                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)
                .glideOverride(200, 200)
                .withAspectRatio(1, 1)
                .freeStyleCropEnabled(true)
                .circleDimmedLayer(false)
                .showCropFrame(false)
                .showCropGrid(false)
                .previewEggs(true)
                .rotateEnabled(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.size() > 0) {
                        String mImageSeletedPath = null;
                        LocalMedia media = selectList.get(0);
                        if (media.isCut() && !media.isCompressed()) {
                            mImageSeletedPath = media.getCutPath();
                        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            mImageSeletedPath = media.getCompressPath();
                        } else {
                            // 原图
                            mImageSeletedPath = media.getPath();
                        }
                        bindingView.relativePhone.setVisibility(View.VISIBLE);
                        mPhotoPath = mImageSeletedPath;
                        Glide.with(activity.getApplication()).load(mImageSeletedPath).thumbnail(0.4f).into(bindingView.imagePhone);

                        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
//                        PictureFileUtils.deleteCacheDirFile(MainActivity.this);
                    }
                    break;
                    default:

                        break;
            }
        }
    }


    private void picturePreview(String path, boolean fullScreen) {
        ScaleAnimation sAnima = new ScaleAnimation(0, 1, 0, 1);
        sAnima.setDuration(200);
        bindingView.linearContent.setVisibility(fullScreen ? View.GONE : View.VISIBLE);
        bindingView.scrollViewImage.setVisibility(fullScreen ? View.VISIBLE : View.GONE);
        if (fullScreen) {
            Bitmap bitmap = CommonUtility.getDiskBitmap(path);
            DisplayMetrics localDisplayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
            float density = getResources().getDisplayMetrics().density;
            Bitmap newBitmap = CommonUtility.getBitmap(bitmap, localDisplayMetrics.widthPixels / density, localDisplayMetrics.heightPixels / density);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) bindingView.imageSrc.getLayoutParams();
            params.width = (int) (newBitmap.getWidth() * density);
            params.height = (int) (newBitmap.getHeight() * density);
            params.gravity = Gravity.CENTER;
            bindingView.imageSrc.setLayoutParams(params);
            bindingView.imageSrc.setImageBitmap(newBitmap);
            bindingView.imageSrc.setAnimation(sAnima);
        }
    }

    @Override
    public void onBackPressed() {
        if (bindingView.scrollViewImage.getVisibility() == View.VISIBLE){
            picturePreview(mPhotoPath,false);
        }else {
            super.onBackPressed();
        }
    }
}
