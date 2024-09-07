#ifndef CPP3_SMARTCALC_V2_SRC_CALCULATOR_MODEL_H_
#define CPP3_SMARTCALC_V2_SRC_CALCULATOR_MODEL_H_

#include <cctype>
#include <cmath>
#include <iomanip>
#include <iostream>
#include <list>
#include <stack>
#include <string>
#include <vector>


struct node {
  bool is_number;
  long double number;  // when its operator = priority
  char opertor;
};

namespace s21 {
class Model {
 public:
  std::string getAnswer();
  int GetError() const { return error_; };
  void CalculateAnswer(const std::string input_expression,
                       const std::string input_x);

 private:
  void trimFunction();
  void infixToPostfix();
  void arithmetic();

  long double answer_ = 0;
  std::string infix_expression_ = {0};
  std::list<node> postfix_;
  std::stack<long double> stack_;
  long double stackTopPop();
  int error_ = 0;
  std::string x = "0";
  bool flag_unar_minus = false;
};

}  // namespace s21

#endif  // CPP3_SMARTCALC_V2_SRC_CALCULATOR_MODEL_H_
