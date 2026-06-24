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

- **lint документации:** `bb docs-lint`
- **взять задачу:** `bb work-claim --task TASK-XXX`
- **закрыть задачу:** `bb work-done --task TASK-XXX`

> Для downstream-проекта здесь обязаны быть объявлены его собственные команды:
> **bootstrap** (установка зависимостей), **run** (запуск), **test** (тесты),
> **build** (сборка). Без них reconstructability не выполняется.

## Лестница верификации

Для изменений в самом фреймворке:

1. `bb docs-lint` — документация консистентна.

> Downstream-проект объявляет свою лестницу (например: `npm run lint`, `npm test`, `npm run build`).

## Граница reconstructability

Если для проекта не определены работающие команды run/test/build, или feature docs
противоречат друг другу — реализация блокируется до canonical resolution (см.
[sync-rules.md](../../01-standards/sync-rules.md)).
