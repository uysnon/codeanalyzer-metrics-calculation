#!/bin/sh
osascript -e 'tell app "Terminal" to do script "java -jar ~/.m2/repository/com/uysnon/codeanalyzer/auth/0.0.1-SNAPSHOT/auth-0.0.1-SNAPSHOT-exec.jar"'
osascript -e 'tell app "Terminal" to do script "java -jar ~/.m2/repository/com/uysnon/codeanalyzer/online-calculation/0.0.1-SNAPSHOT/online-calculation-0.0.1-SNAPSHOT-exec.jar"'
osascript -e 'tell app "Terminal" to do script "java -jar ~/.m2/repository/com/uysnon/codeanalyzer/webui/0.0.1-SNAPSHOT/webui-0.0.1-SNAPSHOT-exec.jar"'
osascript -e 'tell app "Terminal" to do script "java -jar ~/.m2/repository/com/uysnon/codeanalyzer/teacher-ui/0.0.1-SNAPSHOT/teacher-ui-0.0.1-SNAPSHOT-exec.jar"'