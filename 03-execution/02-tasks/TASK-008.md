---
doc_type: task
doc_id: TASK-008
title: Добавить verify, task-aware commit-lint и TODO lint
status: done
owner: execution
last_updated: 2026-06-24
authority: operational
layer: execution
canonical_refs: [GOV-GIT-WORKFLOW, GOV-EXECUTION-STATE-CONTRACT, PROJ-ENGINEERING]
owned_modules:
  - 01-standards/git-workflow.md
  - 01-standards/execution-state-contract.md
  - 02-foundation/02-project/engineering-profile.md
  - 03-execution
  - 05-scripts
  - TODO.md
  - bb.edn
---

# TASK-008: Добавить verify, task-aware commit-lint и TODO lint

## Цель

Реализовать три улучшения: единую команду `bb verify`, проверку существования TASK ID в `bb commit-lint` и lint transient-правила `TODO.md`.

## Canonical refs

- GOV-GIT-WORKFLOW
- GOV-EXECUTION-STATE-CONTRACT
- PROJ-ENGINEERING

## Owned modules

- `01-standards/git-workflow.md`
- `01-standards/execution-state-contract.md`
- `02-foundation/02-project/engineering-profile.md`
- `03-execution/`
- `05-scripts/`
- `TODO.md`
- `bb.edn`

## План

1. Подтвердить canonical rules и обновить docs под три улучшения.
2. Добавить `bb verify`.
3. Усилить `bb commit-lint`: проверять существование `TASK-XXX` в registry.
4. Усилить `bb state-lint`: проверять transient-правило `TODO.md`.
5. Обновить smoke tests.
6. Прогнать verification, закрыть задачу, commit `TASK-008: ...`.

## Impact analysis

Меняется automation для верификации и Git discipline. Product behavior downstream не меняется.

## Верификация

- `bb verify`
- `bb docs-lint`
- `bb state-lint`
- `bb scripts-test`
- после commit: `bb commit-lint`

## Evidence

- `bb work-claim --task TASK-008` — OK, задача переведена в `in_progress`.
- `bb verify` — OK: `bb docs-lint`, `bb state-lint`, `bb scripts-test` прошли.
- `bb work-done --task TASK-008` — OK, registry и task file переведены в `done`, lock снят.
- Финальный `bb verify` — OK.
- Финальный `bb commit-lint` после commit — будет выполнен после локального commit.

## Definition of Done

- [x] `bb verify` добавлен
- [x] `bb commit-lint` проверяет существование task ID
- [x] `bb state-lint` проверяет transient-правило `TODO.md`
- [x] docs обновлены
- [x] verification пройдена
- [x] локальный commit создан в формате `TASK-008: ...`
