---
doc_type: project
doc_id: PROJ-ENGINEERING
title: Инженерный профиль
status: active
owner: project
last_updated: 2026-06-23
authority: canonical
layer: knowledge
---

# Инженерный профиль

## Назначение

Фиксирует стек, инструменты и команды, достаточные для восстановления и запуска проекта. Это
ключевой документ для reconstructability: по нему агент знает, как поднять проект.

> Для downstream-проекта на Rocket этот файл переписывается под его собственный стек. Ниже —
> профиль самого фреймворка Rocket.

## Стек фреймворка Rocket

- **Язык продукта (downstream):** любой. Фиксируется в `engineering-profile.md` самого проекта.
- **Язык автоматизации фреймворка:** Babashka / Clojure — только для скриптов фреймворка
  (`05-scripts`), если это удобно. Продукту не навязывается.
- **Формат канонических и операционных артефактов:** Markdown с YAML-frontmatter и EDN для
  машиночитаемых реестров/состояния.

## Обязательные инструменты pi

Для работы агента в проекте обязательно должны быть установлены следующие инструменты pi —
глобально или в самом проекте. Без них работа не начинается (см. stop condition
«отсутствующий инструмент»):

- **pi-hermes-memory** — pi extension, устанавливается командой
  `pi install npm:pi-hermes-memory`.
- **codespaces** — pi skill, устанавливается командой
  `npx skills add diskd-ai/codespaces`.

Проверь наличие этого extension и skill до взятия первой задачи; при отсутствии — установи их
указанными командами.

## Команды

Команды самого фреймворка Rocket:

- **bootstrap:** установить Babashka и обязательные инструменты pi, затем выполнить
  `bb docs-lint` для проверки готовности документации.
- **run:** N/A для core Rocket — это документационный фреймворк и набор локальных скриптов, а
  не долгоживущий сервис. Запуск downstream-продукта объявляется в его engineering profile.
- **test:** `bb docs-lint` и `bb state-lint`.
- **build:** N/A для core Rocket v0.1.0 — артефакт сборки не производится. Если появится
  packaging/export, команда build станет обязательной частью этого профиля.
- **lint документации:** `bb docs-lint`.
- **lint состояния:** `bb state-lint`.
- **создать downstream skeleton:** `bb project-init --target PATH`.
- **smoke-тесты скриптов:** `bb scripts-test`.
- **полная pre-commit верификация:** `bb verify`.
- **проверка commit message:** `bb commit-lint`.
- **установка Git hooks:** `bb install-hooks`.
- **взять задачу:** `bb work-claim --task TASK-XXX`.
- **закрыть задачу:** `bb work-done --task TASK-XXX`.

Для downstream-проекта здесь обязаны быть объявлены его собственные команды:
**bootstrap** (установка зависимостей), **run** (запуск), **test** (тесты),
**build** (сборка или явное N/A-обоснование, если продукт не собирается). Без них
reconstructability не выполняется.

## Лестница верификации

Для изменений в самом фреймворке:

1. `bb verify` — полная pre-commit проверка (`bb docs-lint`, `bb state-lint`, `bb scripts-test`).
2. Для точечной диагностики можно запускать отдельные команды: `bb docs-lint`, `bb state-lint`,
   `bb scripts-test`.
3. Для изменений скриптов — `bb scripts-test` и/или ручная smoke-проверка затронутой команды
   (`bb work-claim`, `bb work-done`, `bb state-lint`, `bb project-init`, `bb commit-lint`) на
   тестовой/текущей задаче в пределах scope.
4. После локального commit — `bb commit-lint`.

Downstream-проект объявляет свою лестницу (например: `npm run lint`, `npm test`, `npm run build`).

## Граница reconstructability

Если для проекта не определены работающие команды run/test/build, или feature docs
противоречат друг другу — реализация блокируется до canonical resolution (см.
[sync-rules.md](../../01-standards/sync-rules.md)).
