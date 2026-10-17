# 🍉 Tutti Frutti Online — Spring Boot + IA

🇪🇸 [Español](#-español) · 🇬🇧 [English](#-english)

---

# 🇪🇸 Español

Versión web del clásico juego **Tutti Frutti** para un jugador, desarrollada con **Java y Spring Boot**. El jugador configura la partida, completa las categorías con palabras que empiecen con la letra sorteada y un **juez de inteligencia artificial (OpenAI)** valida las respuestas y asigna el puntaje.

## Contexto

Proyecto académico grupal (obligatorio de **Diseño y Desarrollo de Aplicaciones** e **Ingeniería de Software**, 2.º semestre de 2025), desarrollado en **CEI** (Analista Programador) por un equipo de tres personas. El proyecto incluyó el análisis de requerimientos, el diseño, la implementación y la documentación completa del sistema.

**Estado del proyecto:** el **modo un jugador** está implementado de punta a punta. El **modo multijugador** (salas con código de invitación y comunicación en tiempo real con WebSockets) quedó diseñado y documentado, pero no implementado. El código ya incluye los modelos base (`JugadorHost`, `JugadorInvitado`) para extenderlo.

## Funcionalidades

- **Menú principal** con acceso al modo "Jugar solo".
- **Configuración de partida (lobby):** selección de categorías, cantidad de rondas, duración de la ronda y tiempo de gracia. La configuración se guarda y se reutiliza.
- **Partida:** sorteo aleatorio de la letra, contador de tiempo y tabla de categorías por completar.
- **Fin de ronda de tres formas:** botón **Tutti Frutti** (al completar todos los campos), **Rendirse**, o tiempo agotado seguido de un tiempo de gracia.
- **Juez con IA:** valida si cada palabra existe y pertenece a su categoría usando la API de OpenAI.
- **Pantalla de validación:** muestra cada respuesta con el juicio del juez y su puntaje, más el total de la ronda.
- **Resultados finales:** puntaje total de la partida y opción de volver al menú.
- **Catálogo de 24 categorías** guardado en base de datos (Nombre, País, Ciudad, Animal, Color, Comida, Película, etc.).

## Tecnologías

| Área | Herramientas |
|---|---|
| Lenguaje y framework | Java 17, Spring Boot 3.1.5, Spring MVC |
| Vistas | Thymeleaf, HTML, CSS, JavaScript |
| Datos | Spring Data JPA (Hibernate), MySQL |
| IA | API de OpenAI (modelo `gpt-4.1-mini`), consumida con Spring WebClient |
| Otros | Maven, Lombok |
| Herramientas | Git y GitHub, NetBeans, XAMPP / phpMyAdmin, draw.io |

## Diseño del juez con IA

- **Una sola llamada por ronda:** todas las palabras se envían juntas en un único prompt, y la IA responde con un JSON estructurado.
- **Caché en memoria (`IACache`):** si una palabra ya fue validada para una categoría, no se vuelve a consultar.
- **Interfaz `IJuez`:** el sistema de evaluación es intercambiable. Además de `JuezIA`, existe `JuezClasico`, que puntúa sin IA.
- **Puntaje configurable:** 100 puntos por palabra válida única y 50 por palabra válida duplicada (valores configurables en `application.properties`). El criterio de duplicadas queda listo para el modo multijugador.
- **Tolerancia a fallos:** si la IA no responde, la ronda informa que no se pudo validar en lugar de cerrarse con error.

## Mi aporte

- **[Integré la API de OpenAI para validar las respuestas con IA]**
- **[Desarrollé ...]**
- **[Diseñé ...]**

## Cómo ejecutarlo

**Requisitos:** Java 17, Maven, MySQL (por ejemplo con XAMPP) y una clave de API de OpenAI.

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/EliasFernandezz/obligatorioDDA-EFCSMF.git
   ```
2. Iniciar MySQL (en XAMPP, el módulo **MySQL**), crear una base de datos llamada `obligatoriodda` e importar el script `database/obligatoriodda.sql`, que carga las categorías.
3. Revisar el usuario y la contraseña de MySQL en `src/main/resources/application.properties`. Por defecto usa `root` sin contraseña.
4. Configurar tu clave de OpenAI (ver más abajo).
5. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```
   También se puede abrir y ejecutar desde NetBeans.
6. Abrir [http://localhost:8080](http://localhost:8080) en el navegador.

### Clave de API

La app necesita tu propia clave de [OpenAI](https://platform.openai.com/api-keys). Podés configurarla de dos formas:

- En `src/main/resources/application.properties`, en la propiedad `openai.api.key`.
- Como variable de entorno `OPENAI_API_KEY`.

> Reemplazá el valor por tu propia clave de API.

## Estructura del proyecto

```
src/main/
├── java/com/obligatorio/DDA/
│   ├── controller/       # Menú, lobby, partida, validación de ronda y resultados
│   ├── models/           # Entidades y modelos del juego (Lobby, Partida, Categoria, etc.)
│   ├── Services/         # Juez (IA y clásico), cliente de OpenAI, caché y lógica de lobby
│   └── repositoriosJPA/  # Acceso a datos con Spring Data JPA
└── resources/
    ├── templates/        # Vistas Thymeleaf
    ├── static/           # CSS y JavaScript
    └── application.properties
```

## Documentación

El proyecto cuenta con documentación completa: especificación de requerimientos (17 requerimientos funcionales), diagrama de clases, diagramas de casos de uso y de actividad, plan de pruebas, manual de instalación y manual de usuario. [Documentacion](https://drive.google.com/drive/folders/1JKFf8ibBwPaDHqMkN1ZoLtiMnoHAXRMo?usp=sharing)

## Desafíos y aprendizajes

- Integrar la IA como juez de las respuestas, el principal desafío técnico del proyecto.
- Reducir las llamadas a la API con un único prompt por ronda y una caché de respuestas.
- Diseñar una arquitectura modular, con el sistema de evaluación detrás de una interfaz, pensada para extenderla al modo multijugador.
- Coordinar el trabajo en equipo siguiendo un proceso de ingeniería de software completo, desde los requerimientos hasta las pruebas.

## Mejoras futuras

- Implementar el modo multijugador con salas, código de invitación y WebSockets.
- Persistir la caché de validaciones en la base de datos.
- Mover la clave de API fuera del repositorio.
- Ampliar las pruebas automatizadas.

## Equipo

- **Elías Fernández** — [GitHub](https://github.com/EliasFernandezz)
- **Catherine Sonderegger** — [GitHub](https://github.com/CathGirL1)
- **Mateo Faccio** — [GitHub](https://github.com/FaccioMateo?tab=overview&from=2026-10-01&to=2026-10-17)

---

# 🇬🇧 English

A web version of the classic **Tutti Frutti** word game for one player, built with **Java and Spring Boot**. The player configures the game, fills in categories with words starting with a random letter, and an **AI judge (OpenAI)** validates the answers and assigns the score.

## Context

Group academic project (**Application Design and Development** and **Software Engineering** assignment, 2nd semester of 2025), developed at **CEI** (Programmer Analyst program) by a team of three. The project covered requirements analysis, design, implementation and full documentation of the system.

**Project status:** the **single-player mode** is fully implemented. The **multiplayer mode** (rooms with invitation codes and real-time communication over WebSockets) was designed and documented but not implemented. The code already includes base models (`JugadorHost`, `JugadorInvitado`) to extend it.

## Features

- **Main menu** with access to the "Play solo" mode.
- **Game setup (lobby):** choose categories, number of rounds, round duration and grace time. The configuration is saved and reused.
- **Gameplay:** random letter draw, countdown timer and a table of categories to fill in.
- **Three ways to end a round:** the **Tutti Frutti** button (once all fields are filled), **Give up**, or time running out followed by a grace period.
- **AI judge:** checks whether each word exists and belongs to its category using the OpenAI API.
- **Validation screen:** shows each answer with the judge's verdict and score, plus the round total.
- **Final results:** total game score and an option to return to the menu.
- **Catalog of 24 categories** stored in the database (Name, Country, City, Animal, Color, Food, Movie, etc.).

## Tech Stack

| Area | Tools |
|---|---|
| Language and framework | Java 17, Spring Boot 3.1.5, Spring MVC |
| Views | Thymeleaf, HTML, CSS, JavaScript |
| Data | Spring Data JPA (Hibernate), MySQL |
| AI | OpenAI API (`gpt-4.1-mini` model), consumed with Spring WebClient |
| Other | Maven, Lombok |
| Tooling | Git and GitHub, NetBeans, XAMPP / phpMyAdmin, draw.io |

## AI Judge Design

- **One call per round:** all words are sent together in a single prompt, and the AI replies with a structured JSON.
- **In-memory cache (`IACache`):** if a word has already been validated for a category, it is not queried again.
- **`IJuez` interface:** the evaluation system is interchangeable. Besides `JuezIA`, there is `JuezClasico`, which scores without AI.
- **Configurable scoring:** 100 points for a valid unique word and 50 for a valid duplicate (values configurable in `application.properties`). The duplicate rule is ready for the multiplayer mode.
- **Fault tolerance:** if the AI does not respond, the round reports that validation failed instead of crashing.

## My Contribution

- **[Integrated the OpenAI API to validate answers with AI]**
- **[Built ...]**
- **[Designed ...]**

## Getting Started

**Requirements:** Java 17, Maven, MySQL (for example via XAMPP) and an OpenAI API key.

1. Clone the repository:
   ```bash
   git clone https://github.com/EliasFernandezz/obligatorioDDA-EFCSMF.git
   ```
2. Start MySQL (in XAMPP, the **MySQL** module), create a database named `obligatoriodda` and import the `database/obligatoriodda.sql` script, which loads the categories.
3. Check the MySQL user and password in `src/main/resources/application.properties`. By default it uses `root` with no password.
4. Set up your OpenAI key (see below).
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   You can also open and run it from NetBeans.
6. Open [http://localhost:8080](http://localhost:8080) in your browser.

### API Key

The app needs your own [OpenAI](https://platform.openai.com/api-keys) key. You can set it in two ways:

- In `src/main/resources/application.properties`, in the `openai.api.key` property.
- As the `OPENAI_API_KEY` environment variable.

> Replace the value with your own API key.

## Project Structure

```
src/main/
├── java/com/obligatorio/DDA/
│   ├── controller/       # Menu, lobby, game, round validation and results
│   ├── models/           # Game entities and models (Lobby, Partida, Categoria, etc.)
│   ├── Services/         # Judges (AI and classic), OpenAI client, cache and lobby logic
│   └── repositoriosJPA/  # Data access with Spring Data JPA
└── resources/
    ├── templates/        # Thymeleaf views
    ├── static/           # CSS and JavaScript
    └── application.properties
```

## Documentation

The project has full documentation: requirements specification (17 functional requirements), class diagram, use case and activity diagrams, test plan, installation manual and user manual. [Documentation](https://drive.google.com/drive/folders/1JKFf8ibBwPaDHqMkN1ZoLtiMnoHAXRMo?usp=sharing)

## Challenges & Learnings

- Integrating AI as the judge of the answers, the project's main technical challenge.
- Reducing API calls with a single prompt per round and a response cache.
- Designing a modular architecture, with the evaluation system behind an interface, ready to be extended to multiplayer.
- Coordinating teamwork through a full software engineering process, from requirements to testing.

## Future Improvements

- Implement the multiplayer mode with rooms, invitation codes and WebSockets.
- Persist the validation cache in the database.
- Move the API key out of the repository.
- Expand automated testing.

## Team

- **Elías Fernández** — [GitHub](https://github.com/EliasFernandezz)
- **Catherine Sonderegger** — [GitHub](https://github.com/CathGirL1)
- **Mateo Faccio** — [GitHub](https://github.com/FaccioMateo?tab=overview&from=2026-10-01&to=2026-10-17)
