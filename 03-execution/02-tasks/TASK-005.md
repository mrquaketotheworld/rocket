---
doc_type: task
doc_id: TASK-005
title: Реализовать оставшиеся TODO без language pack
status: done
owner: execution
last_updated: 2026-06-24
authority: operational
layer: execution
canonical_refs: [GOV-DOC-CONTRACT, GOV-EXECUTION-STATE-CONTRACT, GOV-VERSIONING-POLICY, FEAT-PROJECT-INIT-EXPORT]
owned_modules:
  - TODO.md
  - README.md
  - CHANGELOG.md
  - 01-standards
  - 02-foundation
  - 03-execution
  - 04-templates
  - 05-scripts
  - examples
  - bb.edn
---

# TASK-005: Реализовать оставшиеся TODO без language pack

## Цель

Записать в `TODO.md` и реализовать оставшиеся пункты аудита документации, кроме language pack.

## Canonical refs

- GOV-DOC-CONTRACT
- GOV-EXECUTION-STATE-CONTRACT
- GOV-VERSIONING-POLICY
- FEAT-PROJECT-INIT-EXPORT

## Owned modules

- `TODO.md`
- `README.md`
- `CHANGELOG.md`
- `01-standards/`
- `02-foundation/`
- `03-execution/`
- `04-templates/`
- `05-scripts/`
- `examples/`
- `bb.edn`

## План

1. Создать `TODO.md` со всеми пунктами кроме language pack.
2. Добавить downstream example.
3. Добавить fixtures/tests для скриптов.
4. Усилить docs-lint.
5. Добавить policy архивирования/удаления документов.
6. Расширить ADR template.
7. Добавить локализацию/doc-id conventions.
8. Добавить project init/export tooling.
9. Обновить индексы, changelog, state/session.
10. Прогнать верификацию и закоммитить.

## Impact analysis

Затрагиваются документационные контракты, templates, scripts и examples. Language pack явно исключён из scope.

## Верификация

- `bb docs-lint`
- `bb state-lint`
- `bb scripts-test`
- smoke: `bb project-init --target <tmp>`
- smoke: `bb work-claim --task TASK-005`
- smoke: `bb work-done --task TASK-005`

## Evidence

- `bb work-claim --task TASK-005` — OK, задача переведена в `in_progress`.
- `bb docs-lint` — OK.
- `bb state-lint` — OK.
- `bb scripts-test` — OK.
- `bb project-init --target <tmp>` проверен внутри `bb scripts-test`.
- `bb work-done --task TASK-005` — OK, registry и task file переведены в `done`, lock снят.
- Финальный `bb docs-lint` — OK.
- Финальный `bb state-lint` — OK.
- Финальный `bb scripts-test` — OK.

## Definition of Done

- [x] `TODO.md` создан и содержит все пункты кроме language pack
- [x] downstream example добавлен
- [x] fixtures/tests для скриптов добавлены
- [x] docs-lint усилен
- [x] policy архивирования/удаления документов добавлена
- [x] ADR template расширен
- [x] локализация/doc-id conventions добавлены
- [x] project init/export tooling добавлен
- [x] verification пройдена
- [x] состояние и сессия обновлены
- [x] локальный commit создан
