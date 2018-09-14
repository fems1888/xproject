//
// Created by xiulu.zhang on 2018/1/17.
//

#include "AesEncryptUtil.h"

#define U_AES_CBC_KEY "16m@yn@NMX7l7V^P"
#define U_AES_CFB_KEY "16m@yn@NMX7l7V^P"
#define U_AES_CFB_H5  "tuekd;$%ee^&*fh6"


void hexify(unsigned char *obuf, const unsigned char *ibuf, int len)
{
    unsigned char l, h;
    while (len != 0)
    {
        h = (*ibuf) / 16;
        l = (*ibuf) % 16;
        if( h < 10 )
            *obuf++ = '0' + h;
        else
            *obuf++ = 'a' + h - 10;
        if( l < 10 )
            *obuf++ = '0' + l;
        else
            *obuf++ = 'a' + l - 10;

        ++ibuf;
        len--;
    }
}

int unhexify(unsigned char *obuf, const char *ibuf)
{
    unsigned char c, c2;
    int len = strlen(ibuf) / 2;
    assert(!(strlen(ibuf) %1)); // must be even number of bytes
    while (*ibuf != 0)
    {
        c = *ibuf++;
        if( c >= '0' && c <= '9' )
            c -= '0';
        else if( c >= 'a' && c <= 'f' )
            c -= 'a' - 10;
        else if( c >= 'A' && c <= 'F' )
            c -= 'A' - 10;
        else
            assert( 0 );
        c2 = *ibuf++;
        if( c2 >= '0' && c2 <= '9' )
            c2 -= '0';
        else if( c2 >= 'a' && c2 <= 'f' )
            c2 -= 'a' - 10;
        else if( c2 >= 'A' && c2 <= 'F' )
            c2 -= 'A' - 10;
        else
            assert( 0 );
        *obuf++ = ( c << 4 ) | c2;
    }
    return len;
}

bool isUTF8(const void* pBuffer, long size)
{
    bool IsUTF8 = true;
    unsigned char* start = (unsigned char*)pBuffer;
    unsigned char* end = (unsigned char*)pBuffer + size;
    while (start < end)
    {
        if (*start < 0x80) // (10000000): value less then 0x80 ASCII char
        {
            start++;
        }
        else if (*start < (0xC0)) // (11000000): between 0x80 and 0xC0 UTF-8 char
        {
            IsUTF8 = false;
            break;
        }
        else if (*start < (0xE0)) // (11100000): 2 bytes UTF-8 char
        {
            if (start >= end - 1)
                break;
            if ((start[1] & (0xC0)) != 0x80)
            {
                IsUTF8 = false;
                break;
            }
            start += 2;
        }
        else if (*start < (0xF0)) // (11110000): 3 bytes UTF-8 char
        {
            if (start >= end - 2)
                break;
            if ((start[1] & (0xC0)) != 0x80 || (start[2] & (0xC0)) != 0x80)
            {
                IsUTF8 = false;
                break;
            }
            start += 3;
        }
        else
        {
            IsUTF8 = false;
            break;
        }
    }
    return IsUTF8;
}


/*
 *
 *
 */

int encrypt(unsigned char *plaintext, int plaintext_len, unsigned char *key,
  unsigned char *iv, unsigned char *ciphertext, int keyLength)
{
  EVP_CIPHER_CTX *ctx;

  int len;

  int ciphertext_len;

  /* Create and initialise the context */
  if(!(ctx = EVP_CIPHER_CTX_new())){
  	 //handleErrors();
  	}

  /* Initialise the encryption operation. IMPORTANT - ensure you use a key
   * and IV size appropriate for your cipher
   * In this example we are using 256 bit AES (i.e. a 256 bit key). The
   * IV size for *most* modes is the same as the block size. For AES this
   * is 128 bits */
  if(keyLength == 16){
        if(1 != EVP_EncryptInit_ex(ctx, EVP_aes_128_cbc(), NULL, key, iv)){
          //handleErrors();
        }
  }else if(keyLength == 32){
      if(1 != EVP_EncryptInit_ex(ctx, EVP_aes_256_cbc(), NULL, key, iv)){
        //handleErrors();
      }
  }

  /* Provide the message to be encrypted, and obtain the encrypted output.
   * EVP_EncryptUpdate can be called multiple times if necessary
   */
  if(1 != EVP_EncryptUpdate(ctx, ciphertext, &len, plaintext, plaintext_len)){
    //handleErrors();
  }
  ciphertext_len = len;

  /* Finalise the encryption. Further ciphertext bytes may be written at
   * this stage.
   */
  if(1 != EVP_EncryptFinal_ex(ctx, ciphertext + len, &len)){
  	//handleErrors();
  }
  ciphertext_len += len;

  /* Clean up */
  EVP_CIPHER_CTX_free(ctx);

  return ciphertext_len;
}

int decrypt(unsigned char *ciphertext, int ciphertext_len, unsigned char *key,
  unsigned char *iv, unsigned char *plaintext, int keyLength)
{
  LOGD("-----------------decrypt begin -------------------------");
  EVP_CIPHER_CTX *ctx;

  int len;

  int plaintext_len;

  /* Create and initialise the context */
  if(!(ctx = EVP_CIPHER_CTX_new())){
  	//handleErrors();
  }

  /* Initialise the decryption operation. IMPORTANT - ensure you use a key
   * and IV size appropriate for your cipher
   * In this example we are using 256 bit AES (i.e. a 256 bit key). The
   * IV size for *most* modes is the same as the block size. For AES this
   * is 128 bits */
  LOGE("strlen key:%d", keyLength);
  if(16 == keyLength){
      if(1 != EVP_DecryptInit_ex(ctx, EVP_aes_128_cbc(), NULL, key, iv)){
          //handleErrors();
        }
  }else if(keyLength == 32){
       if(1 != EVP_DecryptInit_ex(ctx, EVP_aes_256_cbc(), NULL, key, iv)){
            //handleErrors();
        }
  }

  /* Provide the message to be decrypted, and obtain the plaintext output.
   * EVP_DecryptUpdate can be called multiple times if necessary
   */
  if(1 != EVP_DecryptUpdate(ctx, plaintext, &len, ciphertext, ciphertext_len)){
    //handleErrors();
  }
  plaintext_len = len;

  /* Finalise the decryption. Further plaintext bytes may be written at
   * this stage.
   */
  if(1 != EVP_DecryptFinal_ex(ctx, plaintext + len, &len)){
  	//handleErrors();
  }
  plaintext_len += len;

  /* Clean up */
  EVP_CIPHER_CTX_free(ctx);
//  LOGE("decrypt result:%s", plaintext);
  LOGD("-----------------decrypt end  -------------------------");
  return plaintext_len;
}

/*
 *
 *
 */

int encrypt_aes_cfb(unsigned char *plaintext, int plaintext_len, unsigned char *key,
            unsigned char *iv, unsigned char *ciphertext, int keyLength)
{
    EVP_CIPHER_CTX *ctx;

    int len;

    int ciphertext_len;

    /* Create and initialise the context */
    if(!(ctx = EVP_CIPHER_CTX_new())){
        //handleErrors();
    }

    /* Initialise the encryption operation. IMPORTANT - ensure you use a key
     * and IV size appropriate for your cipher
     * In this example we are using 256 bit AES (i.e. a 256 bit key). The
     * IV size for *most* modes is the same as the block size. For AES this
     * is 128 bits */
    if(keyLength == 16){
        if(1 != EVP_EncryptInit_ex(ctx, EVP_aes_128_cfb(), NULL, key, iv)){
            //handleErrors();
        }
    }else if(keyLength == 32){
        if(1 != EVP_EncryptInit_ex(ctx, EVP_aes_256_cfb(), NULL, key, iv)){
            //handleErrors();
        }
    }

    /* Provide the message to be encrypted, and obtain the encrypted output.
     * EVP_EncryptUpdate can be called multiple times if necessary
     */
    if(1 != EVP_EncryptUpdate(ctx, ciphertext, &len, plaintext, plaintext_len)){
        //handleErrors();
    }
    ciphertext_len = len;

    /* Finalise the encryption. Further ciphertext bytes may be written at
     * this stage.
     */
    if(1 != EVP_EncryptFinal_ex(ctx, ciphertext + len, &len)){
        //handleErrors();
    }
    ciphertext_len += len;

    /* Clean up */
    EVP_CIPHER_CTX_free(ctx);

    return ciphertext_len;
}

int decrypt_aes_cfb(unsigned char *ciphertext, int ciphertext_len, unsigned char *key,
            unsigned char *iv, unsigned char *plaintext, int keyLength)
{
    LOGD("-----------------decrypt begin -------------------------");
    EVP_CIPHER_CTX *ctx;

    int len;

    int plaintext_len;

    /* Create and initialise the context */
    if(!(ctx = EVP_CIPHER_CTX_new())){
        //handleErrors();
    }

    /* Initialise the decryption operation. IMPORTANT - ensure you use a key
     * and IV size appropriate for your cipher
     * In this example we are using 256 bit AES (i.e. a 256 bit key). The
     * IV size for *most* modes is the same as the block size. For AES this
     * is 128 bits */
    LOGE("strlen key:%d", keyLength);
    if(16 == keyLength){
        if(1 != EVP_DecryptInit_ex(ctx, EVP_aes_128_cfb(), NULL, key, iv)){
            //handleErrors();
        }
    }else if(keyLength == 32){
        if(1 != EVP_DecryptInit_ex(ctx, EVP_aes_256_cfb(), NULL, key, iv)){
            //handleErrors();
        }
    }

    /* Provide the message to be decrypted, and obtain the plaintext output.
     * EVP_DecryptUpdate can be called multiple times if necessary
     */
    if(1 != EVP_DecryptUpdate(ctx, plaintext, &len, ciphertext, ciphertext_len)){
        //handleErrors();
    }
    plaintext_len = len;

    /* Finalise the decryption. Further plaintext bytes may be written at
     * this stage.
     */
    if(1 != EVP_DecryptFinal_ex(ctx, plaintext + len, &len)){
        //handleErrors();
    }
    plaintext_len += len;

    /* Clean up */
    EVP_CIPHER_CTX_free(ctx);
    plaintext[plaintext_len] = '\0';
//    LOGE("decrypt result:%s", plaintext);
    LOGD("-----------------decrypt end  -------------------------");
    return plaintext_len;
}


jstring  Java_com_qbao_xproject_app_utility_AESUtil_getAesEncrypt(JNIEnv *env, jclass type, jstring plainText_,
                                                 jstring key_) {

 	LOGD("===Java_com_aether_coder_qbao_AESUtil_getAesEncrypt===");

 	if (plainText_ == NULL || key_ == NULL){
        LOGE("----input plainText_ = null or key_ = null----");
     	return NULL;
     }
    const char* str = env->GetStringUTFChars(plainText_, 0);
    const char* key = env->GetStringUTFChars(key_, 0);
    //初始化向量
    unsigned char iv[16] = { '4', 'e', '5','W', 'a', '7', '1', 'f', 'Y', 'o', 'T', '7', 'M', 'F', 'E', 'X'};

 	unsigned char plaintext[2048];
    unsigned char key_str[100];
    unsigned char dst[2048];
    int cipher_len = 0;
    memset((char*) plaintext, 0, sizeof(plaintext));
    memset(key_str, 0, 100);
    memset(dst, 0, sizeof(dst));


 	int pliant_lenth = strlen(str);
 	LOGD("****pliant_lenth=%d",pliant_lenth);
 	unhexify(key_str, key);
 	int keyLength = strlen((char *)key_str);
 	LOGD(" key_src length: %d",keyLength);
// 	LOGD(" key_src : %s",key_str);
 	memcpy(plaintext, str, pliant_lenth);
    LOGD(" plaintext : %s",plaintext);
 	cipher_len = encrypt(plaintext,pliant_lenth ,key_str,iv, dst, keyLength);

 	LOGD("****cipher_len=%d",cipher_len);

 	//将加密后的字符串，转换成hex string返回
 	char retStr[(cipher_len+1)*2];
 	char tmpStr[3];
 	memset(retStr,0x00,sizeof(retStr));
 	hexify((unsigned char *)retStr, dst, strlen((char*)dst));
 	LOGD("****retStr=%s",retStr);

 	env->ReleaseStringUTFChars(plainText_,str);
 	env->ReleaseStringUTFChars(key_,key);

 	return env->NewStringUTF(retStr);

}


jstring Java_com_qbao_xproject_app_utility_AESUtil_getAesDecrypt(JNIEnv *env, jclass type, jstring cipherText_,
                                                 jstring key_) {
     //初始化向量
    unsigned char iv[16] = { '4', 'e', '5','W', 'a', '7', '1', 'f', 'Y', 'o', 'T', '7', 'M', 'F', 'E', 'X'};

    if (cipherText_ == NULL || key_ == NULL){
        LOGE("***aesDecrypt input s = null or k = null");
     	return NULL;
    }
    //将密文转换成字符串
    const char* ciphtertext = env->GetStringUTFChars(cipherText_, NULL);
    //密钥字符串转化成char*
   	const char* key = env->GetStringUTFChars(key_, NULL);
   	int ciphter_length = strlen(ciphtertext);

        LOGE("ciphter_length:%d", ciphter_length);
   	LOGE("origin ciphter_str:%s", ciphtertext);
//   	LOGE("origin key:%s", key);

   	unsigned char key_str[100];
    unsigned char src_str[ciphter_length + 1];
    unsigned char plaint_str[ciphter_length +1];
    unsigned char dst[ciphter_length +1];
    int plaint_len;
    memset(key_str, 0, sizeof(key_str));
    memset(src_str, 0, sizeof(src_str));
    memset(plaint_str, 0, sizeof(plaint_str));
    memset(dst, 0, sizeof(dst));


    unhexify(key_str, key);
    int keyLength = strlen((char *)key_str);
    LOGD(" key_src length: %d",keyLength);
//    LOGE("key_str:%s", key_str);
    unhexify(src_str, ciphtertext);
    LOGE("src_str:%s", src_str);

   	plaint_len = decrypt((unsigned char*)src_str,ciphter_length/2,key_str,iv, dst, keyLength);

   	LOGD("plaint_len=%d",plaint_len);

   	unsigned char retStr[plaint_len + 1];
   	memset(retStr,'\0',sizeof(retStr));
    LOGD("strcpy before: %s",dst);

   	memcpy(retStr,(char *)dst, plaint_len + 1);
   	retStr[plaint_len] = '\0';

    LOGD("strcpy after: %s",retStr);
    int resLength = strlen((char*)retStr);
    LOGD("strcpy length: %d",resLength);
    unsigned char result[2*resLength + 1];
    memset(result, 0, 2*resLength + 1);
    hexify((unsigned char *)result,(unsigned char *)retStr,resLength);
    LOGD("result: %s",result);

    env->ReleaseStringUTFChars(cipherText_,ciphtertext);
   	env->ReleaseStringUTFChars(key_,key);
   	LOGE("ReleaseStringUTFChars end");
   	return env->NewStringUTF((char*)result);
}


jstring
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcEncryptBase64(JNIEnv *env, jclass type,
                                                          jstring plainText_, jstring key_) {
    //初始化向量
    unsigned char iv[16] = { '4', 'e', '5','W', 'a', '7', '1', 'f', 'Y', 'o', 'T', '7', 'M', 'F', 'E', 'X'};

    if (plainText_ == NULL || key_ == NULL){
        LOGE("----input plainText_ = null or key_ = null----");
        return NULL;
    }
    const char *plainText = env->GetStringUTFChars(plainText_, 0);
    const char *key = env->GetStringUTFChars(key_, 0);
    int plaint_length = strlen(plainText);
    int key_length = strlen(key);
    LOGE("input pliant_lenth = %d", plaint_length);
    LOGE("input key_length = %d", key_length);

    unsigned char plaint_src[plaint_length + 1];
    unsigned char key_src[100];
    unsigned char dst[plaint_length + 1];
    int cipher_len = 0;
    memset((char*) plaint_src, 0, sizeof(plaint_src));
    memset(key_src, 0, 100);
    memset(dst, 0, sizeof(dst));

    size_t dlen;
    size_t klen;
    mbedtls_base64_decode(plaint_src, sizeof(plaint_src), &dlen, (const unsigned char *) plainText, plaint_length);
    mbedtls_base64_decode(key_src, sizeof(key_src), &klen, (const unsigned char *) key, key_length);
//    LOGE("plaint_src: %s", plaint_src);
//    LOGE("key_src: %s", key_src);

    int keyLength = strlen((char *)key_src);
    LOGD(" key_src length: %d",keyLength);
    cipher_len = encrypt(plaint_src, strlen((const char *) plaint_src) , key_src, iv, dst, keyLength);

    LOGD("cipher_len=%d",cipher_len);

    unsigned char output[2 * cipher_len];
    //将加密后的字符串，转换成base64编码后返回
    int res = mbedtls_base64_encode(output, sizeof(output), &dlen, dst, cipher_len);/*dlen 对应的base64解密时源字符串长度*/
    LOGD("base64_encode result=%d dst=%d",res, dlen);
    LOGD("base64_encode output=%s",output);

    env->ReleaseStringUTFChars(plainText_, plainText);
    env->ReleaseStringUTFChars(key_, key);

    return env->NewStringUTF((const char *) output);
}



jstring
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcEncryptBase64Default(JNIEnv *env, jclass type,
                                                                 jstring plainText_) {
    const char *plainText = env->GetStringUTFChars(plainText_, 0);

    //初始化向量
    unsigned char iv[16] = { '4', 'e', '5','W', 'a', '7', '1', 'f', 'Y', 'o', 'T', '7', 'M', 'F', 'E', 'X'};
    //解密的key
//    unsigned char key[16] = {'#', '$', ')','$', 'F', 'O', '&', '*', '(', '1', '2', '3', '_', 'H', 'Q', 'W'};

    if (plainText_ == NULL){
        LOGE("----input plainText_ = null or key_ = null----");
        return NULL;
    }
    int plaint_length = strlen(plainText);
//    int key_length = strlen(key);
    LOGE("input pliant_lenth = %d", plaint_length);
//    LOGE("input key_length = %d", key_length);

    unsigned char plaint_src[plaint_length + 1];
//    unsigned char key_src[100];
    unsigned char dst[plaint_length + 1];
    int cipher_len = 0;
    memset((char*) plaint_src, 0, sizeof(plaint_src));
//    memset(key_src, 0, 100);
    memset(dst, 0, sizeof(dst));

    size_t dlen;
    size_t klen;
    mbedtls_base64_decode(plaint_src, sizeof(plaint_src), &dlen, (const unsigned char *) plainText, plaint_length);
//    mbedtls_base64_decode(key_src, sizeof(key_src), &klen, (const unsigned char *) key, key_length);
//    LOGE("plaint_src: %s", plaint_src);

    int keyLength = strlen((char *)U_AES_CBC_KEY);
    LOGD(" key_src length: %d",keyLength);
    cipher_len = encrypt(plaint_src, strlen((const char *) plaint_src) ,
                         (unsigned char *) U_AES_CBC_KEY, iv, dst, keyLength);

    LOGD("cipher_len=%d",cipher_len);

    unsigned char output[2 * cipher_len];
    //将加密后的字符串，转换成base64编码后返回
    int res = mbedtls_base64_encode(output, sizeof(output), &dlen, dst, cipher_len);/*dlen 对应的base64解密时源字符串长度*/
    LOGD("base64_encode result=%d dst=%d",res, dlen);
    LOGD("base64_encode output=%s",output);

    env->ReleaseStringUTFChars(plainText_, plainText);

    return env->NewStringUTF((const char *) output);

}

jstring
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcDecryptBase64(JNIEnv *env, jclass type,
                                                          jstring cipherText_, jstring key_) {
    //初始化向量
    unsigned char iv[16] = { '4', 'e', '5','W', 'a', '7', '1', 'f', 'Y', 'o', 'T', '7', 'M', 'F', 'E', 'X'};

    if (cipherText_ == NULL || key_ == NULL){
        LOGE("----input cipherText_ = null or key_ = null----");
        return NULL;
    }
    const char *cipherText = env->GetStringUTFChars(cipherText_, 0);
    const char *key = env->GetStringUTFChars(key_, 0);
    int ciphter_length = strlen(cipherText);
    int key_length = strlen(key);

    LOGE("ciphter_length:%d", ciphter_length);
    LOGE("origin ciphter_str:%s", cipherText);
    LOGE("origin key:%s", key);

    unsigned char key_src[100];
    unsigned char ciphter_src[ciphter_length];
    unsigned char dst[ciphter_length +1];
    int plaint_len;
    size_t dlen;
    size_t klen;
    size_t clen;
    memset(key_src, 0, sizeof(key_src));
    memset(ciphter_src, 0, sizeof(ciphter_src));
    memset(dst, 0, sizeof(dst));


    mbedtls_base64_decode(ciphter_src, sizeof(ciphter_src), &dlen, (const unsigned char *) cipherText, ciphter_length);
    mbedtls_base64_decode(key_src, sizeof(key_src), &klen, (const unsigned char *) key, key_length);
    LOGE("ciphter_length:%d", dlen);
    LOGE("ciphter_src:%s", ciphter_src);
//    LOGE("key_src:%s", key_src);

    plaint_len = decrypt((unsigned char*)ciphter_src,dlen,key_src,iv, dst, strlen((char *)key_src));

    LOGD("plaint_len=%d dlen= %d",plaint_len, clen);
    if(!plaint_len){
        LOGD("decrypt error");
        env->ReleaseStringUTFChars(cipherText_, cipherText);
        env->ReleaseStringUTFChars(key_, key);
        return env->NewStringUTF("decrypt error");
    }

    unsigned char output[2 * plaint_len + 1];
    //将加密后的字符串，转换成base64编码后返回
    int res = mbedtls_base64_encode(output, sizeof(output), &dlen, dst, plaint_len);/*dlen 对应的base64解密时源字符串长度*/

    LOGD("base64_encode result=%d dst=%d",res, dlen);
    LOGD("base64_encode output=%s",output);

    env->ReleaseStringUTFChars(cipherText_, cipherText);
    env->ReleaseStringUTFChars(key_, key);

    if(res < 0 || !dlen){
        LOGD("base64_encode error");
        return env->NewStringUTF("[]");
    }

    return env->NewStringUTF((const char *) output);
}


jstring
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcDecryptBase64Default(JNIEnv *env, jclass type,
                                                          jstring cipherText_){
    //初始化向量
    unsigned char iv[16] = { '4', 'e', '5','W', 'a', '7', '1', 'f', 'Y', 'o', 'T', '7', 'M', 'F', 'E', 'X'};
    //解密的key
//    unsigned char key[16] = {'#', '$', ')','$', 'F', 'O', '&', '*', '(', '1', '2', '3', '_', 'H', 'Q', 'W'};
    LOGE("---------------- AesCbcDecryptBase64Default begin ----------------");
    if (cipherText_ == NULL){
        LOGE("----input cipherText_ = null or key_ = null----");
        return NULL;
    }
    const char *cipherText = env->GetStringUTFChars(cipherText_, 0);
    int ciphter_length = strlen(cipherText);

    LOGE("ciphter_length:%d", ciphter_length);
    LOGE("origin ciphter_str:%s", cipherText);

    unsigned char ciphter_src[ciphter_length];
    unsigned char dst[ciphter_length +1];
    int plaint_len;
    size_t dlen;
    size_t clen;
    memset(ciphter_src, 0, sizeof(ciphter_src));
    memset(dst, 0, sizeof(dst));


    mbedtls_base64_decode(ciphter_src, sizeof(ciphter_src), &dlen, (const unsigned char *) cipherText, ciphter_length);
    LOGE("ciphter_length:%d", dlen);
    LOGE("ciphter_src:%s", ciphter_src);

    plaint_len = decrypt((unsigned char*)ciphter_src, dlen, (unsigned char *) U_AES_CBC_KEY, iv, dst, strlen((char *)U_AES_CBC_KEY));

    LOGD("plaint_len=%d dlen= %d",plaint_len, clen);
    if(!plaint_len){
        LOGD("decrypt error");
        env->ReleaseStringUTFChars(cipherText_, cipherText);
        return env->NewStringUTF("decrypt error");
    }

    unsigned char output[2 * plaint_len + 1];
    //将加密后的字符串，转换成base64编码后返回
    int res = mbedtls_base64_encode(output, sizeof(output), &dlen, dst, plaint_len);/*dlen 对应的base64解密时源字符串长度*/

    LOGD("encode result=%d dst=%d",res, dlen);
    LOGD("encode output=%s",output);

    env->ReleaseStringUTFChars(cipherText_, cipherText);

    if(res < 0 || !dlen){
        LOGD("base64_encode error");
        return env->NewStringUTF("[]");
    }

    return env->NewStringUTF((const char *) output);
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCfbEncryptBase64(JNIEnv *env, jclass type,
                                                          jstring plainText_) {
    LOGE("------------------- AesCfbEncryptBase64 begin -------------------");
    //初始化向量
    unsigned char iv[16] = { '&', '9', 'c', '(', 'e', 'O', 'Y', 'B', '$', '0', '1', 'N', '@', '~', 'a', 'U'};

    if (plainText_ == NULL){
        LOGE("----input plainText_ = null or key_ = null----");
        return NULL;
    }
    const char *plainText = env->GetStringUTFChars(plainText_, 0);
    LOGE("input plainText: %s", plainText);
    int plaint_length = strlen(plainText);
    LOGE("input pliant_lenth = %d", plaint_length);

    unsigned char plaint_src[plaint_length + 1];
    unsigned char dst[plaint_length + 1];
    int cipher_len = 0;
    memset((char*) plaint_src, 0, sizeof(plaint_src));
    memset(dst, 0, sizeof(dst));

    size_t dlen;
    size_t klen;
    int decode_result = mbedtls_base64_decode(plaint_src, sizeof(plaint_src), &dlen, (const unsigned char *) plainText, plaint_length);
    if(decode_result != 0){
        LOGD("decode_error: decode_result = %d", decode_result);
        return env->NewStringUTF("decode error");
    }
//    LOGE("plaint_src: %s", plaint_src);

    int keyLength = strlen((char *)U_AES_CFB_KEY);
    LOGD(" key_src length: %d",keyLength);
    cipher_len = encrypt_aes_cfb(plaint_src, strlen((const char *) plaint_src) ,
                         (unsigned char *) U_AES_CFB_KEY, iv, dst, keyLength);

    LOGD("cipher_len=%d",cipher_len);

    unsigned char output[2 * cipher_len + 4];//要兼顾数据长度1和2的情况，1->5
    //将加密后的字符串，转换成base64编码后返回
    int res = mbedtls_base64_encode(output, sizeof(output), &dlen, dst, cipher_len);/*dlen 对应的base64解密时源字符串长度*/
    LOGD("base64_encode result=%d dst=%d",res, dlen);
    LOGD("base64_encode output=%s",output);

    env->ReleaseStringUTFChars(plainText_, plainText);

    if(!isUTF8(output, strlen((const char *) output))){
        LOGD("not is utf8");
        return env->NewStringUTF("[]");
    }

    return env->NewStringUTF((const char *) output);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCfbDecryptBase64(JNIEnv *env, jclass type,
                                                          jstring cipherText_) {
    //初始化向量
    unsigned char iv[16] = { '&', '9', 'c', '(', 'e', 'O', 'Y', 'B', '$', '0', '1', 'N', '@', '~', 'a', 'U'};
    //解密的key
    LOGE("---------------- AesDecryptBase64Default begin ----------------");
    if (cipherText_ == NULL){
        LOGE("----input cipherText_ = null or key_ = null----");
        return NULL;
    }
    const char *cipherText = env->GetStringUTFChars(cipherText_, 0);
    int ciphter_length = strlen(cipherText);

    LOGE("ciphter_length:%d", ciphter_length);
    LOGE("origin ciphter_str:%s", cipherText);

    unsigned char *ciphter_src = (unsigned char*)malloc(sizeof(char) * ciphter_length);
    unsigned char *dst = (unsigned char*)malloc(sizeof(char) * (ciphter_length + 1));
    int plaint_len;
    size_t dlen;
    memset(ciphter_src, 0, sizeof(ciphter_src));
    memset(dst, 0, sizeof(dst));

    int decode_result = mbedtls_base64_decode(ciphter_src, ciphter_length, &dlen, (const unsigned char *) cipherText, ciphter_length);
    LOGE("decode_result:%d ciphter_length:%d",decode_result, dlen);
    LOGE("ciphter_src:%s", ciphter_src);

    plaint_len = decrypt_aes_cfb((unsigned char*)ciphter_src, dlen, (unsigned char *) U_AES_CFB_KEY, iv, dst, strlen((char *)U_AES_CFB_KEY));

    LOGD("plaint_len=%d dlen= %d",plaint_len, dlen);
    if(!plaint_len){
        LOGD("decrypt error");
        env->ReleaseStringUTFChars(cipherText_, cipherText);
        return env->NewStringUTF("decrypt error");
    }

    unsigned char *output = (unsigned char*)malloc((int)((sizeof(char)) * (2 * plaint_len + 4)));
    memset(output, '\0', sizeof(output));
    //将加密后的字符串，转换成base64编码后返回
    int res = mbedtls_base64_encode(output,(2 * plaint_len + 4), &dlen, dst, plaint_len);/*dlen 对应的base64解密时源字符串长度*/

    LOGD("encode result=%d dst=%d",res, dlen);
    LOGD("encode output=%s",output);

    env->ReleaseStringUTFChars(cipherText_, cipherText);

    if(res < 0 || !dlen){
        LOGD("base64_encode error");
        return env->NewStringUTF("[]");
    }

    return env->NewStringUTF((const char *) output);
}

#define SALT_ONE "YedHW2jy&F^bd1jPPc8dUwWY"
#define SALT_TWO "7sBswhEkL6Ryq&ad#@U0Y0^tlv"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_formatString(JNIEnv *env, jclass type, jstring arg1_,
                                                   jstring arg2_, jstring arg3_, jstring arg4_) {
    LOGE("---------------- formatString begin ----------------");
    const char *arg1 = env->GetStringUTFChars(arg1_, 0);
    const char *arg2 = env->GetStringUTFChars(arg2_, 0);
    const char *arg3 = env->GetStringUTFChars(arg3_, 0);
    const char *arg4 = env->GetStringUTFChars(arg4_, 0);
    int length1 = strlen(arg1);
    int length2 = strlen(arg2);
    int length3 = strlen(arg3);
    int length4 = strlen(arg4);
    int length = length1 + length2 + length3 + length4 + strlen(SALT_ONE) + strlen(SALT_TWO);
    char out[length + 1];
    memset(out, '\0', length + 1);
    strcpy(out, arg1);
    strcat(out, SALT_ONE);
    strcat(out, arg2);
    strcat(out, SALT_TWO);
    strcat(out, arg3);
    strcat(out, arg4);

    env->ReleaseStringUTFChars(arg1_, arg1);
    env->ReleaseStringUTFChars(arg2_, arg2);
    env->ReleaseStringUTFChars(arg3_, arg3);
    env->ReleaseStringUTFChars(arg4_, arg4);
    LOGE("---------------- formatString end ----------------");
    return env->NewStringUTF(out);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcEncryptBase64H5(JNIEnv *env, jclass type,
                                                            jstring plainText_) {
    LOGE("------------------- AesCbcEncryptBase64H5 begin -------------------");
    //初始化向量
    unsigned char iv[16] = { '4', 'e', '5','W', 'a', '7', '1', 'f', 'Y', 'o', 'T', '7', 'M', 'F', 'E', 'X'};

    if (plainText_ == NULL){
        LOGE("----input plainText_ = null or key_ = null----");
        return NULL;
    }
    const char *plainText = env->GetStringUTFChars(plainText_, 0);
    LOGE("input plainText: %s", plainText);
    int plaint_length = strlen(plainText);
    LOGE("input pliant_lenth = %d", plaint_length);

    unsigned char plaint_src[plaint_length + 1];
    unsigned char dst[plaint_length + 1];
    int cipher_len = 0;
    memset((char*) plaint_src, 0, sizeof(plaint_src));
    memset(dst, 0, sizeof(dst));

    size_t dlen;
    size_t klen;
    int decode_result = mbedtls_base64_decode(plaint_src, sizeof(plaint_src), &dlen, (const unsigned char *) plainText, plaint_length);
    if(decode_result != 0){
        LOGD("decode_error: decode_result = %d", decode_result);
        return env->NewStringUTF("decode error");
    }
//    LOGE("plaint_src: %s", plaint_src);

    int keyLength = strlen((char *)U_AES_CFB_H5);
    LOGD(" key_src length: %d",keyLength);
    cipher_len = encrypt(plaint_src, strlen((const char *) plaint_src) ,
                                 (unsigned char *) U_AES_CFB_H5, iv, dst, keyLength);

    LOGD("cipher_len=%d",cipher_len);

    unsigned char output[2 * cipher_len + 4];//要兼顾数据长度1和2的情况，1->5
    //将加密后的字符串，转换成base64编码后返回
    int res = mbedtls_base64_encode(output, sizeof(output), &dlen, dst, cipher_len);/*dlen 对应的base64解密时源字符串长度*/
    LOGD("base64_encode result=%d dst=%d",res, dlen);
    LOGD("base64_encode output=%s",output);

    env->ReleaseStringUTFChars(plainText_, plainText);

    if(!isUTF8(output, strlen((const char *) output))){
        LOGD("not is utf8");
        return env->NewStringUTF("[]");
    }

    return env->NewStringUTF((const char *) output);
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_AesCbcDecryptBase65H5(JNIEnv *env, jclass type,
                                                            jstring cipherText_) {
    //初始化向量
    unsigned char iv[16] = { '4', 'e', '5','W', 'a', '7', '1', 'f', 'Y', 'o', 'T', '7', 'M', 'F', 'E', 'X'};
    //解密的key
    LOGE("---------------- AesCbcDecryptBase65H5 begin ----------------");
    if (cipherText_ == NULL){
        LOGE("----input cipherText_ = null or key_ = null----");
        return NULL;
    }
    const char *cipherText = env->GetStringUTFChars(cipherText_, 0);
    int ciphter_length = strlen(cipherText);

    LOGE("ciphter_length:%d", ciphter_length);
    LOGE("origin ciphter_str:%s", cipherText);

    unsigned char ciphter_src[ciphter_length];
    unsigned char dst[ciphter_length +1];
    int plaint_len;
    size_t dlen;
    size_t clen;
    memset(ciphter_src, 0, sizeof(ciphter_src));
    memset(dst, 0, sizeof(dst));


    mbedtls_base64_decode(ciphter_src, sizeof(ciphter_src), &dlen, (const unsigned char *) cipherText, ciphter_length);
    LOGE("ciphter_length:%d", dlen);
    LOGE("ciphter_src:%s", ciphter_src);

    plaint_len = decrypt((unsigned char*)ciphter_src, dlen, (unsigned char *) U_AES_CFB_H5, iv, dst, strlen((char *)U_AES_CFB_H5));

    LOGD("plaint_len=%d dlen= %d",plaint_len, clen);
    if(!plaint_len){
        LOGD("decrypt error");
        env->ReleaseStringUTFChars(cipherText_, cipherText);
        return env->NewStringUTF("decrypt error");
    }

    unsigned char output[2 * plaint_len + 4];
    //将加密后的字符串，转换成base64编码后返回
    int res = mbedtls_base64_encode(output, sizeof(output), &dlen, dst, plaint_len);/*dlen 对应的base64解密时源字符串长度*/

    LOGD("encode result=%d dst=%d",res, dlen);
    LOGD("encode output=%s",output);

    env->ReleaseStringUTFChars(cipherText_, cipherText);

    if(res < 0 || !dlen){
        LOGD("base64_encode error");
        return env->NewStringUTF("[]");
    }

    return env->NewStringUTF((const char *) output);
}


#define PAY_SALT_ONE "2(LOSme*e,37}&am@wUs1>y!"
#define PAY_SALT_TWO "w8&_asd-C$Eq+M%aA1wuNSF2"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qbao_xproject_app_utility_AESUtil_formatPayString(JNIEnv *env, jclass type, jstring arg1_,
                                                      jstring arg2_, jstring arg3_, jstring arg4_) {
    LOGE("---------------- formatPayString begin ----------------");
    const char *arg1 = env->GetStringUTFChars(arg1_, 0);
    const char *arg2 = env->GetStringUTFChars(arg2_, 0);
    const char *arg3 = env->GetStringUTFChars(arg3_, 0);
    const char *arg4 = env->GetStringUTFChars(arg4_, 0);
    int length1 = strlen(arg1);
    int length2 = strlen(arg2);
    int length3 = strlen(arg3);
    int length4 = strlen(arg4);
    int length = length1 + length2 + length3 + length4 + strlen(PAY_SALT_ONE) + strlen(PAY_SALT_TWO);
    char out[length + 1];
    memset(out, '\0', length + 1);
    strcpy(out, arg1);
    strcat(out, PAY_SALT_TWO);
    strcat(out, arg2);
    strcat(out, PAY_SALT_ONE);
    strcat(out, arg3);
    strcat(out, arg4);

    env->ReleaseStringUTFChars(arg1_, arg1);
    env->ReleaseStringUTFChars(arg2_, arg2);
    env->ReleaseStringUTFChars(arg3_, arg3);
    env->ReleaseStringUTFChars(arg4_, arg4);
    LOGE("---------------- formatPayString end ----------------");
    return env->NewStringUTF(out);
}
