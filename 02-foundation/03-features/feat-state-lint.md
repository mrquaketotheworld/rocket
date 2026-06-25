---
doc_type: feature
doc_id: FEAT-STATE-LINT
title: Проверка операционного состояния state-lint
status: active
owner: product
last_updated: 2026-06-25
authority: canonical
layer: knowledge
---

# FEAT-STATE-LINT: Проверка операционного состояния state-lint

## Назначение

`bb state-lint` проверяет, что execution layer согласован: registry, task files, current state и
session могут быть использованы агентом для продолжения работы без скрытого контекста.

## Поведение

Команда проверяет:

- валидность `task-registry.edn`;
- уникальность task id;
- корректность `next_id`;
- формат `:last_updated` в registry и отсутствие stale registry date относительно task files;
- наличие файла для каждой задачи из registry;
- совпадение статуса задачи в registry и task file frontmatter;
- обязательные секции task file;
- наличие evidence/отмеченного DoD у done-задач;
- совпадение `current-state.edn :active_tasks` с задачами `in_progress`;
- допустимое значение `:health`.

## Acceptance criteria

- [ ] при консистентном состоянии команда завершается с кодом 0;
- [ ] при несогласованности команда печатает путь и причину;
- [ ] done-задачи без evidence считаются ошибкой;
- [ ] active tasks должны совпадать с registry;
- [ ] registry `:last_updated` не должен быть старее максимального `last_updated` среди файлов задач.

## Edge cases

- `superseded` и `cancelled` задачи сохраняются в registry и должны иметь task file.
- Исторические logs не проверяются этой командой.

## Связанные решения

- ADR-0002
- ADR-0003
