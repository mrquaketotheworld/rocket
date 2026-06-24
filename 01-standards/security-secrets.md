---
doc_type: standards
doc_id: GOV-SECURITY-SECRETS
title: Security и secrets policy
status: active
owner: framework-core
last_updated: 2026-06-24
authority: canonical
layer: knowledge
---

# Security и secrets policy

## Назначение

Фиксирует безопасное обращение с секретами, auth и security-sensitive поверхностью в проектах
на Rocket.

## Правила

1. **Не коммитить secrets.** Ключи, токены, пароли, приватные сертификаты, cookies и реальные
   production-конфиги не должны попадать в Git.
2. **Шаблоны вместо значений.** Допустимы `.env.example`, примеры конфигов и placeholders без
   реальных секретов.
3. **Security-sensitive изменения — supervised/escalate.** Изменения auth, permissions,
   crypto, secrets handling, production access и live data требуют явного подтверждения
   человека.
4. **Обнаруженный secret — stop condition.** Агент останавливается, сообщает путь и тип
   найденного секрета без повторения значения, и ждёт решения человека.
5. **Не трогать live/production данные.** Любое действие против production/live окружения
   запрещено без явной команды и отдельного подтверждения.

## Документирование

Если downstream-проект использует secrets, его `engineering-profile.md` обязан описать:

- какие переменные окружения нужны;
- где лежит безопасный пример (`.env.example`);
- как запустить проект локально без production secrets;
- какие команды безопасны для агента.

## Граница

Эта policy задаёт минимум безопасности для фреймворка. Downstream-проект может ужесточать её,
но не должен ослаблять без явного ADR.
