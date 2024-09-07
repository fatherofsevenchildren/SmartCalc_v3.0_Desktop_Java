#include "model.h"
#include <iostream>


//// int main () {
////     s21::Model model;
////     // model.CalculateAnswer("1+1", "0");
////     std::cout << "ha ";
//
////     model.CalculateAnswer("2+cos(2)", "0");
////     // model.infix_expression___ = "5*5-5/5";
////     // model.trimFunction();
////     // model.infixToPostfix();
////     // model.arithmetic();
////     // long double test = 342.1341342;
////     // std::cout << std::setprecision(100) << model.postfix_.top().number ;
////     // for (auto it = model.postfix_.begin(); it != model.postfix_.end();
////     // ++it) {
////     // std::cout << it->number << ". " << it->opertor << '\t';
////     // }
////     // std::string str = "1e-6";
////     // model.answer_ = std::stod(str);
////     std::cout << model.GetAnswer();
//
////     return 0;
//// }
//
namespace s21 {

std::string Model::getAnswer() {
    std::string stringAnswer = std::to_string(answer_);
    return stringAnswer;
}

void Model::trimFunction() {
  for (auto it = infix_expression_.begin(); it != infix_expression_.end();
       ++it) {
    if (*it == 's') {
      if (*(it + 1) == 'i') {
        infix_expression_.erase(it + 1, it + 3);
      } else {
        *it = 'q';
        infix_expression_.erase(it + 1, it + 4);
      }
    } else if (*it == 'c' || *it == 'm' || *it == 't') {
      infix_expression_.erase(it + 1, it + 3);
    } else if (*it == 'a') {
      if (*(it + 1) == 't') *it = 'n';
      if (*(it + 1) == 's') *it = 'i';
      infix_expression_.erase(it + 1, it + 4);
    } else if (*it == 'l') {
      if (*(it + 1) == 'o') infix_expression_.erase(it);
      infix_expression_.erase(it + 1);
    }
  }
}

void Model::infixToPostfix() {
  std::stack<node> operator_stack_;
  auto it = infix_expression_.begin();
  if (*it == '-') {
    flag_unar_minus = true;
    it++;
  }  // unary minus mayby not work
  for (; it != infix_expression_.end(); it++) {
    if (isdigit(*it)) {
      std::string digit;
      if (flag_unar_minus == true) {
        digit.push_back('-');
        flag_unar_minus = false;
      }
      while (isdigit(*it) || *it == '.' || (*it == 'e' and *(it + 1) == '-') ||
             (*it == 'e')) {
        digit.push_back(*it);
        it++;
      }
      postfix_.push_back({true, std::stod(digit), 0});
      it--;
    } else if (*it == 'x') {
      if (flag_unar_minus == true) {
        postfix_.push_back({true, std::stod(x) * -1, 0});
        flag_unar_minus = false;
      } else {
        postfix_.push_back({true, std::stod(x), 0});
      }
    } else if (*it == '(') {
      if (*(it + 1) == '-') {
        flag_unar_minus = true;
        it++;
      }
      while (!operator_stack_.empty() && 4 <= operator_stack_.top().number) {
        postfix_.push_back(operator_stack_.top());
        operator_stack_.pop();
      }
      operator_stack_.push({false, -1, '('});
    } else if (*it == '^' || *it == 'q') {
      while (!operator_stack_.empty() && 2 <= operator_stack_.top().number) {
        postfix_.push_back(operator_stack_.top());
        operator_stack_.pop();
      }
      operator_stack_.push({false, 2, *it});
    } else if (*it != 'm' && *it != 'q' && *it >= 'a' && *it <= 'z') {
      while (!operator_stack_.empty() && 3 <= operator_stack_.top().number) {
        postfix_.push_back(operator_stack_.top());
        operator_stack_.pop();
      }
      operator_stack_.push({false, 3, *it});
    } else if (*it == '/' || *it == '*' || *it == 'm') {
      while (!operator_stack_.empty() && 1 <= operator_stack_.top().number) {
        postfix_.push_back(operator_stack_.top());
        operator_stack_.pop();
      }
      operator_stack_.push({false, 1, *it});
    } else if (*it == '+' || *it == '-') {
      while (!operator_stack_.empty() && 0 <= operator_stack_.top().number) {
        postfix_.push_back(operator_stack_.top());
        operator_stack_.pop();
      }
      operator_stack_.push({false, 0, *it});
    } else if (*it == ')') {
      while (!operator_stack_.empty() && operator_stack_.top().opertor != '(') {
        postfix_.push_back(operator_stack_.top());
        operator_stack_.pop();
      }
      operator_stack_.pop();
    }
  }
  while (!operator_stack_.empty()) {
    postfix_.push_back(operator_stack_.top());
    operator_stack_.pop();
  }
}

void Model::arithmetic() {
  for (auto it = postfix_.begin(); it != postfix_.end(); ++it) {
    if (it->is_number == true)
      stack_.push(it->number);
    else if (it->opertor == '+') {
      stack_.push(stackTopPop() + stackTopPop());
    } else if (it->opertor == '-') {
      long double subtrahend = stackTopPop();
      long double minuend = stackTopPop();
      stack_.push(minuend - subtrahend);
    } else if (it->opertor == '*') {
      stack_.push(stackTopPop() * stackTopPop());
    } else if (it->opertor == '/') {
      long double divider = stackTopPop();
      long double dividend = stackTopPop();
      stack_.push(dividend / divider);
    } else if (it->opertor == 'm') {
      long double divider = stackTopPop();
      long double dividend = stackTopPop();
      stack_.push(fmod(dividend, divider));
    } else if (it->opertor == '^') {
      long double exponent = stackTopPop();
      long double base = stackTopPop();
      stack_.push(pow(base, exponent));
    } else if (it->opertor == 's') {
      stack_.push(sin(stackTopPop()));
    } else if (it->opertor == 'q') {
      stack_.push(sqrt(stackTopPop()));
    } else if (it->opertor == 'c') {
      stack_.push(cos(stackTopPop()));
    } else if (it->opertor == 't') {
      stack_.push(tan(stackTopPop()));
    } else if (it->opertor == 'a') {
      stack_.push(acos(stackTopPop()));
    } else if (it->opertor == 'i') {
      stack_.push(asin(stackTopPop()));
    } else if (it->opertor == 'n') {
      stack_.push(atan(stackTopPop()));
    } else if (it->opertor == 'l') {
      stack_.push(log(stackTopPop()));
    } else if (it->opertor == 'o') {
      stack_.push(log10(stackTopPop()));
    }
  }
  answer_ = stackTopPop();
  postfix_.clear();
}

void Model::CalculateAnswer(const std::string input_expression,
                            const std::string input_x) {
  infix_expression_ = input_expression;
  x = input_x;
  trimFunction();
  infixToPostfix();
  arithmetic();
}

long double Model::stackTopPop() {
  long double res = stack_.top();
  stack_.pop();
  return res;
}

}  // namespace s21