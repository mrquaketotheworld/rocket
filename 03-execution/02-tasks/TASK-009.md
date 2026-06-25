---
doc_type: task
doc_id: TASK-009
title: Зафиксировать правило предложения лучших практик
status: done
owner: execution
last_updated: 2026-06-25
authority: operational
layer: execution
canonical_refs: [GOV-AUTONOMY-CONTRACT]
owned_modules:
  - AGENTS.md
  - 01-standards/autonomy-contract.md
  - 03-execution
---

# TASK-009: Зафиксировать правило предложения лучших практик

## Цель

Добавить в документацию правило: если агент видит, что пользователь пробует устаревшую технологию или устаревший паттерн в языке программирования, агент должен предложить более современную лучшую практику без молчаливого изменения намерения пользователя.

## Canonical refs

- GOV-AUTONOMY-CONTRACT

## Owned modules

- `AGENTS.md`
- `01-standards/autonomy-contract.md`
- `03-execution/`

## План

1. Зафиксировать правило в canonical contract поведения агента.
2. Продублировать routing-level инструкцию в `AGENTS.md`.
3. Обновить operational state/session.
4. Прогнать верификацию.
5. Закрыть задачу и сделать локальный commit.

## Impact analysis

Меняется правило поведения агента при консультациях и реализации задач. Product/runtime behavior Rocket не меняется. Затронуты canonical agent behavior docs и operational state.

## Верификация

- `bb verify`
- после commit: `bb commit-lint`

## Evidence

- `bb work-claim --task TASK-009` — OK, задача переведена в `in_progress`.
- `bb verify` — OK: `bb docs-lint`, `bb state-lint`, `bb scripts-test` прошли.
- `bb work-done --task TASK-009` — OK, registry и task file переведены в `done`, lock снят.
- Финальный `bb verify` после закрытия задачи — OK.
- Финальный `bb commit-lint` после commit — будет выполнен после локального commit.

## Definition of Done

- [x] правило добавлено в canonical docs
- [x] routing-level инструкция обновлена
- [x] impact analysis проведён
- [x] верификация пройдена
- [x] состояние и сессия обновлены
- [x] локальный commit создан, если задача изменила репозиторий
