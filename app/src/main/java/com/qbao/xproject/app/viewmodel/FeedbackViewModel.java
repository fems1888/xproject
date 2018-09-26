package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.qbao.xproject.app.entity.UploadImageResponseEntity;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.utility.CommonUtility;
import com.qbao.xproject.app.utility.Rx2Subscriber;
import com.qbao.xproject.app.utility.RxSchedulers;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Created by jackieyao on 2018/9/25 下午2:53.
 */

public class FeedbackViewModel extends BaseViewModel {
    private static final int TYPE_FEEDBACK = 1;

    public FeedbackViewModel(@NonNull Application application, String tag) {
        super(application, tag);
    }

    public Observable<Object> commitFeedback(String phone, String content, String attachment) {
        return Observable.create(e -> {
            XProjectService.newInstance().feedBack(phone, content, attachment)
                    .subscribe(new Rx2Subscriber<Object>(application, TAG) {
                        @Override
                        public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                            e.onError(responseThrowable);
                        }

                        @Override
                        public void onNext(Object value) {
                            e.onNext(value);
                        }
                    });
        });
    }

    public Observable<String> upLoadImage(String mPhotoPath) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (TextUtils.isEmpty(mPhotoPath)) {
                    e.onNext("null");
                } else {
                    File file = new File(mPhotoPath);

//        2、创建RequestBody，其中`multipart/form-data`为编码类型
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);

//        3、创建`MultipartBody.Part`，其中需要注意第一个参数`file`需要与服务器对应,也就是`键`
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    XProjectService.newInstance().setEncrypt(false)
                            .uploadImage(TYPE_FEEDBACK, part)
                            .subscribe(new Rx2Subscriber<String>(application, TAG) {
                                @Override
                                public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                                    responseThrowable.printStackTrace();
                                    e.onError(responseThrowable);
                                }

                                @Override
                                public void onNext(String header) {
                                    CommonUtility.DebugLog.log("upload Image = " + header);
                                    e.onNext(header);
                                }
                            });
                }
            }
        });
    }
}
