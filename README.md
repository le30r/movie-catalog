# movie-catalog

Разработать консольное приложение для ведения каталога фильмов.
Информация о фильмах хранится в отдельном файле, формата csv. Каждый фильм имеет название, список жанров, режиссера и список актеров.
Есть предустановленный набор фильмов, которые лежат в файле, расположенном в ресурсах приложения, но так же, можно загружать фильмы из файла, путь к которому хранится в конфигурационном файле приложения. Полный каталог должен включать и предустановленные и внешние данные о фильмах.
Приложение должно уметь:
- выводить список фильмов по жанру;
- выводить список фильмов по актеру или режиссеру;
- выводить список фильмов по вхождению строки в название;
- выводить полную информацию по выбранному фильму;
- создать/изменить/удалить фильм (работа ведется только с фильмами во внешнем файле);

Требования:
- Использовать Spring/Spring Boot;
- Конфигурировать контекст преимущественно через аннотации;
- Предусмотреть кэширование информации о фильмах, жанрах и актерах. Кэш должен быть нашим собственным бином.
- В приложени должная присутствовать доменная модель;
- У приложения должная быть архитектура и соблюдены принципы SOLID;
- Отношение, как к реальному проекту, который может развиваться в дальнейшем до невиданных высот;
- Декомпозировать задачу на подзадачи. Обсудить полученный результат.
- Каждую подзадачу сдавать через PR на github/gitlab.


Подзадачи:
- [x] 1 Конфигурация приложения Spring
- [x] 2 Разработка доменной модели 
- [x] 3 Разработка слоя для работы с данными  
- [ ] 4 Разработка сервисного слоя 
  - [ ] 4.1 Вывод списка фильмов по жанру
  - [ ] 4.2 Вывод списка фильмов по актеру или режиссеру
  - [ ] 4.3 Вывод списка фильмов по вхождению строки в название
  - [ ] 4.4 Вывод полной информации по выбранному фильму
  - [ ] 4.5 Создание фильма
  - [ ] 4.6 Удаление фильма
  - [ ] 4.7 Изменение фильма
- [ ] 5 Разработка слоя представления 
- [ ] 5 Добавление кэша 