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
generated through ChatGPT, persisting in a database in MYSQL, creating a nostalgic experience that reflects the dynamics of a "tamagotchi", but with the charisma and the Pokémon universe.

## ![Egg](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/key-item/mystery-egg.png) Main functionalities

**Authentication and user management:**
- Authorization middleware with JWT: Create a user account with USER or ADMIN roles implementing secure authentication to access different endpoints depending on the role.

**API RESTful:**
- Pet creation: Choose your Pokémon from several options, one of the three first generation starters or Eevee and its evolutions.
- Pet Attributes: Each pokémon has 3 status bars that will be affected according to the interactions made by the user:
    - PH: Life of the pokémon.
    - EX: Experience needed to level up.
    - Happiness: The degree of happiness of each pokémon.
- Interactions with pets: You will be able to perform 5 interactions with pets:
    - Feed: Recover PH.
    - Sleep: The pokémon rests to recover PH and happiness.
    - Play: Recover happiness.
    - Train: Train your pokémon to increase its experience and level up, even evolve.
    - Explore: Explore the world to increase experience.
    - Heal: Take your pokémon to a pokémon center to recover its stats.
- Evolution System: The initial pokémons of the first generation evolve when you take them to the appropriate level, while Eevee you will be able to evolve it whenever you want, however, once evolved its evolutions will not be able to do it.
- Management: Decide whether to “Remove” your pet from your team and release it.

**Relational database:**
- Optimized structure to store information of users and their Pokémon.
- MySQL data persistence.

**Frontend with REACT:**
- The final project of the bootcamp required the use of an AI for the frontend. In this project the REACT frontend was generated using ChatGPT.
You can see the code [here](https://github.com/ariamdev/PokeNest-Frontend).


## ![Vs](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/key-item/vs-recorder.png) Technologies

- Java 21 (Backend)
- Spring Boot
- Authorization middleware with JWT
- REACT (Frontend)
- Node.js
- MySQL

## ![Trs](https://raw.githubusercontent.com/msikma/pokesprite/master/items-outline/tr/fire.png) Contribution

If you want to contribute to the project, open an issue or send a pull request. Any contribution that helps to improve the code or scalability of the backend will be welcome!
