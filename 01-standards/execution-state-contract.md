---
doc_type: standards
doc_id: GOV-EXECUTION-STATE-CONTRACT
title: Контракт операционного состояния
status: active
owner: framework-core
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# Контракт операционного состояния

## Назначение

Фиксирует формат и lifecycle артефактов в [03-execution/](../03-execution). Эти документы не
владеют правдой, но должны быть достаточно точными, чтобы агент мог безопасно продолжить работу
после паузы или смены сессии.

## task-registry.edn

`03-execution/02-tasks/task-registry.edn` содержит:

- `:doc_id` — всегда `"TASK-REGISTRY"`;
- `:last_updated` — дата последнего изменения реестра;
- `:next_id` — следующий свободный номер задачи;
- `:tasks` — вектор задач.

Запись задачи содержит:

- `:id` — `TASK-XXX`;
- `:title` — краткое название;
- `:status` — `ready`, `in_progress`, `done`, `superseded` или `cancelled`;
- `:canonical_refs` — вектор canonical `doc_id`, к которым привязана задача;
- `:owned_modules` — пути или директории, которые меняет задача.

ID считается занятым только когда есть и запись в реестре, и файл задачи.

## Файл задачи

Файл `03-execution/02-tasks/TASK-XXX.md` создаётся из
[task.md](../04-templates/task.md). Он должен содержать:

- цель;
- canonical refs;
- owned modules;
- план;
- impact analysis для изменений canonical truth или поведения;
- evidence верификации;
- Definition of Done.

Статус в frontmatter и статус в реестре должны совпадать.

## current-state.edn

`03-execution/01-state/current-state.edn` — короткий снимок состояния проекта:

- `:active_tasks` — активные задачи (`in_progress`);
- `:blockers` — текущие блокеры;
- `:health` — `green`, `yellow` или `red`;
- `:canonical_refs` — canonical документы, важные для текущего состояния;
- `:verification_summary` — последняя значимая верификация и её результат.

Этот файл не заменяет task registry и не хранит историю.

## current-session.md и logs

`03-execution/03-session/current-session.md` хранит текущий рабочий контекст:

- текущий фокус;
- выходы текущей сессии;
- следующие шаги;
- входные canonical refs.

При начале новой крупной сессии старый `current-session.md` копируется в
`03-execution/04-logs/YYYY-MM-DD-<slug>.md`, после чего `current-session.md` сбрасывается на
новый фокус. Логи не владеют правдой и нужны только для истории.

## Gaps

Если обнаружена отсутствующая или конфликтующая canonical truth, агент фиксирует это как задачу
со статусом `ready` или `blocked`-описанием в задаче, а в `current-state.edn` добавляет блокер.
Реализация поведения останавливается до canonical resolution.

## Верификация состояния

Перед закрытием задачи должны быть согласованы:

- реестр задач;
- файл задачи;
- current state;
- current session;
- локальный Git commit, если задача внесла изменения в репозиторий.
