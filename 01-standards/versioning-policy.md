---
doc_type: standards
doc_id: GOV-VERSIONING-POLICY
title: Политика версионирования Rocket
status: active
owner: framework-core
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# Политика версионирования Rocket

## Назначение

Фиксирует, как версия Rocket меняется и как downstream-проекты понимают, на какой версии
фреймворка они основаны.

## Источник версии

Текущая версия core Rocket хранится в `VERSION` и дублируется в верхнем `README.md` для человека.
При изменении версии оба места обновляются одной задачей.

## SemVer

Rocket использует SemVer:

- **PATCH** — исправления документации, lint, скриптов без изменения контрактов;
- **MINOR** — новые features, scripts, templates или language-extensibility возможности без
  нарушения совместимости;
- **MAJOR** — breaking changes в структуре docs, execution lifecycle, Git-модели или контрактах.

## CHANGELOG

Каждая пользовательски значимая задача обновляет `CHANGELOG.md` в секции `Unreleased`.
При выпуске версии секция `Unreleased` переносится под номер версии и дату.

## Downstream migration

Downstream-проект должен хранить версию Rocket, от которой он был создан или к которой был
обновлён, в своём `current-state.edn :framework_version` и/или README.

Миграция между версиями выполняется отдельной задачей:

1. прочитать `CHANGELOG.md`;
2. определить breaking changes;
3. обновить standards/templates/scripts;
4. сохранить downstream product truth;
5. прогнать verification ladder downstream-проекта;
6. зафиксировать migration notes в task evidence.

## Граница

Release branches и remote publication не являются частью обычного workflow Rocket. Публикация
версии наружу выполняется только по явной команде человека.
