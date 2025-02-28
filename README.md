[![EN](https://img.shields.io/badge/EN-blue.svg?logo=googletranslate&logoColor=white)](#eng)
[![ES](https://img.shields.io/badge/ES-red.svg?logo=googletranslate&logoColor=white)](https://github.com/ariamdev/PokeNest-Backend/blob/main/README.es.md)


<a name=“eng”></a>
# ![Pokeball](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/ball/poke.png) PokeNest API: a RESTfull API of a virtual pet

PokeNest is the final project of the Java & Springboot bootcamp that I have done at IT ACADEMY.
This project consists in the creation of a RESTfull API in Spring boot oriented to the interaction with a virtual pet based on the Pokémon universe, 
where users can take care of their own Pokémon.

## ![Egg](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/key-item/rule-book.png) Description of the project
In PokeNest, users can have more than one Pokémon, choosing between the first generation starters and Eevee and its evolutions, 
as a virtual pet and perform various activities to take care of it and level it up. The project combines a backend made with Java in Spring Boot and a frontend made with REACT 
generated through ChatGPT, persisting in a database in MYSQL, creating a nostalgic experience that reflects the dynamics of a "tamagotchi", but with the charisma of the Pokémon universe.

## ![Egg](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/key-item/mystery-egg.png) Main functionalities

**Authentication and user management:**
- Authorization middleware with JWT: Create a user account with USER or ADMIN roles implementing secure authentication to access different endpoints depending on the role.

![Login](https://github.com/user-attachments/assets/290e10a0-e5e7-4e8f-ad3e-2c2a3ed5a6f9)

<sub>* *Login and Register page.*</sub>


**API RESTful:**
- **Pet creation:** Choose your Pokémon from several options, one of the three first generation starters or Eevee and its evolutions.
  

![Create](https://github.com/user-attachments/assets/414907d0-42b0-47f0-a1ee-50c09fc269c0)

<sub>* *User Menu and pet creation demostration.*</sub>


- **Pet Attributes:** Each pokémon has 3 status bars that will be affected according to the interactions made by the user:
    - PH: Life of the pokémon.
    - EX: Experience needed to level up.
    - Happiness: The degree of happiness of each pokémon.
      
- **Interactions with pets:** You will be able to perform 5 interactions with pets:
    - Feed: Recover PH.
    - Sleep: The pokémon rests to recover PH and happiness.
    - Play: Recover happiness.
    - Train: Train your pokémon to increase its experience and level up, even evolve.
    - Explore: Explore the world to increase experience.
    - Heal: Take your pokémon to a pokémon center to recover its stats.
      
- **Evolution System:** The initial pokémons of the first generation evolve when you take them to the appropriate level, while Eevee you will be able to evolve it whenever you want, however, once evolved its evolutions will not be able to do it.
  
- **Management:** Decide whether to “Remove” your pet from your team and release it.

![Interactions](https://github.com/user-attachments/assets/471c0691-7143-45fd-8eba-fea953ee82eb)

<sub>* *Interaction options.*</sub>

**Relational database:**
- Optimized structure to store information of users and their Pokémon.
- MySQL data persistence.
> :file_folder: You can find the sql schema [here](src%2Fmain%2Fresources%2FBBD.sql). 

**Frontend with REACT:**
- The final project of the bootcamp required the use of an AI for the frontend. In this project the REACT frontend was generated using ChatGPT.
> :file_folder: You can see the code [here](https://github.com/ariamdev/PokeNest-Frontend).


## ![Vs](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/key-item/vs-recorder.png) Technologies

- Java 21 (Backend)
- Spring Boot
- Spring Security
- Authorization middleware with JWT
- REACT (Frontend)
- Node.js
- MySQL

## ![Trs](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/tr/fire.png) Configuration & Contribution 

1. Do a `Fork` of the repository.


2. Clone the repository on your local machine.

    ```bash 
    git clone https://github.com/<USERNAME>/PokeNest-Backend.git
    ```

3. Navigate to the project directory.

    ```bash
    cd PokeNest
    ```
4. Open the project
- In IntelliJ IDEA, click File → Open and select the project folder.
The project uses Maven (pom.xml), IDEA will detect it and prompt you to import it.
- Verify the Java SDK:
Go to File → Project Structure → Project SDK.
Make sure it is configured with the correct Java version I used Java 21.

5. Update dependencies:

The project uses Maven, open a terminal and run:
```bash
mvn clean install
 ```

6. Configure the database of MySQL:
- You will find the sql schema in resources → [BBD.sql](src%2Fmain%2Fresources%2FBBD.sql)
- Don't forget to configure the `application.properties` with your MySQL configuration:
```bash
spring.datasource.url=jdbc:mysql: [//localhost:3306/pokeapi]
spring.datasource.username= [YOURUSERNAME]
spring.datasource.password= [YOURPASSWORD]
```
7. Configure the `jwt-secret.txt`

In order to generate the JWT at Jwt Service the code needs to read the file "jwt-secret.txt" located
in the folder resources. Please, filled it with your secret key.
> :bangbang: **IMPORTANT:** **never** share the content of this file to avoid compromising the security of the API. 

8. `Run` the API:

Now you have everything set up to run the API! 
```bash
mvn spring-boot:run
```

9. To contribute: 

Make a `Pull Request` with a description of your changes or additions. 

Any contribution that helps to improve the code or scalability of the project will be welcome!
