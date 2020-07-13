![Platform](https://img.shields.io/badge/platform-Android-blue.svg) [![kotlin](https://img.shields.io/badge/kotlin-1.3.72-blue.svg)]()

interactor
==========

Kotlin library with several interactors to define domain use-cases.
These interactors are backed by Coroutines and Flow to ensure thead-safety.

The interactors contained in this package are described below:

| interactor | Description |
|-|-|
| Interactor | To be used for use-cases that do not need to produce a result but can have a status (Started/Success/Error).<br><br>This includes:<br> * Updating a local data source (database/file).<br> * Performing actions with a fire & forget mechanism. |  
| ResultInteractor | To be used for use-cases that require a result. |
| SubjectInteractor | To be used for use-cases that perform work in the background and can be observed separately, e.g. in the ViewModel. This could be used for querying a data source (database). |
| SuspendingWorkInteractor | To be used for use-cases that perform suspending work and return the result afterwards. |
