//
// Created by xiulu.zhang on 2018/1/17.
//

#ifndef ANDROID_OPENSSL_AESENCRYPTUTIL_H
#define ANDROID_OPENSSL_AESENCRYPTUTIL_H

#include   <stdio.h>
#include   <stdlib.h>
#include   <time.h>
#include   <string.h>
#include <jni.h>
#include <assert.h>
#include <include/openssl/conf.h>
#include <openssl/evp.h>
#include <openssl/err.h>
#include <android/log.h>
#include "base64.h"

#define TAG "Qbao_jni"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__)

bool isUTF8(const void* pBuffer, long size);

int decrypt(unsigned char *ciphertext, int ciphertext_len, unsigned char *key, unsigned char *iv,
            unsigned char *plaintext, int keyLength);

int encrypt(unsigned char *plaintext, int plaintext_len, unsigned char *key, unsigned char *iv,
            unsigned char *ciphertext, int keyLength);

int encrypt_aes_cfb(unsigned char *plaintext, int plaintext_len, unsigned char *key,
                    unsigned char *iv, unsigned char *ciphertext, int keyLength);

int decrypt_aes_cfb(unsigned char *ciphertext, int ciphertext_len, unsigned char *key,
                    unsigned char *iv, unsigned char *plaintext, int keyLength);

#endif //ANDROID_OPENSSL_AESENCRYPTUTIL_H

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_getAesEncrypt(JNIEnv *env, jclass type, jstring plainText_,
                                                    jstring key_);



extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_getAesDecrypt(JNIEnv *env, jclass type, jstring cipherText_,
                                                    jstring key_);

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcEncryptBase64(JNIEnv *env, jclass type,
                                                          jstring plainText_, jstring key_);

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcDecryptBase64(JNIEnv *env, jclass type,
                                                          jstring cipherText_, jstring key_);

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcDecryptBase64Default(JNIEnv *env, jclass type,
                                                          jstring cipherText_);
extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcEncryptBase64Default(JNIEnv *env, jclass type,
                                                                 jstring plainText_);

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCfbEncryptBase64(JNIEnv *env, jclass type,
                                                          jstring plainText_);

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCfbDecryptBase64(JNIEnv *env, jclass type,
                                                          jstring cipherText_);


extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_formatString(JNIEnv *env, jclass type, jstring arg1_,
                                                   jstring arg2_, jstring arg3_, jstring arg4_);


extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcEncryptBase64H5(JNIEnv *env, jclass type,
                                                            jstring plainText_);
extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcDecryptBase65H5(JNIEnv *env, jclass type,
                                                            jstring cipherText_);


extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_formatPayString(JNIEnv *env, jclass type, jstring arg1_,
                                                      jstring arg2_, jstring arg3_, jstring arg4_);