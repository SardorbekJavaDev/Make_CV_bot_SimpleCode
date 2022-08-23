worker: sh target/bin/Make_CV_bot
web: java $JAVA_OPTS -cp target/classes:target/dependency/* com.company.Main
web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.jar
