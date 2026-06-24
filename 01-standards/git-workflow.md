---
doc_type: standards
doc_id: GOV-GIT-WORKFLOW
title: Git-модель
status: active
owner: framework-core
last_updated: 2026-06-23
authority: canonical
layer: knowledge
---

# Git-модель

## Назначение

Определяет, как Rocket работает с Git. Модель ориентирована на одного оператора и локальную
работу. Полное обоснование — [ADR-0001](../02-foundation/04-decisions/ADR-0001-local-git-single-operator.md).

## Ветки

- **`dev`** — единственная интеграционная ветка и текущее рабочее состояние проекта. Главная
  рабочая копия (где работает оператор и откуда запускается проект) всегда стоит на `dev`.
- **`work/<agent>/<task>`** — локальная ветка для параллельного агента, создаётся из `dev`,
  живёт в отдельном worktree.

Никаких `main`, `release/*`, `hotfix/*` и freeze-механики. Релизы и публикация — отдельное
явное действие человека, вне обычного цикла.

## "Проект слушает dev"

`dev` — то, что запускается. Когда оператор выполняет команду запуска из
`engineering-profile.md` (`npm start`, `python main.py`, и т.п.), берётся код из `dev` в
главном worktree. Поэтому `dev` всегда должен быть интегрированным, актуальным состоянием.

## Режимы работы

### Один агент

Работает прямо в `dev` главного worktree. Никакой изоляции, никаких лишних веток и папок —
минимум церемонии. Коммиты идут в `dev`.

### Несколько агентов параллельно

Чтобы параллельная работа не ломала запускаемый `dev`, каждый агент изолируется вручную:

1. человек или агент создаёт локальную ветку `work/<agent>/<task>` из `dev`;
2. для неё создаётся отдельный worktree (отдельная папка на диске);
3. агент выполняет claim задачи внутри своего worktree;
4. агент меняет файлы только в своём worktree;
5. после закрытия задачи агент делает commit в рабочей ветке;
6. merge обратно в `dev` и удаление временного worktree выполняются отдельными Git-командами,
   не командой `work-done`;
7. главный worktree всё это время остаётся на `dev` и запускаем.

Параллельные агенты безопасны только при непересекающихся `owned_modules` (см.
[execution-model.md](execution-model.md)).

## Commit discipline

Если задача изменила репозиторий, её закрытие завершается локальным commit в `dev` или в
соответствующей `work/<agent>/<task>` ветке перед merge. Одна bounded задача должна давать один
понятный commit или merge-commit.

Правила:

- не оставлять рабочее дерево грязным после завершённой задачи;
- не смешивать несколько независимых задач в одном commit;
- commit message должен начинаться с ID задачи: `TASK-XXX: краткое описание`;
- запрещены commit messages без task prefix, включая `docs: ...`, `feat: ...`, `fix: ...`,
  `chore: ...`;
- перед/после commit используется `bb commit-lint`; для автоматической защиты включается hook
  через `bb install-hooks`;
- push в remote не является частью закрытия задачи.

## Commit hooks

Rocket предоставляет Git `commit-msg` hook в `.githooks/commit-msg`. Он проверяет, что первая
строка commit message соответствует формату:

```text
TASK-XXX: краткое описание
```

Установка hook path:

```bash
bb install-hooks
```

Ручная проверка последнего commit:

```bash
bb commit-lint
```

`bb commit-lint` проверяет не только формат, но и наличие `TASK-XXX` в
`03-execution/02-tasks/task-registry.edn`.

Проверка конкретного сообщения:

```bash
bb commit-lint --message "TASK-007: add commit message lint"
```

## Ручная процедура worktree для параллельной работы

Если одновременно работают несколько агентов:

1. из главного worktree на `dev` выполнить `git worktree add ../rocket-TASK-XXX -b work/<agent>/TASK-XXX dev`;
2. агент работает только в созданном worktree;
3. агент выполняет `bb work-claim --task TASK-XXX` внутри созданного worktree;
4. после `bb work-done --task TASK-XXX` агент делает commit в рабочей ветке;
5. человек или агент в пределах scope мержит ветку обратно в `dev` через `git merge --no-ff`;
6. временный worktree удаляется через `git worktree remove`.

Если merge конфликтует вне `owned_modules` задачи — stop condition.

## Push в remote

- Фреймворк **никогда** не пушит автоматически.
- Push, создание PR, публикация в GitHub — только по явной команде человека.
- Это класс действий `Escalate` (см. [autonomy-contract.md](autonomy-contract.md)).

## Граница

- Вся координация локальная: claim-локи — файлы на одном хосте. Distributed/multi-host safety
  не заявляется и не нужна в этой модели.
- Конфликт при merge в `dev` параллельной работы → stop condition, разрешает человек (либо
  агент, если конфликт тривиален и в пределах его owned_modules).
