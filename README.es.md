[![EN](https://img.shields.io/badge/EN-blue.svg?logo=googletranslate&logoColor=white)](#eng)
[![ES](https://img.shields.io/badge/ES-red.svg?logo=googletranslate&logoColor=white)](#es)


<a name="es"></a>
# ![Pokeball](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/ball/poke.png) PokeNest API: una API RESTfull de una mascota virtual

PokeNest es el proyecto final del bootcamp de Java & Springboot que he realizado en IT ACADEMY.
Este proyecto consiste en la creación de una API RESTfull en Spring boot orientado a la interacción con una mascota virtual basada en el universo Pokémon, 
donde los usuarios puede cuidar de su propio Pokémon.

## ![Egg](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/key-item/rule-book.png) Descripción del proyecto
En PokeNest, los usuarios pueden tener más de un Pokémon, eligiendo entre los iniciales de la primera generación y Eevee y sus evoluciones, 
como mascota virtual y realizar diversas actividades para cuidarlo y subir su nivel. El proyecto combina un backend realizado con Java en Spring Boot y 
un frontend realizado con REACT generado a través de ChatGPT, persistiendo en una base de datos en MYSQL,  creando una experiencia nostalgica que refleja las dinámicas 
de un tamagotchi, pero con el carisma y el universo de Pokémon.

## ![Egg](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/key-item/mystery-egg.png) Funcionalidades principales

**Autenticación y gestión de usuarios**:
- Middleware de autorización con JWT: Crea una cuenta de usuario con roles de USUARIO o ADMIN implementando autenticación segura para acceder a los diferentes endpoints según el rol.

**API RESTful**:
- Creación de mascota: Elige tu Pokémon entre varias opciones, uno de los tres iniciales de la primera generación o Eevee y sus evoluciones.
- Atributos de las mascotas: Cada pokémon tiene 3 barras de status que se verán afectadas según las interacciones que realice el usuario:
    - PH: Vida del pokémon.
    - EX: Experiencia necesaria para subir de nivel.
    - Felicidad: El grado de felicidad de cada pokémon.
- Interacciones con las mascotas: Podrás realizar 5 interacciones con las mascotas:
    - Alimentar: Recupera el PH.
    - Dormir: El pokémon descansa para recuperar PH y felicidad.
    - Jugar: Recupera felicidad.
    - Entrenar: Entrena tu pokémon para aumentar su experiencia y subir su nivel, incluso evolucionar.
    - Explorar: Explora el mundo para aumentar la experiencia.
    - Curar: Lleva tu pokémon a un centro pokémon para que recupere sus stats.
- Sistema de Evoluciones: Los pokémons iniciales de la primera generación evolucionan al llevar al nivel adecuado, mientras que Eevee podrás evolucionarlo cuando quieras, sin embargo, una vez evolucionado sus evoluciones no podrán hacerlo.
- Gestión: Decide si "Eliminas" tu mascota de tu equipo y déjalo en libertad.

**Base de datos relacional:**
- Estructura optimizada para almacenar información de usuarios y sus Pokémon.
- Persistencia de datos en MySQL.

**Frontend con REACT:**
- El proyecto final del bootcamp exigía la utilización de una IA para la realización del frontend. En este proyecto se ha generado el frontend de REACT mediante ChatGPT.

## ![Vs](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/key-item/vs-recorder.png) Tecnologías utilizadas

- Java 21 (Backend)
- Spring Boot
- Middleware de autorización con JWT
- REACT (Frontend)
- Node.js
- MySQL

## ![Trs](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/tr/fire.png) Contribución

Si deseas contribuir al proyecto, abre un issue o envía un pull request. Cualquier aporte que ayude a mejorar el codigo o escalabilidad del backend será bienvenido!
