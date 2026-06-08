# Tabata Timer

A **Tabata interval training timer** built with **Compose Multiplatform** for **Android** and **iOS**. Configure work/rest intervals and rounds, run a guided session with audio cues, and review your workout summary, all from a shared codebase with a clean, Material 3 interface.

The app is structured so workout logic lives in a small domain layer (session coordinator + countdown timer), the UI reacts through a single action flow, and preferences persist across launches.

## Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/6cd20c53-8307-4720-bbff-6083c26ff6e9" width="24%" />
  <img src="https://github.com/user-attachments/assets/32015fc1-0040-41d6-b99c-d4e4b183ad13" width="24%" />
  <img src="https://github.com/user-attachments/assets/c0980a2b-7226-4d8a-ad81-14f7acac8734" width="24%" />
  <img src="https://github.com/user-attachments/assets/755bf85e-3f5f-4c13-bb2c-25593f837500" width="24%" />
</p>

## Features

- **Cross-platform** — Shared UI and logic on Android and iOS.
- **Quick presets** — Classic (20s/10s/8 rounds), Endurance (40s/20s/8 rounds), Light (20s/20s/6 rounds).
- **Custom setup** — Adjust work time, rest time, and rounds with steppers.
- **Live session** — Circular countdown, round progress, phase labels (Starting, Work, Rest), pause/resume, skip, and stop.
- **Audio cues** — Whistle sound effect at each interval transition (toggleable).
- **Workout summary** — Duration, rounds completed, and estimated calories after each session.
- **Persistence** — Last-used settings remembered across launches.
- **UX** — Screen stays on during sessions, 5-second starting countdown before first work interval.

## Tech stack & architecture

- **Language** — Kotlin
- **UI** — Compose Multiplatform (Material 3)
- **Architecture** — Layered: presentation (ViewModels, Compose) → domain (session coordinator, countdown timer, audio interfaces) → data (DataStore, timer impl, platform audio)
- **DI** — Koin
- **Persistence** — DataStore Preferences
- **Navigation** — Navigation Compose (type-safe serializable routes)
- **Platforms** — Android, iOS

## License

This project is **open source** and **free to use** under the **MIT License**. Attribution is appreciated.

See the [`LICENSE`](LICENSE) file for the full text.

## Acknowledgements

Built as a learning / portfolio project. If you fork or reuse parts of it, a link back to this repository is welcome.


