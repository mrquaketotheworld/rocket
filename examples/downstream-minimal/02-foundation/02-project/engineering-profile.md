---
doc_type: project
doc_id: PROJ-ENGINEERING
title: Инженерный профиль
status: active
owner: project
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# Инженерный профиль

## Стек

- **Язык продукта:** не выбран; пример документационный.
- **Package manager:** N/A.
- **Runtime:** POSIX shell для демонстрационных команд.

## Команды

- **bootstrap:** `true` — зависимостей нет.
- **run:** `echo downstream-minimal`.
- **test:** `true`.
- **build:** `true` — артефакт сборки отсутствует.

## Лестница верификации

1. `bb docs-lint`
2. `bb state-lint`
3. `true`

## Граница reconstructability

Реальный downstream обязан заменить этот профиль на свой язык, runtime, package manager и
команды bootstrap/run/test/build.
