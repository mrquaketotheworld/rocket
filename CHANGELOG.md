# Changelog

Все пользовательски значимые изменения Rocket фиксируются здесь.

Формат основан на `Unreleased` + версиях SemVer. Политика описана в
[versioning-policy.md](01-standards/versioning-policy.md).

## Unreleased

### Added

- Добавлена политика версионирования Rocket и правило ведения changelog.
- Добавлена feature `FEAT-STATE-LINT` и команда `bb state-lint` для проверки execution state.
- Добавлена feature `FEAT-PROJECT-INIT-EXPORT` для downstream init/export/reset lifecycle.

### Changed

- Git/worktree workflow уточнён: `work-done` не выполняет merge/worktree cleanup автоматически.
- Verification ladder core Rocket расширена командой `bb state-lint`.

## v0.1.0 — 2026-06-23

### Added

- Инициализирован лёгкий фреймворк Rocket.
- Добавлены canonical standards, foundation, execution layer, шаблоны и лёгкая Babashka-автоматизация.
