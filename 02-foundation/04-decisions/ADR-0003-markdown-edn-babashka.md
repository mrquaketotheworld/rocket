---
doc_type: adr
doc_id: ADR-0003
title: Markdown, EDN и Babashka для лёгкой автоматизации
status: accepted
owner: architecture
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# ADR-0003: Markdown, EDN и Babashka для лёгкой автоматизации

## Статус

Accepted.

## Контекст

Rocket должен быть лёгким, читаемым человеком и восстанавливаемым без тяжёлой инфраструктуры.
Документы должны быть удобны для человека, а operational state — достаточно структурирован для
скриптов.

## Решение

Использовать:

- Markdown с YAML-frontmatter для canonical docs, задач, сессий и шаблонов;
- EDN для машиночитаемых registry/state файлов;
- Babashka/Clojure для небольших скриптов автоматизации самого Rocket.

Эти технологии относятся к фреймворку Rocket и не навязываются downstream-продуктам.

## Последствия

- Документы легко читать и редактировать вручную.
- EDN state можно безопасно читать и обновлять скриптами.
- Babashka даёт быстрые локальные команды без полноценного build system.
- Downstream-проект сохраняет свободу выбора языка и инструментов.

## Границы

- ADR не требует Clojure/Babashka от продукта downstream-проекта.
- ADR не запрещает добавить language-specific automation в language pack.
- ADR не вводит базу данных или remote coordination для state.
