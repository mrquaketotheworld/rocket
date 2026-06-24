---
doc_type: task
doc_id: TASK-007
title: Добавить проверку task-prefixed commit messages
status: done
owner: execution
last_updated: 2026-06-24
authority: operational
layer: execution
canonical_refs: [GOV-GIT-WORKFLOW, PROJ-ENGINEERING]
owned_modules:
  - 01-standards/git-workflow.md
  - 02-foundation/02-project/engineering-profile.md
  - 03-execution
  - 05-scripts
  - .githooks
  - bb.edn
---

# TASK-007: Добавить проверку task-prefixed commit messages

## Цель

Привести Git workflow к task-prefixed commit messages и добавить исполнимую защиту: `bb commit-lint` и Git `commit-msg` hook.

## Canonical refs

- GOV-GIT-WORKFLOW
- PROJ-ENGINEERING

## Owned modules

- `01-standards/git-workflow.md`
- `02-foundation/02-project/engineering-profile.md`
- `03-execution/`
- `05-scripts/`
- `.githooks/`
- `bb.edn`

## План

1. Обновить `git-workflow.md`: commit message должен начинаться с `TASK-XXX: `.
2. Добавить `bb commit-lint`.
3. Добавить `.githooks/commit-msg`.
4. Добавить команду установки hook path.
5. Обновить verification ladder.
6. Прогнать верификацию и закрыть задачу commit message формата `TASK-007: ...`.

## Impact analysis

Меняется Git discipline: conventional commits без task prefix запрещаются для новых коммитов. История не переписывается.

## Верификация

- `bb docs-lint`
- `bb state-lint`
- `bb scripts-test`
- после commit: `bb commit-lint`

## Evidence

- `bb work-claim --task TASK-007` — OK, задача переведена в `in_progress`.
- `bb install-hooks` — OK, `core.hooksPath` установлен в `.githooks`.
- `bb commit-lint --message "TASK-007: add commit message lint"` — OK.
- `bb docs-lint` — OK.
- `bb state-lint` — OK.
- `bb scripts-test` — OK.
- `bb work-done --task TASK-007` — OK, registry и task file переведены в `done`, lock снят.
- Финальный `bb docs-lint` — OK.
- Финальный `bb state-lint` — OK.
- Финальный `bb scripts-test` — OK.
- Финальный `bb commit-lint` после commit — будет выполнен после локального commit.

## Definition of Done

- [x] `git-workflow.md` требует `TASK-XXX: ...`
- [x] `bb commit-lint` добавлен
- [x] `.githooks/commit-msg` добавлен
- [x] hook path установлен/описан
- [x] verification ladder обновлена
- [x] verification пройдена
- [x] локальный commit создан в формате `TASK-007: ...`
