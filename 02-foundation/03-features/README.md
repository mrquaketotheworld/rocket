---
doc_type: feature
doc_id: FEAT-INDEX
title: Индекс функций
status: active
owner: product
last_updated: 2026-06-23
authority: canonical
layer: knowledge
---

# Функции

Каждая реализованная функция продукта описывается отдельным feature-документом: назначение,
сценарии, acceptance criteria, edge cases. Минимум один документ на каждую реализованную
функцию.

## Структура

Папка на функцию: `feat-<name>/README.md` (или один файл `feat-<name>.md` для простой функции).

## Функции

- [FEAT-DOCS-LINT](feat-docs-lint.md) — проверка документации `bb docs-lint`.
- [FEAT-WORK-CLAIM](feat-work-claim.md) — claim задачи `bb work-claim`.
- [FEAT-WORK-DONE](feat-work-done.md) — закрытие задачи `bb work-done`.
- [FEAT-ROCKET-SKELETON](feat-rocket-skeleton.md) — каркас проекта Rocket.

## Шаблон

Новый feature создаётся из [04-templates/feature.md](../../04-templates/feature.md) и
добавляется в этот индекс.
