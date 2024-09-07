all: install open

open:
	mvn clean javafx:run

install: clean
	g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin src/main/resources/cpplib/edu_school21_smartcalc_model_Model.cpp -o src/main/resources/cpplib/edu_school21_smartcalc_model_Model.o
	g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin src/main/resources/cpplib/model.cpp -o src/main/resources/cpplib/model.o
	g++ -dynamiclib -o src/main/resources/cpplib/libnative.dylib src/main/resources/cpplib/edu_school21_smartcalc_model_Model.o src/main/resources/cpplib/model.o -lc
	mvn clean javafx:jlink
	cp target/app/bin ~/Desktop/

clean:
	rm -rf src/main/resources/cpplib/*.o *.dylib