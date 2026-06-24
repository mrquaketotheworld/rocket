---
doc_type: standards
doc_id: GOV-LANGUAGE-EXTENSION-CONTRACT
title: Контракт языковой расширяемости
status: active
owner: framework-core
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# Контракт языковой расширяемости

## Назначение

Rocket — фреймворк документации и агентного workflow, а не фреймворк одного языка. Этот
контракт фиксирует, как добавлять поддержку разных языков программирования, не ломая
документационный фундамент.

## Базовое правило

Canonical docs описывают продукт, поведение, команды и границы. Язык реализации — деталь
конкретного downstream-проекта и фиксируется в его `engineering-profile.md`.

## Что должен объявить downstream-проект

Для выбранного языка или нескольких языков `engineering-profile.md` обязан указать:

- runtime и версии;
- package manager;
- bootstrap-команду;
- run-команду;
- test-команду;
- build-команду;
- lint/format/typecheck, если применимо;
- расположение исходников и тестов;
- safe local environment и required env vars;
- verification ladder для агента.

## Добавление language pack

Если Rocket получает reusable поддержку языка, она оформляется как отдельный language pack или
шаблон, который содержит:

- пример `engineering-profile.md` для языка;
- рекомендуемые команды bootstrap/run/test/build;
- шаблоны feature/test conventions;
- дополнительные docs-lint или code-lint проверки, если нужны;
- границы: что pack предполагает и чего не делает.

Language pack не должен менять core rules Rocket и не должен навязываться всем проектам.

## Многоязычные проекты

Если downstream-проект использует несколько языков, `engineering-profile.md` должен объявить
verification ladder для каждого компонента и общий порядок интеграционной проверки.

## Stop condition

Если для выбранного языка не определены bootstrap/run/test/build или невозможно понять, как
восстановить проект, агент останавливает реализацию и оформляет canonical gap.
