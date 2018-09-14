APP_STL := stlport_static
APP_ABI := armeabi-v7a arm64-v8a x86

APP_PROJECT_PATH := $(shell pwd)
APP_BUILD_SCRIPT := $(APP_PROJECT_PATH)/Android.mk
APP_MODULES := android_openssl