@echo off
cd bin
java -cp ../lib/java-cup-11b-runtime.jar;../lib/java-cup-11b.jar;. Mrl ../usecase/skate.txt
echo finish
exit