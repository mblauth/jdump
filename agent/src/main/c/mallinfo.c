#include <malloc.h>

#include "jni.h"

JNIEXPORT jobject Java_jdump_agent_MallInfo_get0(JNIEnv *env, jclass class) {
    struct mallinfo mi = mallinfo();

    jmethodID constructor = (*env)->GetMethodID(env, class, "<init>", "(JJJJJJJJJ)V");
    if (!constructor) {
        return NULL; // return with pending exception
    }

    return (*env)->NewObject(env, class, constructor, mi.arena, mi.ordblks, mi.smblks, mi.hblks, mi.hblkhd,
                                                      mi.fsmblks, mi.uordblks, mi.fordblks, mi.keepcost);

}