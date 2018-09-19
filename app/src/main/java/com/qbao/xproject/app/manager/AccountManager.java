package com.aether.coder.qbao.manager;

import android.content.Context;

import com.aether.coder.qbao.QbaoApplication;
import com.aether.coder.qbao.model.db.LocalAddress;
import com.aether.coder.qbao.model.db.LocalAddressDao;
import com.aether.coder.qbao.model.entity.Account;
import com.aether.coder.qbao.utils.Constants;
import com.qbao.library.utility.CommonUtility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.aether.coder.qbao.utils.Constants.ACCOUNT;

/**
 * Created by hubert on 2017/9/5.
 */

public class AccountManager {
    public static Context mContext;
    private String mAccountString = "";
    private Account mAccount;

    public static AccountManager getInstance(Context context) {
        mContext = context.getApplicationContext();
        return AccountHolder.INSTANCE;
    }

    public static AccountManager getInstance() {
        mContext = QbaoApplication.getInstance().getApplicationContext();
        return AccountHolder.INSTANCE;
    }

    public static class AccountHolder {
        private static final AccountManager INSTANCE = new AccountManager();
    }


    public void saveAccount(Object object) {
        if (object instanceof JSONObject || object instanceof String) {
            CommonUtility.SharedPreferencesUtility.put(mContext,
                    ACCOUNT, object.toString());
        } else {
            CommonUtility.DebugLog.e("Hubert", "saveAccountJson" + CommonUtility.JSONObjectUtility.GSON.toJson(object));
            CommonUtility.SharedPreferencesUtility.put(mContext,
                    ACCOUNT, CommonUtility.JSONObjectUtility.GSON.toJson(object));
        }
    }

    public Account getAccountEntity() {

        String localAccount = CommonUtility.SharedPreferencesUtility.getSharedPreferences(mContext).getString(ACCOUNT, "");
        if (CommonUtility.Utility.isNull(mAccountString) || !mAccountString.equals(localAccount)) {
            JSONObject user = null;
            try {
                user = new JSONObject(localAccount);
                mAccount = CommonUtility.JSONObjectUtility.convertJSONObject2Obj(user, Account.class);
            } catch (Exception e) {
                if (CommonUtility.Utility.isNull(mAccount)) {
                    mAccount = new Account();
                }
                return mAccount;
            }
            mAccountString = localAccount;
        }
        if (CommonUtility.Utility.isNull(mAccount)) {
            mAccount = new Account();
        }
        return mAccount;
    }

    public void cleanAccount() {
        CommonUtility.SharedPreferencesUtility.put(mContext,
                ACCOUNT, "");
        CommonUtility.SharedPreferencesUtility.put(mContext,
                Constants.ACCOUNT_CONTACT_VERSION, "");
        CommonUtility.SharedPreferencesUtility.put(mContext,
                Constants.ACCOUNT_GROUP_VERSION, "");
    }

    public boolean isNewType() {
        if (getAccountEntity() != null) {
            return getAccountEntity().isNewType();
        }
        return false;
    }

    /**
     * 加载用户信息
     *
     * @return
     */
    public Observable<Boolean> loadQtumWallet() {
        return Observable.create(e -> {
            if (!CommonUtility.Utility.isNull(getAccountEntity()) && !CommonUtility.Utility.isNull(getAccountEntity().getAccountNo()) && getQtumAddressList().size() > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });

    }

    /**
     * 获取用户所有 Qtum地址集合
     *
     * @return
     */
    public List<String> getQtumAddressList() {
        List<String> addressList = new ArrayList<>();
        List<LocalAddress> localAddresses = QbaoApplication.getInstance().getDaoSession().getLocalAddressDao().loadAll();
        if (localAddresses.size() == 10) {
            for (LocalAddress localAddress : localAddresses) {
                addressList.add(localAddress.getAddress());
            }
        }
        return addressList;
    }

    /**
     * 获取当前用户Qtum地址
     *
     * @return
     */
    public String getCurrentQtumAddress() {
        List<LocalAddress> localAddresses = QbaoApplication.getInstance().getDaoSession().getLocalAddressDao().queryBuilder()
                .where(LocalAddressDao.Properties.Checked.eq(true)).list();
        String address = "";
        if (localAddresses.size() > 0) {
            address = localAddresses.get(0).getAddress();
        } else {
            List<LocalAddress> addressList = QbaoApplication.getInstance().getDaoSession().getLocalAddressDao().loadAll();
            if (addressList.size() > 0) {
                address = addressList.get(0).getAddress();
                addressList.get(0).setChecked(true);
                updateCurrentQtumAddress(address);
            }
        }
        CommonUtility.DebugLog.i("当前地址 = " + address);
        return address;
    }
//
//    /**
//     * 根据position 获取 Qtum地址
//     *
//     * @return
//     */
//    public String getQtumAddressByPosition(int position) {
//        List<LocalAddress> localAddresses = QbaoApplication.getInstance().getDaoSession().getLocalAddressDao().loadAll();
//        String address = "";
//        if (localAddresses.size() == 10) {
//            address = localAddresses.get(position).getAddress();
//        }
//        return address;
//    }


    /**
     * 获取当前用户主Qtum地址 position
     *
     * @return
     */
    public int getCurrentQtumAddressPosition() {
        List<LocalAddress> localAddresses = QbaoApplication.getInstance().getDaoSession().getLocalAddressDao().queryBuilder()
                .where(LocalAddressDao.Properties.Checked.eq(true)).list();
        int position = 0;
        if (localAddresses.size() > 0) {
            position = Integer.parseInt(CommonUtility.formatString(localAddresses.get(0).getId()));
        }
        return position;
    }

    /**
     * 根据 Qtum 地址获取  position
     *
     * @param address
     * @return
     */
    public int getPositionByAddress(String address) {
        List<LocalAddress> localAddresses = QbaoApplication.getInstance().getDaoSession().getLocalAddressDao().queryBuilder()
                .where(LocalAddressDao.Properties.Address.eq(address)).list();
        int position = 0;
        if (localAddresses.size() > 0) {
            position = Integer.parseInt(CommonUtility.formatString(localAddresses.get(0).getId()));
        }
        return position;

    }

    public void updateCurrentQtumAddress(String address) {
        if (!CommonUtility.Utility.isNull(address)) {
            List<LocalAddress> localAddresses = QbaoApplication.getInstance().getDaoSession().getLocalAddressDao().loadAll();
            if (localAddresses.size() > 0) {
                for (LocalAddress localAddress : localAddresses) {
                    if (address.equals(localAddress.getAddress())) {
                        localAddress.setChecked(true);
                    } else {
                        localAddress.setChecked(false);
                    }
                    QbaoApplication.getInstance().getDaoSession().getLocalAddressDao().update(localAddress);
                }
            }
        }
    }
}
