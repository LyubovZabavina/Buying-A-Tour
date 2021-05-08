# Процедура установки, настройки и запуска ПО
1. Перед запуском авто-тестов, необходимо уcтановить среду разработки "Intellij IDEA Ultimate" и "Docker", "Docker Compose", для работы с контейнерами "MySQL", "PostgreSQL", "Node-app"

2. Проверить наличие установленных версий библиотек в файле "build.gradle", необходимых для запуска и корректной работы авто-тестов.

3. Для запуска контейнеров "MySQL", "PostgreSQL", "Node-app", в фоновом режиме, необходимо ввести в терминал следующую команду: `docker-compose up -d`
4. Для запуска SUT с "MySQL",  необходимо ввести в терминал следующую команду:  
`java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar`
5. Для запуска SUT с "PostgreSQL",  необходимо ввести в терминал следующую команду:  
`java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -jar artifacts/aqa-shop.jar`
6. Для запуска SUT с "MySQL",  необходимо ввести в терминал следующую команду:  
`./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app`
7. Для запуска SUT с "PostgreSQL",  необходимо ввести в терминал следующую команду:  
`./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/postgres`
8. Для запуска и просмотра отчета по результатам тестирования, с помощью "Allure", выполнить команду:  
`./gradlew allureReport`, затем `./gradlew allureServe`
