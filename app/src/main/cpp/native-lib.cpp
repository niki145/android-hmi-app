#include <jni.h>
#include <string>
#include <android/log.h>
#include <ctime>



#define LOG_TAG "ADAS_APP"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_adas_1app_MainActivity_updateSpeed(JNIEnv *env, jobject /* this */, jint speed) {

    int currentSpeed = speed;
    std::string color;
    if(currentSpeed < 80){

        color = "WHITE";
    }
    else if(currentSpeed > 80 && currentSpeed <= 110){

        color = "YELLOW";

    }
    else if(currentSpeed > 110){
        color = "RED";
    }

    return env->NewStringUTF(color.c_str());
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_example_adas_1app_MainActivity_updateProgressStats(JNIEnv *env, jobject thiz,
                                                            jint progress) {
    // TODO: implement updateProgressStats()

    if(progress <= 20 || progress >= 90){
        return 30; //low efficiency
    }else if(progress > 20 && progress <= 70){
        return 80; // high efficiency
    }else if(progress > 70 && progress < 90){
        return 50; //medium efficiency
    }
}