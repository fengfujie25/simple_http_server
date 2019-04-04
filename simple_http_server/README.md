简介
========================

此项目简单模拟了python的SimpleHTTPServer服务（python -m SimpleHTTPServer）。


操作步骤
-----------------------------------

    $ cd ~/myDir
    $ java -jar tjava -jar simple_http_server.jar
    
      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::        (v2.1.3.RELEASE)
    
    2019-04-04 11:13:31.414  INFO 94637 --- [           main] c.f.p.s.SimpleHttpServerApplication      : Starting SimpleHttpServerApplication v0.0.1-SNAPSHOT on fengfujies-MacBook-Pro.local with PID 94637 (/Users/fengfujie/work/alibaba/simple_http_server/target/simple_http_server.jar started by fengfujie in /Users/fengfujie/work/alibaba/simple_http_server/target)
    2019-04-04 11:13:31.416  INFO 94637 --- [           main] c.f.p.s.SimpleHttpServerApplication      : No active profile set, falling back to default profiles: default
    2019-04-04 11:13:31.831  INFO 94637 --- [           main] c.f.p.s.SimpleHttpServerApplication      : Started SimpleHttpServerApplication in 0.67 seconds (JVM running for 0.978)
    Server http on 0.0.0.0 port 8000 ......

默认监听端口：8000，文件夹目录为当前目录。
端口和文件夹目录可以自己配置：

    $ cd ~/myDir
    $ java -jar tjava -jar simple_http_server.jar 9000 /myDir/test
    
自己配置监听端口和文件夹目录请确保参数顺序，否则按默认参数启动



