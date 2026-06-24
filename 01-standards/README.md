---
doc_type: standards
doc_id: GOV-STANDARDS-INDEX
title: Индекс стандартов
status: active
owner: framework-core
last_updated: 2026-06-23
authority: canonical
layer: knowledge
---

# Стандарты Rocket

Универсальные правила фреймворка. Применяются к любому проекту на Rocket.

## Документы

- [document-principles.md](document-principles.md) — иерархия доверия, SSoT, атомарность,
  reconstructability.
- [document-lifecycle.md](document-lifecycle.md) — статусы, supersede, удаление и архивация.
- [doc-contract.md](doc-contract.md) — обязательный frontmatter и стабильные ID.
- [sync-rules.md](sync-rules.md) — doc-first, impact analysis, защита от дрейфа.
- [foundation-contract.md](foundation-contract.md) — как устроен слой правды проекта.
- [execution-model.md](execution-model.md) — задачи, claim, жизненный цикл.
- [execution-state-contract.md](execution-state-contract.md) — формат registry/state/session/logs.
- [autonomy-contract.md](autonomy-contract.md) — границы автономии и stop conditions.
- [git-workflow.md](git-workflow.md) — локальная Git-модель, dev как рабочее состояние.
- [language-extension-contract.md](language-extension-contract.md) — как добавлять языки без изменения core.
- [localization-and-ids.md](localization-and-ids.md) — язык документации, имена файлов и `doc_id`.
- [memory-policy.md](memory-policy.md) — durable memory и роль Hermes.
- [security-secrets.md](security-secrets.md) — secrets, auth и security-sensitive границы.
- [versioning-policy.md](versioning-policy.md) — версии Rocket, changelog и миграции.

## Принцип

Эти документы — каноническая правда о том, **как** вести проект. Правда **про конкретный**
проект живёт в [02-foundation/](../02-foundation). Что делается сейчас — в
[03-execution/](../03-execution).
