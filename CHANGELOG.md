# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

Types of changes:
- *Added* for new features
- *Changed* for changes in existing functionality
- *Deprecated* for soon-to-be removed features.
- *Removed* for now removed features.
- *Fixed* for any bug fixes.
- *Security* in case of vulnerabilities.

## [Unreleased]

## Updated
- Dependencies: SDK version 31
- Dependencies: Kotlin version 1.6.10 compatibility 
- Dependencies: Gradle Wrapper 7.4
- Dependencies: AGP to 7.1.1

## Fixed
- Breadcrumbs activity check on `FragmentActivity` instead of `AppComponentActivity`

## [0.3.0]
### Added
- Breadcrumbs class was added into the [Logging](./logging) module. Tracks the most important lifecycle events and posts them to Timber.

## [Released]
## [0.2.0] - 2020-07-14
### Added
- [Interactor](./interactor) module with use case examples. Includes a base Interactor, ResultInteractor, SubjectInteractor and SuspendingWorkInteractor.

## [0.1.0] - 2020-06-24
### Added
- Initial test release
