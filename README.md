# pastebox
## Описание
Бэкенд (REST API) для сервиса аналогичного pastebin.com - сервис позволяет заливать куски текста/кода ("пасту") и получать на них короткую ссылку, которую можно отправить другим
При загрузке "пасты" пользователь указывает:
﻿﻿﻿Срок, в течение которого "паста" будет доступна по ссылке (expiration time) 10 мин, 1 час, 3 часа, 1 день, 1 неделя, 1 месяц, после окончания срока получить доступ к "пасте" нельзя, в том числе и автору
﻿﻿﻿Ограничение доступа: public - доступна всем unlisted - доступна только по ссылке. Для загруженной пасты выдается короткая ссылка вида
http://my-awesome-pastebin.tld/{какой-то-рандомный-хеш}, например
http://my-awesome-pastebin.tld/ab12cd34
Пользователи могут получить информацию о последних 10 загруженных публичных "пастах".

## Языки и инструменты
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?&style=for-the-badge&logo=java&logoColor=white")
![Spring](https://img.shields.io/badge/spring%20-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white")
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?&style=for-the-badge&logo=postgresql&logoColor=white")
