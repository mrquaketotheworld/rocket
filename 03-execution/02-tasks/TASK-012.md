---
doc_type: task
doc_id: TASK-012
title: Добавить обязательные skills brainstorming и find-skills
status: done
owner: execution
last_updated: 2026-06-25
authority: operational
layer: execution
canonical_refs: ["PROJ-ENGINEERING"]
owned_modules:
  - AGENTS.md
  - 02-foundation/02-project/engineering-profile.md
  - 03-execution
---

# TASK-012: Добавить обязательные skills brainstorming и find-skills

## Цель

В canonical docs и маршрутизации агента зафиксировано, что для работы в Rocket обязательны pi skills `brainstorming` из `obra/superpowers` и `find-skills`.

## Canonical refs

- PROJ-ENGINEERING

## Owned modules

- `AGENTS.md`
- `02-foundation/02-project/engineering-profile.md`
- `03-execution`

## План

1. Обновить список обязательных инструментов pi в `engineering-profile.md`.
2. Обновить краткое правило в `AGENTS.md`.
3. Обновить operational state и session.
4. Прогнать `bb verify`.

## Impact analysis

Затронуты canonical project engineering docs и агентная маршрутизация. Код скриптов не меняется. Feature docs и ADR не требуют изменения, потому что поведение продукта/архитектура не меняются.

## Верификация

- `bb verify`

## Evidence

- `bb verify` — OK:
  - `bb docs-lint` — OK, проверено файлов 51
  - `bb state-lint` — OK, проверено задач 12
  - `bb scripts-test` — OK

## Definition of Done

- [x] изменение выполнено по canonical docs
- [x] impact analysis проведён
- [x] верификация пройдена
- [x] состояние и сессия обновлены
- [x] локальный commit создан, если задача изменила репозиторий
