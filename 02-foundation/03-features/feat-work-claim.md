---
doc_type: feature
doc_id: FEAT-WORK-CLAIM
title: Claim задачи
status: active
owner: product
last_updated: 2026-06-25
authority: canonical
layer: knowledge
---

# FEAT-WORK-CLAIM: Claim задачи

## Назначение

`bb work-claim --task TASK-XXX` закрепляет задачу за агентом локальным lock-файлом и защищает
параллельную работу от пересечения `owned_modules`.

## Поведение

Команда:

1. читает `03-execution/02-tasks/task-registry.edn`;
2. проверяет, что задача существует;
3. проверяет, что задача ещё не заклеймлена;
4. сравнивает `owned_modules` с активными claim-locks;
5. создаёт `03-execution/05-locks/TASK-XXX.edn`;
6. переводит задачу в `in_progress` в registry и task file;
7. обновляет `last_updated` в task file и `:last_updated` в registry;
8. сообщает agent id и owned modules.

В single-agent режиме агент продолжает работать в `dev`. В parallel режиме worktree создаётся
по процедуре из [git-workflow.md](../../01-standards/git-workflow.md).

## Статус задачи

Claim-lock является техническим фактом владения. Статус задачи переводится в `in_progress`
автоматизацией вместе с обновлением task file/registry и их дат `last_updated`. Если
автоматизация не делает этого сама, агент обязан синхронизировать статус и даты до содержательных
правок.

## Acceptance criteria

- [ ] несуществующая задача завершается ошибкой;
- [ ] повторный claim той же задачи завершается ошибкой;
- [ ] пересечение `owned_modules` с активным claim завершается ошибкой;
- [ ] успешный claim создаёт lock-файл;
- [ ] успешный claim обновляет статус и `last_updated` в registry/task file;
- [ ] lock-файлы не коммитятся.

## Edge cases

- Пустой `owned_modules` допустим только для read-only/analysis задач; для правок он должен быть
  явным.
- `.rocket-agent-id` является локальным служебным файлом и не должен коммититься.

## Связанные решения

- ADR-0001
