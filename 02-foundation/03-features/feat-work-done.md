---
doc_type: feature
doc_id: FEAT-WORK-DONE
title: Закрытие задачи
status: active
owner: product
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# FEAT-WORK-DONE: Закрытие задачи

## Назначение

`bb work-done --task TASK-XXX` закрывает активный claim задачи после выполненной работы и
пройденной верификации.

## Поведение

Перед запуском `work-done` агент должен:

1. выполнить scope задачи;
2. обновить файл задачи и evidence;
3. обновить `current-state.edn` и `current-session.md`;
4. прогнать verification ladder;
5. убедиться, что задача готова к статусу `done`.

Команда:

- проверяет наличие активного claim;
- переводит задачу в реестре в `done`;
- удаляет lock-файл;
- напоминает, что push в remote запрещён без явной команды человека.

После `work-done` агент делает локальный commit, если задача изменила репозиторий.

## Acceptance criteria

- [ ] без активного claim команда завершается ошибкой;
- [ ] успешная команда переводит запись реестра в `done`;
- [ ] успешная команда удаляет lock-файл;
- [ ] task file, state и session согласованы до закрытия задачи;
- [ ] remote push не выполняется автоматически.

## Edge cases

- Если verification failed и агент не может исправить проблему в scope задачи, задача не
  закрывается и фиксируется blocker.
- Если изменения не затрагивали репозиторий, commit не требуется.

## Связанные решения

- ADR-0001
- ADR-0002
