worker: sh target/bin/Make_CV_bot
web: java $JAVA_OPTS -cp target/classes:target/dependency/* com.company.Main
web: java -jar target/java-getting-started-1.0.jar
web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
