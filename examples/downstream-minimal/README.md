# Downstream Minimal Example

Минимальный пример downstream-проекта на Rocket. Показывает, какие документы должен заполнить
проект, чтобы агент мог восстановить контекст и начать работу.

## Стек примера

Пример intentionally stack-light: продукт представлен документацией и shell-командами-заглушками.
Реальный downstream заменяет команды в `02-foundation/02-project/engineering-profile.md` на свой
язык и инструменты.

## Проверки

Из корня примера должны проходить:

```bash
bb docs-lint
bb state-lint
```
