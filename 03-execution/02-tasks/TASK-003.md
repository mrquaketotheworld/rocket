---
doc_type: task
doc_id: TASK-003
title: Закрыть фундаментальные пробелы документации Rocket
status: done
owner: execution
last_updated: 2026-06-24
authority: operational
layer: execution
canonical_refs: [PROD-INTENT, PROD-SCOPE, PROJ-ENGINEERING, GOV-FOUNDATION-CONTRACT, GOV-EXECUTION-MODEL, GOV-GIT-WORKFLOW, GOV-DOC-CONTRACT]
owned_modules:
  - README.md
  - AGENTS.md
  - 01-standards
  - 02-foundation
  - 03-execution
  - 04-templates
  - 05-scripts
---

# TASK-003: Закрыть фундаментальные пробелы документации Rocket

## Цель

Закрыть найденные gaps документации и привести фреймворк к состоянию, где документация является фундаментом для восстановления, расширения языками и агентной работы.

## Canonical refs

- PROD-INTENT
- PROD-SCOPE
- PROJ-ENGINEERING
- GOV-FOUNDATION-CONTRACT
- GOV-EXECUTION-MODEL
- GOV-GIT-WORKFLOW
- GOV-DOC-CONTRACT

## Owned modules

- `README.md`
- `AGENTS.md`
- `01-standards/`
- `02-foundation/`
- `03-execution/`
- `04-templates/`
- `05-scripts/`

## План

1. Добавить недостающие feature docs для реализованного поведения.
2. Уточнить reconstructability, bootstrap/run/test/build и расширяемость языков.
3. Добавить контракты execution state, security/secrets, memory, gaps, session/log lifecycle.
4. Зафиксировать commit discipline и параллельный worktree workflow.
5. Добавить ADR для Markdown/EDN/Babashka и языковой расширяемости.
6. Обновить шаблоны и индексы.
7. При необходимости привести лёгкую автоматизацию к задокументированному lifecycle.
8. Прогнать верификацию и обновить execution state.

## Impact analysis

Затронуты canonical standards, project docs, feature/ADR indexes, task template и лёгкая автоматизация claim/done. Изменения направлены на устранение gaps без привязки Rocket к конкретному языку программирования: language-specific детали вынесены в engineering profile и language packs.

## Верификация

- `bb docs-lint`
- smoke: `bb work-claim --task TASK-003` перевёл задачу в `in_progress` и создал lock
- smoke: `bb work-done --task TASK-003` должен перевести задачу в `done` и снять lock

## Evidence

- `bb docs-lint` — OK до закрытия задачи.
- `bb work-claim --task TASK-003` — OK, lock создан, статус синхронизирован вручную из-за изменения скрипта во время задачи.
- `bb work-done --task TASK-003` — OK, registry и task file переведены в `done`, lock снят.
- Финальный `bb docs-lint` — OK.

## Definition of Done

- [x] feature docs добавлены для существующих команд и каркаса Rocket
- [x] reconstructability покрыта для Rocket и downstream-проектов
- [x] execution state/session/log/gap/commit/security/memory правила описаны
- [x] ADR и product/project docs отражают stack-agnostic и language-extensible модель
- [x] шаблоны обновлены под новые правила
- [x] автоматизация не противоречит lifecycle документации
- [x] верификация пройдена (`bb docs-lint`)
- [x] состояние и сессия обновлены
