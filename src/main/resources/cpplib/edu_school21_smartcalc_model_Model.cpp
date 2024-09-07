#include "edu_school21_smartcalc_model_Model.h"
#include "model.h"
#include <string>

//std::string getAnswer() {
//    std::string answer = "hello from c++";
//    return answer;
//}

JNIEXPORT jstring JNICALL Java_edu_school21_smartcalc_model_Model_performCalculation
  (JNIEnv * env, jobject thisObject, jstring expression, jstring x) {
      s21::Model model;
      model.CalculateAnswer(env->GetStringUTFChars(expression, NULL), env->GetStringUTFChars(x, NULL));
      return env->NewStringUTF(model.getAnswer().c_str());
  }
