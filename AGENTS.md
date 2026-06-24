# Маршрутизация агента

Этот файл задаёт агенту порядок чтения и правила работы в проекте на Rocket.

## Главные принципы

- **Каноническая правда побеждает.** [01-standards/](01-standards) и [02-foundation/](02-foundation)
  — правда. [03-execution/](03-execution) — только операционное состояние. Код реализует
  правду, но не переопределяет её. При конфликте прав документ, код догоняет.
- **Doc-first.** Перед изменением поведения сначала обнови или подтверди canonical docs,
  затем меняй код.
- **Reconstructability.** По текущим docs должно быть возможно восстановить и запустить
  рабочий проект. Если run/test/build команды не определены или feature docs противоречат
  друг другу — остановись и заведи canonical gap, не выдумывай.
- **Один оператор, локальный Git.** Все ветки локальные. Никогда не пушь в remote без явной
  команды человека.
- **Стек любой.** Язык продукта зафиксирован в `engineering-profile.md`. Не навязывай Clojure
  или другой стек — он используется только внутри самого фреймворка, если нужно.
- **Обязательные инструменты pi.** До начала работы убедись, что установлены:
  pi extension `pi-hermes-memory` через `pi install npm:pi-hermes-memory` и pi skill
  `codespaces` через `npx skills add diskd-ai/codespaces` (глобально или в проекте).
  Подробности — в [engineering-profile.md](02-foundation/02-project/engineering-profile.md).

## Порядок чтения (short path)

Для первого контакта и первой полезной задачи достаточно:

1. [README.md](README.md)
2. правила фреймворка:
   - [01-standards/document-principles.md](01-standards/document-principles.md)
   - [01-standards/sync-rules.md](01-standards/sync-rules.md)
   - [01-standards/autonomy-contract.md](01-standards/autonomy-contract.md)
3. правда проекта:
   - [02-foundation/02-project/engineering-profile.md](02-foundation/02-project/engineering-profile.md)
   - [02-foundation/02-project/glossary.md](02-foundation/02-project/glossary.md)
4. релевантный feature в [02-foundation/03-features/](02-foundation/03-features) и
   релевантный ADR в [02-foundation/04-decisions/](02-foundation/04-decisions), если задача их касается
5. текущее состояние:
   - [03-execution/01-state/current-state.edn](03-execution/01-state/current-state.edn)
   - [03-execution/02-tasks/task-registry.edn](03-execution/02-tasks/task-registry.edn)
   - соответствующий файл задачи в [03-execution/02-tasks/](03-execution/02-tasks)
   - [03-execution/03-session/current-session.md](03-execution/03-session/current-session.md)

Этого достаточно, чтобы взять одну задачу. Глубже читать только если short path не покрывает вопрос.

## Полный порядок чтения (deep read)

Нужен для аудита, разрешения конфликтов и сложных задач:

1. [README.md](README.md)
2. [01-standards/document-principles.md](01-standards/document-principles.md)
3. [01-standards/doc-contract.md](01-standards/doc-contract.md)
4. [01-standards/sync-rules.md](01-standards/sync-rules.md)
5. [01-standards/foundation-contract.md](01-standards/foundation-contract.md)
6. [01-standards/execution-model.md](01-standards/execution-model.md)
7. [01-standards/autonomy-contract.md](01-standards/autonomy-contract.md)
8. [01-standards/git-workflow.md](01-standards/git-workflow.md)
9. [02-foundation/01-product/product-intent.md](02-foundation/01-product/product-intent.md)
10. [02-foundation/01-product/product-scope.md](02-foundation/01-product/product-scope.md)
11. [02-foundation/02-project/engineering-profile.md](02-foundation/02-project/engineering-profile.md)
12. [02-foundation/02-project/glossary.md](02-foundation/02-project/glossary.md)
13. [02-foundation/03-features/](02-foundation/03-features) — релевантные features
14. [02-foundation/04-decisions/](02-foundation/04-decisions) — релевантные ADR
15. [03-execution/01-state/current-state.edn](03-execution/01-state/current-state.edn)
16. [03-execution/02-tasks/task-registry.edn](03-execution/02-tasks/task-registry.edn)
17. соответствующий файл задачи
18. [03-execution/03-session/current-session.md](03-execution/03-session/current-session.md)

## Правила маршрутизации

- Считать [01-standards/](01-standards) и [02-foundation/](02-foundation) канонической правдой.
- Считать [03-execution/](03-execution) только операционным слоем.
- При конфликте execution и canonical — побеждает canonical.
- При конфликте код и canonical — побеждает canonical, код обновляется.
- Перед изменением поведения сначала обнови/подтверди canonical docs, затем код.
- Перед закрытием задачи обнови состояние и статус верификации.

## Правила записи

- Новое поведение продукта — в [02-foundation/01-product/](02-foundation/01-product),
  [02-foundation/02-project/](02-foundation/02-project) или [02-foundation/03-features/](02-foundation/03-features).
- Новые архитектурные решения — в [02-foundation/04-decisions/](02-foundation/04-decisions).
- Прогресс задач, заметки сессий, история — в [03-execution/](03-execution).
- Шаблоны — в [04-templates/](04-templates).

## Минимальный цикл агента

1. Прочитать канонический контекст (short path).
2. Если изменение меняет поведение — сначала обновить/подтвердить canonical docs.
3. Прочитать активное состояние исполнения.
4. Выбрать задачу из реестра и заклеймить её.
5. Реализовать изменение в пределах scope задачи.
6. Прогнать объявленную лестницу верификации проекта.
7. Обновить состояние, файл задачи и журнал сессии.

## Правило эскалации

Если работу нельзя привязать к каноническому документу — остановись и предложи недостающий
документ, не выдумывай поведение.

Если по текущей документации нельзя восстановить и запустить проект, или есть противоречие
между feature docs, product scope и кодом — останови реализацию и оформи canonical gap.

Универсальные stop conditions (см. [autonomy-contract.md](01-standards/autonomy-contract.md))
действуют всегда: отсутствующая правда, конфликт правды, необратимое действие, внешний
блокер, провал верификации без авто-фикса, блокирующий открытый вопрос, расширение scope.
