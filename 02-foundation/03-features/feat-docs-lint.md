---
doc_type: feature
doc_id: FEAT-DOCS-LINT
title: Проверка документации docs-lint
status: active
owner: product
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# FEAT-DOCS-LINT: Проверка документации docs-lint

## Назначение

`bb docs-lint` проверяет, что canonical docs и шаблоны остаются согласованными и пригодными для
навигации.

## Поведение

Команда проверяет Markdown-файлы в `01-standards/`, `02-foundation/`, `04-templates/`, а также
внутренние ссылки из корневых `README.md`, `AGENTS.md`, `CLAUDE.md`.

Проверяются:

- наличие обязательного frontmatter в knowledge/template документах;
- допустимые значения `authority`, `layer`, `status`;
- формат `last_updated` для нешаблонных документов;
- уникальность `doc_id` для нешаблонных документов;
- существование относительных markdown-ссылок;
- существование markdown-якорей.

## Acceptance criteria

- [ ] при консистентной документации команда завершается с кодом 0;
- [ ] при ошибке команда печатает список файлов и причин;
- [ ] шаблоны с placeholder `XXXX` не создают конфликт `doc_id`;
- [ ] внешние ссылки не проверяются.

## Edge cases

- Корневые документы без frontmatter проверяются только на ссылки.
- Чисто якорные и внешние ссылки игнорируются.

## Связанные решения

- ADR-0002
- ADR-0003
