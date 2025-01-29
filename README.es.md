[![EN](https://img.shields.io/badge/EN-blue.svg?logo=googletranslate&logoColor=white)](https://github.com/ariamdev/PokeNest-Backend/blob/main/README.md)
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

![Login](https://github.com/user-attachments/assets/290e10a0-e5e7-4e8f-ad3e-2c2a3ed5a6f9)

  <sub>* *Pagina de Login y registro.*</sub>

**API RESTful**:

- **Creación de mascota:** Elige tu Pokémon entre varias opciones, uno de los tres iniciales de la primera generación o Eevee y sus evoluciones.


![Create](https://github.com/user-attachments/assets/414907d0-42b0-47f0-a1ee-50c09fc269c0)

<sub>* *Menú User y creación de mascota.*</sub>
  
- **Atributos de las mascotas:** Cada pokémon tiene 3 barras de status que se verán afectadas según las interacciones que realice el usuario:
    - PH: Vida del pokémon.
    - EX: Experiencia necesaria para subir de nivel.
    - Felicidad: El grado de felicidad de cada pokémon.
      
- **Interacciones con las mascotas:** Podrás realizar 5 interacciones con las mascotas:
    - Alimentar: Recupera el PH.
    - Dormir: El pokémon descansa para recuperar PH y felicidad.
    - Jugar: Recupera felicidad.
    - Entrenar: Entrena tu pokémon para aumentar su experiencia y subir su nivel, incluso evolucionar.
    - Explorar: Explora el mundo para aumentar la experiencia.
    - Curar: Lleva tu pokémon a un centro pokémon para que recupere sus stats.
      
- **Sistema de Evoluciones:** Los pokémons iniciales de la primera generación evolucionan al llevar al nivel adecuado, mientras que Eevee podrás evolucionarlo cuando quieras, sin embargo, una vez evolucionado sus evoluciones no podrán hacerlo.
  
- **Gestión:** Decide si "Eliminas" tu mascota de tu equipo y déjalo en libertad.

![Interactions](https://github.com/user-attachments/assets/471c0691-7143-45fd-8eba-fea953ee82eb)

<sub>* *Interacciones con la mascota.*</sub>

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

## ![Trs](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/tr/fire.png) Configuración & Contribución

1. Haz un `Fork` del repositorio.


2. Clona el repositorio en local.

   ```bash
   git clone https://github.com/<USERNAME>/PokeNest-Backend.git
    ```

3. Navega al directorio del proyecto.

    ```bash
    cd PokeNest
    ```
4. Abre el proyecto
- En IntelliJ IDEA, haz clic en Archivo → Abrir y selecciona la carpeta del proyecto.
  El proyecto utiliza Maven (pom.xml), IDEA lo detectará y te pedirá que lo importes.
- Verifica el SDK de Java:
  Ve a Archivo → Estructura del proyecto → SDK del proyecto.
  Asegúrate de que está configurado con la versión correcta de Java, yo he utilizado **Java 21**.

5. Actualiza las dependencias:

El proyecto utiliza Maven, abre una terminal y ejecuta:
```bash
mvn clean install
 ```

6. Configura la base de datos de MySQL:
- Encontrarás el esquema sql en la carpeta resources → [BBD.sql](src%2Fmain%2Fresources%2FBBD.sql).
- No olvides configurar el `application.properties` con tu configuración de MySQL:
```bash
spring.datasource.url=jdbc:mysql: [//localhost:3306/pokeapi]
spring.datasource.username= [YOURUSERNAME]
spring.datasource.password= [YOURPASSWORD]
```

7. Configurar el `jwt-secret.txt`.

Para generar el JWT en el Servicio Jwt el código necesita leer el fichero «jwt-secret.txt» situado
en la carpeta resources. Por favor, rellénalo con tu clave secreta.
> :bangbang: **IMPORTANTE:** **nunca** compartas el contenido de este archivo para no comprometer la seguridad de la API. 

8. `Ejecutar` la API:

Ya tienes todo listo para ejecutar la API. 
```bash
mvn spring-boot:run
```

9. Para contribuir:

Haz una `Pull Request` con una descripción de tus cambios o añadidos.

Cualquier contribución que ayude a mejorar el código o la escalabilidad del proyecto será bienvenido!