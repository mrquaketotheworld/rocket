---
doc_type: standards
doc_id: GOV-LOCALIZATION-IDS
title: Язык документации и идентификаторы
status: active
owner: framework-core
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# Язык документации и идентификаторы

## Назначение

Предотвращает language drift в документах и задаёт единые conventions для `doc_id`, имён файлов и
человекочитаемого текста.

## Язык

- Core-документация Rocket ведётся на русском языке.
- Downstream-проект может выбрать язык документации в своём `engineering-profile.md`.
- В пределах одного downstream-проекта canonical docs должны использовать один основной язык.
- Технические ключи (`doc_id`, EDN keys, команды, имена файлов) остаются ASCII/English-style.

## Имена файлов

- Markdown-файлы именуются lowercase kebab-case: `document-lifecycle.md`.
- Feature-файлы именуются `feat-<slug>.md` или `feat-<slug>/README.md`.
- ADR-файлы именуются `ADR-XXXX-<slug>.md`.
- Task-файлы именуются `TASK-XXX.md`.

## doc_id conventions

- `GOV-*` — uppercase kebab-case: `GOV-DOCUMENT-LIFECYCLE`.
- `PROD-*` — project/product truth: `PROD-INTENT`.
- `PROJ-*` — project-level truth: `PROJ-ENGINEERING`.
- `FEAT-*` — uppercase kebab-case: `FEAT-STATE-LINT`.
- `ADR-*` — numeric: `ADR-0001`.
- `TASK-*` — numeric: `TASK-001`.
- `STATE-*`, `SESSION-*` — operational singleton IDs.

`doc_id` стабилен: переименование файла или заголовка не меняет `doc_id`. Смена `doc_id`
допустима только как supersede/replacement по [document-lifecycle.md](document-lifecycle.md).

## Якоря и ссылки

Markdown-заголовки могут быть на выбранном языке документации. Относительные ссылки должны быть
проверяемыми `bb docs-lint`; при сомнении предпочтительны ссылки на файл без якоря.
