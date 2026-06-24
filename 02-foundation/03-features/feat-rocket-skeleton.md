---
doc_type: feature
doc_id: FEAT-ROCKET-SKELETON
title: Каркас проекта Rocket
status: active
owner: product
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# FEAT-ROCKET-SKELETON: Каркас проекта Rocket

## Назначение

Каркас Rocket даёт downstream-проекту документационный фундамент: структуру canonical truth,
execution state, шаблоны и минимальную локальную автоматизацию.

## Поведение

Новый проект на Rocket содержит:

- `AGENTS.md` — маршрутизация агента;
- `README.md` — обзор проекта;
- `01-standards/` — правила фреймворка;
- `02-foundation/` — правда конкретного проекта;
- `03-execution/` — operational state;
- `04-templates/` — шаблоны документов;
- `05-scripts/` — лёгкая автоматизация;
- `bb.edn` — команды Rocket.

Downstream-проект переписывает product/project truth под себя, но не ломает core standards без
явного ADR. Подробный init/export/reset lifecycle описан в
[FEAT-PROJECT-INIT-EXPORT](feat-project-init-export.md).

## Быстрый старт downstream-проекта

1. Заполнить `02-foundation/01-product/product-intent.md`.
2. Заполнить `02-foundation/01-product/product-scope.md`.
3. Переписать `02-foundation/02-project/engineering-profile.md` под выбранный язык/стек.
4. Указать bootstrap/run/test/build и verification ladder.
5. Добавить feature docs для первого поведения.
6. Установить обязательные инструменты pi.
7. Прогнать `bb docs-lint` и `bb state-lint`.
8. Завести и выполнить первую задачу.

## Acceptance criteria

- [ ] по docs понятно, где живёт canonical truth;
- [ ] по engineering profile можно восстановить проект;
- [ ] агент может найти текущую задачу и состояние;
- [ ] язык реализации задаётся проектом, а не core Rocket.

## Связанные решения

- ADR-0002
- ADR-0003
