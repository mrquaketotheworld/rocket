---
doc_type: adr
doc_id: ADR-INDEX
title: Индекс архитектурных решений
status: active
owner: architecture
last_updated: 2026-06-23
authority: canonical
layer: knowledge
---

# Архитектурные решения (ADR)

Каждое нетривиальное архитектурное решение фиксируется отдельным ADR: контекст, решение,
последствия, границы.

## Решения

- [ADR-0001](ADR-0001-local-git-single-operator.md) — локальный Git и модель одного оператора.
- [ADR-0002](ADR-0002-docs-as-foundation.md) — документация как фундамент и языконезависимое ядро.
- [ADR-0003](ADR-0003-markdown-edn-babashka.md) — Markdown, EDN и Babashka для лёгкой автоматизации.

## Шаблон

Новый ADR создаётся из [04-templates/adr.md](../../04-templates/adr.md) и добавляется в этот
индекс тем же изменением.
