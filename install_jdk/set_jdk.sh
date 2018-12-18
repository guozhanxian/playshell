echo "export JAVA_HOME=${HOME}/jdk" >> ~/.bash_profile
echo 'export CLASSPATH=.:${JAVA_HOME}/lib' >> ~/.bash_profile
echo 'export PATH=${JAVA_HOME}/bin:$PATH' >> ~/.bash_profile
source ~/.bash_profile