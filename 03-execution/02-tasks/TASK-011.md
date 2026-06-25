---
doc_type: task
doc_id: TASK-011
title: Добавить матрицу разрешения конфликтов truth
status: done
owner: execution
last_updated: 2026-06-25
authority: operational
layer: execution
canonical_refs: [GOV-DOCUMENT-PRINCIPLES, GOV-SYNC-RULES]
owned_modules:
  - 01-standards/document-principles.md
  - 01-standards/sync-rules.md
  - 03-execution
---

# TASK-011: Добавить матрицу разрешения конфликтов truth

## Цель

Добавить в canonical docs практическую матрицу conflict resolution: какой источник побеждает при типовых конфликтах между canonical docs, operational state, README, кодом и тестами, и что должен делать агент.

## Canonical refs

- GOV-DOCUMENT-PRINCIPLES
- GOV-SYNC-RULES

## Owned modules

- `01-standards/document-principles.md`
- `01-standards/sync-rules.md`
- `03-execution/`

## План

1. Добавить матрицу разрешения конфликтов в canonical docs.
2. Обновить operational state/session.
3. Прогнать verification.
4. Закрыть задачу и сделать локальный commit.

## Impact analysis

Меняется только документация правил разрешения конфликтов. Код, runtime behavior и automation не меняются.

## Верификация

- `bb verify`
- после commit: `bb commit-lint`

## Evidence

- `bb work-claim --task TASK-011` — OK, задача переведена в `in_progress`.
- `bb verify` — OK: `bb docs-lint`, `bb state-lint`, `bb scripts-test` прошли.
- `bb work-done --task TASK-011` — OK, registry и task file переведены в `done`, lock снят.
- Финальный `bb verify` после закрытия задачи — OK.
- Финальный `bb commit-lint` после commit — будет выполнен после локального commit.

## Definition of Done

- [x] матрица добавлена в canonical docs
- [x] impact analysis проведён
- [x] верификация пройдена
- [x] состояние и сессия обновлены
- [x] локальный commit создан, если задача изменила репозиторий
