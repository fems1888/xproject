package com.qbao.xproject.app.manager;

/**
 * @author Created by jackieyao on 2018/9/21 下午4:27.
 */

public class RxBusManager {
    public static class EventSpeedFactor{
        public EventSpeedFactor(double count) {
            this.count = count;
        }

        public double getCount() {
            return count;
        }

        private double count;
    }
    public static class EventWalletRefresh{
    }

    public static class EventRefreshNextBet{
    }
}
