---
doc_type: feature
doc_id: FEAT-PROJECT-INIT-EXPORT
title: Инициализация и экспорт downstream-проекта
status: active
owner: product
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# FEAT-PROJECT-INIT-EXPORT: Инициализация и экспорт downstream-проекта

## Назначение

Rocket должен быть переносимым каркасом. Downstream-проект получает фундамент docs/workflow, но
не должен наследовать историю задач разработки самого Rocket как свой operational state.

## Поведение

При создании downstream-проекта из Rocket переносятся:

- `01-standards/`;
- `04-templates/`;
- `05-scripts/`;
- `bb.edn`;
- корневые инструкции `README.md`, `AGENTS.md`, `CLAUDE.md` как стартовые шаблоны;
- структура `02-foundation/` как заготовка для правды проекта;
- структура `03-execution/` как пустой operational layer.

Downstream обязан переписать:

- `02-foundation/01-product/product-intent.md`;
- `02-foundation/01-product/product-scope.md`;
- `02-foundation/02-project/engineering-profile.md`;
- `02-foundation/02-project/glossary.md` при появлении терминов;
- feature docs под своё поведение.

Downstream не наследует:

- `03-execution/02-tasks/TASK-*` истории core Rocket;
- заполненный `task-registry.edn` core Rocket;
- `current-session.md` core Rocket;
- локальные locks и `.rocket-agent-id`.

## Reset operational state

Для нового downstream-проекта execution layer сбрасывается:

- `task-registry.edn`: `:next_id 1`, `:tasks []`;
- `current-state.edn`: `:active_tasks []`, `:blockers []`, `:health "green"`;
- `current-session.md`: первый фокус — заполнить foundation и создать первую задачу;
- `03-execution/04-logs/` пустой;
- `03-execution/05-locks/` содержит только `.gitkeep`.

## Tooling

`bb project-init --target PATH` создаёт downstream skeleton в пустой директории:

- копирует core standards/templates/scripts;
- исключает `.git`, locks, `.rocket-agent-id` и историю `TASK-*` core Rocket;
- сбрасывает execution layer под новый проект;
- сохраняет framework version в `current-state.edn`.

Команда не переписывает непустой target.

## Acceptance criteria

- [ ] downstream-проект получает standards/templates/scripts Rocket;
- [ ] downstream product truth не копирует смысл core Rocket;
- [ ] operational history core Rocket не переносится как история downstream;
- [ ] после reset проходят `bb docs-lint` и `bb state-lint`;
- [ ] `bb project-init --target PATH` создаёт skeleton только в пустом target.

## Связанные решения

- ADR-0002
- ADR-0003
