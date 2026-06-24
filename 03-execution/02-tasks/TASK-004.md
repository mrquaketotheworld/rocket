---
doc_type: task
doc_id: TASK-004
title: Закрыть оставшиеся foundation gaps без language pack
status: done
owner: execution
last_updated: 2026-06-24
authority: operational
layer: execution
canonical_refs: [GOV-GIT-WORKFLOW, GOV-EXECUTION-STATE-CONTRACT, PROJ-ENGINEERING, FEAT-ROCKET-SKELETON]
owned_modules:
  - README.md
  - CHANGELOG.md
  - VERSION
  - 01-standards
  - 02-foundation
  - 03-execution
  - 04-templates
  - 05-scripts
  - bb.edn
---

# TASK-004: Закрыть оставшиеся foundation gaps без language pack

## Цель

Исправить приоритетные gaps документации и автоматизации, кроме language pack: worktree/merge противоречие, state consistency lint, downstream init/export/reset lifecycle, versioning/changelog.

## Canonical refs

- GOV-GIT-WORKFLOW
- GOV-EXECUTION-STATE-CONTRACT
- PROJ-ENGINEERING
- FEAT-ROCKET-SKELETON

## Owned modules

- `README.md`
- `CHANGELOG.md`
- `VERSION`
- `01-standards/`
- `02-foundation/`
- `03-execution/`
- `04-templates/`
- `05-scripts/`
- `bb.edn`

## План

1. Устранить противоречие в Git/worktree workflow: work-done не мержит автоматически.
2. Добавить state consistency lint как docs + команду `bb state-lint`.
3. Описать downstream init/export/reset lifecycle.
4. Добавить versioning/changelog policy и `CHANGELOG.md`.
5. Обновить индексы, шаблоны и verification ladder.
6. Прогнать верификацию, закрыть задачу, сделать commit.

## Impact analysis

Затрагиваются standards, feature docs, lightweight scripts и operational state. Language pack намеренно не реализуется и не документируется сверх уже существующего общего контракта языковой расширяемости.

## Верификация

- `bb docs-lint`
- `bb state-lint`
- smoke: `bb work-claim --task TASK-004`
- smoke: `bb work-done --task TASK-004`

## Evidence

- `bb work-claim --task TASK-004` — OK, задача переведена в `in_progress`.
- `bb docs-lint` — OK.
- `bb state-lint` — OK.
- `bb work-done --task TASK-004` — OK, registry и task file переведены в `done`, lock снят.
- Финальный `bb docs-lint` — OK.
- Финальный `bb state-lint` — OK.

## Definition of Done

- [x] Git/worktree docs не утверждают, что `work-done` делает merge автоматически
- [x] `state-lint` описан и реализован
- [x] downstream init/export/reset lifecycle описан
- [x] versioning/changelog policy добавлена
- [x] `CHANGELOG.md` добавлен или обновлён
- [x] verification ladder обновлена
- [x] верификация пройдена (`bb docs-lint`, `bb state-lint`)
- [x] состояние и сессия обновлены
- [x] локальный commit создан
