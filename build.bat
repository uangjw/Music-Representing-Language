@echo off
echo building...
javac -d bin -encoding utf-8 -cp lib/java-cup-11b-runtime.jar;lib/java-cup-11b.jar src/*.java src/write_midi/*.java src/exceptions/*.java
echo finish building
exit