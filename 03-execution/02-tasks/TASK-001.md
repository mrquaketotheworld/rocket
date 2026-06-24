---
doc_type: task
doc_id: TASK-001
title: Уточнить типы обязательных инструментов pi
status: done
owner: execution
last_updated: 2026-06-24
authority: operational
layer: execution
canonical_refs: [PROJ-ENGINEERING]
owned_modules:
  - AGENTS.md
  - 02-foundation/02-project/engineering-profile.md
---

# TASK-001: Уточнить типы обязательных инструментов pi

## Цель

В документации явно различить обязательные инструменты pi: `pi-hermes-memory` как pi extension и `codespaces` как pi skill.

## Canonical refs

- PROJ-ENGINEERING

## Owned modules

- `AGENTS.md`
- `02-foundation/02-project/engineering-profile.md`

## План

1. Обновить формулировку в `AGENTS.md`.
2. Обновить раздел обязательных инструментов в `engineering-profile.md`.
3. Прогнать документационную верификацию.
4. Закрыть задачу и обновить состояние исполнения.

## Верификация

- `bb docs-lint`

## Evidence

- `bb docs-lint` — OK.
- Commit: `5c46f50 docs: clarify required pi tools`.

## Definition of Done

- [x] документация уточняет, что `pi-hermes-memory` — pi extension
- [x] документация уточняет, что `codespaces` — pi skill
- [x] верификация пройдена (`bb docs-lint`)
- [x] состояние и сессия обновлены
