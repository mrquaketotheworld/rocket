---
doc_type: task
doc_id: TASK-006
title: Зафиксировать transient-правило TODO.md
status: done
owner: execution
last_updated: 2026-06-24
authority: operational
layer: execution
canonical_refs: [GOV-EXECUTION-MODEL, GOV-EXECUTION-STATE-CONTRACT]
owned_modules:
  - TODO.md
  - 01-standards/execution-model.md
  - 01-standards/execution-state-contract.md
  - 03-execution
---

# TASK-006: Зафиксировать transient-правило TODO.md

## Цель

Зафиксировать в правилах, что `TODO.md` — временный рабочий файл агента для текущих назначенных задач, а не глобальный backlog, и очистить его, если все пункты выполнены.

## Canonical refs

- GOV-EXECUTION-MODEL
- GOV-EXECUTION-STATE-CONTRACT

## Owned modules

- `TODO.md`
- `01-standards/execution-model.md`
- `01-standards/execution-state-contract.md`
- `03-execution/`

## План

1. Добавить правило `TODO.md` в canonical execution docs.
2. Очистить `TODO.md`, так как все текущие пункты выполнены.
3. Обновить execution state/session/task.
4. Прогнать верификацию и закрыть задачу.

## Impact analysis

Меняется операционное правило работы агента с transient TODO-файлом. Product behavior не меняется.

## Верификация

- `bb docs-lint`
- `bb state-lint`

## Evidence

- `bb work-claim --task TASK-006` — OK, задача переведена в `in_progress`.
- `bb docs-lint` — OK.
- `bb state-lint` — OK.
- `TODO.md` очищен, так как все пункты из предыдущего списка выполнены.
- `bb work-done --task TASK-006` — OK, registry и task file переведены в `done`, lock снят.
- Финальный `bb docs-lint` — OK.
- Финальный `bb state-lint` — OK.

## Definition of Done

- [x] правило `TODO.md` добавлено в canonical docs
- [x] `TODO.md` очищен после завершения всех пунктов
- [x] execution state/session обновлены
- [x] verification пройдена
- [x] локальный commit создан
