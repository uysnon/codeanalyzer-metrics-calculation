Шлюз-сервис
Переадресовывает запросы бэкенд сервису

Запуск проекта:
1) Настройка окружения
    - Переменные окружения
      1) ONLINE_CALCULATION_SERVICE_URL = localhost:8100

   
сборка через docker:
- собрать образ: docker build ./ -t uysnon/codeanalyzer-auth:latest
- запустить образ: docker run -d -p 80:80 -p 8080:8080 --name gateway --env ONLINE_CALCULATION_SERVICE_URL=http://metrics-online-calculation metrics-gateway:1 --server.port=80 --management.server.port=8080




