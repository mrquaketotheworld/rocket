---
doc_type: standards
doc_id: GOV-DOC-CONTRACT
title: Контракт документов
status: active
owner: framework-core
last_updated: 2026-06-23
authority: canonical
layer: knowledge
---

# Контракт документов

## Назначение

Задаёт формальные требования к каждому markdown-документу в слоях знаний и шаблонов, чтобы их
можно было проверять автоматически (`bb docs-lint`).

## Обязательные поля frontmatter

Каждый markdown-документ в [01-standards/](.), [02-foundation/](../02-foundation) и
[04-templates/](../04-templates) должен объявлять во frontmatter:

- `doc_type` — тип документа
- `doc_id` — стабильный уникальный идентификатор
- `title` — человекочитаемое название
- `status` — состояние документа/артефакта. Для knowledge-документов: `active`, `draft`,
  `superseded`, `cancelled`; для ADR: `proposed`, `accepted`, `superseded`; для задач:
  `ready`, `in_progress`, `done`, `cancelled`
- `owner` — кто владеет документом
- `last_updated` — дата последнего обновления (`YYYY-MM-DD`)
- `authority` — `canonical` или `operational`
- `layer` — `knowledge`, `execution` или `template`

## Допустимые значения

### doc_type

`standards`, `product`, `project`, `feature`, `adr`, `state`, `task`, `session`, `template`.

### authority

- `canonical` — документ владеет правдой.
- `operational` — документ описывает текущее состояние, не владеет правдой.

### layer

- `knowledge` — слой знаний.
- `execution` — слой исполнения.
- `template` — шаблон.

## Стабильные идентификаторы

Префиксы `doc_id` по типам:

- `GOV-*` — стандарты фреймворка
- `PROD-*` — product truth
- `PROJ-*` — project truth
- `FEAT-*` — feature
- `ADR-*` — архитектурное решение
- `TASK-*` — задача
- `STATE-*`, `SESSION-*` — операционные снимки

Конкретный ID считается зарезервированным только когда существует **и** запись в реестре,
**и** файл. Упоминание ID в разговоре или планах его не резервирует. До создания артефакта
говори «следующий доступный TASK», а не конкретный номер.

## Связи

Документы исполнения, ссылающиеся на правду, обязаны указывать canonical `doc_id`, к которому
относятся. Это даёт трассируемость требование → решение → задача → код.

## Внутренние ссылки

Все относительные markdown-ссылки между документами должны вести на существующие файлы, а
якоря (`...#секция`) — на существующие заголовки. Битые ссылки и битые якоря считаются
дефектом и проверяются `bb docs-lint`. Внешние ссылки (`http`, `mailto`) не проверяются.
