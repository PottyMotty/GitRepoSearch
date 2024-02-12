The following project is a native Android application built using Kotlin + Jetpack Compose

Used technologies:
- Retrofit + OkHttp: Manage Rest Api calls
- Moshi: Serialization
- Room: Local persistence storage
- Paging 3: Paging solution. The search results are paged and loaded automaticly as needed.
- Jetpack Compose: UI layer
- Design: The design is based on the material design language. On systems that support it Material You dynamic colors are used. Thanks to this dark mode support is also included.
- Voyager: Jetpack Compose navigation library
- Koin: DI
- Coil: Image loading

A freshly generated release .apk file can be found in the app/release folder

Potential upgrade options:
- For simplicities sake the app does not contain any kind of localization.
- Currently there is no mechanism for clearing old cached data from the local db. One could be added as a way to optimize disk space usage.

Demo videos:


https://github.com/PottyMotty/GitRepoSearch/assets/79474318/58b1bdf0-4c79-4824-b973-f77dee7b05c0


https://github.com/PottyMotty/GitRepoSearch/assets/79474318/f95e1c88-f961-4098-ab63-5fbbf08e2447




