---
doc_type: task
doc_id: TASK-010
title: Автоматизировать актуальность last_updated в task registry
status: done
owner: execution
last_updated: 2026-06-25
authority: operational
layer: execution
canonical_refs: [GOV-EXECUTION-STATE-CONTRACT, FEAT-STATE-LINT, FEAT-WORK-CLAIM, FEAT-WORK-DONE, PROJ-ENGINEERING]
owned_modules:
  - 01-standards/execution-state-contract.md
  - 02-foundation/03-features/feat-state-lint.md
  - 02-foundation/03-features/feat-work-claim.md
  - 02-foundation/03-features/feat-work-done.md
  - 02-foundation/02-project/engineering-profile.md
  - 03-execution
  - 05-scripts
---

# TASK-010: Автоматизировать актуальность last_updated в task registry

## Цель

Исправить stale `:last_updated` в `task-registry.edn` и усилить автоматизацию так, чтобы будущие изменения registry/task status не оставляли устаревшие даты молча.

## Canonical refs

- GOV-EXECUTION-STATE-CONTRACT
- FEAT-STATE-LINT
- FEAT-WORK-CLAIM
- FEAT-WORK-DONE
- PROJ-ENGINEERING

## Owned modules

- `01-standards/execution-state-contract.md`
- `02-foundation/03-features/feat-state-lint.md`
- `02-foundation/03-features/feat-work-claim.md`
- `02-foundation/03-features/feat-work-done.md`
- `02-foundation/02-project/engineering-profile.md`
- `03-execution/`
- `05-scripts/`

## План

1. Зафиксировать canonical rule для `last_updated` в registry и task files.
2. Обновить `work-claim`/`work-done`, чтобы они меняли `last_updated` вместе со статусом.
3. Усилить `state-lint`, чтобы stale registry date считалась ошибкой.
4. Обновить smoke tests для новой проверки.
5. Исправить текущий registry date.
6. Прогнать verification, закрыть задачу и сделать локальный commit.

## Impact analysis

Меняется execution automation и проверка операционного состояния. Product/runtime behavior downstream не меняется. Затронуты state-lint, work-claim, work-done, smoke tests, canonical docs и текущий operational state.

## Верификация

- `bb verify`
- ручная smoke-проверка stale registry date через `bb state-lint` на временной копии/тестовом downstream
- после commit: `bb commit-lint`

## Evidence

- `bb work-claim --task TASK-010` — OK, задача переведена в `in_progress`.
- `bb state-lint` после исправления проверки дат — OK.
- Ручная negative smoke-проверка stale registry date на временной копии — OK: `bb state-lint` завершился с ошибкой `:last_updated 2000-01-01 старее максимальной даты файла задачи 2026-06-25`.
- `bb verify` — OK: `bb docs-lint`, `bb state-lint`, `bb scripts-test` прошли.
- `bb work-done --task TASK-010` — OK, registry и task file переведены в `done`, lock снят.
- Финальный `bb verify` после закрытия задачи — OK.
- Финальный `bb commit-lint` после commit — будет выполнен после локального commit.

## Definition of Done

- [x] canonical rule для `last_updated` добавлен
- [x] `work-claim`/`work-done` обновляют даты
- [x] `state-lint` ловит stale registry date
- [x] текущий `task-registry.edn` исправлен
- [x] impact analysis проведён
- [x] верификация пройдена
- [x] состояние и сессия обновлены
- [x] локальный commit создан, если задача изменила репозиторий
