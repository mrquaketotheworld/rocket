# Changelog

Все пользовательски значимые изменения Rocket фиксируются здесь.

Формат основан на `Unreleased` + версиях SemVer. Политика описана в
[versioning-policy.md](01-standards/versioning-policy.md).

## Unreleased

### Added

- Добавлена политика версионирования Rocket и правило ведения changelog.
- Добавлена feature `FEAT-STATE-LINT` и команда `bb state-lint` для проверки execution state.
- Добавлена feature `FEAT-PROJECT-INIT-EXPORT` для downstream init/export/reset lifecycle.
- Добавлены `TODO.md`, downstream minimal example, policies для document lifecycle/localization/doc IDs.
- Добавлены `bb project-init` и `bb scripts-test`.
- Расширен ADR template.

### Changed

- Git/worktree workflow уточнён: `work-done` не выполняет merge/worktree cleanup автоматически.
- Verification ladder core Rocket расширена командой `bb state-lint`.
- `docs-lint` усилен проверками execution markdown, индексов feature/ADR и соответствия `VERSION` ↔ README.

## v0.1.0 — 2026-06-23

### Added

- Инициализирован лёгкий фреймворк Rocket.
- Добавлены canonical standards, foundation, execution layer, шаблоны и лёгкая Babashka-автоматизация.
