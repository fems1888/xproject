package com.qbao.xproject.app.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.qbao.xproject.app.entity.BillResponseEntity;
import com.qbao.xproject.app.entity.BillSection;
import com.qbao.xproject.app.http.ExceptionHandle;
import com.qbao.xproject.app.http.XProjectService;
import com.qbao.xproject.app.utility.Rx2Subscriber;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;

/**
 * @author Created by jackieyao on 2018/9/20 下午8:01.
 */

public class BillViewModel extends BaseViewModel {
    public BillViewModel(@NonNull Application application, String tag) {
        super(application, tag);
    }

    public Observable<List<BillResponseEntity>> findBillList(int mPage, int size) {
        return Observable.create(e -> {
            XProjectService.newInstance().findBillList(mPage, size).subscribe(new Rx2Subscriber<List<BillResponseEntity>>(application, TAG) {
                @Override
                public void onError(ExceptionHandle.ResponseThrowable responseThrowable) {
                    e.onError(responseThrowable);
                }

                @Override
                public void onNext(List<BillResponseEntity> value) {
                    e.onNext(value);
                }
            });
        });
    }

    public ObservableSource<List<BillSection>> mapList(List<BillResponseEntity> billResponseEntities) {
        return Observable.create(new ObservableOnSubscribe<List<BillSection>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BillSection>> e) throws Exception {
                List<BillSection> list = new ArrayList<>();
                for (BillResponseEntity entity:billResponseEntities){
                    BillSection sectionHeader = new BillSection(true,entity.getTime());
                    list.add(sectionHeader);
                    for (BillResponseEntity.BillContentResponse response:entity.getContent()){
                        BillSection section = new BillSection(response);
                        list.add(section);
                    }
                }
                e.onNext(list);
            }
        });
    }
}
