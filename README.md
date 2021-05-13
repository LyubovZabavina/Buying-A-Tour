## Документация
1. [План автоматизации тестирования](https://github.com/ZabavinaL/Diploma/blob/e6ccdda5bfa68c192eeb3e6f09ebec95161059e8/documentation/Plan.md)
2. [Отчет по итогам тестирования](https://github.com/ZabavinaL/Diploma/blob/eb8c1959346756c401b2a3c0123a1ebd47c088ba/documentation/Report.md)
3. [Отчет по итогам автоматизации тестирования](https://github.com/ZabavinaL/Diploma/blob/eb8c1959346756c401b2a3c0123a1ebd47c088ba/documentation/Summary.md)

## Процедура установки, настройки и запуска ПО
### Перед запуском авто-тестов, необходимо:
* Уcтановить среду разработки "Intellij IDEA Ultimate", "Docker", "Docker-compose", для работы с контейнерами "MySQL", "PostgreSQL", "Node-app";

* Проверить наличие установленных версий библиотек в файле "build.gradle", необходимых для запуска и корректной работы авто-тестов;

* Запустить контейнеры "MySQL", "PostgreSQL", "Node-app" в "Docker-compose";

* Запустить SUT для "MySQL" или "PostgreSQL".

**1. Для запуска контейнеров "MySQL", "PostgreSQL", "Node-app", в фоновом режиме, необходимо ввести в терминал следующую команду:**  
 `docker-compose up -d`

**2. Для запуска SUT с "MySQL",  необходимо ввести в терминал следующую команду:**  
`java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar`  

**Примечание**   
Перед запуском, необходимо:
* Открыть "application.properties", раскомментировать строки дл "MySQL", нажав на сочетание клавиш `Cmd+/` (для MacOS), `Ctrl+/` (для Windows):
`spring.datasource.username=user`  
`spring.datasource.password=12345`

* Закомментировать строки, для "PostgreSQL":
`spring.datasource.username=user`  
`spring.datasource.password=pass`

**3. Для запуска SUT с "PostgreSQL",  необходимо ввести в терминал следующую команду:**  
`java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -jar artifacts/aqa-shop.jar`  

**Примечание**  
Перед запуском, необходимо:
* Открыть "application.properties", раскомментировать строки для "PostgreSQL", нажав на сочетание клавиш `Cmd+/` (для MacOS), `Ctrl+/` (для Windows):
`spring.datasource.username=user`  
`spring.datasource.password=pass`

* Закомментировать строки, для "MySQL":
`spring.datasource.username=user`  
`spring.datasource.password=12345`

**4. Для запуска авто-тестов с "MySQL",  необходимо открыть новую вкладку терминала и ввести следующую команду:** 
`./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app`

**5. Для запуска авто-тестов с "PostgreSQL",  необходимо открыть новую вкладку терминала и ввести следующую команду:**
`./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/postgres`

**6. Для запуска и просмотра отчета по результатам тестирования, с помощью "Allure", выполнить команду:**  
`./gradlew allureReport`, затем `./gradlew allureServe`

**7. Для завершения работы SUT, необходимо в терминале, где был запущен SUT, ввести команду:**  
`Ctrl+C`

**8. Для остановки работы контейнеров "Docker-Compose", необходимо ввести в терминал следующую команду:**  
`docker-compose down`
