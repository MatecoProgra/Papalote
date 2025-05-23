#include <stdio.h>
#include <stddef.h>
#include <memory.h>

#include "D:\progra\graal\include\jni.h"

int JNIHeaderDirectivesJDK22OrLater() {
    printf("NativeCodeInfo:JNIHeaderDirectivesJDK22OrLater:ConstantInfo:JNI_VERSION_21:PropertyInfo:size=%llu\n", ((unsigned long long)sizeof(JNI_VERSION_21)));
    printf("NativeCodeInfo:JNIHeaderDirectivesJDK22OrLater:ConstantInfo:JNI_VERSION_21:PropertyInfo:signedness=$%s$\n", ((JNI_VERSION_21>=0 ? 1 : 0)) ? "UNSIGNED" : "SIGNED");
    printf("NativeCodeInfo:JNIHeaderDirectivesJDK22OrLater:ConstantInfo:JNI_VERSION_21:PropertyInfo:value=%llX\n", ((unsigned long long)JNI_VERSION_21));
    return 0;
}

int main(void) {
    return JNIHeaderDirectivesJDK22OrLater();
}
