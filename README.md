# Децентрализованная система ипотечного страхования

Платформа обеспечивает взаимодействие двух видов участников – страховый компаний, 
и банков – и позволяет снизить издержки обоих сторона на документооборот 
за счет цифровизации договора страхования и страхового полиса с использованием
технологии распределенного реестра и проведения платежей с использованием цифрового рубля. 
Возможность включения стоимости страхового полиса в состав ипотечных платежей
снижает риски смены страховой компании или отказа от полиса со стороны 
Заемщика для Банка.

## Сборка проекта

Перед сборкой необходимо установить следующее ПО:
- OpenJDK 1.8.0_212+ (Java 11 и выше не подойдут)
- Docker версии 20.10+

На Unix/Linux системах перед сборкий нужно выполнить команду `chmod +x ./gradlew`

Сборка проекта осуществляется командой: `./gradlew clean build dockerTag -x test`

## Запуск проекта

Перед запуском необходимо запустить БД PostgreSQL. 
Запустить БД можно при помощи команды:

```
docker network create mortgage 
docker run --network mortgage --name mortgage-pg -d -e POSTGRES_PASSWORD=password -e POSTGRES_USER=postgres -e POSTGRES_DB=db_mortgage_app -p 5432:5432 -d postgres:11.10
```

После запуска БД можно запустить проект командой 

```
docker run --network mortgage --name mortgage-app -d -e "spring.datasource.url=jdbc:postgresql://mortgage-pg:5432/db_mortgage_app" -p 8080:8080 registry.weintegrator.com/mortgage/mortgage-webapp-app:latest   
```

## Работа с проектом

### Предсозданные роли и организации

```
Банки:
alfabank/alfabank - для работы от лица Банка (Альфа)
sber/sber - для работы от лица Банка (Сбер)
vtb/vtb - для работы от лица Банка (ВТБ)

Страховые компании:
sogaz/sogaz - для работы от лица Страховой Компании (Согаз)
alphains/alphains - для работы от лица Страховой Компании (Альфа-Страхования)

Заемщики:
user/user
```

### Прохождение процесса

Сначала от лица банка (заходим как alfabank) аккредитуем страховые компании Согаз и Альфа-Страхование.
Для этого закинем в REST /bank/aux/accredited JSON
```
["3N5xJWrQPrXnZxcvqu9W2xTJ9FgXFJYiaEX", "3N5ihzq38ZbHJyHCkFD36nf836BxoenN8Qx"]
```

Затем от лица страховых компаний sogaz и alphains установим формулы.
Для этого закинем в REST /insurer/aux/formula JSON
```
{
    "risk": "PROPERTY",
    "formula": {
        "bankMap": {
            "3MpPEoTnh5SsBFK2fCqoKtKCu67LYc8aeV8": "0.1",
            "3N9UQJ6bdCqzuPWoepZrSxa3exCDeyoANKd": "0.2",
            "3MsQ3qpkqUpoUAmfskRWYQotiZ4qf56WBve": "0.15"
        },
        "ageMap": [
            {
                "range": {
                    "from": "0",
                    "to": "30"
                },
                "koeff": "0.1"
            },
            {
                "range": {
                    "from": "31",
                    "to": "90"
                },
                "koeff": "0.15"
            }
        ],
        "remainMap": [
            {
                "range": {
                    "from": "0",
                    "to": "1000000000000"
                },
                "koeff": "0.01"
            }
        ],
        "typeMap": {
            "Дом": "0.1",
            "Квартира": "0.2"
        },
        "sexMap": {
            "FEEMALE": "0.1",
            "MALE": "0.2"
        }
    }
}
```
Для значений risk=PROPERTY, LIFE, TITUL (коэффициенты можно менять)

Теперь от лица Банка внесем в систему договор. 
Войдем как Альфа-Банк (alfabank) и отправим JSON в REST /mortgage/agreements

```
{
  "documentNumber": "1234/1345-AB-18",
  "personName": "Леонид",
  "personSurname": "Сергеевич",
  "personPatronymic": "Рипейный",
  "personId": "4ba39f08-af08-48c0-9ee3-22383fe6cd86",
  "personSex": "MALE",
  "personBirthDate": "1989-09-25T02:19:50.811Z",
  "personOccupation": "Программист",
  "documentDate": "2021-09-25T02:19:50.811Z",
  "passportNumber": "444876",
  "passportSeries": "1234",
  "type": "Дом",
  "city": "г. Ленинград",
  "estimatedCost": 13000000,
  "remainingAmount": 8000000,
  "buildingYear": 2011,
  "kadasterNumber": "31:16:0:0034:043679-00",
  "address": "г. Ленинград, ул. Домоседская, кв. 34"
}
```

После загрузки убедимся что пришли предложения от страховых. Вызовем 
REST GET /insurance/offers, выберем ID пары предложений.

Например:
```
4c3GkFiFzmn1DvcR2h2JuuMjM5sPKZwQLU12CzxgauqP_3N5ihzq38ZbHJyHCkFD36nf836BxoenN8Qx_PROPERTY,
4c3GkFiFzmn1DvcR2h2JuuMjM5sPKZwQLU12CzxgauqP_3N5xJWrQPrXnZxcvqu9W2xTJ9FgXFJYiaEX_TITUL

```

Hint: Видно что предложения пришли от разных страховых по второму сегменту ID.

Примем их, для чего оправим в REST /insurance/offers/accept JSON

```
[
  {
    "risk": "PROPERTY",
    "offerId": "8886c213892c0e4f13059c697a90b1b959bc0bbc9a6bf52462793cdfd2e9c083"
  }
]
```

Подождем обработки транзакции. После обработки транзакции заглянем
в REST /insurance/agreements.

У нас сформировались страховые договоры на каждую выбранную страховую опцию.

Затем под страховой компанией, которой адресован договор (sogaz) и подтвердим его.

Вызовем REST /insurance/agreements с ID подтверждаемого договора и параметрами:
```
{
    "payAmount": "10000",
    "policeNumber": "P-1100"
}
```

Аналогично, подтвердим от лица пользователя, зайдя под ним (user) и вызвав
REST /insurance/agreements с ID подтверждаемого договора.

Убедимся что сформиорвались полисы через вызов REST GET /insurance/polices

Затем выполним подтверждение по ID оплаты по договору последовательно у Банка:

REST /mortgage/agreements/{id}/bankAcceptsPayment

и у страховой

REST /mortgage/agreements/{id}/companyAcceptsPayment

### Технологический стек

 - Waves Enterprise (https://github.com/waves-enterprise/WE-releases)
 - Waves Enterprise SDK (https://sdk.weintegrator.com/)
 - Kotlin (https://kotlinlang.org/)
 - Spring Boot (https://spring.io/projects/spring-boot)
 - Docker (https://www.docker.com/)
 - PostgreSQL (https://www.postgresql.org/)
 
### Разработчики

 - Борис Тютин
 - Андрей Куренков

### Тестовый стенд

[Тестовый стенд](https://mortgage.easychain.tech/) (бек + фронт + блокчейн) расположен по адресу
[Поработать с API](https://mortgage.easychain.tech/api/v0/mortgage-app/swagger-ui.html) напрямую можно по ссылке
[Обозреватель Blockchain](https://explorer.mortgage-dev.weintegrator.com/)  (логин/пароль: Test1@test.ru/Test1@test.ru)
