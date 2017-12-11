
Приложение поставляется в виде war файла.

Для его запуска необходимо:
1) Подкючить на машине базу данных MySql (url=jdbc:mysql://localhost/lportal username=root password=root)
2) Выполнить скрипт schema.sql (не смог исправить баг при инициализации DB)
3) Иметь на машине установленный и запущенный портал Liferay. Для тестирования использовалась версия liferay-ce-portal-7.0-ga4
    со встроенным tomcat-8.0.32 (если версия отличается, исправить properties в pom.xml)
4) При запуске Tomcat указать пустую переменную CATALINA_BASE
5) Установить переменную окружения LIFERAY_HOME со ссылкой на базовую директорию портала.
6) Разместить war файл в директории deploy портала
7) Обладая правами администратора, добавить портлет HhVacancies на страницу портала (Add -> Application -> Sample -> HhVacancies)

Код приложения доступен на https://github.com/Kowi77/HhVacanciesService

