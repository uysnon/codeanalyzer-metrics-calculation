# Вычисление метрик программного кода
Онлайн сервис для вычисления метрик проекта

При локальном запуске должен быть запущен на 8100 порту

Настройка в Intellij IDEA:
1) VM options: -Dserver.port=8100 (online service)
2) Program arguments: --management.server.port=8101 (actuator)

сборка: mvn clean package
запуск из консоли: java -Dserver.port=8100 -jar online-calculation/target/metrics-online-calculation.jar --management.server.port=8101

