---
doc_type: task
doc_id: TASK-002
title: Зафиксировать правило no-edit-on-question
status: done
owner: execution
last_updated: 2026-06-24
authority: operational
layer: execution
canonical_refs: [GOV-AUTONOMY-CONTRACT]
owned_modules:
  - AGENTS.md
  - 01-standards/autonomy-contract.md
---

# TASK-002: Зафиксировать правило no-edit-on-question

## Цель

Зафиксировать в правилах Rocket, что вопрос пользователя означает запрос на объяснение/информацию, а не поручение менять файлы. Правки допустимы только при явной команде на изменение.

## Canonical refs

- GOV-AUTONOMY-CONTRACT

## Owned modules

- `AGENTS.md`
- `01-standards/autonomy-contract.md`

## План

1. Добавить правило в canonical autonomy contract.
2. Добавить краткое правило в agent routing.
3. Прогнать документационную верификацию.
4. Закрыть задачу и обновить состояние исполнения.

## Верификация

- `bb docs-lint`

## Evidence

- `bb docs-lint` — OK.
- Commit: `0bf32cb docs: treat questions as informational`.

## Definition of Done

- [x] правило зафиксировано в canonical docs
- [x] правило отражено в маршрутизации агента
- [x] верификация пройдена (`bb docs-lint`)
- [x] состояние и сессия обновлены
